package com.huawei.tech.batch;

import com.huawei.tech.domain.Series;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Component
@Slf4j
public class SeriesProcessor implements ItemProcessor<Series, Series> {

    private Set<Series> seenSeriesReference = new HashSet<>();

//    DateTimeFormatter formatter = new DateTimeFormatterBuilder()
//            .parseCaseInsensitive()
//            .appendPattern("yyyy M")
//            .toFormatter(Locale.ENGLISH);

    @Override
    public Series process(Series series) throws Exception {
        // Checking duplicates in file
//        if(seenSeriesReference.contains(series)) {
//            return null;
//        }
//        log.info("Formatting date :" + series.getPeriod().toString());
//        YearMonth.parse(series.getPeriod().toString(), formatter);
//        seenSeriesReference.add(series);

        return series;

    }
}
