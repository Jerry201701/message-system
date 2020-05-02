package com.nsd.recruitment.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.sql.Timestamp;
@Document(indexName = "employee",type = "applicant")
@Data
public class ApplicantInfo implements Serializable {
    private static final long serialVersionUID = 7315535687473306044L;
    @Id
    private Long id;
    private Long userId;
    private String applicantName;
    private String telephone;
    private Integer age;
    private Integer workYears;
    private String email;
    private String introduce;
    private Timestamp createTime;
    private Timestamp  updateTime;
    private Boolean flag;
    private String expectPosition;
    private String certificateList;
    private String educationLevel;
    private String educationExperience;
    private String workExperience;
    private Integer maxExpectSalary;
    private Integer minExpectSalary;



}
