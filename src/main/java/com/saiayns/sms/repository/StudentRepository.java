package com.saiayns.sms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.saiayns.sms.model.AcademicYear;
import com.saiayns.sms.model.Student;
import com.saiayns.sms.model.enums.StudentClass;

public interface StudentRepository extends JpaRepository<Student, Long>{
	
	List<Student> findByStudentClassAndAcademicYear(StudentClass studentClass, AcademicYear academicYear);
	List<Student> findByAcademicYear(AcademicYear academicYear);
	List<Student> findByGuardianPhoneAndAcademicYear(String guardianPhone, AcademicYear academicYear);
	
	@Query("SELECT s FROM Student s WHERE s.academicYear IN (SELECT a FROM AcademicYear a WHERE a.isRecentlyClosed = true)")
    List<Student> findStudentsFromRecentlyClosedAcademicYear();
}
