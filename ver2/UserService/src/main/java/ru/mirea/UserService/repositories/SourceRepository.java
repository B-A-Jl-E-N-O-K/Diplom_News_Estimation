package ru.mirea.UserService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.UserService.models.Source;

public interface SourceRepository extends JpaRepository<Source, Integer> {
}
