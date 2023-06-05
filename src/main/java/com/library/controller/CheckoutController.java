package com.library.controller;

import com.library.dto.OrderDto;
import com.library.dto.UserDto;
import com.library.model.CartItemModel;
import com.library.model.CartSession;
import com.library.model.ShoppingCartModel;
import com.library.service.OrderService;
import com.library.service.UserService;
import com.library.utils.SystemConstants;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class CheckoutController {
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartSession cartSession;

    @PostMapping("/checkout")
    public String saveOrder(Principal principal,
                            ShoppingCartModel shoppingCartModel,
                            RedirectAttributes attributes,
                            HttpSession session) {
        if (principal == null) {
            return "redirect:/login";
        }
        ShoppingCartModel cart = (ShoppingCartModel) session.getAttribute(SystemConstants.SHOPPING_CART);
        Set<CartItemModel> cartItems = (Set<CartItemModel>) session.getAttribute(SystemConstants.CART_ITEMS);
        if(cart == null || cartItems == null) {
            return "redirect:/user/cart";
        }

        if(cartItems.size() == 0) {
            return "redirect:/user/cart";
        }
        cart.setPhoneNumber(shoppingCartModel.getPhoneNumber());
        cart.setAddress(shoppingCartModel.getAddress());
        cart.setNotes(shoppingCartModel.getNotes());

        UserDto userDto = userService.findByUsername(principal.getName());

        OrderDto dto = orderService.saveOrder(cart, cartItems, userDto);
        if(dto == null) {
            return "redirect:/user/cart";
        }
        cartSession.removeAll(session);
        attributes.addFlashAttribute(SystemConstants.ORDER_SUCCESS, "success");
        return "redirect:/account/order-history";
    }
}
