package com.saiayns.sms.dto;

import java.time.LocalDate;

import com.saiayns.sms.model.enums.AttendanceStatusEnum;

public class AttendanceDTO {
	private LocalDate date = LocalDate.now();
    private AttendanceStatusEnum status;
    
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public AttendanceStatusEnum getStatus() {
		return status;
	}
	public void setStatus(AttendanceStatusEnum status) {
		this.status = status;
	}
}
