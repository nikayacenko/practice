package com.stu.stock.DTO;

import lombok.Data;

// RegistrationDTO.java
@Data
public class RegistrationDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String major;
    private Integer graduationYear;

    private String companyName;
    private String contactPerson;

    public String getCompanyName(){
        return companyName;
    }
}
