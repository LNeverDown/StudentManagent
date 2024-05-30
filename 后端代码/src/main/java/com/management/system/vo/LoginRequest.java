package com.management.system.vo;

import lombok.Data;


@Data
public class LoginRequest {

	/**
	 * 用户名
	 */
	private String account;

	/**
	 * 用户密码
	 */
	private String password;

	/**
	 * 验证码
	 */
	private String captcha;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
}
