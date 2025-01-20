package com.saiayns.sms.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saiayns.sms.dto.MarksDTO;
import com.saiayns.sms.model.Marks;
import com.saiayns.sms.model.Student;
import com.saiayns.sms.model.enums.StudentClass;
import com.saiayns.sms.repository.MarksRepository;

@Service
public class MarksService {

	@Autowired
	private MarksRepository marksRepo;
	
	@Autowired
	private StudentService studentService;
	
	public Marks addMarks(Long studentId, MarksDTO marks) {
		Student studentObject = studentService.getStudentById(studentId).orElseThrow(NoSuchElementException::new);
		Marks marksObj = new Marks();
		marksObj.setStudent(studentObject);
		marksObj.setSubject(marks.getSubject());
		marksObj.setTerm(marks.getTerm());
		marksObj.setMarksObtained(marks.getMarksObtained());
		return marksRepo.save(marksObj);
	}
	
	public List<Marks> getMarksOfTermByStudent(String term, Long studentId){
		Student studentObject = studentService.getStudentById(studentId).orElseThrow(NoSuchElementException::new);
		return marksRepo.findByTermAndStudent(term, studentObject);
	}
	
	public List<Marks> getMarksOfTermByClassAndSubject(String term, String studentClass, String subject){
		return marksRepo.findByTermAndSubject(term, subject).stream()
				.filter(tempMarks -> tempMarks.getStudent().getStudentClass().equals(StudentClass.valueOf(studentClass)))
				.toList();
	}
}
