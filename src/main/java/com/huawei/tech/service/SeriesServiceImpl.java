package com.huawei.tech.service;

import com.huawei.tech.domain.Series;
import com.huawei.tech.repository.SeriesCrudRepository;
import com.huawei.tech.repository.SeriesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class SeriesServiceImpl implements SeriesService {

    private final SeriesCrudRepository seriesCrudRepository;

    public SeriesServiceImpl(SeriesCrudRepository seriesCrudRepository) {
        this.seriesCrudRepository = seriesCrudRepository;
    }

    @Override
    public Set<Series> getSeries() {
        log.debug("In the Series service");

        Set<Series> series = new HashSet<>();
        seriesCrudRepository.findAll().iterator().forEachRemaining(series::add);

        return series;
    }
}
