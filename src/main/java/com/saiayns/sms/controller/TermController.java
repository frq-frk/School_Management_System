package com.saiayns.sms.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saiayns.sms.model.Term;
import com.saiayns.sms.service.TermService;

@RestController
@RequestMapping("/api/terms")
public class TermController {
    @Autowired
    private TermService termService;

    @PostMapping("/add")
    public ResponseEntity<Term> createTerm(@RequestParam String name,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam String desciprtion
    		) {
        return ResponseEntity.ok(termService.createTerm(name, startDate, endDate, desciprtion));
    }

    @GetMapping("/get")
    public ResponseEntity<List<Term>> getAllTerms() {
        return ResponseEntity.ok(termService.getAllTerms());
    }
}