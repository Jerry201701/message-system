package com.nsd.recruitment.service.impl;

import com.alibaba.fastjson.JSON;
import com.nsd.recruitment.domain.ApplicantInfo;
import com.nsd.recruitment.repository.ApplicantRepository;
import com.nsd.recruitment.service.EditApplicantService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = "edit")
public class EditApplicantServiceImpl implements EditApplicantService {
    @Autowired
    private ApplicantRepository applicantRepository;
    @Override
    @RabbitHandler
    public void editApplicant(String applicant) {
        ApplicantInfo applicantInfo= JSON.parseObject(applicant,ApplicantInfo.class);
        applicantRepository.save(applicantInfo);

    }
}
