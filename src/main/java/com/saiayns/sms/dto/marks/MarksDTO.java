package com.saiayns.sms.dto.marks;

public class MarksDTO {
	private Long studentId;
	private Long subjectId;
    private Integer marksObtained;
    private String remarks;
    
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public Long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Long subject_id) {
		this.subjectId = subject_id;
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
	
}
