package com.management.system.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class StuTeacherInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Integer id;

    private String idNum;

    private String name;

    private String phone;

    private String email;

    private Integer age;

    private String sex;

    /**
     * 学历 education background
     */
    private String ed;
    private Integer courseId;
    public static long getSerialVersionUID() { return serialVersionUID; }
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getIdNum() { return idNum; }
    public void setIdNum(String idNum) { this.idNum = idNum; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }
    public String getEd() { return ed; }
    public void setEd(String ed) { this.ed = ed; }
    public Integer getCourseId() { return courseId; }
    public void setCourseId(Integer courseId) { this.courseId = courseId; }
}
