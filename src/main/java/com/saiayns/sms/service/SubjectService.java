package com.saiayns.sms.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saiayns.sms.dto.SubjectDTO;
import com.saiayns.sms.tenant.model.AcademicYear;
import com.saiayns.sms.tenant.model.Subject;
import com.saiayns.sms.tenant.model.Term;
import com.saiayns.sms.tenant.repository.SubjectRepository;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;
    
    @Autowired
    private TermService termService;
    
    @Autowired
	AcademicYearService academicYearService;

    public Subject createSubject(SubjectDTO subject) {
    	Term term = termService.getTermById(subject.getTermId()).orElseThrow(NoSuchElementException::new);
    	AcademicYear activeYear = academicYearService.getActiveAcademicYear();
    	Subject subjectObject = new Subject();
    	subjectObject.setMaxMarks(subject.getMaxMarks());
    	subjectObject.setName(subject.getName());
    	subjectObject.setPassMarks(subject.getPassMarks());
    	subjectObject.setSubCode(subject.getSubCode());
    	subjectObject.setTerm(term);
    	subjectObject.setAcademicYear(activeYear);
        return subjectRepository.save(subjectObject);
    }
    
    public Optional<Subject> getSubjectById(Long subId) {
    	return subjectRepository.findById(subId);
    }

    public List<SubjectDTO> getSubjectsByTerm(Long termId) {
    	AcademicYear activeYear = academicYearService.getActiveAcademicYear();
        List<Subject> subjectList = subjectRepository.findByTermIdAndAcademicYear(termId, activeYear);
        return subjectList.stream().map(s -> SubjectDTO.toDTO(s)).toList();
    }
}

