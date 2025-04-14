package com.stu.stock.repository;

import com.stu.stock.model.Users;
import com.stu.stock.model.Vacancies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.List;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancies, Long> {

    // Дополнительные методы поиска, если необходимо
    List<Vacancies> findVacanciesByCompany(String company);
    Vacancies findVacanciesById(Long id);
    Vacancies getVacanciesById(Long id);
    List<Vacancies> findBySalary(Double salary);
    List<Vacancies> findVacanciesByEmployerIs(Users employer);
    //List<Vacancies> findByTitleContainingIgnoreCase(String title);

    // static Page<Vacancies> findVacanciesByTitleContainingIgnoreCaseAndCompanyContainingIgnoreCase(/*User owner,*/ String title, String company, Pageable pageable) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'findVacanciesByTitleContainingIgnoreCaseAndCompanyContainingIgnoreCase'");
    // }
    // static Page<Vacancies> findVacanciesByTitleContainingIgnoreCase(/*User owner,*/ String title, Pageable pageable) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'findVacanciesByTitleContainingIgnoreCase'");
    // }
    Page<Vacancies> findVacanciesByTitleContainingIgnoreCaseAndCompanyContainingIgnoreCase(/*User owner,*/ String title, String company, Pageable pageable);
    Page<Vacancies> findVacanciesByTitleContainingIgnoreCase(/*User owner,*/ String title, Pageable pageable);
    Page<Vacancies> findVacanciesByCompanyContainingIgnoreCase(/*User owner,*/ String company, Pageable pageable);


}