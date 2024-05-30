package com.management.system.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class StuPerformance implements Serializable ,Comparable<StuPerformance> {

    private static final long serialVersionUID = 1L;

    /**
     * 学号
     */
    @TableId(value="number")
    private Integer number;

    /**
     * 姓名
     */
    private String name;

    /**
     * 班级
     */
    @TableField(value = "class_num")
    private Integer classNum;

    /**
     * 计算机网络 network of computer
     */
    private Double noc;

    /**
     * 操作系统 operating system
     */
    private Double os;

    /**
     * 计算机组成原理 principles of computer composition
     */
    private Double pocc;

    /**
     * C++
     */
    private Double cpp;

    /**
     * Rust
     */
    private Double rust;

    /**
     * Go
     */
    private Double golang;

    /**
     * Java
     */
    private Double java;

    /**
     * 英语
     */
    private Double english;

    /**
     * advanced mathematics 高等数学
     */
    private Double math;

    /**
     * 总分
     */
    private Double total;

    /**
     * 排名
     */
    private Integer ranking;

    @Override
    public int compareTo(StuPerformance s) {
        return (int)(s.total - this.total);
    }

    public void updateTotal(){
        this.total = this.cpp + this.english + this.golang + this.java
                + this.math + this.noc + this.os + this.pocc + this.rust;
    }

    public static long getSerialVersionUID() { return serialVersionUID; }
    public Integer getNumber() { return number; }
    public void setNumber(Integer number) { this.number = number; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getClassNum() { return classNum; }
    public void setClassNum(Integer classNum) { this.classNum = classNum; }
    public Double getNoc() { return noc; }
    public void setNoc(Double noc) { this.noc = noc; }
    public Double getOs() { return os; }
    public void setOs(Double os) { this.os = os; }
    public Double getPocc() { return pocc; }
    public void setPocc(Double pocc) { this.pocc = pocc; }
    public Double getCpp() { return cpp; }
    public void setCpp(Double cpp) { this.cpp = cpp; }
    public Double getRust() { return rust; }
    public void setRust(Double rust) { this.rust = rust; }
    public Double getGolang() { return golang; }
    public void setGolang(Double golang) { this.golang = golang; }
    public Double getJava() { return java; }
    public void setJava(Double java) { this.java = java; }
    public Double getEnglish() { return english; }
    public void setEnglish(Double english) { this.english = english; }
    public Double getMath() { return math; }
    public void setMath(Double math) { this.math = math; }
    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
    public Integer getRanking() { return ranking; }
    public void setRanking(Integer ranking) { this.ranking = ranking; }
}
