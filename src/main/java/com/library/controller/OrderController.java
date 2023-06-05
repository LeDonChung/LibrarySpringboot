package com.library.controller;

import com.library.dto.CategoryDto;
import com.library.dto.OrderDto;
import com.library.dto.UserDto;
import com.library.service.CategoryService;
import com.library.service.OrderService;
import com.library.service.UserService;
import com.library.utils.SystemConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;


    @RequestMapping(value = "/order/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public OrderDto updateStatusOrder(
            @PathVariable("id") Long id,
            @RequestParam("status") String status) {
        return orderService.updateStatus(id, status);
    }
    @GetMapping("/order-detail/{id}")
    public String getOrder(Principal principal,
                           @PathVariable("id") Long id,
                           Model model) {

        if(principal == null) {
            return "redirect:/account/login";
        }
        UserDto userDto = userService.findByUsername(principal.getName());

        List<CategoryDto> categories = categoryService.findAll();
        OrderDto order = orderService.findById(id);
        model.addAttribute(SystemConstants.ORDER, order);
        model.addAttribute(SystemConstants.USER_LOGIN, userDto);
        model.addAttribute(SystemConstants.CATEGORIES, categories);
        return "order-details";
    }
}
