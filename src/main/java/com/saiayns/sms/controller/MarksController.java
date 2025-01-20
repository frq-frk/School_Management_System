package com.saiayns.sms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saiayns.sms.dto.MarksDTO;
import com.saiayns.sms.model.Marks;
import com.saiayns.sms.service.MarksService;

@RestController
@RequestMapping("/api/marks")
public class MarksController {
	
	@Autowired
	MarksService marksService;
	
	@PostMapping("/add/{student-id}")
	public ResponseEntity<Marks> addMarks(@RequestBody MarksDTO marksDetails, @RequestParam("student-id") Long studentId){
		return ResponseEntity.ok(marksService.addMarks(studentId, marksDetails));
	}
	
	@GetMapping("/get/{term}/{student-id}")
	public ResponseEntity<List<Marks>> getMarksOfTermByStudent(@RequestParam("term") String term, @RequestParam("student-id") Long studentId ){
		return ResponseEntity.ok(marksService.getMarksOfTermByStudent(term, studentId));
	}
	
	@GetMapping("/get/{term}/{class}/{subject}")
	public ResponseEntity<List<Marks>> getMarksOfTermBySubject(@RequestParam("term") String term, @RequestParam("class") String studentClass, @RequestParam("subject") String subject ){
		return ResponseEntity.ok(marksService.getMarksOfTermByClassAndSubject(term, studentClass, subject));
	}
}
