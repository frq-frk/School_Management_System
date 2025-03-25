package com.saiayns.sms.tenant.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.saiayns.sms.dto.student.StudentDTO;
import com.saiayns.sms.model.enums.StudentClass;
import com.saiayns.sms.service.StudentService;
import com.saiayns.sms.tenant.model.Student;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "BearerAuth")
@RequestMapping("/api/students")
public class StudentController {
	
	@Autowired
	StudentService studentService;

	@GetMapping("/{className}")
	public ResponseEntity<List<Student>> getStudentsByClass(@PathVariable("className") StudentClass studentClass){
		List<Student> studentList = studentService.getAllStudentsByClass(studentClass);
		return ResponseEntity.ok(studentList);
	}
	
	@GetMapping("/get")
	public ResponseEntity<List<Student>> getAllStudents(){
		List<Student> studentList = studentService.getAllStudents();
		return ResponseEntity.ok(studentList);
	}
	
	@PostMapping("/add")
	public ResponseEntity<Student> addStudent(@RequestBody StudentDTO studentData){
		return ResponseEntity.ok(studentService.addStudent(studentData));
	}
	
	@PutMapping("/update")
	public ResponseEntity<Student> updateStudent(@RequestParam("studentId") Long studentId, @RequestBody StudentDTO studentData){
		return ResponseEntity.ok(studentService.updateStudent(studentId, studentData));
	}
	
	@PostMapping(path = "/addUsingExcel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	 public ResponseEntity<String> addStudentsUsingExcel(@RequestParam("file") MultipartFile file) {
		Boolean areCreated = studentService.addStudentsUsingExcel(file);
		return Boolean.TRUE.equals(areCreated) ? 
				ResponseEntity.ok("Successfully added the students data into the database") :
					ResponseEntity.ok("Error!! Couldn't create the data.");
	}
	
	@PutMapping("/promote")
	public ResponseEntity<String> promoteStudents(@RequestBody List<Long> studentIds) {
		studentService.promoteStudents(studentIds);
		return ResponseEntity.ok("Students promoted successfully.");
	}
	
	@GetMapping("/get-recently-closed/{className}")
	public ResponseEntity<List<Student>> getStudentsByClassFromRecentlyClosedAcademicYear(
	        @PathVariable("className") StudentClass studentClass) {
	    
	    List<Student> studentList = studentService.getStudentsByClassFromRecentlyClosedAcademicYear(studentClass);
	    return ResponseEntity.ok(studentList);
	}

	@GetMapping("/get-recently-closed")
	public ResponseEntity<List<Student>> getAllStudentsFromRecentlyClosedAcademicYear() {
	    List<Student> studentList = studentService.getAllStudentsFromRecentlyClosedAcademicYear();
	    return ResponseEntity.ok(studentList);
	}

}
