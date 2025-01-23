package com.saiayns.sms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.saiayns.sms.model.Student;
import com.saiayns.sms.model.enums.StudentClass;
import com.saiayns.sms.service.StudentService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "BearerAuth")
@RequestMapping("/api/students")
public class StudentController {
	
	@Autowired
	StudentService studentService;

	@GetMapping("/{className}")
	public ResponseEntity<List<Student>> getStudentsByClass(@RequestParam("className") StudentClass studentClass){
		List<Student> studentList = studentService.getAllStudentsByClass(studentClass);
		return ResponseEntity.ok(studentList);
	}
	
	@PostMapping("/add")
	public ResponseEntity<Student> addStudent(@RequestBody Student studentData){
		return ResponseEntity.ok(studentService.addStudent(studentData));
	}
	
	@PutMapping("/update/{student-id}")
	public ResponseEntity<Student> updateStudent(@RequestParam("student-id") Long studentId, @RequestBody Student studentData){
		return ResponseEntity.ok(studentService.updateStudent(studentId, studentData));
	}
	
	@PostMapping(path = "/addUsingExcel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	 public ResponseEntity<String> addStudentsUsingExcel(@RequestParam("file") MultipartFile file) {
		Boolean areCreated = studentService.addStudentsUsingExcel(file);
		return Boolean.TRUE.equals(areCreated) ? 
				ResponseEntity.ok("Successfully added the students data into the database") :
					ResponseEntity.ok("Error!! Couldn't create the data.");
	}
}
