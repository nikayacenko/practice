// HomeController.java
package com.stu.stock.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.stu.stock.model.Vacancies;
import com.stu.stock.repository.VacancyRepository;
import com.stu.stock.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.List;

@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private final VacancyService vacancyService;
    @Autowired
    private VacancyRepository vacancyRepository;
    @Autowired
    public HomeController(VacancyService vacancyService) {
        this.vacancyService = vacancyService;
    }

    @GetMapping("/") // Обрабатываем GET-запрос к корневому пути ("/")
    public String main(Model model) {
        //logger.info("Запрос к корневому пути /");
        List<Vacancies> vacancies = vacancyService.getAllVacancies(); // Получаем список вакансий
        //logger.info("Получено вакансий: {}", vacancies.size()); // Выводим количество полученных вакансий
        //logger.debug("Вакансии: {}", vacancies); // Выведет детальную информацию о вакансиях (если настроен уровень DEBUG)
        model.addAttribute("vacancies", vacancies); // Добавляем список вакансий в модель
        //model.addAttribute("title", title);
        return "main.html"; // Возвращаем имя Thymeleaf-шаблона ("home.html")
    }
    private final int DEFAULT_PAGE_SIZE = 8; // Можно настроить
    @GetMapping("/main")
    public String mainPage(@RequestParam(name="title",required = false) String title,
                       @RequestParam(name="company",required = false) String company,
                       @RequestParam(defaultValue = "0") int page,
                       Model model) {

        logger.info("Received request with title: {} and company: {}", title, company);
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //User user = userRepo.findByUsername(auth.getName());

        Pageable pageable = PageRequest.of(page, DEFAULT_PAGE_SIZE); // Создаем объект Pageable для пагинации
        Page<Vacancies> vacancyPage;
        
        if (title != null && !title.isEmpty() && company != null && !company.isEmpty()) {
            vacancyPage = vacancyRepository.findVacanciesByTitleContainingIgnoreCaseAndCompanyContainingIgnoreCase(/*user,*/ title, company, pageable);
            //model.addAttribute("vacancy", vacancy);
        } else if (title != null && !title.isEmpty()) {
            vacancyPage = vacancyRepository.findVacanciesByTitleContainingIgnoreCase(/*user,*/ title, pageable);
            //model.addAttribute("vacancy", vacancy);
        } else if (company != null && !company.isEmpty()) {
            vacancyPage = vacancyRepository.findVacanciesByCompanyContainingIgnoreCase(company, pageable);
        } else {
            vacancyPage = vacancyRepository.findAll(pageable);
        }
        List<Vacancies> vacs = vacancyPage.getContent();
        logger.info("Found {} vacancies", vacs.size());
        model.addAttribute("vacs", vacs);
        model.addAttribute("title", title);  // Передаем параметры поиска обратно в шаблон
        model.addAttribute("company", company);  // чтобы сохранить значения в полях
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", vacancyPage.getTotalPages());
        return "main.html";
    }
}

