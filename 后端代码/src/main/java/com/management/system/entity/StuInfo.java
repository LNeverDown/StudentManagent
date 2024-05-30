package com.management.system.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class StuInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学生学号
     */
    @TableId(value = "number")
    private Integer number;

    /**
     * 学生姓名
     */
    private String name;


    /**
     * 所属班级
     */
    private Integer classNum;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 身份证号码
     */
    private String id;

    /**
     * 性别
     */
    private String sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 是否违纪
     */
    private Boolean ifViolated;

    @Override
    public String toString() {
        return "StuInfo{" +
                "number=" + number +
                ", name='" + name + '\'' +
                ", classNum=" + classNum +
                ", phone='" + phone + '\'' +
                ", id='" + id + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", ifViolated=" + ifViolated +
                '}';
    }

    public static long getSerialVersionUID() { return serialVersionUID; }
    public Integer getNumber() { return number; }
    public void setNumber(Integer number) { this.number = number; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getClassNum() { return classNum; }
    public void setClassNum(Integer classNum) { this.classNum = classNum; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    public Boolean getIfViolated() { return ifViolated; }
    public void setIfViolated(Boolean ifViolated) { this.ifViolated = ifViolated; }
}
