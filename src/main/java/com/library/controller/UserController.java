package com.library.controller;

import com.library.dto.BookDto;
import com.library.dto.CategoryDto;
import com.library.dto.UserDto;
import com.library.service.BookService;
import com.library.service.CategoryService;
import com.library.service.UserService;
import com.library.utils.SystemConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/index")
    public String index(Principal principal, Model model) {
        if(principal == null) {
            return "redirect:/account/login";
        }

        UserDto userDto = userService.findByUsername(principal.getName());
        List<BookDto> books =  bookService.findAllByActivated();
        List<CategoryDto> categories = categoryService.findAll();
        model.addAttribute(SystemConstants.BOOKS, books);
        model.addAttribute(SystemConstants.CATEGORIES, categories);
        model.addAttribute(SystemConstants.USER_LOGIN, userDto);
        return "user";
    }
}
