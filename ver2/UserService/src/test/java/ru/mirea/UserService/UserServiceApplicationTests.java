package ru.mirea.UserService;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.mirea.UserService.models.News;
import ru.mirea.UserService.models.Security;
import ru.mirea.UserService.services.NewsService;

import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

@SpringBootTest
class UserServiceApplicationTests {

//    private final TestService testService;
//
//    @Autowired
//    public UserServiceApplicationTests(TestService testService) {
//        this.testService = testService;
//    }

//	@Test
//	void contextLoads() {
//	}

//    @Test
//    public void getNews() throws JsonProcessingException, IOException {
//        String inp = "{index: 0, link: 'link', datetime: 'datetime', title_out:'title_out', company:'AFLT', industry: 'industry', class_news: 0}";
//        List<News> news = testService.listenNews(inp);
//
//        News res = new News(null, "link", "title_out", true, "datetime", true);
//
//        assertEquals(news.get(0), res);
//    }

}
