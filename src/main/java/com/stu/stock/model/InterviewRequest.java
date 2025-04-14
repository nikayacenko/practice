package com.stu.stock.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "interview_requests")
public class InterviewRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vacancy_id", nullable = false)
    private Vacancies vacancy;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Users student;

    @ManyToOne
    @JoinColumn(name = "employer_id", nullable = false)
    private Users employer;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(columnDefinition = "TEXT")
    private String employerResponse;

    @Column(nullable = false)
    private String status = "PENDING"; // PENDING, ACCEPTED, REJECTED

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Геттеры, сеттеры, конструкторы (обязательно)

    public InterviewRequest() {}

    public InterviewRequest(Vacancies vacancy, Users student, Users employer, String message) {
        this.vacancy = vacancy;
        this.student = student;
        this.employer = employer;
        this.message = message;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vacancies getVacancy() {
        return vacancy;
    }

    public void setVacancy(Vacancies vacancy) {
        this.vacancy = vacancy;
    }

    public Users getStudent() {
        return student;
    }

    public void setStudent(Users student) {
        this.student = student;
    }

    public Users getEmployer() {
        return employer;
    }

    public void setEmployer(Users employer) {
        this.employer = employer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmployerResponse() {
        return employerResponse;
    }

    public void setEmployerResponse(String employerResponse) {
        this.employerResponse = employerResponse;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
}
