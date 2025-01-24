package com.saiayns.sms.dto;

import java.time.LocalDate;

import com.saiayns.sms.model.enums.AttendanceStatusEnum;

public class AttendanceRequest {
    private Long studentId;
    private LocalDate date;
    private AttendanceStatusEnum status;

    // Getters and Setters
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

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

