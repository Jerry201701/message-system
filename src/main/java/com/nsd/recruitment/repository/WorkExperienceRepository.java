package com.nsd.recruitment.repository;

import com.nsd.recruitment.domain.WorkExperience;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface WorkExperienceRepository extends ElasticsearchRepository<WorkExperience,Long> {
}
