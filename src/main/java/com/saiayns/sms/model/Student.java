package com.saiayns.sms.model;

import com.saiayns.sms.model.enums.StudentClass;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Student {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private StudentClass studentClass;
    private String guardianName;
    private String guardianPhone;
    
//	public Student(String name, String studentClass, String guardianName, String guardianPhone) {
//		super();
//		this.name = name;
//		this.studentClass = studentClass;
//		this.guardianName = guardianName;
//		this.guardianPhone = guardianPhone;
//	}
	
	public Student() {
		super();
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public StudentClass getStudentClass() {
		return studentClass;
	}
	public void setStudentClass(StudentClass studentClass) {
		this.studentClass = studentClass;
	}
	public String getGuardianName() {
		return guardianName;
	}
	public void setGuardianName(String guardianName) {
		this.guardianName = guardianName;
	}
	public String getGuardianPhone() {
		return guardianPhone;
	}
	public void setGuardianPhone(String guardianPhone) {
		this.guardianPhone = guardianPhone;
	}
}
