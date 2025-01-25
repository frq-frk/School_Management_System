package com.saiayns.sms.model.enums;

import java.util.Arrays;
import java.util.List;

public enum StudentClass {
	I_CLASS,
	II_CLASS,
	III_CLASS,
	IV_CLASS,
	V_CLASS,
	VI_CLASS,
	VII_CLASS,
	VIII_CLASS,
	IX_CLASS,
	X_CLASS,
	XI_CLASS,
	XII_CLASS;
	
	private static final StudentClass[] VALUES = values();
	
	public StudentClass getNext() {
        int currentIndex = this.ordinal();
        int nextIndex = (currentIndex + 1) % VALUES.length; // Circular navigation
        return VALUES[nextIndex];
    }
	
	public static List<StudentClass> getStudentClassAsList() {
		return Arrays.asList(VALUES);
	}
}
