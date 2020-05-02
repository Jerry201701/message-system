package com.nsd.recruitment.domain;

import lombok.Data;

@Data
public class JobQuery {
    private String regionName;
    private String categoryName;
    private Integer minSalary;
    private Integer maxSalary;
}
