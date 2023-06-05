package com.library.controller;

import com.library.dto.BookDto;
import com.library.dto.CategoryDto;
import com.library.dto.CommentDto;
import com.library.dto.UserDto;
import com.library.service.BookService;
import com.library.service.CategoryService;
import com.library.service.CommentService;
import com.library.service.UserService;
import com.library.utils.SystemConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user/book")
public class BookDetailController {
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CommentService commentService;



    @GetMapping("/{id}")
    public String showDetailView(Principal principal,
                                 @PathVariable Long id,
                                 Model model) {
        if(principal == null) {
            return "redirect:/account/login";
        }

        UserDto userDto = userService.findByUsername(principal.getName());
        BookDto book =  bookService.findById(id);
        List<CategoryDto> categories = categoryService.findAll();
        model.addAttribute(SystemConstants.CATEGORIES, categories);
        model.addAttribute(SystemConstants.BOOK, book);
        model.addAttribute(SystemConstants.USER_LOGIN, userDto);
        return "detail";
    }
    @PostMapping("/{id}/comment")
    public String createComment(RedirectAttributes attributes,
                         CommentDto commentDto,
                         Principal principal,
                                @PathVariable Long id) {
        if(principal == null) {
            return "redirect:/account/login";
        }

        try {
            UserDto userDto = userService.findByUsername(principal.getName());
            BookDto bookDto = bookService.findById(id);
            commentService.save(commentDto, userDto, bookDto);
            attributes.addFlashAttribute(SystemConstants.COMMENT_SUCCESS, "success");
        } catch (Exception e) {
            attributes.addFlashAttribute(SystemConstants.COMMENT_FAIL, "The server has been errors");
        }
        return "redirect:/user/book/" + id;
    }
}
