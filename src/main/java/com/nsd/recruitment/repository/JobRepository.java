package com.nsd.recruitment.repository;

import com.nsd.recruitment.domain.JobInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface JobRepository extends ElasticsearchRepository<JobInfo,Long> {
}
