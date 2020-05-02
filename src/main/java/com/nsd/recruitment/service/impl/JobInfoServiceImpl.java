package com.nsd.recruitment.service.impl;

import com.alibaba.fastjson.JSON;
import com.nsd.recruitment.domain.JobInfo;
import com.nsd.recruitment.repository.JobRepository;
import com.nsd.recruitment.service.JobService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = "job")
public class JobInfoServiceImpl implements JobService {
    @Autowired
    private JobRepository jobRepository;
    @Override
    @RabbitHandler
    public void saveJobInfo(String jobInfo) {
        System.out.println("收到中间的消息");
        JobInfo job= JSON.parseObject(jobInfo,JobInfo.class);
        jobRepository.save(job);
    }
}
