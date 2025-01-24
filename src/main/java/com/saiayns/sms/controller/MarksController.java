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
import com.saiayns.sms.model.Student;
import com.saiayns.sms.service.MarksService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "BearerAuth")
@RequestMapping("/api/marks")
public class MarksController {

	@Autowired
	MarksService marksService;

	@PostMapping("/add/{student-id}")
	public ResponseEntity<Marks> addMarks(@RequestBody MarksDTO marksDetails,
			@RequestParam("student-id") Long studentId) {
		return ResponseEntity.ok(marksService.addMarks(studentId, marksDetails));
	}

	@GetMapping("/get/{term}/{student-id}")
	public ResponseEntity<List<Marks>> getMarksOfTermByStudent(@RequestParam("term") Long term,
			@RequestParam("student-id") Long studentId) {
		return ResponseEntity.ok(marksService.getMarksOfTermByStudent(term, studentId));
	}

	@GetMapping("/get/{term}/{class}/{subject}")
	public ResponseEntity<List<Marks>> getMarksOfTermBySubject(@RequestParam("term") Long term,
			@RequestParam("class") String studentClass, @RequestParam("subject") Long subject) {
		return ResponseEntity.ok(marksService.getMarksOfTermByClassAndSubject(term, studentClass, subject));
	}

	@PostMapping("/download-request")
	public ResponseEntity<List<Student>> requestMarksSheet(@RequestParam("guardianPhone") String guardianPhone) {
		List<Student> studentsList =  marksService.generateOTP(guardianPhone);
		return ResponseEntity.ok(studentsList);
	}

	@PostMapping("/download-validate")
	public ResponseEntity<?> validateAndDownloadMarksSheet(@RequestParam Long studentId, @RequestParam long term,
			@RequestParam String otp) {
		// Validate OTP
		boolean isValid = marksService.validateOTP(studentId, otp);
		if (!isValid) {
			return ResponseEntity.status(400).body("Invalid or expired OTP");
		}

		// Fetch marks and generate marks sheet
		byte[] marksSheet = marksService.generateMarksSheet(studentId, term);

		// Return marks sheet as downloadable file
		return ResponseEntity.ok()
				.header("Content-Disposition", "attachment; filename=marks_sheet_student_" + studentId + ".pdf")
				.body(marksSheet);
	}
}
