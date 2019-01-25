package com.huawei.tech.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.YearMonth;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Series {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(unique=true)
    private String seriesReference;

//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "Y:M")
//  private Date period;
    private String period;

    private Integer dataValue;

    private String status;

    private String units;

    private Integer magnitude;

    private String subject;

    private String groupName;

    private String seriesTitle;






//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "series")
//    private Set<FinanceStatistic> financeStatistics = new HashSet<>();
}
