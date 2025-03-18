package com.saiayns.sms.dto.student;

import java.time.LocalDate;

import com.saiayns.sms.model.enums.StudentClass;
import com.saiayns.sms.model.enums.StudentGender;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class StudentDTO {
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
    
	public StudentDTO(String name, StudentClass studentClass, String guardianName, String guardianPhone,
			String guardianEmail, StudentGender gender, LocalDate dateOfBirth, String address) {
		super();
		this.name = name;
		this.studentClass = studentClass;
		this.guardianName = guardianName;
		this.guardianPhone = guardianPhone;
		this.guardianEmail = guardianEmail;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
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
