package com.library.model;

import com.library.dto.BookDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemModel {
    private Long id;

    private int quantity;

    private BookDto bookDto;
}
