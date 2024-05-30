package com.management.system.vo;

import lombok.Data;

@Data
public class QueryStuPerformanceRequest {

    /**
     * 当前页数
     */
    private Integer pageNum;

    /**
     * 每一页的个数
     */
    private Integer pageSize;

    /**
     * 分数下限
     */
    private Integer minimumScore;

    /**
     * 学科代码，也是排序依据，排序为降序
     */
    private String subject;

    /**
     * 班级信息
     */
    private Integer classNum;

    /**
     * 分数上限
     */
    private Integer maximumScore;

    @Override
    public String toString() {
        return "QueryStuPerformanceRequest{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", minimumScore=" + minimumScore +
                ", subject='" + subject + '\'' +
                ", classNum=" + classNum +
                ", maximumScore=" + maximumScore +
                '}';
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getMinimumScore() {
        return minimumScore;
    }

    public void setMinimumScore(Integer minimumScore) {
        this.minimumScore = minimumScore;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getClassNum() {
        return classNum;
    }

    public void setClassNum(Integer classNum) {
        this.classNum = classNum;
    }

    public Integer getMaximumScore() {
        return maximumScore;
    }

    public void setMaximumScore(Integer maximumScore) {
        this.maximumScore = maximumScore;
    }
}
