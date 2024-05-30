package com.management.system.controller;


import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.management.system.entity.StuUser;
import com.management.system.security.JwtAuthenticatioToken;
import com.management.system.service.IStuUserService;
import com.management.system.utils.HttpResponse;
import com.management.system.utils.IOUtils;
import com.management.system.utils.PasswordUtils;
import com.management.system.utils.SecurityUtils;
import com.management.system.vo.LoginRequest;
import com.management.system.vo.LoginResponse;
import com.management.system.vo.NormalQueryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import org.apache.log4j.Logger;


@RestController
@RequestMapping("/stu-user")
public class StuUserController {

    @Autowired
    private Producer producer;

    @Autowired
    private IStuUserService iStuUserService;


    @Autowired
    private AuthenticationManager authenticationManager;


    private final Logger log = Logger.getLogger(StuUserController.class);

    @GetMapping("/user-list")
    public HttpResponse getUserList(NormalQueryInfo normalQueryInfo){
        return HttpResponse.success(iStuUserService.getUserList(normalQueryInfo));
    }

    @PostMapping("/edit")
    public HttpResponse editUser(@RequestBody StuUser stuUser){
        return HttpResponse.success(iStuUserService.editUser(stuUser));
    }

    @DeleteMapping("/del")
    public HttpResponse delUser(@RequestBody StuUser stuUser){
        return HttpResponse.success(iStuUserService.deleteUser(stuUser));
    }
    /**
     * 验证码
     */
    @GetMapping("/captcha.jpg")
    public void captcha(HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        // 生成文字验证码
        String text = producer.createText();
        // 生成图片验证码
        BufferedImage image = producer.createImage(text);
        // 保存到验证码到 session
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, text);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    /**
     * 登录接口
     */
    @PostMapping(value = "/login")
    public HttpResponse login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) throws IOException {
        String username = loginRequest.getAccount();
        String password = loginRequest.getPassword();
//        String captcha = loginBean.getCaptcha();
        // 从session中获取之前保存的验证码跟前台传来的验证码进行匹配
//        Object kaptcha = request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
//        if(kaptcha == null){
//            return HttpResult.error("验证码已失效");
//        }
//        if(!captcha.equals(kaptcha)){
//            return HttpResult.error("验证码不正确");
//        }
//        // 用户信息
//        System.out.println(username+"|"+password+"|"+captcha+" ");
        StuUser user = iStuUserService.findByName(username);
        // 账号不存在、密码错误
        if (user == null) {
            return HttpResponse.error("账号不存在");
        }
        if (!PasswordUtils.matches(user.getSalt(), password, user.getPassword())) {
            return HttpResponse.error("密码不正确");
        }
        JwtAuthenticatioToken token = SecurityUtils.login(request, username, password, authenticationManager);
        log.info("user:"+token.getName()+"login Success! [In "+token.getDetails().toString()+"]");
        return HttpResponse.success(new LoginResponse(token.getName(),token.getToken(),"OK!"));
    }

    @PostMapping(value="/save")
    public HttpResponse addUser(@RequestBody StuUser stuUser) {
        return HttpResponse.success(iStuUserService.addUser(stuUser));
    }



    @GetMapping(value="/findByName")
    public HttpResponse findByUserName(@RequestParam String name) {
        return HttpResponse.success(iStuUserService.findByName(name));
    }
}

