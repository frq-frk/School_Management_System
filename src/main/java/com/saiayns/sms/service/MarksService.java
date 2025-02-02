package com.saiayns.sms.service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saiayns.sms.dto.MarksDTO;
import com.saiayns.sms.model.AcademicYear;
import com.saiayns.sms.model.Marks;
import com.saiayns.sms.model.Student;
import com.saiayns.sms.model.Subject;
import com.saiayns.sms.model.Term;
import com.saiayns.sms.model.enums.StudentClass;
import com.saiayns.sms.notifications.impl.SMSNotificationService;
import com.saiayns.sms.repository.MarksRepository;
import com.saiayns.sms.utils.Helper;

@Service
public class MarksService {

	@Autowired
	private MarksRepository marksRepo;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private TermService termService;
	
	@Autowired
	private SubjectService subService;
	
	@Autowired
	private OTPService otpService;
	
	@Autowired
	private SMSNotificationService smsNotifyService;
	
	@Autowired
	private AcademicYearService academicYearService;
	
	public Marks addMarks(Long studentId, MarksDTO marks) {
		Student studentObject = studentService.getStudentById(studentId).orElseThrow(NoSuchElementException::new);
		Subject subjectObject = subService.getSubjectById(marks.getSubjectId()).orElseThrow(NoSuchElementException::new);
		AcademicYear activeYear = academicYearService.getActiveAcademicYear();
		Marks marksObj = new Marks();
		marksObj.setStudent(studentObject);
		marksObj.setSubject(subjectObject);
		marksObj.setMarksObtained(marks.getMarksObtained());
		marksObj.setRemarks(marks.getRemarks());
		marksObj.setAcademicYear(activeYear);
		return marksRepo.save(marksObj);
	}
	
	public List<Marks> getMarksOfTermByStudent(Long termId, Long studentId){
		Student studentObject = studentService.getStudentById(studentId).orElseThrow(NoSuchElementException::new);
		AcademicYear activeYear = academicYearService.getActiveAcademicYear();
		Term termObject = termService.getTermById(termId).orElseThrow(NoSuchElementException::new);
		return marksRepo.findBySubject_TermAndStudentAndAcademicYear(termObject, studentObject, activeYear);
	}
	
	public List<Marks> getMarksOfTermByClassAndSubject(Long termId, String studentClass, Long subjectId){
		 // Fetch Term and Subject entities from their respective repositories
	    Term term = termService.getTermById(termId)
	            .orElseThrow(() -> new RuntimeException("Term not found with ID: " + termId));
	    Subject subject = subService.getSubjectById(subjectId)
	            .orElseThrow(() -> new RuntimeException("Subject not found with ID: " + subjectId));
	    AcademicYear activeYear = academicYearService.getActiveAcademicYear();

	    // Fetch marks using the repository query
	    return marksRepo.findBySubject_TermAndSubjectAndAcademicYear(term, subject, activeYear).stream()
	            .filter(tempMarks -> tempMarks.getStudent().getStudentClass().equals(StudentClass.valueOf(studentClass)))
	            .toList();
	}
	
	public List<Student> generateOTP(String guardianPhone) {
		List<Student> studentObject = studentService.getStudentsByPhone(guardianPhone);
		String otp = otpService.generateOTP(guardianPhone);
		smsNotifyService.sendNotification("+91"+guardianPhone, Helper.getOTPSMSMessage(otp));
		return studentObject;
	}
	
	public boolean validateOTP(Long studentId, String otp) {
		Student student = studentService.getStudentById(studentId).orElseThrow(NoSuchElementException::new);
		return otpService.validateOTP(student.getGuardianPhone(), otp);
	}
	
	public byte[] generateMarksSheet(Long studentId, Long termId) {
		Student student = studentService.getStudentById(studentId).orElseThrow(NoSuchElementException::new);
		Term term = termService.getTermById(termId).orElseThrow(NoSuchElementException::new);
		AcademicYear activeYear = academicYearService.getActiveAcademicYear();
		List<Marks> marksList = marksRepo.findBySubject_TermAndStudentAndAcademicYear(term, student, activeYear);
		// Generate PDF
        try (PDDocument document = new PDDocument(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // School Name Heading
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 22);
                contentStream.beginText();
                contentStream.newLineAtOffset(200, 750); // Center-aligned heading
                contentStream.showText("XYZ International School");
                contentStream.endText();

                // Add a horizontal line under the heading
                contentStream.setLineWidth(1);
                contentStream.moveTo(50, 740);
                contentStream.lineTo(550, 740);
                contentStream.stroke();

                // Student Details
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 700);
                contentStream.showText("Student Details:");
                contentStream.endText();

                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(70, 680);
                contentStream.setLeading(20f);
                contentStream.showText("Name: " + student.getName());
                contentStream.newLine();
                contentStream.showText("Class: " + student.getStudentClass());
                contentStream.newLine();
                contentStream.showText("Guardian: " + student.getGuardianName());
                contentStream.newLine();
                contentStream.showText("Phone: " + student.getGuardianPhone());
                contentStream.endText();

                // Marks Table Header
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 600);
                contentStream.showText("Marks Details:");
                contentStream.endText();

                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 580);
                contentStream.showText("Subject");
                contentStream.newLineAtOffset(150, 0);
                contentStream.showText("Marks Obtained");
                contentStream.newLineAtOffset(150, 0);
                contentStream.showText("Term");
                contentStream.endText();

                // Draw horizontal line for table header
                contentStream.setLineWidth(0.5f);
                contentStream.moveTo(50, 575);
                contentStream.lineTo(550, 575);
                contentStream.stroke();

                // Marks Table Rows
                int yPosition = 550; // Starting Y position for marks rows
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);

                for (Marks marks : marksList) {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, yPosition);
                    contentStream.showText(marks.getSubject().getName());
                    contentStream.newLineAtOffset(150, 0);
                    contentStream.showText(marks.getMarksObtained().toString());
                    contentStream.newLineAtOffset(150, 0);
                    contentStream.showText(marks.getSubject().getTerm().getName());
                    contentStream.endText();

                    yPosition -= 20; // Move down for the next row
                }

                // End of Content Stream
            }

            // Save and return as a byte array
            document.save(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate marks sheet", e);
        }
	}
}
