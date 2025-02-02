package com.saiayns.sms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saiayns.sms.model.AcademicYear;
import com.saiayns.sms.model.Marks;
import com.saiayns.sms.model.Student;
import com.saiayns.sms.model.Subject;
import com.saiayns.sms.model.Term;


public interface MarksRepository extends JpaRepository<Marks, Long> {

    // Find marks by term and student within an academic year
    List<Marks> findBySubject_TermAndStudentAndAcademicYear(Term term, Student student, AcademicYear academicYear);

    // Find marks by term and subject within an academic year
    List<Marks> findBySubject_TermAndSubjectAndAcademicYear(Term term, Subject subject, AcademicYear academicYear);

    // Find marks by subject within an academic year
    List<Marks> findBySubjectAndAcademicYear(Subject subject, AcademicYear academicYear);

    // Find marks by student within an academic year
    List<Marks> findByStudentAndAcademicYear(Student student, AcademicYear academicYear);

    // Find marks for a specific academic year
    List<Marks> findByAcademicYear(AcademicYear academicYear);
}
