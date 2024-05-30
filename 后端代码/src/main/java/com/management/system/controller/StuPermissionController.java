package com.management.system.controller;


import com.management.system.service.IStuPermissionService;
import com.management.system.utils.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/stu-permission")
public class StuPermissionController {

    @Autowired
    private IStuPermissionService iStuPermissionService;

    /**
     * 获取菜单列表
     * */
    @GetMapping("/menus")
    public HttpResponse getMenus(){
        return HttpResponse.success(iStuPermissionService.getMenus());
    }

}

