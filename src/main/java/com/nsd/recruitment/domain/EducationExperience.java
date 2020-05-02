package com.nsd.recruitment.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Parent;

import java.io.Serializable;

@Data
@Document(indexName = "education",type = "level")
public class EducationExperience implements Serializable {
    private static final long serialVersionUID = -8606623081582226202L;
    @Id
    private Long id;
    private Long applicantId;
    private String schoolName;
    private String majorName;
    private String qualification;



}
