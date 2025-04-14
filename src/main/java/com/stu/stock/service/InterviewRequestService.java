// VacancyService.java
package com.stu.stock.service;

//import com.stu.stock.model.ResourceNotFoundException;
import com.stu.stock.model.Users;
import com.stu.stock.model.Vacancies;
import com.stu.stock.model.InterviewRequest;
import com.stu.stock.repository.VacancyRepository;
import com.stu.stock.repository.InterviewRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class InterviewRequestService {

    private final InterviewRequestRepository interviewRequestRepository;

    @Autowired
    public InterviewRequestService(InterviewRequestRepository interviewRequestRepository) {
        this.interviewRequestRepository = interviewRequestRepository;
    }

    public List<InterviewRequest> getSentRequests(Long studentId) {
        return interviewRequestRepository.findByStudentId(studentId);
    }

    public List<InterviewRequest> getReceivedRequests(Long employerId) {
        return interviewRequestRepository.findByEmployerId(employerId);
    }

    public InterviewRequest getInterviewRequestById(Long id) {
        return interviewRequestRepository.findById(id).orElse(null); // Обработка, если не найдено
    }

    public InterviewRequest saveInterviewRequest(InterviewRequest interviewRequest) {
        return interviewRequestRepository.save(interviewRequest);
    }

    public void deleteInterviewRequest(Long id) {
        interviewRequestRepository.deleteById(id);
    }

    public void updateInterviewRequestStatus(Long id, String status, String employerResponse) {
        InterviewRequest request = interviewRequestRepository.findById(id).orElse(null);
        if (request != null) {
            request.setStatus(status);
            request.setEmployerResponse(employerResponse);
            interviewRequestRepository.save(request);
        } else {
            // Обработка, если запрос не найден
            throw new IllegalArgumentException("Interview request not found with id: " + id);
        }
    }
}