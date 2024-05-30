package com.management.system.service;

import com.management.system.entity.StuInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.management.system.vo.QueryListResponse;
import com.management.system.vo.QueryStuInfoListRequest;


public interface IStuInfoService extends IService<StuInfo> {

    /**
     * 基本查询，分页获取学生信息
     */
    QueryListResponse<StuInfo> getPageStudentInfo(Integer currPage, Integer TotalPage);

    /**
     * 高级查询，根据不同的条件进行查询
     */
    QueryListResponse<StuInfo> getStudentInfoByCondition(QueryStuInfoListRequest queryStuInfoListRequest);

    /**
     * 添加学生信息
     * */
    String addStudentInfo(StuInfo stuInfo);

    /**
     * 编辑学生信息
     * */
    int editStudentInfo(StuInfo stuInfo);

    /**
     * 删除学生信息
     * */
    int deleteStudentInfo(StuInfo stuInfo);

    /**
     * 修改学生违纪状态
     * */
    int changeStudentStatus(StuInfo stuInfo);
}
