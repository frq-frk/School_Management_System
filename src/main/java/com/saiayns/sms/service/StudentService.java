package com.saiayns.sms.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.saiayns.sms.model.Student;
import com.saiayns.sms.model.enums.StudentClass;
import com.saiayns.sms.repository.StudentRepository;
import com.saiayns.sms.utils.ExcelHelper;

@Service
public class StudentService {

	@Autowired
	StudentRepository studentRepo;
	
	public List<Student> getAllStudents(){
		return studentRepo.findAll();
	}
	
	public List<Student> getAllStudentsByClass(StudentClass studentClass){
		return studentRepo.findByStudentClass(studentClass);
	}
	
	public Student addStudent(Student studentDetails) {
		return studentRepo.save(studentDetails);
	}
	
	public Optional<Student> getStudentById(Long id){
		return studentRepo.findById(id);
	}
	
	public Student updateStudent(Long studentId, Student updatedDetails) {
		Optional<Student> existingStudentOpt = studentRepo.findById(studentId);

        if (existingStudentOpt.isEmpty()) {
            throw new IllegalArgumentException("Student with ID " + studentId + " not found.");
        }

        Student existingStudent = existingStudentOpt.get();
        existingStudent.setName(updatedDetails.getName());
        existingStudent.setStudentClass(updatedDetails.getStudentClass());
        existingStudent.setGuardianName(updatedDetails.getGuardianName());
        existingStudent.setGuardianPhone(updatedDetails.getGuardianPhone());

        return studentRepo.save(existingStudent);
	}
	
	public boolean addStudentsUsingExcel(MultipartFile file) {
		try {
			List<Student> studentList = ExcelHelper.parseStudentExcelFile(file.getInputStream());
			studentRepo.saveAll(studentList);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void deleteStudent(Long id) {
		studentRepo.deleteById(id);
	}
}
