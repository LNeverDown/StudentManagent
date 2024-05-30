package com.management.system.entity;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class StuPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Integer id;

    @JsonProperty("auth_name")
    private String authName;

    private Integer pid;

    private Integer level;

    private String path;

    @TableField(exist = false)
    private List<StuPermission> children;

    public static long getSerialVersionUID() { return serialVersionUID; }
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getAuthName() { return authName; }
    public void setAuthName(String authName) { this.authName = authName; }
    public Integer getPid() { return pid; }
    public void setPid(Integer pid) { this.pid = pid; }
    public Integer getLevel() { return level; }
    public void setLevel(Integer level) { this.level = level; }
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
    public List<StuPermission> getChildren() { return children; }
    public void setChildren(List<StuPermission> children) { this.children = children; }
}
