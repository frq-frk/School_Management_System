package com.saiayns.sms.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saiayns.sms.dto.SubjectDTO;
import com.saiayns.sms.model.Subject;
import com.saiayns.sms.model.Term;
import com.saiayns.sms.repository.SubjectRepository;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;
    
    @Autowired
    private TermService termService;

    public Subject createSubject(SubjectDTO subject) {
    	Term term = termService.getTermById(subject.getTermId()).orElseThrow(NoSuchElementException::new);
    	Subject subjectObject = new Subject();
    	subjectObject.setMaxMarks(subject.getMaxMarks());
    	subjectObject.setName(subject.getName());
    	subjectObject.setTerm(term);
        return subjectRepository.save(subjectObject);
    }
    
    public Optional<Subject> getSubjectById(Long subId) {
    	return subjectRepository.findById(subId);
    }

    public List<Subject> getSubjectsByTerm(Long termId) {
        return subjectRepository.findByTermId(termId);
    }
}

