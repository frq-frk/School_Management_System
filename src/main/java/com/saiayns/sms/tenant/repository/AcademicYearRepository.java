package com.saiayns.sms.tenant.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.saiayns.sms.tenant.model.AcademicYear;

public interface AcademicYearRepository extends JpaRepository<AcademicYear, Long> {
	Optional<AcademicYear> findByIsActiveTrue();
	List<AcademicYear> findByIsActiveFalse();
	Optional<AcademicYear> findByIsRecentlyClosedTrue();
	boolean existsByIsActiveTrue();

	Optional<AcademicYear> findTopByIsActiveFalseOrderByEndDateDesc();
	
    @Modifying
    @Query("UPDATE AcademicYear a SET a.isRecentlyClosed = false")
    void resetRecentlyClosed();
}
