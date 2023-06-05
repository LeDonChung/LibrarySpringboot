package com.library.controller;

import com.library.dto.BookDto;
import com.library.dto.CategoryDto;
import com.library.dto.UserDto;
import com.library.model.CartItemModel;
import com.library.model.CartSession;
import com.library.model.ShoppingCartModel;
import com.library.service.BookService;
import com.library.service.CategoryService;
import com.library.service.UserService;
import com.library.utils.SystemConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class CartController {
    @Autowired
    private CartSession cartSession;
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/cart")
    public String showCart(Model model, Principal principal, HttpSession session) {
        if(principal == null) {
            return "redirect:/account/login";
        }

        // Get All Categories
        List<CategoryDto> categories = categoryService.findAll();

        ShoppingCartModel cart = (ShoppingCartModel) session.getAttribute(SystemConstants.SHOPPING_CART);
        Set<CartItemModel> cartItems = (Set<CartItemModel>) session.getAttribute(SystemConstants.CART_ITEMS);
        if(cart == null) {
            cart = new ShoppingCartModel();
        }
        if(cartItems == null) {
            cartItems = new HashSet<>();
        }

        UserDto userDto = userService.findByUsername(principal.getName());
        model.addAttribute(SystemConstants.CATEGORIES, categories);
        model.addAttribute(SystemConstants.USER_LOGIN, userDto);
        session.setAttribute(SystemConstants.SHOPPING_CART, cart);
        session.setAttribute(SystemConstants.CART_ITEMS, cartItems);
        model.addAttribute(SystemConstants.CATEGORIES, categories);
        return "cart";
    }
    @RequestMapping(value = "/add-to-cart/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String addItemToCart(HttpServletRequest request,
                                @PathVariable("id") Long bookId,
                                @RequestParam(value = "quantity", defaultValue = "1") int quantity,
                                Principal principal,
                                HttpSession session,
                                RedirectAttributes attributes) {
        if (principal == null) {
            return "redirect:/account/login";
        }

        BookDto productDto = bookService.findById(bookId);

        try {
            cartSession.addItemToCart(session, productDto, quantity);
            attributes.addFlashAttribute(SystemConstants.SUCCESS, "Add to cart successfully");
        } catch (Exception e) {
            attributes.addFlashAttribute(SystemConstants.FAIL, "Add to cart fail");
        }


        return "redirect:" + request.getHeader("Referer");
    }

    @RequestMapping(value = "/update-to-cart/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String updateItemToCart(HttpServletRequest request,
                                   @PathVariable("id") Long bookId,
                                   @RequestParam(value = "quantity", defaultValue = "1") int quantity,
                                   Principal principal,
                                   HttpSession session) {
        if (principal == null) {
            return "redirect:/account/login";
        }

        BookDto productDto = bookService.findById(bookId);
        cartSession.updateItemToCart(session, productDto, quantity);

        return "redirect:" + request.getHeader("Referer");
    }

    @RequestMapping(value = "/remove-to-cart/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String removeItemToCart(HttpServletRequest request,
                                   @PathVariable("id") Long bookId,
                                   Principal principal,
                                   HttpSession session) {

        if (principal == null) {
            return "redirect:/login";
        }


        BookDto bookDto = bookService.findById(bookId);

        cartSession.deleteToCart(session, bookDto);

        return "redirect:" + request.getHeader("Referer");
    }
}
