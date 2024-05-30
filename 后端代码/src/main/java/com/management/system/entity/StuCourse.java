package com.management.system.entity;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class StuCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Integer id;

    /**
     * 课程名称
     */
    private String name;

    /**
     * 该课程的分数值
     */
    private Integer scoreValue;

    /**
     * 课时
     */
    private Integer period;

    /**
     * 是否结课
     */
    private Boolean finished;


    @TableField(exist = false)
    private List<StuTeacherTag> tagList;

    public static long getSerialVersionUID() { return serialVersionUID; }
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getScoreValue() { return scoreValue; }
    public void setScoreValue(Integer scoreValue) { this.scoreValue = scoreValue; }
    public Integer getPeriod() { return period; }
    public void setPeriod(Integer period) { this.period = period; }
    public Boolean getFinished() { return finished; }
    public void setFinished(Boolean finished) { this.finished = finished; }
    public List<StuTeacherTag> getTagList() { return tagList; }
    public void setTagList(List<StuTeacherTag> tagList) { this.tagList = tagList; }
}
