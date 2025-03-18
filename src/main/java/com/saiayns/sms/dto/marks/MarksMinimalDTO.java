package com.saiayns.sms.dto.marks;

import com.saiayns.sms.dto.SubjectDTO;
import com.saiayns.sms.dto.student.StudentMinimalDTO;

public class MarksMinimalDTO {
	private Long id;
    private int marksObtained;
    private String remarks;
    private StudentMinimalDTO student;
    private SubjectDTO subject;
    private String termName;
    
	public MarksMinimalDTO(Long id, int marksObtained, String remarks, StudentMinimalDTO studentMinimalDTO, SubjectDTO subject,
			String termName) {
		super();
		this.id = id;
		this.marksObtained = marksObtained;
		this.remarks = remarks;
		this.student = studentMinimalDTO;
		this.subject = subject;
		this.termName = termName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getMarksObtained() {
		return marksObtained;
	}
	public void setMarksObtained(int marksObtained) {
		this.marksObtained = marksObtained;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public StudentMinimalDTO getStudent() {
		return student;
	}
	public void setStudent(StudentMinimalDTO student) {
		this.student = student;
	}
	public SubjectDTO getSubject() {
		return subject;
	}
	public void setSubject(SubjectDTO subject) {
		this.subject = subject;
	}
	public String getTermName() {
		return termName;
	}
	public void setTermName(String termName) {
		this.termName = termName;
	}
    
    
}
