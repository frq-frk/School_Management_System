package com.saiayns.sms.dto;

public class MarksDTO {
	private Long subject_id;
    private Integer marksObtained;
    private String term;
    private String remarks;
    
	public Long getSubjectId() {
		return subject_id;
	}
	public void setSubjectId(Long subject_id) {
		this.subject_id = subject_id;
	}
	public Integer getMarksObtained() {
		return marksObtained;
	}
	public void setMarksObtained(Integer marksObtained) {
		this.marksObtained = marksObtained;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
