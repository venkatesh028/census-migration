package com.ideas2it.readXlsxFile.repository;

import com.ideas2it.readXlsxFile.model.ExcelRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<ExcelRecord, Integer> {
}
