package com.huawei.tech.controller;

import com.huawei.tech.batch.JobCreator;
import com.huawei.tech.domain.CsvFile;
import com.huawei.tech.service.CsvService;
import com.huawei.tech.service.SeriesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
public class SeriesController {

    private final CsvService csvService;
    private final SeriesService seriesService;

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    JobCreator jobCreator;


    public SeriesController(CsvService csvService, SeriesService seriesService) {
        this.csvService = csvService;
        this.seriesService = seriesService;
    }

    @RequestMapping({"", "/", "index"})
    public String getIndexPage(){

        return "index";
    }

    @PostMapping("/file-upload")
    @RequestMapping(value = "/file-upload", method = RequestMethod.POST)
    public @ResponseBody List<CsvFile> upload(MultipartHttpServletRequest req, HttpServletResponse res){
        log.info("File uploading");
        List<CsvFile> csvFiles = csvService.saveCsvFiles(req.getFileMap());

        return csvFiles;
    }

    @RequestMapping(value = "/process-csv", method = RequestMethod.POST)
    public @ResponseBody BatchStatus processCsv(@RequestBody List<CsvFile> jsonString) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        log.info("Processing CSV file");
        List<CsvFile> csvFiles = jsonString;
        log.info("Number of CSV files being processed: " + csvFiles.size());

        csvService.saveToDB(csvFiles.get(0));

        Resource csvResource =  csvService.loadAsResource(csvFiles.get(0).getFilename());

        Job job = jobCreator.createJob(csvResource);
        JobParameters jobParameters = new JobParametersBuilder().addLong("time", new Date().getTime()).toJobParameters();
        JobExecution jobExecution = jobLauncher.run(job, jobParameters);

        //        Map<String, JobParameter> maps = new HashMap<>();
        //        maps.put("time", new JobParameter(System.currentTimeMillis()));
        //        JobParameters parameters = new JobParameters(maps);
        //        JobExecution jobExecution = jobLauncher.run(job, parameters);

        log.info("JobExecution: " + jobExecution.getStatus());

        log.info("Batch is Running...");

        while (jobExecution.isRunning()) {
            log.info("...");
        }
        return jobExecution.getStatus();

    }

    @RequestMapping("/analysis")
    public String getIndexPage(Model model){
        log.debug("Display Graph Analysis");
        model.addAttribute("series", seriesService.getSeries());

        return "analysis";
    }

    @RequestMapping("/view-uploads")
    public String getUploadedFiles(Model model){
        log.debug("Display index mapping page");
        model.addAttribute("csvFiles", csvService.listFiles());
        return "uploadedFiles";
    }


}
