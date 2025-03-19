package com.saiayns.sms.tenant.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saiayns.sms.tenant.model.AcademicYear;
import com.saiayns.sms.tenant.model.Attendance;
import com.saiayns.sms.tenant.model.Student;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

	List<Attendance> findByStudentAndAcademicYear(Student student, AcademicYear academicYear);
    List<Attendance> findByDate(LocalDate date);
}
