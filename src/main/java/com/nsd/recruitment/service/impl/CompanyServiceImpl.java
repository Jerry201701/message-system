package com.nsd.recruitment.service.impl;

import com.alibaba.fastjson.JSON;
import com.nsd.recruitment.domain.CompanyInfo;
import com.nsd.recruitment.repository.CompanyRepository;
import com.nsd.recruitment.service.CompanyService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = "company")
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    @RabbitHandler
    public void   saveCompany(String company) {
        System.out.println("收到中间的消息");
       CompanyInfo companyInfo= JSON.parseObject(company,CompanyInfo.class);
       companyRepository.save(companyInfo);


    }
}
