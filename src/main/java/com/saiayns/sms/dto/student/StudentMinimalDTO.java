package com.saiayns.sms.dto.student;

import com.saiayns.sms.model.enums.StudentClass;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class StudentMinimalDTO {
	private Long id;
	private String name;
    @Enumerated(EnumType.STRING)
    private StudentClass studentClass;
    
	public StudentMinimalDTO(Long id, String name, StudentClass studentClass) {
		super();
		this.id = id;
		this.name = name;
		this.studentClass = studentClass;
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
}
