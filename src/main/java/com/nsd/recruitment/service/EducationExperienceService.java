package com.nsd.recruitment.service;

import com.alibaba.fastjson.JSON;
import com.nsd.recruitment.domain.EducationExperience;
import com.nsd.recruitment.repository.ApplicantRepository;
import com.nsd.recruitment.repository.EducationRepository;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Service
@RabbitListener(queues = "education")
public class EducationExperienceService {
    @Autowired
    private EducationRepository educationRepository;
    @Autowired
    private ApplicantRepository applicantRepository;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;

    @RabbitHandler
    public void addEducation(String educationExperience){
        System.out.println("收到消息");
        List<EducationExperience>list=JSON.parseArray(educationExperience,EducationExperience.class);
        if (list!=null&&list.size()>0) {


            Long applicantId = list.get(0).getApplicantId();
//        UpdateQuery updateQuery=new UpdateQuery();
//        updateQuery.setIndexName("employee");
//        updateQuery.setType("applicant");
//        updateQuery.setId(applicantId.toString());
//        UpdateResponse updateResponse=elasticsearchTemplate.update(updateQuery);
//        System.out.println(updateResponse.status().getStatus());


            RestHighLevelClient client = elasticsearchTemplate.getClient();

            UpdateRequest updateRequest = new UpdateRequest("employee", "applicant", applicantId.toString());
            try {
                updateRequest.doc(XContentFactory.jsonBuilder().startObject().field("educationExperience", educationExperience).endObject());
                UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
                System.out.println(updateResponse.status().getStatus());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




//        Iterable<EducationExperience> iterable=educationRepository.search(QueryBuilders.termQuery("applicantId",applicantId));
//        Iterator<EducationExperience> iterator=iterable.iterator();
//        while (iterator.hasNext()){
//            educationRepository.delete(iterator.next());
//        }
//
//        for (EducationExperience education:list){
//
//            educationRepository.save(education);
//
//        }

    }
}
