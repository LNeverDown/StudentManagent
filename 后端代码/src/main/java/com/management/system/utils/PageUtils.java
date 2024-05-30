package com.management.system.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.management.system.vo.NormalQueryInfo;

import java.util.List;

public class PageUtils<Type> {

    public  List<Type> getPage(NormalQueryInfo normalQueryInfo, BaseMapper<Type> typeMapper, QueryWrapper<Type> wapper){
        IPage<Type> infoPage = new Page<>(normalQueryInfo.getPageNum(), normalQueryInfo.getPageSize());
        infoPage = typeMapper.selectPage(infoPage, null);
        return  infoPage.getRecords();
    }
}
