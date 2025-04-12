package com.saiayns.sms.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saiayns.sms.dto.SubjectDTO;
import com.saiayns.sms.dto.marks.MarksDTO;
import com.saiayns.sms.dto.marks.MarksMinimalDTO;
import com.saiayns.sms.dto.student.StudentMinimalDTO;
import com.saiayns.sms.model.enums.StudentClass;
import com.saiayns.sms.notifications.impl.SMSNotificationService;
import com.saiayns.sms.tenant.model.AcademicYear;
import com.saiayns.sms.tenant.model.Marks;
import com.saiayns.sms.tenant.model.Student;
import com.saiayns.sms.tenant.model.Subject;
import com.saiayns.sms.tenant.model.Term;
import com.saiayns.sms.tenant.repository.MarksRepository;
import com.saiayns.sms.utils.SmsNotificationHelper;

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

	public Marks addMarks(MarksDTO marks) {
		Student studentObject = studentService.getStudentById(marks.getStudentId())
				.orElseThrow(NoSuchElementException::new);
		Subject subjectObject = subService.getSubjectById(marks.getSubjectId())
				.orElseThrow(NoSuchElementException::new);
		AcademicYear activeYear = academicYearService.getActiveAcademicYear();
		Marks marksObj = new Marks();
		marksObj.setStudent(studentObject);
		marksObj.setSubject(subjectObject);
		marksObj.setMarksObtained(marks.getMarksObtained());
		marksObj.setRemarks(marks.getRemarks());
		marksObj.setAcademicYear(activeYear);
		return marksRepo.save(marksObj);
	}

	public void addMarksForClass(StudentClass studentClass, Long subjectId, List<MarksDTO> marksList) {
		// Fetch the active academic year
		AcademicYear activeAcademicYear = academicYearService.getActiveAcademicYear();

		// Fetch the subject and validate
		Subject subject = subService.getSubjectById(subjectId)
				.orElseThrow(() -> new IllegalArgumentException("Subject not found with ID: " + subjectId));

		Term term = subject.getTerm();

		// Validate subject belongs to a term and that term belongs to the active
		// academic year
		if (term == null || term.getAcademicYear().getId() != activeAcademicYear.getId()) {
			throw new IllegalStateException("Subject does not belong to any term in the active academic year.");
		}

		// Fetch all students in the class linked to the active academic year
		List<Student> students = studentService.getAllStudentsByClass(studentClass);

		// Map DTO to Marks entity and save
		List<Marks> marksEntities = marksList.stream().map(dto -> {
			Student student = students.stream().filter(s -> s.getId().equals(dto.getStudentId())).findFirst()
					.orElseThrow(() -> new IllegalArgumentException(
							"Student ID " + dto.getStudentId() + " not found in class."));

			return new Marks(student, subject, dto.getMarksObtained(), dto.getRemarks(), activeAcademicYear);
		}).collect(Collectors.toList());

		marksRepo.saveAll(marksEntities);
	}

	public List<MarksMinimalDTO> getMarksOfTermByStudent(Long termId, Long studentId) {
		Student studentObject = studentService.getStudentById(studentId).orElseThrow(NoSuchElementException::new);
		AcademicYear activeYear = academicYearService.getActiveAcademicYear();
		Term termObject = termService.getTermById(termId).orElseThrow(NoSuchElementException::new);
		List<Marks> marksList = marksRepo.findBySubject_TermAndStudentAndAcademicYear(termObject, studentObject,
				activeYear);
		return marksList.stream()
				.map(marks -> new MarksMinimalDTO(marks.getId(), marks.getMarksObtained(), marks.getRemarks(),
						new StudentMinimalDTO(marks.getStudent().getId(), marks.getStudent().getName(),
								marks.getStudent().getStudentClass()), // Assuming a lightweight StudentDTO constructor
						new SubjectDTO(marks.getSubject().getId(), marks.getSubject().getName(),
								marks.getSubject().getSubCode(), marks.getSubject().getMaxMarks(),
								marks.getSubject().getPassMarks()), // Assuming a lightweight SubjectDTO constructor
						marks.getSubject().getTerm().getName()))
				.collect(Collectors.toList());
	}

	public List<MarksMinimalDTO> getMarksOfTermByClassAndSubject(Long termId, String studentClass, Long subjectId) {
		// Fetch Term and Subject entities from their respective repositories
		Term term = termService.getTermById(termId)
				.orElseThrow(() -> new RuntimeException("Term not found with ID: " + termId));
		Subject subject = subService.getSubjectById(subjectId)
				.orElseThrow(() -> new RuntimeException("Subject not found with ID: " + subjectId));
		AcademicYear activeYear = academicYearService.getActiveAcademicYear();

		// Fetch marks using the repository query
		List<Marks> marksList = marksRepo.findBySubject_TermAndSubjectAndAcademicYear(term, subject, activeYear)
				.stream().filter(tempMarks -> tempMarks.getStudent().getStudentClass()
						.equals(StudentClass.valueOf(studentClass)))
				.toList();
		return marksList.stream()
				.map(marks -> new MarksMinimalDTO(marks.getId(), marks.getMarksObtained(), marks.getRemarks(),
						new StudentMinimalDTO(marks.getStudent().getId(), marks.getStudent().getName(),
								marks.getStudent().getStudentClass()), // Assuming a lightweight StudentDTO constructor
						new SubjectDTO(marks.getSubject().getId(), marks.getSubject().getName(),
								marks.getSubject().getSubCode(), marks.getSubject().getMaxMarks(),
								marks.getSubject().getPassMarks()), // Assuming a lightweight SubjectDTO constructor
						marks.getSubject().getTerm().getName()))
				.collect(Collectors.toList());
	}

	public List<Student> generateOTP(String guardianPhone) {
		List<Student> studentObject = studentService.getStudentsByPhone(guardianPhone);
		String otp = otpService.generateOTP(guardianPhone);
		smsNotifyService.sendNotification("+91" + guardianPhone, SmsNotificationHelper.getOTPSMSMessage(otp));
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

		try (PDDocument document = new PDDocument(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			PDPage page = new PDPage();
			document.addPage(page);

			try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
				float margin = 50;
				float yStart = 750;
				float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
				float yPosition = yStart;

				// 🔹 School Header
				contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 22);
				contentStream.beginText();
				contentStream.newLineAtOffset(margin + 120, yPosition);
				contentStream.showText("XYZ International School");
				contentStream.endText();

				// 🔹 School Address
				contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
				contentStream.beginText();
				contentStream.newLineAtOffset(margin + 150, yPosition - 20);
				contentStream.showText("123 School Road, Cityname, Country");
				contentStream.endText();

				// 🔹 Draw a separator line
				yPosition -= 40;
				contentStream.moveTo(margin, yPosition);
				contentStream.lineTo(margin + tableWidth, yPosition);
				contentStream.stroke();

				// 🔹 Student Details Table
				yPosition -= 30;
				float rowHeight = 20;
				drawTableRow(contentStream, margin, yPosition, tableWidth, rowHeight,
						new String[] { "Student Name", student.getName() });
				yPosition -= rowHeight;
				drawTableRow(contentStream, margin, yPosition, tableWidth, rowHeight,
						new String[] { "Class", String.valueOf(student.getStudentClass()) });
				yPosition -= rowHeight;
				drawTableRow(contentStream, margin, yPosition, tableWidth, rowHeight,
						new String[] { "Guardian", student.getGuardianName() });
				yPosition -= rowHeight;
				drawTableRow(contentStream, margin, yPosition, tableWidth, rowHeight,
						new String[] { "Phone", student.getGuardianPhone() });
				yPosition -= rowHeight;
				drawTableRow(contentStream, margin, yPosition, tableWidth, rowHeight,
						new String[] { "Address", student.getAddress() });
				yPosition -= rowHeight + 20; // Add spacing after student details

				// 🔹 Marks Table Header
				drawTableRow(contentStream, margin, yPosition, tableWidth, rowHeight,
						new String[] { "Subject", "Marks", "Max Marks", "Term" });
				yPosition -= rowHeight;

				// 🔹 Marks Table Rows
				for (Marks marks : marksList) {
					drawTableRow(contentStream, margin, yPosition, tableWidth, rowHeight,
							new String[] { marks.getSubject().getName(), marks.getMarksObtained().toString(),
									String.valueOf(marks.getSubject().getMaxMarks()),
									marks.getSubject().getTerm().getName() });
					yPosition -= rowHeight;
				}
			}

			// 🔹 Save and return as byte array
			document.save(outputStream);
			return outputStream.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException("Failed to generate marks sheet", e);
		}
	}

	private void drawTableRow(PDPageContentStream contentStream, float x, float y, float tableWidth, float rowHeight,
			String[] values) throws IOException {
		float cellWidth = tableWidth / values.length;
		float textMargin = 5;

		contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);

		for (int i = 0; i < values.length; i++) {
			// 🔹 Draw cell border
			contentStream.moveTo(x + (i * cellWidth), y);
			contentStream.lineTo(x + (i * cellWidth), y - rowHeight);
			contentStream.stroke();

			// 🔹 Add text inside the cell
			contentStream.beginText();
			contentStream.newLineAtOffset(x + (i * cellWidth) + textMargin, y - 15);
			contentStream.showText(values[i]);
			contentStream.endText();
		}

		// 🔹 Draw row bottom line
		contentStream.moveTo(x, y - rowHeight);
		contentStream.lineTo(x + tableWidth, y - rowHeight);
		contentStream.stroke();
	}

}
