package com.saiayns.sms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saiayns.sms.model.Marks;
import com.saiayns.sms.model.Student;

public interface MarksRepository extends JpaRepository<Marks, Long> {

	List<Marks> findByTermAndStudent(String term, Student student);
	List<Marks> findByTermAndSubject(String term, String Subject);
}
