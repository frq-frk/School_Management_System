package com.saiayns.sms.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saiayns.sms.dto.AttendanceDTO;
import com.saiayns.sms.model.Attendance;
import com.saiayns.sms.model.enums.StudentClass;
import com.saiayns.sms.service.AttendanceService;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceConctroller {
	
	@Autowired
	AttendanceService attendanceService;

	@PostMapping("mark/{student_id}")
	public ResponseEntity<Attendance> markAttendance(@RequestParam("student_id") Long studentId, @RequestBody AttendanceDTO attendanceDetails) {
		return ResponseEntity.ok(attendanceService.markAttendance(attendanceDetails, studentId));
	}
	
	@GetMapping("get/{studentClass}/{date}")
	public ResponseEntity<List<Attendance>> getAttendanceOfClassByDate(@RequestParam("studentClass") StudentClass studentClass, @RequestParam("date") String date){
		return ResponseEntity.ok(attendanceService.getAttendanceOfClassByDate(date, studentClass));
	}
	
	@GetMapping("get/{student_id}/{year}/{month}")
	public ResponseEntity<List<Attendance>> getAttendanceOfStudentByMonth(@RequestParam("student_id") Long studentId, @RequestParam("year") int year, @RequestParam("month") int month){
		return ResponseEntity.ok(attendanceService.getAttendanceOfStudentByMonth(studentId, year, month));
	}
	
}
