package com.management.system.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class StuCourseTeacher implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任课教师的id
     */
    @TableId(value = "teacher_id")
    private Integer teacherId;

    /**
     * 教授的课程
     */
    private String courseName;

    public static long getSerialVersionUID() { return serialVersionUID; }
    public Integer getTeacherId() { return teacherId; }
    public void setTeacherId(Integer teacherId) { this.teacherId = teacherId; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
}
