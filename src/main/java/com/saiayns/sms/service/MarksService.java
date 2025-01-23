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
import com.saiayns.sms.model.Marks;
import com.saiayns.sms.model.Student;
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
	private OTPService otpService;
	
	@Autowired
	private SMSNotificationService smsNotifyService;
	
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
	
	public List<Student> generateOTP(String guardianPhone) {
		List<Student> studentObject = studentService.getStudentsByPhone(guardianPhone);
		String otp = otpService.generateOTP(guardianPhone);
		smsNotifyService.sendNotification("+916281276093", Helper.getOTPSMSMessage(otp));
		return studentObject;
	}
	
	public boolean validateOTP(Long studentId, String otp) {
		Student student = studentService.getStudentById(studentId).orElseThrow(NoSuchElementException::new);
		return otpService.validateOTP(student.getGuardianPhone(), otp);
	}
	
	public byte[] generateMarksSheet(Long studentId, String term) {
		Student student = studentService.getStudentById(studentId).orElseThrow(NoSuchElementException::new);
		List<Marks> marksList = marksRepo.findByTermAndStudent(term, student);
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
                    contentStream.showText(marks.getSubject());
                    contentStream.newLineAtOffset(150, 0);
                    contentStream.showText(marks.getMarksObtained().toString());
                    contentStream.newLineAtOffset(150, 0);
                    contentStream.showText(marks.getTerm());
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
