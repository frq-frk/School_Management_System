package com.saiayns.sms.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saiayns.sms.tenant.model.AcademicYear;
import com.saiayns.sms.tenant.repository.AcademicYearRepository;

@Service
public class AcademicYearService {
	@Autowired
	private AcademicYearRepository academicYearRepo;

	public AcademicYear getActiveAcademicYear() {
		return academicYearRepo.findByIsActiveTrue()
				.orElseThrow(() -> new RuntimeException("No active academic year found!"));
	}

	public AcademicYear createNewAcademicYear(LocalDate startDate, LocalDate endDate) {
		// Check if there's an existing active academic year
		if (academicYearRepo.existsByIsActiveTrue()) {
			throw new IllegalStateException(
					"Cannot create a new academic year while an active academic year exists. Please close the current academic year first.");
		}
		String academicYearName = startDate.getYear() + " - " + endDate.getYear();
		// Create the new academic year
		AcademicYear newYear = new AcademicYear(academicYearName, startDate, endDate);
		return academicYearRepo.save(newYear);
	}

	public AcademicYear closeCurrentAcademicYear() {
		// Fetch the currently active academic year
		AcademicYear activeAcademicYear = academicYearRepo.findByIsActiveTrue()
				.orElseThrow(() -> new IllegalStateException("No active academic year found."));

		// Reset all existing "recently closed" flags
		academicYearRepo.resetRecentlyClosed();

		// Close the current academic year
		activeAcademicYear.setActive(false);
		activeAcademicYear.setIsRecentlyClosed(true);

		return academicYearRepo.save(activeAcademicYear);
	}

	public AcademicYear getRecenltyClosedAcademicYear() {
		return academicYearRepo.findByIsRecentlyClosedTrue()
				.orElseThrow(() -> new IllegalStateException("No recently closed academic year found."));
	}
}
