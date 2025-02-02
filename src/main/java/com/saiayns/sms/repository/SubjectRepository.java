package com.saiayns.sms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saiayns.sms.model.AcademicYear;
import com.saiayns.sms.model.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long>{

	List<Subject> findByTermIdAndAcademicYear(Long termId, AcademicYear academicYear);

}
