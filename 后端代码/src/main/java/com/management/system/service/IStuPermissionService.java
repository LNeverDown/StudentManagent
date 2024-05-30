package com.management.system.service;

import com.management.system.entity.StuPermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface IStuPermissionService extends IService<StuPermission> {

    List<StuPermission> getMenus();
}
