package com.saiayns.sms.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saiayns.sms.tenant.model.AcademicYear;
import com.saiayns.sms.tenant.model.Term;
import com.saiayns.sms.tenant.repository.TermRepository;

@Service
public class TermService {
    @Autowired
    private TermRepository termRepository;
    
    @Autowired
	AcademicYearService academicYearService;

    public Term createTerm(String name, LocalDate startDate, LocalDate endDate, String description) {
    	AcademicYear activeYear = academicYearService.getActiveAcademicYear();
    	Term term = new Term(name, startDate, endDate, activeYear, description);
        Term savedTerm = termRepository.save(term);
        return termRepository.save(savedTerm);
    }
    
    public Optional<Term> getTermById(Long termId) {
    	return termRepository.findById(termId);
    }

    public List<Term> getAllTerms() {
    	AcademicYear activeYear = academicYearService.getActiveAcademicYear();
        return termRepository.findByAcademicYearId(activeYear.getId());
    }
}

