package com.example.Web_Service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookController {

    @GetMapping("/books")
    public List<String> Books () {
        List<String> books = new ArrayList<>();
        books.add("Book1");
        books.add("Book2");
        books.add("Book3");
        return books;
    }
}