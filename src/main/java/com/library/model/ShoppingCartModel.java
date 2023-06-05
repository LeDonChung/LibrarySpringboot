package com.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartModel {

    private Long id;
    private String address;
    private String notes;
    private String phoneNumber;
    private int totalItem;
}
