package com.saiayns.sms.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saiayns.sms.service.AcademicYearService;
import com.saiayns.sms.tenant.model.AcademicYear;

@RestController
@RequestMapping("/api/academic-year")
public class AcademicYearController {
    @Autowired
    private AcademicYearService academicYearService;

    @GetMapping("/active")
    public ResponseEntity<AcademicYear> getActiveAcademicYear() {
        return ResponseEntity.ok(academicYearService.getActiveAcademicYear());
    }

    @PostMapping("/create")
    public ResponseEntity<AcademicYear> createNewAcademicYear(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return ResponseEntity.ok(academicYearService.createNewAcademicYear(startDate, endDate));
    }
    
    @PostMapping("/close")
    public ResponseEntity<AcademicYear> closeCurrentAcademicYear(){
    	return ResponseEntity.ok(academicYearService.closeCurrentAcademicYear());
    }
}

