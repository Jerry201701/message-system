package com.nsd.recruitment.service;

import com.alibaba.fastjson.JSON;
import com.nsd.recruitment.domain.WorkExperience;
import com.nsd.recruitment.repository.WorkExperienceRepository;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;

@Service
@RabbitListener(queues = "work")
public class WorkExperienceService {
    @Autowired
    private WorkExperienceRepository workExperienceRepository;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;
    @RabbitHandler
    public void addWorkExperience(String workExperience){
        System.out.println("收到消息");
        List<WorkExperience> list=JSON.parseArray(workExperience,WorkExperience.class);

        if (list!=null&&list.size()>0){
       Long applicantId=list.get(0).getApplicantId();

        RestHighLevelClient client = elasticsearchTemplate.getClient();
        UpdateRequest updateRequest=new UpdateRequest("employee","applicant",applicantId.toString());
        try {
            updateRequest.doc(XContentFactory.jsonBuilder().startObject().field("workExperience",workExperience).endObject());
            UpdateResponse updateResponse=client.update(updateRequest, RequestOptions.DEFAULT);
            System.out.println(updateResponse.status().getStatus());
        } catch (IOException e) {
            e.printStackTrace();
        }
        }

//        Iterable<WorkExperience> iterable=workExperienceRepository.search(QueryBuilders.termQuery("applicantId",applicantId));
//        Iterator<WorkExperience>iterator=iterable.iterator();
//        while (iterator.hasNext()){
//            workExperienceRepository.delete(iterator.next());
//        }
//        for (WorkExperience work:list) {
//
//            workExperienceRepository.save(work);
//        }

       // WorkExperience work= JSON.parseObject(workExperience,WorkExperience.class);
      //  wordExperienceRepository.save(work);
    }
}
