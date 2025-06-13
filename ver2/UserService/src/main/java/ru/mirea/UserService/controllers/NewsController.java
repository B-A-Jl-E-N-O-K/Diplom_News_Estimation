package ru.mirea.UserService.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mirea.UserService.dao.NewsDAO;
import ru.mirea.UserService.dao.SecDAO;
import ru.mirea.UserService.models.Industry;
import ru.mirea.UserService.models.News;
import ru.mirea.UserService.models.Security;
import ru.mirea.UserService.models.Source;
import ru.mirea.UserService.services.NewsService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class NewsController {
    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

//    @GetMapping("/check")
//    public boolean isOk() {
//        return true;
//    }

    @GetMapping("/source")
    public Source getSource() {
        return newsService.getSource(1);
    }

    @GetMapping("/industry")
    public List<Industry> getIndustries() {
        return newsService.getAllIndustries();
    }

    @GetMapping("/security")
    public List<SecDAO> getAllSec() {
        return newsService.getAllSec();
    }

    @GetMapping("/security/{name}")
    public List<Security> getSecurity(@PathVariable("name") String name) {
        return newsService.getSecByIndName(name);
    }

    @GetMapping("/news")
    public List<NewsDAO> getNews() {
        return newsService.getAllNews();
    }

//    @GetMapping("/news/industry/{name}")
//    public List<News> getNewsInd(@PathVariable("name") String name) {
//        return newsService.getNewsByIndName(name);
//    }

//    @GetMapping("/news/security/{code}")
//    public List<News> getNewsSec(@PathVariable("code") String code) {
//        return newsService.getNewsBySecCode(code);
//    }
//
//    @GetMapping("/news/source")
//    public List<News> getNewsSource() {
//        return newsService.getNewsBySource();
//    }


}
