package com.nsd.recruitment.repository;

import com.nsd.recruitment.domain.EducationExperience;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface EducationRepository extends ElasticsearchRepository<EducationExperience,Long> {



}
