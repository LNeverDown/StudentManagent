package com.management.system.controller;


import com.management.system.entity.StuPerformance;
import com.management.system.service.IStuPerformanceService;
import com.management.system.utils.ConstName;
import com.management.system.utils.HttpResponse;
import com.management.system.vo.AnalysisParameter;
import com.management.system.vo.QueryStuPerformanceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/stu-performance")
public class StuPerformanceController {

    @Autowired
    private IStuPerformanceService iStuPerformanceService;

    /**
     * 分页获取按照总分排序的总列表
     */
    @GetMapping("/get")
    public HttpResponse getStuPerformanceList(QueryStuPerformanceRequest queryStuPerformanceRequest){
        return HttpResponse.success(iStuPerformanceService.getStuPerformanceList(queryStuPerformanceRequest));
    }

    /**
     * 通过学号和姓名查询同学成绩
     */
    @GetMapping("/get-student")
    public HttpResponse getSingleStudentPerformance(@RequestParam(value = ConstName.NAME_COLUMN) String name,
                                                    @RequestParam(value = ConstName.NUMBER_COLUMN) Integer number){
        return HttpResponse.success(iStuPerformanceService.getSingleStudentPerformance(name,number));
    }

    /**
     * 修改学生成绩
     */
    @PostMapping("/change")
    public HttpResponse changeStudentPerformance(@RequestBody StuPerformance stuPerformance){
        return HttpResponse.success(iStuPerformanceService.changeStudentPerformance(stuPerformance));
    }

    /**
     * 成绩信息录入
     */
    @PostMapping("/add")
    public HttpResponse addSingleStudentPerformance(@RequestBody StuPerformance stuPerformance){
        return HttpResponse.success(iStuPerformanceService.addSingleStudentPerformance(stuPerformance));
    }

    /**
     * 获取成绩分析信息
     * */
    @GetMapping("/get-total-info")
    public HttpResponse getPerformanceDataList(AnalysisParameter analysisParameter){
        if(analysisParameter.getWayValue().equals(ConstName.TOTAL_COLUMN)){
            return HttpResponse.success(iStuPerformanceService.totalPerformanceAnalyse(analysisParameter));
        }else{
            return HttpResponse.success(iStuPerformanceService.classPerformanceAnalyse(analysisParameter));
        }
    }
}

