//package com.huawei.tech.config;
//
//import com.huawei.tech.domain.Series;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.ItemReader;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.LineMapper;
//import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
//import org.springframework.batch.item.file.mapping.DefaultLineMapper;
//import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
//import org.springframework.beans.factory.BeanFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.context.annotation.Scope;
//import org.springframework.core.io.Resource;
//
//@Configuration
//@EnableBatchProcessing
//public class SpringBatchConfig {
//
//
//    @Bean
//    @Scope(value = "prototype")
//    public Job job(JobBuilderFactory jobBuilderFactory,
//                   StepBuilderFactory stepBuilderFactory,
//                   ItemReader<Series> itemReader,
//                   ItemProcessor<Series, Series> itemProcessor,
//                   ItemWriter<Series> itemWriter){
//
//        Step step = stepBuilderFactory.get("csv-file-load")
//                        .<Series, Series>chunk(100)
//                        .reader(itemReader)
//                        .processor(itemProcessor)
//                        .writer(itemWriter)
//                        .build();
//
//        Job job = jobBuilderFactory.get("FI-Load")
//                    .incrementer(new RunIdIncrementer())
//                    .start(step)
//                    .build();
//
//        return job;
//    }
//
//    @Bean
//    @Scope(value = "prototype")
//    public FlatFileItemReader<Series> itemReader(Resource resource){
//        FlatFileItemReader<Series> flatFileItemReader = new FlatFileItemReader<>();
//        flatFileItemReader.setResource(resource);
//        flatFileItemReader.setName("CSV-Reader");
//        flatFileItemReader.setLinesToSkip(1);
//        flatFileItemReader.setLineMapper(lineMapper());
//        return flatFileItemReader;
//    }
//
//    @Bean
//    public LineMapper<Series> lineMapper() {
//        DefaultLineMapper<Series> defaultLineMapper = new DefaultLineMapper<>();
//        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
//
//        lineTokenizer.setDelimiter(",");
//        lineTokenizer.setStrict(false);
//        lineTokenizer.setNames(new String[]{"seriesReference", "units", "magnitude", "subject", "groupName", "seriesTitle"});
//
//        // Selecting the columns to use
//        lineTokenizer.setIncludedFields(new int[]{0,4,5,6,7,8});
//
//
//        BeanWrapperFieldSetMapper<Series> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
//        fieldSetMapper.setTargetType(Series.class);
//
//        defaultLineMapper.setLineTokenizer(lineTokenizer);
//        defaultLineMapper.setFieldSetMapper(fieldSetMapper);
//
//        return defaultLineMapper;
//    }
//
//
//}
