package com.stu.stock.repository;

import com.stu.stock.model.Users;
import com.stu.stock.model.Vacancies;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
    Users findUsersById(Long id);

}