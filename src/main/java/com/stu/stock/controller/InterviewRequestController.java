package com.stu.stock.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.stu.stock.model.Vacancies;
import com.stu.stock.model.InterviewRequest;
import com.stu.stock.model.Users;
import com.stu.stock.repository.UserRepository;
import com.stu.stock.repository.VacancyRepository;
import com.stu.stock.service.InterviewRequestService;
import com.stu.stock.service.VacancyService;
import com.stu.stock.service.UserService;
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

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.List;

@Controller
public class InterviewRequestController {
    @Autowired
    InterviewRequestService interviewRequestService;
    @Autowired
    UserService userService; // Предполагается, что у вас есть сервис для работы с пользователями

    @Autowired
    VacancyRepository vacRep;

    @Autowired
    UserRepository userRep;

    @Autowired
    VacancyService vacServ;

    // @Autowired
    // public InterviewRequestController(InterviewRequestService interviewRequestService, UserService userService, VacancyService vacancyService) {
    //     this.interviewRequestService = interviewRequestService;
    //     this.userService = userService;
    //     this.vacancyService = vacancyService;
    // }

    // Отправка запроса на собеседование
    @PostMapping("/vacancies/{vacancyId}/apply")
    public String applyForVacancy(@PathVariable Long vacancyId, @RequestParam String message, Principal principal) {
        // if (principal == null) {
        //     return "redirect:/login"; // Или другая обработка неавторизованных пользователей
        // }

        Users student = userRep.findByUsername("суперкомпания"); // Получаем текущего пользователя
        Vacancies vac = vacRep.getVacanciesById(vacancyId); // Получаем вакансию

        // if (student == null || vac == null) {
        //     return "redirect:/vacancies"; // Или обработка ошибки
        // }
        Users employer = vac.getEmployer();

        InterviewRequest request = new InterviewRequest(vac, student, employer, message);
        interviewRequestService.saveInterviewRequest(request);

        //return "redirect:/interview-requests/sent"; // Перенаправляем на страницу отправленных запросов
        return "redirect:/myRequest";

    }

    // Страница отправленных запросов
    //@GetMapping("/interview-requests/sent")
    @GetMapping("/myRequest")
    public String sentRequests(Model model, Principal principal) {
        // if (principal == null) {
        //     return "redirect:/login";
        // }

        Users user = userRep.findByUsername("суперкомпания");
        List<InterviewRequest> sentRequests = interviewRequestService.getSentRequests(user.getId());
        model.addAttribute("sentReq", sentRequests);
        return "myRequest"; // Имя вашего Thymeleaf шаблона
    }

    // Страница полученных запросов
    //@GetMapping("/interview-requests/received")
    @GetMapping("/resRequest")
    public String receivedRequests(Model model, Principal principal) {
        // if (principal == null) {
        //     return "redirect:/login";
        // }
        Users user = userRep.findByUsername("суперкомпания");
        List<InterviewRequest> receivedRequests = interviewRequestService.getReceivedRequests(user.getId());
        model.addAttribute("receivedReq", receivedRequests);
        return "resRequest"; // Имя вашего Thymeleaf шаблона
    }

    // Принятие/отклонение запроса (обновление статуса)
    //@PostMapping("/interview-requests/{id}/update-status")
    @PostMapping("/resRequest/{id}/update-status")
    public String updateRequestStatus(@PathVariable Long id, @RequestParam(value = "status", required = false) String status, @RequestParam(value = "employerResponse", required = false) String employerResponse,@RequestParam(value = "action", required = false) String action) {
        if ("DELETE".equals(action)) {
            // Удаляем запрос
            interviewRequestService.deleteInterviewRequest(id);
        }else{
        interviewRequestService.updateInterviewRequestStatus(id, status, employerResponse);}
        //return "redirect:/interview-requests/received";
        return "redirect:/resRequest";
    }
    
}
