package com.management.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.management.system.entity.StuInfo;
import com.management.system.entity.StuPerformance;
import com.management.system.mapper.StuInfoMapper;
import com.management.system.service.IStuInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.management.system.utils.ConstName;
import com.management.system.vo.QueryListResponse;
import com.management.system.vo.QueryStuInfoListRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class StuInfoServiceImpl extends ServiceImpl<StuInfoMapper, StuInfo> implements IStuInfoService {

    @Autowired
    private StuInfoMapper stuInfoMapper;

    @Override
    public QueryListResponse<StuInfo> getPageStudentInfo(Integer currPage, Integer TotalPage){
        IPage<StuInfo> stuInfoPage = new Page<>(currPage, TotalPage);//参数一是当前页，参数二是每页个数
        stuInfoPage = stuInfoMapper.selectPage(stuInfoPage, null);
        return new QueryListResponse<>(stuInfoPage.getRecords(),stuInfoMapper.selectCount(null));
    }

    @Override
    public QueryListResponse<StuInfo> getStudentInfoByCondition(QueryStuInfoListRequest queryStuInfoListRequest){
        Object conditionVal;
        //进行基本的类型转换，将String类型转换成int
        switch (queryStuInfoListRequest.getCondition()){
            case ConstName.NUMBER_COLUMN:
            case ConstName.CLASS_COLUMN:
            case ConstName.AGE_COLUMN:
            case ConstName.IF_VIOLATED_COLUMN:
                try {
                    conditionVal = Integer.parseInt(queryStuInfoListRequest.getQuery());
                }catch (NumberFormatException n){
                    return new QueryListResponse<>(null,-1);
                }
                break;
            default:
                conditionVal = queryStuInfoListRequest.getQuery();
        }
        QueryWrapper<StuInfo> queryWrapper = new QueryWrapper<>();
        // 当按照班级进行精确查询时不进行分页
        if(queryStuInfoListRequest.getCondition().equals(ConstName.CLASS_COLUMN)){
            queryWrapper.eq(queryStuInfoListRequest.getCondition(),conditionVal);
            return new QueryListResponse<>(stuInfoMapper.selectList(queryWrapper),stuInfoMapper.selectCount(queryWrapper));
        }else {
            //参数一是当前页，参数二是每页个数
            IPage<StuInfo> stuInfoPage = new Page<>(queryStuInfoListRequest.getPageNum(), queryStuInfoListRequest.getPageSize());
            if(!queryStuInfoListRequest.getIfFuzzySearch()){ // false:精确查询
                queryWrapper.eq(queryStuInfoListRequest.getCondition(),conditionVal);
            } else { // true:模糊查询
                queryWrapper.like(queryStuInfoListRequest.getCondition(),conditionVal);
            }
            stuInfoPage = stuInfoMapper.selectPage(stuInfoPage,queryWrapper);
            return new QueryListResponse<>(stuInfoPage.getRecords(),stuInfoMapper.selectCount(queryWrapper));
        }
    }

    @Override
    public String addStudentInfo(StuInfo stuInfo){
        Integer classNum = stuInfo.getClassNum();
        Integer indexInClass = stuInfoMapper.selectCount(new QueryWrapper<StuInfo>().eq(ConstName.CLASS_COLUMN,classNum));
        stuInfo.setNumber(161430000 + (classNum*100) + indexInClass + 1);
        stuInfo.setIfViolated(false);
        try{
            int count = stuInfoMapper.insert(stuInfo);
            return count == 1 ? "" : "Please Check!";
        }catch (RuntimeException e){
            String errMsg = e.getMessage();
            return errMsg.substring(errMsg.indexOf("Duplicate entry "),errMsg.indexOf("### The error may exist in"));
        }
    }

    @Override
    public int editStudentInfo(StuInfo stuInfo){
        UpdateWrapper<StuInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq(ConstName.ID_COLUMN,stuInfo.getId());
        return stuInfoMapper.update(stuInfo,updateWrapper);
    }


    @Override
    public int deleteStudentInfo(StuInfo stuInfo){
        Map<String,Object> map = new HashMap<>();
        map.put(ConstName.NUMBER_COLUMN,stuInfo.getNumber());
        return stuInfoMapper.deleteByMap(map);
    }

    @Override
    public int changeStudentStatus(StuInfo stuInfo){
        UpdateWrapper<StuInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq(ConstName.ID_COLUMN,stuInfo.getId());
        return stuInfoMapper.update(stuInfo,updateWrapper);
    }

}
