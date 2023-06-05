package com.library.controller;

import com.library.dto.BookDto;
import com.library.dto.UserDto;
import com.library.service.BookService;
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
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @GetMapping(value = "/index")
    public String index(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/account/login";
        }
        UserDto userDto = userService.findByUsername(principal.getName());

        if (!userDto.hasRole("ADMIN")) {
            return "redirect:/account/login";
        }
        List<BookDto> books = bookService.findAll();
        model.addAttribute(SystemConstants.BOOKS, books);
        model.addAttribute(SystemConstants.ADMIN, userDto);
        return "admin";
    }
}
