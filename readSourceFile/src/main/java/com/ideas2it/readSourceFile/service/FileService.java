package com.ideas2it.readXlsxFile.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    public boolean EhrConverter(MultipartFile file);
}
