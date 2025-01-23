package com.saiayns.sms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saiayns.sms.model.Student;
import com.saiayns.sms.model.enums.StudentClass;

public interface StudentRepository extends JpaRepository<Student, Long>{
	
	List<Student> findByStudentClass(StudentClass studentClass);
	
	List<Student> findByGuardianPhone(String guardianPhone);
}
