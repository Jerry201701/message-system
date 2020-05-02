package com.nsd.recruitment.domain;

import lombok.Data;

@Data
public class SearchApplicantVo {
    private String keyword;
    private String jobName;
    private Integer minWorkExperience;
    private Integer maxWorkExperience;
    private String  educationLevel;
    private Integer minSalary;
    private Integer maxSalary;
}
