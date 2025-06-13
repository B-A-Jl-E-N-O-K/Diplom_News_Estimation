package ru.mirea.UserService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.mirea.UserService.models.News;
import ru.mirea.UserService.models.Quotes;
import ru.mirea.UserService.models.QuotesPK;

import java.util.List;

public interface QuotesRepository extends JpaRepository<Quotes, QuotesPK> {

    List<Quotes> findAllBySecurity_Code(String code);
}
