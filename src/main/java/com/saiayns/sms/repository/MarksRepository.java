package com.saiayns.sms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saiayns.sms.model.Marks;
import com.saiayns.sms.model.Student;
import com.saiayns.sms.model.Subject;
import com.saiayns.sms.model.Term;

public interface MarksRepository extends JpaRepository<Marks, Long> {

	// Find marks by term and student
    List<Marks> findBySubject_TermAndStudent(Term term, Student student);

    // Find marks by term and subject
    List<Marks> findBySubject_TermAndSubject(Term term, Subject subject);

    // Find marks by subject
    List<Marks> findBySubject(Subject subject);

    // Find marks by student
    List<Marks> findByStudent(Student student);
}
