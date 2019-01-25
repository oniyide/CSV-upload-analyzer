package com.huawei.tech.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.YearMonth;

@Data
@Entity
public class FinanceStatistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne
//    private Series series;

    private YearMonth period;

    private Integer dataValue;

    private String status;



}
