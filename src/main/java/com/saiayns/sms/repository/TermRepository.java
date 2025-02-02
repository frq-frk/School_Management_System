package com.saiayns.sms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saiayns.sms.model.Term;

public interface TermRepository extends JpaRepository<Term, Long>{
	List<Term> findByAcademicYearId(Long academicYearId);
}
