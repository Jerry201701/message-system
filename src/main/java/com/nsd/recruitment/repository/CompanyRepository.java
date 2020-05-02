package com.nsd.recruitment.repository;

import com.nsd.recruitment.domain.CompanyInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface CompanyRepository extends ElasticsearchRepository<CompanyInfo,Long> {
}
