package com.nsd.recruitment.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;


@Data
@Document(indexName = "work",type = "experience")
public class WorkExperience implements Serializable {
    private static final long serialVersionUID = 7146638931316888601L;
    @Id
    private Long id;
    private Long applicantId;
    private String companyName;
    private String jobName;

}
