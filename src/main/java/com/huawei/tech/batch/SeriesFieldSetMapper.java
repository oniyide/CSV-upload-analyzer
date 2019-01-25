//package com.huawei.tech.batch;
//
//import com.huawei.tech.domain.Series;
//import org.springframework.batch.item.file.mapping.FieldSetMapper;
//import org.springframework.batch.item.file.transform.FieldSet;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.BindException;
//
//import java.time.YearMonth;
//import java.time.format.DateTimeFormatter;
//
//@Component
//public class SeriesFieldSetMapper implements FieldSetMapper<Series> {
//    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("Y.M.D");
//
//    @Override
//    public Series mapFieldSet(FieldSet fieldSet) throws BindException {
//        Series series = new Series();
//        series.setSeriesReference(fieldSet.readRawString(0));
//        series.setPeriod(fieldSet.readDate(1));
//        series.setDataValue(fieldSet.readInt(2));
//        series.setStatus(fieldSet.readRawString(3));
//        series.setUnits(fieldSet.readRawString(4));
//        series.setMagnitude(fieldSet.readInt(5));
//        series.setSubject(fieldSet.readRawString(6));
//        series.setGroupName(fieldSet.readRawString(7));
//        series.setSeriesTitle(fieldSet.readRawString(8));
//
//        return series;
//
//    }
//}
