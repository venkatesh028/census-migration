package com.ideas2it.readXlsxFile.service.impl;

import com.ideas2it.readXlsxFile.customException.InvalidFileFormatException;
import com.ideas2it.readXlsxFile.helper.FileReadHelper;
import com.ideas2it.readXlsxFile.model.ExcelRecord;
import com.ideas2it.readXlsxFile.repository.PatientRepository;
import com.ideas2it.readXlsxFile.service.FileService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    private final PatientRepository patientRepository;
    private static final Logger logger = LogManager.getLogger(FileServiceImpl.class);

    public FileServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public boolean EhrConverter(MultipartFile file) {
        boolean isValidFormat = FileReadHelper.checkFileFormat(file);
        List<ExcelRecord> patientRecords;
        if (isValidFormat) {
            patientRecords = FileReadHelper.convertExcelToEhrFormat(file);
            if (patientRecords.isEmpty()) {
                return false;
            } else {
                patientRepository.saveAll(patientRecords);
                return true;
            }
        } else {
            logger.error("Invalid file format provided filename: " + file.getOriginalFilename());
            throw new InvalidFileFormatException("Invalid file format(" + file.getContentType() + "). Please check the format of the file: " + file.getOriginalFilename());
        }
    }
}
