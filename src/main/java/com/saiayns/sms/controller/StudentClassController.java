package com.saiayns.sms.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saiayns.sms.model.enums.StudentClass;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "BearerAuth")
@RequestMapping("/api/student-class")
public class StudentClassController {
	
	@GetMapping("/get-list")
	public ResponseEntity<List<StudentClass>> getStudentClassList(){
		return ResponseEntity.ok(StudentClass.getStudentClassAsList());
	} 
}
