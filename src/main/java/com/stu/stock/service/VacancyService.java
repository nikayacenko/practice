// VacancyService.java
package com.stu.stock.service;

//import com.stu.stock.model.ResourceNotFoundException;
import com.stu.stock.model.Users;
import com.stu.stock.model.Vacancies;
import com.stu.stock.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class VacancyService {

    private final VacancyRepository vacancyRepository;

    @Autowired
    public VacancyService(VacancyRepository vacancyRepository) {
        this.vacancyRepository = vacancyRepository;
    }

    public List<Vacancies> getAllVacancies() {
        return vacancyRepository.findAll();
    }

    public List<Vacancies> getByCompany(String company){
        return vacancyRepository.findVacanciesByCompany(company);
    }

    // public List<Vacancies> getByTitle(String title){
    //     return vacancyRepository.findByTitleContainingIgnoreCase(title);
    // }

        public Page<Vacancies> findVacanciesByTitlePaginated(String title, int page, int size, String sortBy, String sortDirection) {
        Sort sort = Sort.by(sortBy);
        if (sortDirection.equalsIgnoreCase("desc")) {
            sort = sort.descending();
        }

        Pageable pageable = PageRequest.of(page, size, sort);
        return vacancyRepository.findVacanciesByTitleContainingIgnoreCase(title, pageable);
    }
    public Vacancies getVacancyById(Long id) {
        Optional<Vacancies> vacancy = vacancyRepository.findById(id);
        return vacancy.orElse(null); // Возвращаем null, если вакансия не найдена, или можно бросить исключение
    }
}

//    public Vacancies getVacancyById(Long id) {
//        return vacancyRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Vacancy not found with id: " + id));
//    }

//    public Vacancies createVacancy(Vacancies vacancy) {
//        vacancy.setDatePosted(LocalDate.now());
//        return vacancyRepository.save(vacancy);
//    }

//    public Vacancies updateVacancy(Long id, Vacancies vacancyDetails) {
//        Vacancies vacancy = vacancyRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Vacancy not found with id: " + id));
//        vacancy.setTitle(vacancyDetails.getTitle());
//        vacancy.setDescription(vacancyDetails.getDescription());
//        vacancy.setSalary(vacancyDetails.getSalary());
//
//        return vacancyRepository.save(vacancy);
//    }
//
//    public void deleteVacancy(Long id) {
//        Vacancies vacancy = vacancyRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Vacancy not found with id: " + id));
//        vacancyRepository.delete(vacancy);
//    }
//
//    public List<Vacancies> searchVacancies(String keyword) {
//        return vacancyRepository.findByTitleContainingIgnoreCase(keyword);
//    }
//
//    public List<Vacancies> getVacanciesByEmployer(String employer) {
//        return vacancyRepository.findByEmployer(employer);
//    }



