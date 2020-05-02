package com.nsd.recruitment.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Document(indexName = "recruit",type = "company")
@Data
public class CompanyInfo implements Serializable {
    private static final long serialVersionUID = 6319447731517579159L;
    @Id
    private Long  id;
    private String fullName;
    private String shortName;
    private String brand;
    private Integer employeesNumber;
    private String address;
    private Timestamp createTime;
    private Timestamp updateTime;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String description;
    private Integer minStaff;
    private Integer maxStaff;
    private Boolean flag;
    private String companyCode;
    private String companyLogo;


}
