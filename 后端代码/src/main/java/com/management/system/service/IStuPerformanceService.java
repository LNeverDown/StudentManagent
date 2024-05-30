package com.management.system.service;

import com.management.system.entity.StuPerformance;
import com.baomidou.mybatisplus.extension.service.IService;
import com.management.system.vo.*;

import java.util.List;


public interface IStuPerformanceService extends IService<StuPerformance> {

    QueryListResponse<StuPerformance> getStuPerformanceList(QueryStuPerformanceRequest queryStuPerformanceRequest);

    QueryListResponse<StuPerformance> getSingleStudentPerformance(String name,Integer number);

    Boolean changeStudentPerformance(StuPerformance stuPerformance);

    Boolean addSingleStudentPerformance(StuPerformance stuPerformance);

    List<Double> totalPerformanceAnalyse(AnalysisParameter analysisParameter);

    ClassPerformanceAnalyseResult classPerformanceAnalyse(AnalysisParameter analysisParameter);
}
