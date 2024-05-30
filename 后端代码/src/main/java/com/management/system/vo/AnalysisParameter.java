package com.management.system.vo;

import lombok.Data;

@Data
public class AnalysisParameter {

    /**
     * 一级参数：代表了分类的基本方式，分为：成绩信息总计，班级信息总计
     * */
    private String wayValue;

    /**
     * 二级参数：代表学科信息
     * */
    private String subjectValue;

    /**
     * 三级参数：代表班级信息
     * */
    private Integer classValue;

    /**
     * 三级参数：代表成绩信息总计时的分类指标
     * */
    private String indexValue;


    public String getWayValue() {
        return wayValue;
    }

    public void setWayValue(String wayValue) {
        this.wayValue = wayValue;
    }

    public String getSubjectValue() {
        return subjectValue;
    }

    public void setSubjectValue(String subjectValue) {
        this.subjectValue = subjectValue;
    }

    public Integer getClassValue() {
        return classValue;
    }

    public void setClassValue(Integer classValue) {
        this.classValue = classValue;
    }

    public String getIndexValue() {
        return indexValue;
    }

    public void setIndexValue(String indexValue) {
        this.indexValue = indexValue;
    }


    @Override
    public String toString() {
        return "AnalysisParameter{" +
                "wayValue='" + wayValue + '\'' +
                ", subjectValue='" + subjectValue + '\'' +
                ", classValue='" + classValue + '\'' +
                ", indexValue='" + indexValue + '\'' +
                '}';
    }
}
