package com.management.system.vo;

import lombok.Data;

/**
 * 登录返回类
 */
@Data
public class LoginResponse {

    /**
     * 用户名
     */
    private String account;

    /**
     * token值
     */

    private String token;

    /**
     * 用户角色名
     */

    private String roleName;

    /**
     * 登录状态
     */
    private Integer loginStatus;


    public LoginResponse(String account,String token,String roleName){
        this.account = account;
        this.token = token;
        this.roleName = roleName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(Integer loginStatus) {
        this.loginStatus = loginStatus;
    }
}
