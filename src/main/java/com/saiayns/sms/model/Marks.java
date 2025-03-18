package com.saiayns.sms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Marks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;
    private Integer marksObtained;
    private String remarks;
    
    @ManyToOne
    @JoinColumn(name = "academic_year_id", nullable = false)
    private AcademicYear academicYear;
    
	public Marks(Student student, Subject subject, Integer marksObtained, String remarks, AcademicYear academicYear) {
		super();
		this.student = student;
		this.subject = subject;
		this.marksObtained = marksObtained;
		this.remarks = remarks;
		this.academicYear = academicYear;
	}
	public Marks() {}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public Integer getMarksObtained() {
		return marksObtained;
	}
	public void setMarksObtained(Integer marksObtained) {
		this.marksObtained = marksObtained;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public AcademicYear getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(AcademicYear academicYear) {
		this.academicYear = academicYear;
	}
	
}
