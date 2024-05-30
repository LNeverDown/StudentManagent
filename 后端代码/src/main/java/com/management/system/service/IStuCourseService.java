package com.management.system.service;

import com.management.system.entity.StuCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.management.system.entity.StuTeacherTag;
import com.management.system.vo.NormalQueryInfo;
import com.management.system.vo.QueryListResponse;

import java.util.List;


public interface IStuCourseService extends IService<StuCourse> {

    QueryListResponse<StuCourse> getCourseList(NormalQueryInfo normalQueryInfo);

    List<StuTeacherTag> getStuTeacherTagList(Integer courseId);

    Boolean addCourse( StuCourse stuCourse);

    Boolean editCourse( StuCourse stuCourse);

    Boolean delCourse( StuCourse stuCourse);

    Boolean updateTeacherCourseEqualNoneBatchById(List<StuTeacherTag> tagList);

}
