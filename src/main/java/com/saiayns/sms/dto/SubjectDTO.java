package com.saiayns.sms.dto;

import com.saiayns.sms.model.Subject;

public class SubjectDTO {
	private String name;
	private String subCode;
    private Integer maxMarks;
    private Integer passMarks;
    private Long termId;
    
	public SubjectDTO(String name, String subCode, Integer maxMarks, Integer passMarks) {
		super();
		this.name = name;
		this.subCode = subCode;
		this.maxMarks = maxMarks;
		this.passMarks = passMarks;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getMaxMarks() {
		return maxMarks;
	}
	public void setMaxMarks(Integer maxMarks) {
		this.maxMarks = maxMarks;
	}
	public Long getTermId() {
		return termId;
	}
	public void setTermId(Long termId) {
		this.termId = termId;
	}
	public String getSubCode() {
		return subCode;
	}
	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}
	public Integer getPassMarks() {
		return passMarks;
	}
	public void setPassMarks(Integer passMarks) {
		this.passMarks = passMarks;
	}
	
	public static SubjectDTO toDTO(Subject subject) {
		return new SubjectDTO(subject.getName(), subject.getSubCode(), subject.getMaxMarks(), subject.getPassMarks());
	}
	
}
