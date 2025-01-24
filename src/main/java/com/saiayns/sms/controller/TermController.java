package com.saiayns.sms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saiayns.sms.model.Term;
import com.saiayns.sms.service.TermService;

@RestController
@RequestMapping("/api/terms")
public class TermController {
    @Autowired
    private TermService termService;

    @PostMapping("/add")
    public ResponseEntity<Term> createTerm(@RequestBody Term term) {
        return ResponseEntity.ok(termService.createTerm(term));
    }

    @GetMapping("/get")
    public ResponseEntity<List<Term>> getAllTerms() {
        return ResponseEntity.ok(termService.getAllTerms());
    }
}