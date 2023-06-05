package com.library.controller;

import com.library.dto.CategoryDto;
import com.library.dto.OrderDto;
import com.library.dto.UserDto;
import com.library.service.CategoryService;
import com.library.service.OrderService;
import com.library.service.UserService;
import com.library.utils.SystemConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/login")
    public String showLoginPage(Model model) {
        return "login";
    }

    @GetMapping("/auth")
    public String auth(Principal principal) {
        // Principal principal: đăng nhập
        // principal.getName(): username
        if (principal == null) {
            return "redirect:/account/login";
        }
        // đã đăng nhập
        UserDto userDto = userService.findByUsername(principal.getName());

        if (userDto.hasRole("USER")) {
            return "redirect:/user/index";
        } else if (userDto.hasRole("ADMIN")) {
            return "redirect:/admin/index";
        } 

        return "redirect:/account/login";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute(SystemConstants.USER_DTO, new UserDto());
        return "register";
    }

    @PostMapping("/do-register")
    public String registerProcess(Model model,
                                  @Valid @ModelAttribute(SystemConstants.USER_DTO) UserDto userDto,
                                  BindingResult result
    ) {

        model.addAttribute(SystemConstants.USER_DTO, userDto);
        try {
            // Check valid
            if (result.hasErrors()) {
                return "register";
            }

            String username = userDto.getUsername();
            UserDto user = userService.findByUsername(username);
            if (user != null) {
                // have account
                model.addAttribute(SystemConstants.MESSAGE, "Your username has been registered");
                return "register";
            }
            // check repeatPassword
            if (!userDto.getPassword().equals(userDto.getRepeatPassword())) {
                model.addAttribute(SystemConstants.MESSAGE, "Password is not same");
                return "register";
            }

            // register
            userService.save(userDto);
            model.addAttribute(SystemConstants.MESSAGE, "Register successfully");
            return "register";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute(SystemConstants.MESSAGE, "The server has been errors");
            return "register";
        }
    }
    @GetMapping("/order-history")
    public String showMyOrder(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/account/login";
        }
        UserDto userDto = userService.findByUsername(principal.getName());
        System.out.println(userDto);
        List<CategoryDto> categories = categoryService.findAll();
        List<OrderDto> orders = orderService.findByCustomerId(userDto.getId());
        model.addAttribute(SystemConstants.USER_LOGIN, userDto);
        model.addAttribute(SystemConstants.ORDERS, orders);
        model.addAttribute(SystemConstants.CATEGORIES, categories);
        return "my-order";
    }


}
