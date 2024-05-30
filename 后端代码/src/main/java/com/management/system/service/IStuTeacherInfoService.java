package com.management.system.service;

import com.management.system.entity.StuTeacherInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.management.system.vo.NormalQueryInfo;
import com.management.system.vo.QueryListResponse;


public interface IStuTeacherInfoService extends IService<StuTeacherInfo> {

    QueryListResponse<StuTeacherInfo> getTeacherInfosList(NormalQueryInfo normalQueryInfo);

    String addTeacherInfosList(StuTeacherInfo stuTeacherInfo);

    Boolean editTeacherInfosList(StuTeacherInfo stuTeacherInfo);

    Boolean delTeacherInfosList(StuTeacherInfo stuTeacherInfo);

    QueryListResponse<StuTeacherInfo> getAllTeacherInfosListNoCourse();

    Boolean setCourseTeacherById(Integer teacherId,Integer courseId);

}
