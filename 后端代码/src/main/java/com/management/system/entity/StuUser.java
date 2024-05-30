package com.management.system.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class StuUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value="id")
    private Integer id;

    /**
     * 管理员姓名
     */
    private String name;

    /**
     * 管理员的密码
     */
    private String password;

    /**
     * 管理员手机号
     */
    private String phone;

    /**
     * 管理员角色id
     */
    private Integer roleId;

    /**
     * 密码盐值
     */
    private String salt;
    public static long getSerialVersionUID() { return serialVersionUID; }
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public Integer getRoleId() { return roleId; }
    public void setRoleId(Integer roleId) { this.roleId = roleId; }
    public String getSalt() { return salt; }
    public void setSalt(String salt) { this.salt = salt; }
}
