package com.saiayns.sms.service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saiayns.sms.dto.AttendanceDTO;
import com.saiayns.sms.model.Attendance;
import com.saiayns.sms.model.Student;
import com.saiayns.sms.model.enums.StudentClass;
import com.saiayns.sms.repository.AttendanceRepository;

@Service
public class AttendanceService {
	
	@Autowired
    private AttendanceRepository attendanceRepo;
	
	@Autowired
	private StudentService studentService;

    public Attendance markAttendance(AttendanceDTO attendanceDetails, Long studentId) {
    	Student studentObject = studentService.getStudentById(studentId).orElseThrow(NoSuchElementException::new);
        Attendance attendance = new Attendance();
        attendance.setStudent(studentObject);
        attendance.setDate(attendanceDetails.getDate());
        attendance.setStatus(attendanceDetails.getStatus());
    	return attendanceRepo.save(attendance);
    }
    
    public List<Attendance> getAttendanceOfClassByDate(String date, StudentClass studentClass) {
        return attendanceRepo.findAll().stream()
                .filter(att -> att.getDate().toString().equals(date) &&
                		att.getStudent().getStudentClass().equals(studentClass))
                .toList();
    }
    
    public List<Attendance> getAttendanceOfStudentByMonth(Long studentId, int year, int month) {
    	Student student = studentService.getStudentById(studentId).orElseThrow(NoSuchElementException::new);
        return attendanceRepo.findByStudent(student).stream()
                .filter(att -> att.getDate().getYear() == year && att.getDate().getMonthValue() == month)
                .toList();
    }

}
