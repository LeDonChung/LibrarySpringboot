package com.library.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.library.entity.OrderDetail;
import com.library.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;

    private Date orderDate;

    private String address;

    private String phoneNumber;

    private String orderStatus;

    private String notes;

    private User user;

    @JsonIgnore
    private List<OrderDetail> orderDetails;
}
