package com.management.system.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class StuRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "role_id")
    private Integer roleId;

    private String roleName;

    private String psIds;

    private String roleDesc;

    public static long getSerialVersionUID() { return serialVersionUID; }
    public Integer getRoleId() { return roleId; }
    public void setRoleId(Integer roleId) { this.roleId = roleId; }
    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }
    public String getPsIds() { return psIds; }
    public void setPsIds(String psIds) { this.psIds = psIds; }
    public String getRoleDesc() { return roleDesc; }
    public void setRoleDesc(String roleDesc) { this.roleDesc = roleDesc; }
}
