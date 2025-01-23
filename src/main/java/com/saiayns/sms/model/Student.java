package com.saiayns.sms.model;

import java.time.LocalDate;

import com.saiayns.sms.model.enums.StudentClass;
import com.saiayns.sms.model.enums.StudentGender;

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
    private String guardianEmail;
    
    @Enumerated(EnumType.STRING)
    private StudentGender gender;
    private LocalDate dateOfBirth;
    private String address;
	
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

	public String getGuardianEmail() {
		return guardianEmail;
	}

	public void setGuardianEmail(String guardianEmail) {
		this.guardianEmail = guardianEmail;
	}

	public StudentGender getGender() {
		return gender;
	}

	public void setGender(StudentGender gender) {
		this.gender = gender;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
