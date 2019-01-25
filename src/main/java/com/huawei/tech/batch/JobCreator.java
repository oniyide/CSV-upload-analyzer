package com.huawei.tech.batch;

import com.huawei.tech.domain.Series;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@EnableBatchProcessing
public class JobCreator {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    ItemProcessor<Series, Series> itemProcessor;

    @Autowired
    ItemWriter<Series> itemWriter;

//    @Autowired
//    BeanWrapperFieldSetMapperCustom<Series> fieldSetMapper;

//    @Autowired
//    SeriesFieldSetMapper fieldSetMapper;


    public Job createJob(Resource resource) {
        FlatFileItemReader<Series> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(resource);
        itemReader.setName("CSV-Reader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());

        Step step = stepBuilderFactory.get("csv-file-load")
                .<Series, Series>chunk(100)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();

        Job job = jobBuilderFactory.get("csv-Load")
                    .incrementer(new RunIdIncrementer())
                    .start(step)
                    .build();

        return job;
    }

    public LineMapper<Series> lineMapper(){
        DefaultLineMapper<Series> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(new String[]{"seriesReference", "period", "dataValue", "status", "units",
                                    "magnitude", "subject", "groupName", "seriesTitle"});

        // Selecting the columns to use
        lineTokenizer.setIncludedFields(new int[]{0,1,2,3,4,5,6,7,8});


        BeanWrapperFieldSetMapper<Series> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Series.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }




}
