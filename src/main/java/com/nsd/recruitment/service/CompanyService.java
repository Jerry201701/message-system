package com.nsd.recruitment.service;

import com.nsd.recruitment.domain.CompanyInfo;
import org.springframework.stereotype.Service;

@Service
public interface CompanyService {
    void saveCompany(String company);

}
