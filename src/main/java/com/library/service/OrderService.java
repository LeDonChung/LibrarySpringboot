package com.library.service;


import com.library.dto.OrderDto;
import com.library.dto.UserDto;
import com.library.model.CartItemModel;
import com.library.model.ShoppingCartModel;

import java.util.List;
import java.util.Set;

public interface OrderService {
    // Customer
    List<OrderDto> findByCustomerId(Long id);
    OrderDto findById(Long id);
    OrderDto updateStatus(Long orderId, String status);
    OrderDto saveOrder(ShoppingCartModel cart, Set<CartItemModel> cartItems, UserDto userDto);

}
