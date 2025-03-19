package com.saiayns.sms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saiayns.sms.dto.AttendanceDTO;
import com.saiayns.sms.dto.AttendanceRequest;
import com.saiayns.sms.dto.AttendanceRequestWrapper;
import com.saiayns.sms.model.enums.StudentClass;
import com.saiayns.sms.service.AttendanceService;
import com.saiayns.sms.tenant.model.Attendance;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "BearerAuth")
@RequestMapping("/api/attendance")
public class AttendanceController {
	
	@Autowired
	AttendanceService attendanceService;

	@PostMapping("mark/{student_id}")
	public ResponseEntity<Attendance> markAttendance(@RequestParam("student_id") Long studentId, @RequestBody AttendanceDTO attendanceDetails) {
		return ResponseEntity.ok(attendanceService.markAttendance(attendanceDetails, studentId));
	}
	
	@PostMapping("mark-class/")
    public ResponseEntity<String> markClassAttendance(@RequestParam("studentClass") StudentClass studentClass, @RequestBody AttendanceRequestWrapper requestWrapper) {
        List<AttendanceRequest> attendanceRequests = requestWrapper.getAttendanceRequests();
		attendanceService.markClassAttendance(studentClass, attendanceRequests);
        return ResponseEntity.ok("Attendance marked for the whole class.");
    }
	
	@GetMapping("get/{studentClass}/{date}")
	public ResponseEntity<List<Attendance>> getAttendanceOfClassByDate(@PathVariable("studentClass") StudentClass studentClass, @PathVariable("date") String date){
		return ResponseEntity.ok(attendanceService.getAttendanceOfClassByDate(date, studentClass));
	}
	
	@GetMapping("get/{student_id}/{year}/{month}")
	public ResponseEntity<List<Attendance>> getAttendanceOfStudentByMonth(@RequestParam("student_id") Long studentId, @RequestParam("year") int year, @RequestParam("month") int month){
		return ResponseEntity.ok(attendanceService.getAttendanceOfStudentByMonth(studentId, year, month));
	}
	
}
