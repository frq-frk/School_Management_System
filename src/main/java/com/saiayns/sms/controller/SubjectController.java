package com.saiayns.sms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saiayns.sms.dto.SubjectDTO;
import com.saiayns.sms.model.Subject;
import com.saiayns.sms.service.SubjectService;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @PostMapping("/add")
    public ResponseEntity<Subject> createSubject(@RequestBody SubjectDTO subject) {
        return ResponseEntity.ok(subjectService.createSubject(subject));
    }

    @GetMapping("/term/{termId}")
    public ResponseEntity<List<SubjectDTO>> getSubjectsByTerm(@PathVariable Long termId) {
        return ResponseEntity.ok(subjectService.getSubjectsByTerm(termId));
    }
}
