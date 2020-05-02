package com.nsd.recruitment.controller;

import com.nsd.recruitment.domain.*;
import com.nsd.recruitment.repository.ApplicantRepository;
import com.nsd.recruitment.repository.CompanyRepository;
import com.nsd.recruitment.repository.JobRepository;
import jdk.nashorn.internal.scripts.JO;
import org.apache.http.HttpStatus;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.join.query.JoinQueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
public class ElasticController {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private ApplicantRepository applicantRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;

    @PostMapping(value = "/elastic/add")
    public String addCompany(@RequestBody CompanyInfo company){
        companyRepository.save(company);
        return "success";
    }
    @GetMapping(value = "/find/all")
    public List<CompanyInfo> listAllCompany(){
        List<CompanyInfo>list=new ArrayList<>();
        Iterator<CompanyInfo> iterator= companyRepository.findAll().iterator();
        while (iterator.hasNext()){
            list.add(iterator.next());

        }
        //companyRepository.

//        Iterable<CompanyInfo>iterable=companyRepository.findAll();
//        while (iterable.iterator().hasNext()){
//            list.add(iterable.iterator().next());
//        }

        return list;
    }
    @PostMapping(value = "/applicant/keyword")
    public List<ApplicantInfo> searchApplicantByKeyword(@RequestBody SearchApplicantVo searchApplicantVo){
        List<ApplicantInfo>list=new ArrayList<>();
        BoolQueryBuilder boolQueryBuilder=QueryBuilders.boolQuery();
        if (searchApplicantVo.getJobName()!=null&&!searchApplicantVo.getJobName().isEmpty()){
            boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("expectPosition",searchApplicantVo.getJobName()));
        }
        if (searchApplicantVo.getMaxWorkExperience()!=null&&searchApplicantVo.getMinWorkExperience()!=null){
            boolQueryBuilder.must(QueryBuilders.rangeQuery("workYears")
                    .gte(searchApplicantVo.getMinWorkExperience()).lte(searchApplicantVo.getMaxWorkExperience()));

        }
        if (searchApplicantVo.getMinSalary()!=null&&searchApplicantVo.getMaxSalary()!=null){
            boolQueryBuilder.must(QueryBuilders.rangeQuery("minExpectSalary").lte(searchApplicantVo.getMinSalary()));
            boolQueryBuilder.must(QueryBuilders.rangeQuery("maxExpectSalary").lte(searchApplicantVo.getMaxSalary()));


        }

        if (searchApplicantVo.getEducationLevel()!=null&&searchApplicantVo.getEducationLevel().isEmpty()){
            boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("educationExperience",searchApplicantVo.getEducationLevel()));
        }

        if (searchApplicantVo.getKeyword()!=null&&!searchApplicantVo.getKeyword().isEmpty()){
            boolQueryBuilder.should(QueryBuilders.matchPhraseQuery("applicantName",searchApplicantVo.getKeyword()))
                    .should(QueryBuilders.matchPhraseQuery("expectPosition",searchApplicantVo.getKeyword()))
                    .should(QueryBuilders.matchPhraseQuery("educationExperience",searchApplicantVo.getKeyword()))
                    .should(QueryBuilders.matchPhraseQuery("workExperience",searchApplicantVo.getKeyword()));
        }

        Iterable<ApplicantInfo>iterable1=applicantRepository.search(boolQueryBuilder);
        Iterator<ApplicantInfo>iterator1=iterable1.iterator();
        while (iterator1.hasNext()){
            list.add(iterator1.next());
        }
        return  list;
    }




    @PostMapping(value = "/applicant/add")
    public void addApplicantTest(@RequestBody ApplicantInfo applicantInfo){
        applicantRepository.save(applicantInfo);


    }
    @PostMapping(value = "/job/add")
    public String addJob(@RequestBody JobInfo jobInfo){
        jobRepository.save(jobInfo);
        return "success";
    }
    @GetMapping(value = "/company/keyword")
    public List<CompanyInfo> searchCompanyByKey(@RequestParam(value = "keyword")String keyword,@RequestParam(value = "choose") Integer choose){
        List<CompanyInfo> list=new ArrayList<>();
        if (choose==0){
        Iterable<CompanyInfo>all=companyRepository.search(QueryBuilders.boolQuery()
                .should(QueryBuilders.matchPhraseQuery("fullName",keyword))
                .should(QueryBuilders.matchPhraseQuery("shortName",keyword))
                .should(QueryBuilders.matchPhraseQuery("brand",keyword)));
        Iterator<CompanyInfo>allIterator=all.iterator();
        while (allIterator.hasNext()){
            list.add(allIterator.next());
        }
        }
        if (choose==1){
            Iterable<CompanyInfo>full=companyRepository.search(QueryBuilders.matchPhraseQuery("fullName",keyword));
            Iterator<CompanyInfo>iterator1=full.iterator();
            while (iterator1.hasNext()){
                list.add(iterator1.next());
            }
        }
        if (choose==2){
            Iterable<CompanyInfo>shortName=companyRepository.search(QueryBuilders.matchPhraseQuery("brand",keyword));
            Iterator<CompanyInfo>iterator2=shortName.iterator();
            while (iterator2.hasNext()){
                list.add(iterator2.next());
            }
        }
        if (choose==3){
            Iterable<CompanyInfo>brand=companyRepository.search(QueryBuilders.matchPhraseQuery("shortName",keyword));
            Iterator<CompanyInfo>iterator3=brand.iterator();
            while (iterator3.hasNext()){
                list.add(iterator3.next());
            }

        }

        return  list;
    }

    @PostMapping(value = "/search/job")
    public List<JobInfo> searchJobByKeyword(@RequestBody JobQuery jobQuery){
        List<JobInfo>list=new ArrayList<>();
        BoolQueryBuilder boolQueryBuilder=QueryBuilders.boolQuery();

        if (jobQuery.getCategoryName()!=null&&!jobQuery.getCategoryName().isEmpty()){

         boolQueryBuilder.must(QueryBuilders.termQuery("jobName",jobQuery.getCategoryName()));

        }
        if (jobQuery.getRegionName()!=null&&!jobQuery.getRegionName().isEmpty()){
            boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("address",jobQuery.getRegionName()));


        }
        if (jobQuery.getMaxSalary()!=null&&jobQuery.getMinSalary()!=null){
            boolQueryBuilder.must(QueryBuilders.rangeQuery("minSalary").gte(jobQuery.getMinSalary()))
                    .must(QueryBuilders.rangeQuery("maxSalary").gte(jobQuery.getMaxSalary()));


        }

//        Iterable<JobInfo> iterable=jobRepository.search(QueryBuilders.boolQuery()
//        .must()
//                .must(QueryBuilders.matchPhraseQuery("address",jobQuery.getRegionName()))
//                .must(QueryBuilders.rangeQuery("minSalary").gte(jobQuery.getMinSalary()))
//                .must(QueryBuilders.rangeQuery("maxSalary").gte(jobQuery.getMaxSalary()))
//
//        );
        Iterable<JobInfo>iterable=jobRepository.search(boolQueryBuilder);

        Iterator<JobInfo> iterator =iterable.iterator();
        while (iterator.hasNext()){
            list.add(iterator.next());
        }

        return list;
    }



    @RequestMapping("/health")
    public int health(){
        return HttpStatus.SC_OK;
    }


}
