package com.nsd.recruitment.service.impl;

import com.alibaba.fastjson.JSON;
import com.nsd.recruitment.domain.ApplicantInfo;
import com.nsd.recruitment.repository.ApplicantRepository;
import com.nsd.recruitment.service.ApplicantService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = "applicant")
public class ApplicantServiceImpl implements ApplicantService {
    @Autowired
    private ApplicantRepository applicantRepository;
    @Override
    @RabbitHandler
    public void saveApplicant(String applicant) {
        System.out.println("收到中间的消息");
        ApplicantInfo applicantInfo= JSON.parseObject(applicant,ApplicantInfo.class);
        applicantRepository.save(applicantInfo);


    }
}
