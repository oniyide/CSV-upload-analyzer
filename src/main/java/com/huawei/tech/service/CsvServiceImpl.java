package com.huawei.tech.service;

import com.huawei.tech.config.PropertyConfig;
import com.huawei.tech.domain.CsvFile;
import com.huawei.tech.repository.CsvFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.*;

@Service
@Slf4j
public class CsvServiceImpl implements CsvService {

    private final CsvFileRepository csvFileRepository;

    private final PropertyConfig propertyConfig;

    public CsvServiceImpl(CsvFileRepository csvFileRepository, PropertyConfig propertyConfig) {
        this.csvFileRepository = csvFileRepository;
        this.propertyConfig = propertyConfig;
    }

    @Override
    public List<CsvFile> saveCsvFiles(Map<String, MultipartFile> fileMap) {

        // Maintain a list to send back the files info. to the client side
        List<CsvFile> csvFiles = new ArrayList<>();

        // Iterate through the map
        for (MultipartFile multipartFile : fileMap.values()) {

            // Save the file to local disk
            saveFileToLocalDisk(multipartFile);

            CsvFile fileInfo = getUploadedFileInfo(multipartFile);

            // Save the file info to database
            fileInfo = saveFileToDatabase(fileInfo);

            // adding the file info to the list
            csvFiles.add(fileInfo);
        }

        return csvFiles;
    }

    @Override
    public Set<CsvFile> listFiles() {
        Set<CsvFile> csvFiles = new HashSet<>();
        csvFileRepository.findAll().iterator().forEachRemaining(csvFiles::add);
        return csvFiles;
    }

    @Override
    public CsvFile getFile(Long id) {
        return null;
    }

    @Override
    public Path load(String filename) {
        return propertyConfig.csvFilePath().resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        Path file = load(filename);
        Resource resource = null;
        try {
            resource = new UrlResource(file.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (resource.exists() || resource.isReadable()) {
            return resource;
        }
        else {
            log.debug("Could not read file: " + filename);
            return resource;
        }
    }

    @Override
    public CsvFile saveToDB(CsvFile csvFile) {
        CsvFile csv = csvFileRepository.save(csvFile);
        log.debug("CSV record saved to database");

        return csv;
    }


    private CsvFile saveFileToDatabase(CsvFile fileInfo) {
        return csvFileRepository.save(fileInfo);
    }

    private CsvFile getUploadedFileInfo(MultipartFile multipartFile) {
        CsvFile fileInfo = new CsvFile();
        fileInfo.setFilename(multipartFile.getOriginalFilename());
        fileInfo.setSize(multipartFile.getSize());
        fileInfo.setUploadTime(new Timestamp(System.currentTimeMillis()));

        return fileInfo;
    }

    private void saveFileToLocalDisk(MultipartFile multipartFile) {
//        String outputFileName = getOutputFilename(multipartFile);
//
//        FileCopyUtils.copy(multipartFile.getBytes(), new FileOutputStream(
//                outputFileName));

        String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Files.copy(inputStream, propertyConfig.csvFilePath().resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
            log.info("CSV File uploaded");
        }catch (IOException e){
            log.debug("Failed to store file " + filename, e);
        }
    }

    private String getOutputFilename(MultipartFile multipartFile) {
        return getDestinationLocation() + multipartFile.getOriginalFilename();
    }

    private String getDestinationLocation() {
        return "C:/huaweiCsvFiles/";
    }



}
