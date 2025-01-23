package com.saiayns.sms.utils;

public class Helper {

	public static String getAbsentSMSMessage(String guardianName, String studentName, String date, String studentClass,
			String schoolName) {
		return String.format(
				"Dear %s, %nThis is to inform you that %s was marked **ABSENT** on %s for %s.%nIf you have any concerns, please contact the school administration.%n%nThank you,%n%s",
				guardianName, studentName, date, studentClass, schoolName
		);
	}
	
	public static String getOTPSMSMessage(String otp) {
		return String.format("This is %s",otp);
	}
}
