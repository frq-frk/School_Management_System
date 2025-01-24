package com.saiayns.sms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saiayns.sms.model.Term;
import com.saiayns.sms.repository.TermRepository;

@Service
public class TermService {
    @Autowired
    private TermRepository termRepository;

    public Term createTerm(Term term) {
        return termRepository.save(term);
    }
    
    public Optional<Term> getTermById(Long termId) {
    	return termRepository.findById(termId);
    }

    public List<Term> getAllTerms() {
        return termRepository.findAll();
    }
}

