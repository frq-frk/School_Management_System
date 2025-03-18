package com.saiayns.sms.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.saiayns.sms.dto.student.StudentDTO;
import com.saiayns.sms.model.AcademicYear;
import com.saiayns.sms.model.Student;
import com.saiayns.sms.model.enums.StudentClass;
import com.saiayns.sms.repository.StudentRepository;
import com.saiayns.sms.utils.ExcelHelper;

@Service
public class StudentService {

	@Autowired
	StudentRepository studentRepo;

	@Autowired
	AcademicYearService academicYearService;

	public List<Student> getAllStudents() {
		AcademicYear activeYear = academicYearService.getActiveAcademicYear();
		return studentRepo.findByAcademicYear(activeYear);
	}

	public List<Student> getAllStudentsByClass(StudentClass studentClass) {
		AcademicYear activeYear = academicYearService.getActiveAcademicYear();
		return studentRepo.findByStudentClassAndAcademicYear(studentClass, activeYear);
	}

	public Student addStudent(StudentDTO studentData) {
		AcademicYear activeYear = academicYearService.getActiveAcademicYear();
		Student studentDetails = new Student(studentData.getName(), studentData.getStudentClass(),
				studentData.getGuardianName(), studentData.getGuardianPhone(), studentData.getGuardianEmail(),
				studentData.getGender(), studentData.getDateOfBirth(), studentData.getAddress(), activeYear);
		return studentRepo.save(studentDetails);
	}

	public Optional<Student> getStudentById(Long id) {
		return studentRepo.findById(id);
	}

	public List<Student> getStudentsByPhone(String guardianPhone) {
		AcademicYear activeYear = academicYearService.getActiveAcademicYear();
		return studentRepo.findByGuardianPhoneAndAcademicYear(guardianPhone, activeYear);
	}

	public Student updateStudent(Long studentId, StudentDTO updatedDetails) {
		Optional<Student> existingStudentOpt = studentRepo.findById(studentId);

		if (existingStudentOpt.isEmpty()) {
			throw new IllegalArgumentException("Student with ID " + studentId + " not found.");
		}

		Student existingStudent = existingStudentOpt.get();
		existingStudent.setName(updatedDetails.getName());
		existingStudent.setStudentClass(updatedDetails.getStudentClass());
		existingStudent.setGuardianName(updatedDetails.getGuardianName());
		existingStudent.setGuardianPhone(updatedDetails.getGuardianPhone());
		existingStudent.setGuardianEmail(updatedDetails.getGuardianEmail());
		existingStudent.setGender(updatedDetails.getGender());
		existingStudent.setAddress(updatedDetails.getAddress());
		existingStudent.setDateOfBirth(updatedDetails.getDateOfBirth());

		return studentRepo.save(existingStudent);
	}

	public boolean addStudentsUsingExcel(MultipartFile file) {
		try {
			List<Student> studentList = ExcelHelper.parseStudentExcelFile(file.getInputStream());
			AcademicYear activeYear = academicYearService.getActiveAcademicYear();
			studentList.stream().forEach(student -> student.setAcademicYear(activeYear));
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
	
	public List<Student> getStudentsByClassFromRecentlyClosedAcademicYear(StudentClass studentClass) {
	    // Fetch the most recently closed academic year
	    AcademicYear recentClosedYear = academicYearService.getRecenltyClosedAcademicYear();

	    // Fetch students linked to that academic year and class
	    return studentRepo.findByStudentClassAndAcademicYear(studentClass, recentClosedYear);
	}

	public List<Student> getAllStudentsFromRecentlyClosedAcademicYear() {
	    // Fetch the most recently closed academic year
	    AcademicYear recentClosedYear = academicYearService.getRecenltyClosedAcademicYear();;

	    // Fetch students linked to that academic year
	    return studentRepo.findByAcademicYear(recentClosedYear);
	}

	
	public void promoteStudents(List<Long> studentIds){
		// Fetch students
	    List<Student> students = studentRepo.findAllById(studentIds);

	    if (students.isEmpty()) {
	         throw new IllegalStateException("No students found for promotion.");
	    }

	    // Get their academic year (assuming all belong to the same year)
	    AcademicYear oldAcademicYear = students.get(0).getAcademicYear();

	    // Get the new academic year
	    AcademicYear newAcademicYear = academicYearService.getActiveAcademicYear();

	    // Check if the new and old academic years are the same
	    if (oldAcademicYear.equals(newAcademicYear)) {
	    	throw new IllegalStateException("Please close the current academic year and create a new one before promoting students.");
	    }

	    // Promote students
	    for (Student student : students) {
	        StudentClass newClass = student.getStudentClass().getNext(); // Assuming a method to get the next class
	        student.setStudentClass(newClass);
	        student.setAcademicYear(newAcademicYear);
	    }

	    studentRepo.saveAll(students);
	}
}
