package com.management.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.management.system.entity.StuTeacherTag;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StuTeacherTagMapper extends BaseMapper<StuTeacherTag> {

    @Select("SELECT sti.id, sti.name FROM stu_course AS sc LEFT JOIN stu_teacher_info AS sti ON sc.id = sti.course_id ${ew.customSqlSegment};")
    List<StuTeacherTag> getTeacherListByCourseName(@Param(Constants.WRAPPER) Wrapper wrapper);
}