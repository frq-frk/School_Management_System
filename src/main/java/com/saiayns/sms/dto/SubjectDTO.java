package com.saiayns.sms.dto;

import com.saiayns.sms.tenant.model.Subject;

public class SubjectDTO {
	private Long id;
	private String name;
	private String subCode;
    private Integer maxMarks;
    private Integer passMarks;
    private Long termId;
    
	public SubjectDTO(Long id, String name, String subCode, Integer maxMarks, Integer passMarks) {
		super();
		this.name = name;
		this.subCode = subCode;
		this.maxMarks = maxMarks;
		this.passMarks = passMarks;
		this.id = id;
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
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public static SubjectDTO toDTO(Subject subject) {
		return new SubjectDTO(subject.getId(), subject.getName(), subject.getSubCode(), subject.getMaxMarks(), subject.getPassMarks());
	}
	
}
