package com.saiayns.sms.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saiayns.sms.model.Attendance;
import com.saiayns.sms.model.Student;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

	List<Attendance> findByStudent(Student student);
    List<Attendance> findByDate(LocalDate date);
}
