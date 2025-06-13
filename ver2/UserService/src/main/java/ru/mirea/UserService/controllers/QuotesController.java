package ru.mirea.UserService.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mirea.UserService.models.Industry;
import ru.mirea.UserService.models.Quotes;
import ru.mirea.UserService.models.Source;
import ru.mirea.UserService.services.NewsService;
import ru.mirea.UserService.services.QuotesService;

import java.util.List;

@RestController
@RequestMapping("/quotes")
@CrossOrigin(origins = "http://localhost:3000")
public class QuotesController {

    private final QuotesService quotesService;

    @Autowired
    public QuotesController(QuotesService quotesService) {
        this.quotesService = quotesService;
    }

//    @GetMapping("/check")
//    public boolean isOk() {
//        return true;
//    }


    @GetMapping("/{code}")
    public List<Quotes> getQuotes(@PathVariable("code") String code) {
        return quotesService.getQuotesBySecCode(code);
    }
}
