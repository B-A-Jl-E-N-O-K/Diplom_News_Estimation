package ru.mirea.UserService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.UserService.models.News;

import java.util.List;


public interface NewsRepository extends JpaRepository<News, Integer>{
    List<News> findAllBySecurityNews_Code(String name);
    List<News> findAllByResAndSecurityNews_Code(Integer res, String code);

}
