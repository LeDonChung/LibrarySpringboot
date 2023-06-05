package com.library.service.impl;

import com.library.dto.OrderDto;
import com.library.dto.UserDto;
import com.library.entity.Book;
import com.library.entity.Order;
import com.library.entity.OrderDetail;
import com.library.mapper.OrderMapper;
import com.library.model.CartItemModel;
import com.library.model.ShoppingCartModel;
import com.library.repository.BookRepository;
import com.library.repository.OrderDetailRepository;
import com.library.repository.OrderRepository;
import com.library.repository.UserRepository;
import com.library.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderMapper orderMapper;


    @Override
    public List<OrderDto> findByCustomerId(Long id) {
        List<OrderDto> dtos = new ArrayList<>();
        List<Order> entities = orderRepository.findByUserId(id);
        for (Order entity: entities) {
            dtos.add(orderMapper.toDto(entity));
        }
        return dtos;
    }

    @Override
    public OrderDto findById(Long id) {
        return orderMapper.toDto(orderRepository.findById(id).get());
    }

    @Override
    public OrderDto updateStatus(Long orderId, String status) {
        Order orderEntity = orderRepository.findById(orderId).get();
        for (OrderDetail detail: orderEntity.getOrderDetails()) {
            Book book = detail.getBook();
            book.setNumberPay(book.getNumberPay() - detail.getQuantity());
            bookRepository.save(book);
        }
        orderEntity.setOrderStatus(status);
        return orderMapper.toDto(orderRepository.save(orderEntity));
    }

    @Override
    public OrderDto saveOrder(ShoppingCartModel cart, Set<CartItemModel> cartItems, UserDto userDto) {
        Order entity = new Order();
        entity.setOrderDate(new Date());
        entity.setAddress(cart.getAddress());
        entity.setPhoneNumber(cart.getPhoneNumber());
        entity.setOrderStatus("PENDING");
        entity.setNotes(cart.getNotes());
        if(userDto != null) {
            entity.setUser(userRepository.findByUsername(userDto.getUsername()));
        }
        List<OrderDetail> orders = new ArrayList<>();
        if(cartItems != null) {
            for (CartItemModel cartItem: cartItems) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setQuantity(cartItem.getQuantity());
                orderDetail.setOrder(entity);

                Book book = bookRepository.findById(cartItem.getBookDto().getId()).get();
                book.setNumberPay(book.getNumberPay() + cartItem.getQuantity());
                bookRepository.save(book);


                orderDetail.setBook(book);
                orderDetailRepository.save(orderDetail);
                orders.add(orderDetail);
            }
        }
        entity.setOrderDetails(orders);
        entity = orderRepository.save(entity);
        return orderMapper.toDto(entity);
    }
}
