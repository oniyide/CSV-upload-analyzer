package com.huawei.tech.service;

import com.huawei.tech.domain.CsvFile;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CsvService {

    List<CsvFile> saveCsvFiles(Map<String, MultipartFile> fileMap);

    Set<CsvFile> listFiles();

    CsvFile getFile(Long id);

    Path load(String filename);

    Resource loadAsResource(String filename);

    CsvFile saveToDB(CsvFile csvFile);
}
