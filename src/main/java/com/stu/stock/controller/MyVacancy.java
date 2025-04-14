package com.stu.stock.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.stu.stock.model.Vacancies;
import com.stu.stock.model.Users;
import com.stu.stock.repository.UserRepository;
import com.stu.stock.repository.VacancyRepository;
import com.stu.stock.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
public class MyVacancy {
    @Autowired
    VacancyRepository vacRep;

    @Autowired
    UserRepository userRep;

    @Autowired
    VacancyService vacServ;

    @GetMapping("/myVacancy")
    public String MyBooksGet(Model model)
    {
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user=userRep.findByUsername("каска");
        List<Vacancies> v=vacRep.findVacanciesByEmployerIs(user);
        model.addAttribute("MyVac", v);
        return "myVacancy.html";
    }

    @GetMapping("/add")
    public String addGet(Model model)
    {
        return "add.html";
    }
    private String validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return "Название не может быть пустым.";
        } else if (title.length() > 255) {
            return "Название не может быть длиннее 255 символов.";
        }
        return null;
    }
    private String validateCompany(String company) {
        if (company == null || company.trim().isEmpty()) {
            return "Поле компания не может быть пустым.";
        } else if (company.length() > 255) {
            return "Поле автор не может быть длиннее 255 символов.";
        }
        return null;
    }
    private String validateDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            return "Описание не может быть пустым.";
        } else if (description.length() > 1000) {
            return "Описание не может быть длиннее 1000 символов.";
        }
        return null;
    }
    private String validateSalary(Double salary) {
        if (salary == null) {
            return "Поле зарплата арплата не может быть пустым.";
        } else if (salary < 0) {
            return "Поле зарплата не может содержать отрицательное значение";
        }
        return null;
    }
    private String validateType(String type) {
        if (type == null || type.trim().isEmpty()) {
            return "Поле классификации профессии не может быть пустым.";
        } else if (type.length() > 1000) {
            return "Поле классификации профессии не может быть длиннее 1000 символов.";
        }
        return null;
    }
    @PostMapping("/add")
    public String add(
            @RequestParam String title,
            @RequestParam String company,
            @RequestParam String description,
            @RequestParam(defaultValue = "0") Double salary,
            @RequestParam String type,
            // @RequestParam(defaultValue = "0") Integer publicationYear,
            // @RequestParam("coverImage") MultipartFile coverImage, // Получаем загруженный файл
            RedirectAttributes redirectAttributes,
            Model model) {

        String titleError = validateTitle(title);
        String companyError = validateCompany(company);
        //String isbnError = null;//validateIsbn(isbn);
        String descriptionError = validateDescription(description);
        String salaryError = validateSalary(salary);
        String typeError = validateType(type);


        if (titleError != null || companyError != null ||
                descriptionError != null || salaryError != null || typeError != null) {

            redirectAttributes.addFlashAttribute("titleError", titleError);
            redirectAttributes.addFlashAttribute("companyError", companyError);
            //redirectAttributes.addFlashAttribute("isbnError", isbnError);
            redirectAttributes.addFlashAttribute("descriptionError", descriptionError);
            redirectAttributes.addFlashAttribute("salaryError", salaryError);
            redirectAttributes.addFlashAttribute("typeError", typeError);

            redirectAttributes.addFlashAttribute("title", title);
            redirectAttributes.addFlashAttribute("company", company);
            //redirectAttributes.addFlashAttribute("isbn", isbn);
            redirectAttributes.addFlashAttribute("description", description);
            redirectAttributes.addFlashAttribute("salary", salary);
            redirectAttributes.addFlashAttribute("type", type);

            return "redirect:/add";
        }
        Users user = userRep.findByUsername("каска");
        Vacancies v = new Vacancies(title,company,description,salary,type, user);
        vacRep.save(v);
        return "redirect:/myVacancy";
    }

    @PostMapping("/update")
    public String updatePost(@RequestParam Long id,
                                 Model model)
    {
        Vacancies v = vacRep.findVacanciesById(id);
        return "update";
    }

    @GetMapping("/update")
    public String upGet(Model model) {
        return "redirect:/main";
    }

    @GetMapping("/update/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Vacancies v = vacRep.getVacanciesById(id);
        model.addAttribute("vacs", v);
        return "update";
    }

    @PostMapping("/update/{id}")
    public String updateVac(@PathVariable("id") Long id, @ModelAttribute("vac") Vacancies vac, /*@RequestParam("coverImage") MultipartFile file,*/ Model model, RedirectAttributes redirectAttributes) {
        Vacancies existingVac = vacRep.getVacanciesById(id);
        if (existingVac == null){
            return "error";
        }
        String titleError = validateTitle(vac.getTitle());
        String companyError = validateCompany(vac.getCompany());
        //String isbnError = null;//validateIsbn(book.getIsbn());
        String descriptionError = validateDescription(vac.getDescription());
        String salaryError = validateSalary(vac.getSalary());
        String typeError = validateType(vac.getType());

        if (titleError != null || companyError != null ||
                descriptionError != null || salaryError != null || typeError != null) {

            redirectAttributes.addFlashAttribute("titleError", titleError);
            redirectAttributes.addFlashAttribute("companyrError", companyError);
            //redirectAttributes.addFlashAttribute("isbnError", isbnError);
            redirectAttributes.addFlashAttribute("descriptionError", descriptionError);
            redirectAttributes.addFlashAttribute("salaryError", salaryError);
            redirectAttributes.addFlashAttribute("typeError", typeError);

            redirectAttributes.addFlashAttribute("title", vac.getTitle());
            redirectAttributes.addFlashAttribute("company", vac.getCompany());
            //redirectAttributes.addFlashAttribute("isbn", book.getIsbn());
            redirectAttributes.addFlashAttribute("description", vac.getDescription());
            redirectAttributes.addFlashAttribute("salary", vac.getSalary());
            redirectAttributes.addFlashAttribute("type", vac.getType());

            return "redirect:/update/{id}";
        }
        existingVac.setTitle(vac.getTitle());
        existingVac.setCompany(vac.getCompany());
        existingVac.setDescription(vac.getDescription());
        //existingVac.setIsbn(book.getIsbn());
        existingVac.setSalary(vac.getSalary());
        existingVac.setType(vac.getType());

        // if (!file.isEmpty()) {
        //     try {
        //         bookService.saveCoverImage(existingBook, file);
        //     } catch (Exception e) {
        //         return "error";
        //     }
        // }
        vacRep.save(existingVac);
        return "redirect:/myVacancy";
    }

    @PostMapping("/myVacancy")
    public String delete(@RequestParam Long id,  Model model)
    {
        Vacancies v=vacRep.getVacanciesById(id);
        vacRep.delete(v);
        //vacServ.deleteOldCoverImage(b);
        return "redirect:/myVacancy";
    }

}
