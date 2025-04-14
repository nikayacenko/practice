// Vacancy.java
package com.stu.stock.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "vacancies")
public class Vacancies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "company", nullable = false)
    private String company;

    // @Column(name = "skills", nullable = false)
    // private String skills;

    @Column(name = "salary")
    private Double salary;

    @ManyToOne
    @JoinColumn(name = "employer_id", nullable = false)
    private Users employer; // Указываем связь с работодателем

    @Column(name = "type") // <- Добавьте это поле, если его нет
    private String type;

    @Transient // Это поле не будет сохраняться в базе данных
    private String employerUsername=getEmployerUsername(); // Добавили поле для имени пользователя
    public Vacancies(String title, String company, String description, Double salary, String type, Users employer){
        this.title=title;
        this.company=company;
        this.salary=salary;
        this.description=description;
        this.employer=employer;
        this.type=type;
        //this.status=status;
        //this.imageUrl=imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // public String getSkills() {
    //     return skills;
    // }

    // public void setSkills(String skills) {
    //     this.skills = skills;
    // }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Users getEmployer() {
        return employer;
    }

    public void setEmployer(Users employer) {
        this.employer = employer;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
