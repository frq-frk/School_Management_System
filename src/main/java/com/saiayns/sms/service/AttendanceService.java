package com.saiayns.sms.service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saiayns.sms.dto.AttendanceDTO;
import com.saiayns.sms.dto.AttendanceRequest;
import com.saiayns.sms.model.enums.AttendanceStatusEnum;
import com.saiayns.sms.model.enums.StudentClass;
import com.saiayns.sms.notifications.impl.SMSNotificationService;
import com.saiayns.sms.tenant.model.AcademicYear;
import com.saiayns.sms.tenant.model.Attendance;
import com.saiayns.sms.tenant.model.Student;
import com.saiayns.sms.tenant.repository.AttendanceRepository;
import com.saiayns.sms.utils.Helper;

import jakarta.transaction.Transactional;

@Service
public class AttendanceService {

	@Autowired
	private AttendanceRepository attendanceRepo;

	@Autowired
	private StudentService studentService;

	@Autowired
	private SMSNotificationService smsService;
	
	@Autowired
	AcademicYearService academicYearService;

	public Attendance markAttendance(AttendanceDTO attendanceDetails, Long studentId) {
		Student studentObject = studentService.getStudentById(studentId).orElseThrow(NoSuchElementException::new);
		AcademicYear activeYear = academicYearService.getActiveAcademicYear();
		Attendance attendance = new Attendance();
		attendance.setStudent(studentObject);
		attendance.setDate(attendanceDetails.getDate());
		attendance.setStatus(attendanceDetails.getStatus());
		attendance.setAcademicYear(activeYear);
		if (attendanceDetails.getStatus().equals(AttendanceStatusEnum.ABSENT)) {
			String msg = Helper.getAbsentSMSMessage(studentObject.getGuardianName(), studentObject.getName(), attendanceDetails.getDate().toString(), studentObject.getStudentClass().toString(), "Test School");
			smsService.sendNotification("+91"+studentObject.getGuardianPhone(), msg);
		}
		return attendanceRepo.save(attendance);
	}
	
	@Transactional
    public void markClassAttendance(StudentClass studentClass, List<AttendanceRequest> attendanceRequests) {
		AcademicYear activeYear = academicYearService.getActiveAcademicYear();
		for (AttendanceRequest request : attendanceRequests) {
            // Validate student exists and belongs to the specified class
            Student student = studentService.getStudentById(request.getStudentId())
                    .orElseThrow(() -> new RuntimeException("Student not found with ID: " + request.getStudentId()));

            if (!student.getStudentClass().equals(studentClass)) {
                throw new RuntimeException("Student ID " + request.getStudentId() + " does not belong to class " + studentClass);
            }

            // Create and save attendance record
            Attendance attendance = new Attendance();
            attendance.setStudent(student);
            attendance.setDate(request.getDate());
            attendance.setStatus(request.getStatus());
            attendance.setAcademicYear(activeYear);
            if (request.getStatus().equals(AttendanceStatusEnum.ABSENT)) {
    			String msg = Helper.getAbsentSMSMessage(student.getGuardianName(), student.getName(), request.getDate().toString(), student.getStudentClass().toString(), "Test School");
    			smsService.sendNotification("+91"+student.getGuardianPhone(), msg);
    		}
            attendanceRepo.save(attendance);
        }
    }

	public List<Attendance> getAttendanceOfClassByDate(String date, StudentClass studentClass) {
		return attendanceRepo.findByDate(LocalDate.parse(date)).stream().filter(
				att -> att.getStudent().getStudentClass().equals(studentClass))
				.toList();
	}

	public List<Attendance> getAttendanceOfStudentByMonth(Long studentId, int year, int month) {
		Student student = studentService.getStudentById(studentId).orElseThrow(NoSuchElementException::new);
		AcademicYear activeYear = academicYearService.getActiveAcademicYear();
		return attendanceRepo.findByStudentAndAcademicYear(student, activeYear).stream()
				.filter(att -> att.getDate().getYear() == year && att.getDate().getMonthValue() == month).toList();
	}

}
