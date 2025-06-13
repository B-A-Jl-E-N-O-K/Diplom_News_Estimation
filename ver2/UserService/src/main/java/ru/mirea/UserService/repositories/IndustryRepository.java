package ru.mirea.UserService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.UserService.models.Industry;

public interface IndustryRepository extends JpaRepository<Industry, Integer> {
    Industry findOneByName(String name);
}
