package com.management.system.controller;


import com.management.system.entity.StuInfo;
import com.management.system.service.IStuInfoService;
import com.management.system.utils.HttpResponse;
import com.management.system.vo.QueryListResponse;
import com.management.system.vo.QueryStuInfoListRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/stu-info")
public class StuInfoController {


    @Autowired
    private IStuInfoService iStuInfoService;

    /**
     * 分页查询全部信息
     * */
    @GetMapping("/get")
    public HttpResponse getPageStudentInfo(QueryStuInfoListRequest queryStuInfoListRequest){
        return HttpResponse.success(iStuInfoService.getPageStudentInfo(
                queryStuInfoListRequest.getPageNum(), queryStuInfoListRequest.getPageSize()
        ));
    }

    /**
     * 对基本信息进行高级查询
     * */
    @GetMapping("/get-test")
    public HttpResponse getPageStudentInfoByCondition(QueryStuInfoListRequest queryStuInfoListRequest){
        iStuInfoService.getStudentInfoByCondition(queryStuInfoListRequest);
        QueryListResponse<StuInfo> queryStuInfoListResponse = iStuInfoService.getStudentInfoByCondition(queryStuInfoListRequest);
        //如果stuInfoListResponse.getNumOfTotalRecord() < 0 ，则表明输入类型和预期不符且发生异常，则返回错误
        return queryStuInfoListResponse.getNumOfTotalRecord() < 0 ?
                HttpResponse.error("输入信息类型错误！") : HttpResponse.success(queryStuInfoListResponse);
    }

    /**
     * 添加学生信息
     * */
    @PostMapping("/add")
    public HttpResponse addStudentInfo(@RequestBody StuInfo stuInfo){
        String res = iStuInfoService.addStudentInfo(stuInfo);
        //返回信息的长度大于0，则表示有错误或有提示信息
        return res.length() > 0 ? HttpResponse.error(res) : HttpResponse.success("成功插入一条记录") ;
    }

    /**
     * 编辑学生性别或手机号信息
     * */
    @PostMapping("/edit")
    public HttpResponse editStudentSexOrPhoneInfo(@RequestBody StuInfo stuInfo){
        return HttpResponse.success(iStuInfoService.editStudentInfo(stuInfo));
    }

    /**
     * 根据学号删除学生信息
     * */
    @DeleteMapping("/delete")
    public HttpResponse deleteStudentInfoByNumber(@RequestBody StuInfo stuInfo){
        return HttpResponse.success(iStuInfoService.deleteStudentInfo(stuInfo));
    }

    /**
     * 修改学生违纪状态
     * */
    @PostMapping("/change")
    public HttpResponse changeStudentStatus(@RequestBody StuInfo stuInfo){
        return HttpResponse.success(iStuInfoService.changeStudentStatus(stuInfo));
    }
}

