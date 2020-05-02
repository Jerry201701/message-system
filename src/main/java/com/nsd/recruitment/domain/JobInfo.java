package com.nsd.recruitment.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
@Data
@Document(indexName = "release",type = "job")
public class JobInfo implements Serializable {
    private static final long serialVersionUID = 676796546902995822L;
    @Id
    private Long id;
    private Long companyId;
    private String jobName;
    private String educationLevel;
    private String jobDescription;
    private String salaryRange;
    private String certificates;
    private Timestamp updateTime;
    private Timestamp  createTime;
    private Integer  updateBy;
    private Integer  createBy;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer flag;
    private String workExperience;
    private Integer jobCategory;
    private Integer minSalary;
    private Integer maxSalary;
    private Integer minWorkYears;
    private Integer maxWorkYears;
}
