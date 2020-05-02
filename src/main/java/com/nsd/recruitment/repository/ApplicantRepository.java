package com.nsd.recruitment.repository;

import com.nsd.recruitment.domain.ApplicantInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface ApplicantRepository extends ElasticsearchRepository<ApplicantInfo,Long> {
}
