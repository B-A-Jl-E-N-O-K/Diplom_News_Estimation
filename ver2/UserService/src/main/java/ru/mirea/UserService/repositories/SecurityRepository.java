package ru.mirea.UserService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.UserService.models.Security;

import java.util.List;

public interface SecurityRepository extends JpaRepository<Security, Integer> {
    Security findOneByCode(String code);
    List<Security> findAllByIndustry_Name(String name);
    Security findOneByIndustry_Name(String name);
}
