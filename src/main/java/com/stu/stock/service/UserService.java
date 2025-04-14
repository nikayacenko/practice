package com.stu.stock.service;
// VacancyService.java

import com.stu.stock.DTO.RegistrationDTO;
import com.stu.stock.model.ResourceNotFoundException;
import com.stu.stock.model.Roles;
import com.stu.stock.model.Users;
import com.stu.stock.model.Vacancies;
import com.stu.stock.repository.RoleRepository;
import com.stu.stock.repository.UserRepository;
import com.stu.stock.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;


@Service
public class UserService {

    private final UserRepository employerRepository;

    private final RoleRepository roleRepository;

    //private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepository employerRepository, RoleRepository roleRepository/*, PasswordEncoder passwordEncoder*/) {
        this.employerRepository = employerRepository;
        this.roleRepository = roleRepository;
        //this.passwordEncoder = passwordEncoder;
    }
    public Users findByUsername(String username) {
        return employerRepository.findByUsername(username);
    }
    public Users findById(Long id) {
        Optional<Users> user = employerRepository.findById(id);
        return user.orElse(null); // Возвращаем null, если пользователь не найден, или можно бросить исключение
    }


//     private Collection<? extends GrantedAuthority> getAuthorities(Collection<Roles> roles) {
//         return roles.stream()
//                 .map(role -> new SimpleGrantedAuthority(role.getName()))
//                 .collect(Collectors.toList());
//     }



//     public Users registerEmployer(RegistrationDTO registrationDTO) {
//         Users employer = new Users();
//         employer.setUsername(registrationDTO.getCompanyName());
//         employer.setContactPerson(registrationDTO.getContactPerson());
//         employer.setEmail(registrationDTO.getEmail());
//         employer.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));

//          Assign default role 'ROLE_EMPLOYER'
//         Roles employerRole = roleRepository.findByName("ROLE_EMPLOYER");
//         if (employerRole == null) {
//             employerRole = new Roles();
//             employerRole.setName("ROLE_EMPLOYER");
//             roleRepository.save(employerRole);
//         }
//         //employer.getRoles().add(employerRole);
//         return employerRepository.save(employer);
//     }

//     public Administrator registerAdmin(RegistrationDTO registrationDTO) {
//         Administrator administrator = new Administrator();
//         administrator.setEmail(registrationDTO.getEmail());
//         administrator.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));


//         // Assign default role 'ROLE_ADMIN'
//         Role adminRole = roleRepository.findByName("ROLE_ADMIN");
//         if (adminRole == null) {
//             adminRole = new Role();
//             adminRole.setName("ROLE_ADMIN");
//             roleRepository.save(adminRole);
//         }
//         administrator.getRoles().add(adminRole);
//         return administratorRepository.save(administrator);
//     }
}