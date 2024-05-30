package com.management.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.management.system.controller.StuUserController;
import com.management.system.entity.StuUser;
import com.management.system.mapper.StuUserMapper;
import com.management.system.security.JwtAuthenticatioToken;
import com.management.system.service.IStuUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.management.system.utils.ConstName;
import com.management.system.utils.HttpResponse;
import com.management.system.utils.PasswordUtils;
import com.management.system.utils.SecurityUtils;
import com.management.system.vo.LoginRequest;
import com.management.system.vo.LoginResponse;
import com.management.system.vo.NormalQueryInfo;
import com.management.system.vo.QueryListResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class StuUserServiceImpl extends ServiceImpl<StuUserMapper, StuUser> implements IStuUserService {

    @Autowired
    private StuUserMapper stuUserMapper;

    // @Override
    // public StuUser findById(Integer id){
    //     return null;
    // }


    private final Logger log = Logger.getLogger(StuUserController.class);

    @Override
    public StuUser findByName(String name){
        QueryWrapper<StuUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",name);
        return stuUserMapper.selectOne(queryWrapper);

    }

    @Override
    public Object delete(List<StuUser> records){
        return null;
    }

    @Override
    public Set<String> findPermissions(String name){
        Set<String> set = new HashSet<>();
        set.add("ignore");
        return set;
    }

    @Override
    public QueryListResponse<StuUser> getUserList(NormalQueryInfo normalQueryInfo){
        QueryWrapper<StuUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","name","role_id","phone");
        return new QueryListResponse<>(stuUserMapper.selectList(queryWrapper),stuUserMapper.selectCount(null));
    }

    @Override
    public String deleteUser(StuUser stuUser){
        if(stuUser.getId() == 0){
            return "根用户不能被删除";
        }
        try {
            int res = stuUserMapper.deleteById(stuUser);
            if(res != 1) return "更新失败";
        }catch (RuntimeException err){
            return err.getMessage();
        }
        return null;
    }

    @Override
    public String editUser(StuUser stuUser){
        //判断是否需要更改密码
        String password = stuUser.getPassword();
        if(password != null && !password.equals("")){
            String salt = PasswordUtils.getSalt();
            stuUser.setSalt(salt);
            stuUser.setPassword(PasswordUtils.encode(password, salt));
        }
        int res = stuUserMapper.updateById(stuUser);
        if(res != 1) return "更新失败";
        return null;
    }

    @Override
    public Boolean addUser(StuUser stuUser){
        stuUser.setRoleId(1234);
        String salt = PasswordUtils.getSalt();
        stuUser.setSalt(salt);
        stuUser.setPassword(PasswordUtils.encode(stuUser.getPassword(), salt));
        stuUser.setId(stuUserMapper.selectCount(null));
        return this.save(stuUser);
    }

}
