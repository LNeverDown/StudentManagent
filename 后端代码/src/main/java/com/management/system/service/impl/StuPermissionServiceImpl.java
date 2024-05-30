package com.management.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.management.system.entity.StuPermission;
import com.management.system.mapper.StuPermissionMapper;
import com.management.system.service.IStuPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class StuPermissionServiceImpl extends ServiceImpl<StuPermissionMapper, StuPermission> implements IStuPermissionService {

    @Autowired
    private StuPermissionMapper stuPermissionMapper;

    @Override
    public List<StuPermission> getMenus(){
        QueryWrapper<StuPermission> stuPermissionWrapper = new QueryWrapper<>();
        stuPermissionWrapper.select("id,auth_name","pid","level","path");
        stuPermissionWrapper.orderByAsc("level");
        List<StuPermission> res =  stuPermissionMapper.selectList(stuPermissionWrapper);
        return processMenu(res,false);
    }

    private List<StuPermission> processMenu(List<StuPermission> menuItems,boolean isLevelThree){
        StuPermission[] Menus = new StuPermission[5];
        Map<Integer,Integer> LevelOneMenuIdIndexMap = new HashMap<>();
        Map<Integer,Integer> LevelTwoMenuIdIndexMap = new HashMap<>();
        int menusIndex = 0;
        StuPermission menuItem;
        Iterator<StuPermission> menuIterable  = menuItems.iterator();
        while (menuIterable.hasNext()) {
            menuItem = menuIterable.next();
            if (menuItem.getLevel() == 0) {
                //处理并得到一级菜单
                //menu.setChildren(new ArrayList<>());
                Menus[menusIndex] = menuItem;
                Menus[menusIndex].setChildren(new ArrayList<>());
                //记录当前id的位置
                LevelOneMenuIdIndexMap.put(menuItem.getId(),menusIndex);
                menusIndex++;
            }else if( menuItem.getLevel() ==  1 ) {
                //处理并得到二菜单，由于没有重复的PsId字段，所以字典可以重用
                //获取一级菜单在切片中的索引位置
                menusIndex = LevelOneMenuIdIndexMap.get(menuItem.getPid());
                //添加信息
                Menus[menusIndex].getChildren().add(menuItem);
                //记录当前id的父id
                LevelOneMenuIdIndexMap.put(menuItem.getId(),menuItem.getPid());
                //记录当前id在第二级切片中的索引位置
                LevelTwoMenuIdIndexMap.put(menuItem.getId(),Menus[menusIndex].getChildren().size()-1);
            }else{
                break;
            }
        }
        if(isLevelThree){
            int LevelOneMenuIndex, LevelTwoMenuIndex;
            while(menuIterable.hasNext()){
                menuItem = menuIterable.next();
                LevelOneMenuIndex = LevelOneMenuIdIndexMap.get(LevelOneMenuIdIndexMap.get(menuItem.getPid()));
                LevelTwoMenuIndex = LevelTwoMenuIdIndexMap.get(menuItem.getPid());
                Menus[LevelOneMenuIndex].getChildren().get(LevelTwoMenuIndex).getChildren().add(menuItem);
            }
        }
        return Arrays.asList(Menus);
    }

}
