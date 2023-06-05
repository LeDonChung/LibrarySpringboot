package com.library.model;

import com.library.dto.BookDto;
import com.library.utils.SystemConstants;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CartSession {
    public boolean addItemToCart(HttpSession session,
                                           BookDto bookDto,
                                           int quantity) {
        ShoppingCartModel cart = (ShoppingCartModel) session.getAttribute(SystemConstants.SHOPPING_CART);
        Set<CartItemModel> cartItems = (Set<CartItemModel>) session.getAttribute(SystemConstants.CART_ITEMS);
        if(cart == null) {
            cart = new ShoppingCartModel();
        }
        if(cartItems == null) {
            cartItems = new HashSet<>();
        }

        CartItemModel cartItem = getCartItem(cartItems, bookDto);

        if(cartItem == null) {
            cartItem = new CartItemModel();
            cartItem.setQuantity(quantity);
            cartItem.setBookDto(bookDto);
            cartItems.add(cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItems.removeIf(cartItemModel -> cartItemModel.getBookDto().getId() == bookDto.getId());
        }
        cartItems.add(cartItem);
        cart.setTotalItem(getTotalItem(cartItems));

        session.setAttribute(SystemConstants.SHOPPING_CART, cart);
        session.setAttribute(SystemConstants.CART_ITEMS, cartItems);
        return true;
    }

    public boolean updateItemToCart(HttpSession session,
                              BookDto bookDto,
                              int quantity) {
        ShoppingCartModel cart = (ShoppingCartModel) session.getAttribute(SystemConstants.SHOPPING_CART);
        Set<CartItemModel> cartItems = (Set<CartItemModel>) session.getAttribute(SystemConstants.CART_ITEMS);
        if(cart == null) {
            cart = new ShoppingCartModel();
        }
        if(cartItems == null) {
            cartItems = new HashSet<>();
        }

        CartItemModel cartItem = getCartItem(cartItems, bookDto);

        cartItem.setQuantity(quantity);
        cartItems.removeIf(cartItemModel -> cartItemModel.getBookDto().getId() == bookDto.getId());

        cartItems.add(cartItem);
        cart.setTotalItem(getTotalItem(cartItems));

        session.setAttribute(SystemConstants.SHOPPING_CART, cart);
        session.setAttribute(SystemConstants.CART_ITEMS, cartItems);
        return true;
    }

    public void removeAll(HttpSession session) {
        session.removeAttribute(SystemConstants.SHOPPING_CART);
        session.removeAttribute(SystemConstants.CART_ITEMS);
    }

    public void deleteToCart(HttpSession session,
                                 BookDto bookDto) {
        ShoppingCartModel cart = (ShoppingCartModel) session.getAttribute(SystemConstants.SHOPPING_CART);
        Set<CartItemModel> cartItems = (Set<CartItemModel>) session.getAttribute(SystemConstants.CART_ITEMS);
        if(cart == null) {
            cart = new ShoppingCartModel();
        }
        if(cartItems == null) {
            cartItems = new HashSet<>();
        }

        cartItems.removeIf(cartItemModel -> cartItemModel.getBookDto().getId() == bookDto.getId());
        cart.setTotalItem(getTotalItem(cartItems));
        session.setAttribute(SystemConstants.SHOPPING_CART, cart);
        session.setAttribute(SystemConstants.CART_ITEMS, cartItems);
    }
    public CartItemModel getCartItem(Set<CartItemModel> cartItems,
                                     BookDto bookDto) {
        if(cartItems == null) {
            return null;
        }

        CartItemModel model = null;
        for (CartItemModel cartItem: cartItems) {
            if(cartItem.getBookDto().getId() == bookDto.getId()) {
                model = cartItem;
            }
        }
        return model;
    }

    public int getTotalItem(Set<CartItemModel> cartItems) {
        int totalItem = 0;
        for (CartItemModel cartItem: cartItems) {
            totalItem += cartItem.getQuantity();
        }
        return totalItem;
    }
}
