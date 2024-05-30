package com.management.system.service;

import com.management.system.entity.StuUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.management.system.vo.LoginRequest;
import com.management.system.vo.LoginResponse;
import com.management.system.vo.NormalQueryInfo;
import com.management.system.vo.QueryListResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;


public interface IStuUserService extends IService<StuUser> {

    // LoginResponse login(LoginRequest loginRequest, HttpServletRequest request);

    StuUser findByName(String name);

    Object delete(List<StuUser> records);

    Set<String> findPermissions(String name);

    QueryListResponse<StuUser> getUserList(NormalQueryInfo normalQueryInfo);

    String deleteUser(StuUser stuUser);

    String editUser(StuUser stuUser);

    Boolean addUser(StuUser stuUser);
}
