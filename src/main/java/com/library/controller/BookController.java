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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;
    @GetMapping("/{id}")
    public String showView(Model model, Principal principal, @PathVariable("id") Long id) {
        if(principal == null) {
            return "redirect:/account/login";
        }
        UserDto userDto = userService.findByUsername(principal.getName());
        BookDto bookDto = bookService.findById(id);
        List<CategoryDto> categories = categoryService.findAll();
        model.addAttribute(SystemConstants.CATEGORIES, categories);
        model.addAttribute(SystemConstants.ADMIN, userDto);
        model.addAttribute(SystemConstants.BOOK, bookDto);
        model.addAttribute(SystemConstants.EDITBOOK, "editBook");
        return "save-book";
    }
    @GetMapping("/add")
    public String showAdd(Model model, Principal principal) {
        if(principal == null) {
            return "redirect:/account/login";
        }
        UserDto userDto = userService.findByUsername(principal.getName());
        List<CategoryDto> categories = categoryService.findAll();
        model.addAttribute(SystemConstants.CATEGORIES, categories);
        model.addAttribute(SystemConstants.ADMIN, userDto);
        model.addAttribute(SystemConstants.BOOK, new BookDto());
        model.addAttribute(SystemConstants.ADDBOOK, "addBook");
        return "save-book";
    }
    @PostMapping("/add")
    public String addBook(Model model,
                            @RequestParam("bookCoverUpload") MultipartFile bookCover,
                             RedirectAttributes attributes,
                             @ModelAttribute(SystemConstants.BOOK) BookDto book,
                             Principal principal) {
        if (principal == null) {
            return "redirect:/account/login";
        }

        try {
            if(bookService.findByTitleAndAuthor(book.getTitle(), book.getAuthor()) != null) {
                attributes.addFlashAttribute(SystemConstants.FAIL, "books already exist");
                return "redirect:/admin/books/add";
            }
            bookService.save(book, bookCover);
            attributes.addFlashAttribute(SystemConstants.SUCCESS, "Add book successfully");
        } catch (Exception e) {
            model.addAttribute(SystemConstants.MESSAGE, "The server has been errors");
            e.printStackTrace();
        }

        return "redirect:/admin/books/add";
    }
    @PostMapping("/update")
    public String updateBook(Model model,
                          @RequestParam("id") Long id,
                          @RequestParam("bookCoverUpload") MultipartFile bookCover,
                          RedirectAttributes attributes,
                          @ModelAttribute(SystemConstants.BOOK) BookDto book,
                          Principal principal) {
        if (principal == null) {
            return "redirect:/account/login";
        }

        try {
            book.setId(id);
            bookService.save(book, bookCover);
            attributes.addFlashAttribute(SystemConstants.SUCCESS, "Update book successfully");
        } catch (Exception e) {
            attributes.addFlashAttribute(SystemConstants.FAIL, "The server has been errors");
            e.printStackTrace();
        }

        return "redirect:/admin/index";
    }
    @GetMapping(value = "/enable")
    public String enableProduct(Model model, RedirectAttributes attributes, Long id) {
        System.out.println("BOOK ENABLE");
        try {
            bookService.enable(id);
            attributes.addFlashAttribute(SystemConstants.SUCCESS, "Enabled book successfully");
        } catch (Exception e) {
            attributes.addFlashAttribute(SystemConstants.FAIL, "The server has been errors");
            e.printStackTrace();
        }
        return "redirect:/admin/index";
    }

    @GetMapping(value = "/delete")
    public String disableProduct(Model model, RedirectAttributes attributes, Long id) {
        try {
            bookService.delete(id);
            attributes.addFlashAttribute(SystemConstants.SUCCESS, "Deleted book successfully");
        } catch (Exception e) {
            attributes.addFlashAttribute(SystemConstants.FAIL, "The server has been errors");
            e.printStackTrace();
        }
        return "redirect:/admin/index";
    }
}
