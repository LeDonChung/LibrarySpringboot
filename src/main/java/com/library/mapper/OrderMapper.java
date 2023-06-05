package com.library.mapper;

import com.library.dto.OrderDto;
import com.library.entity.Order;
import org.springframework.stereotype.Component;


@Component
public class OrderMapper {
    public OrderDto toDto(Order entity) {
        OrderDto dto = new OrderDto();
        dto.setId(entity.getId());
        dto.setOrderDate(entity.getOrderDate());
        dto.setAddress(entity.getAddress());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setOrderStatus(entity.getOrderStatus());
        dto.setNotes(entity.getNotes());
        if(entity.getUser() != null) {
            dto.setUser(entity.getUser());
        }
        if(entity.getOrderDetails() != null) {
            dto.setOrderDetails(entity.getOrderDetails());

        }
        return dto;
    }

    public Order toEntity(OrderDto dto) {
        Order entity = new Order();
        entity.setOrderDate(dto.getOrderDate());
        entity.setAddress(dto.getAddress());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setOrderStatus(dto.getOrderStatus());
        entity.setNotes(dto.getNotes());
        if(dto.getUser() != null) {
            entity.setUser(dto.getUser());
        }
        if(dto.getOrderDetails() != null) {
            entity.setOrderDetails(dto.getOrderDetails());
        }
        return entity;
    }
}
