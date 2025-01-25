package com.saiayns.sms.dto;
import java.util.List;

public class AttendanceRequestWrapper {
    private List<AttendanceRequest> attendanceRequests;

    // Getters and Setters
    public List<AttendanceRequest> getAttendanceRequests() {
        return attendanceRequests;
    }

    public void setAttendanceRequests(List<AttendanceRequest> attendanceRequests) {
        this.attendanceRequests = attendanceRequests;
    }
}
