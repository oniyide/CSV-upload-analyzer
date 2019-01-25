package com.huawei.tech.batch;

import com.huawei.tech.domain.Series;
import com.huawei.tech.repository.SeriesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class DBWriter implements ItemWriter<Series> {

    private final SeriesRepository seriesRepository;

    public DBWriter(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }

    @Override
    public void write(List<? extends Series> series) throws Exception {
        log.info("Saving " + series.size() + " records");
        seriesRepository.saveAll(series);
        log.info("Data Saved for Series: ");
    }
}
