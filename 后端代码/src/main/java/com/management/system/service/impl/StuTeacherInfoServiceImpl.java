package com.management.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.management.system.entity.StuTeacherInfo;
import com.management.system.mapper.StuTeacherInfoMapper;
import com.management.system.service.IStuTeacherInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.management.system.utils.ConstName;
import com.management.system.utils.PageUtils;
import com.management.system.vo.NormalQueryInfo;
import com.management.system.vo.QueryListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StuTeacherInfoServiceImpl extends ServiceImpl<StuTeacherInfoMapper, StuTeacherInfo> implements IStuTeacherInfoService {


    @Autowired
    private StuTeacherInfoMapper stuTeacherInfoMapper;

    /**
     * 获取教师信息列表
     * */
    @Override
    public QueryListResponse<StuTeacherInfo> getTeacherInfosList(NormalQueryInfo normalQueryInfo){
        return new QueryListResponse<>(
                new PageUtils<StuTeacherInfo>().getPage(normalQueryInfo,stuTeacherInfoMapper,null),
                stuTeacherInfoMapper.selectCount(null)
        );
    }

    /**
     * 添加教师信息
     * */
    @Override
    public String addTeacherInfosList(StuTeacherInfo stuTeacherInfo){
        stuTeacherInfo.setId(ConstName.getOnlyTeacherId()+stuTeacherInfo.getId());
        try{
            stuTeacherInfoMapper.insert(stuTeacherInfo);
        }catch (RuntimeException err){
            String errMsg = err.getMessage();
            return errMsg.substring(errMsg.indexOf("Duplicate entry "),errMsg.indexOf("### The error may exist in"));
        }
        return null;
    }

    /**
     * 修改教师信息
     * */
    @Override
    public Boolean editTeacherInfosList(StuTeacherInfo stuTeacherInfo){
        try{
            stuTeacherInfoMapper.updateById(stuTeacherInfo);
        }catch (RuntimeException err){
            return false;
        }
        return true;
    }

    /**
     * 删除教师信息
     * */
    @Override
    public Boolean delTeacherInfosList(StuTeacherInfo stuTeacherInfo){
        try{
            stuTeacherInfoMapper.deleteById(stuTeacherInfo);
        }catch (RuntimeException err){
            return false;
        }
        return true;
    }

    /**
     * 获取所有没有相关课程的教师信息
     * */
    @Override
    public QueryListResponse<StuTeacherInfo> getAllTeacherInfosListNoCourse(){
        QueryWrapper<StuTeacherInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",-1);
        List<StuTeacherInfo> res = stuTeacherInfoMapper.selectList(queryWrapper);
        return new QueryListResponse<>(res,res.size());
    }

    /**
     * 为老师设置课程
     * */
    @Override
    public Boolean setCourseTeacherById(Integer teacherId,Integer courseId){
        UpdateWrapper<StuTeacherInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("course_id",courseId);
        updateWrapper.eq("id",teacherId);
        return this.update(updateWrapper);
    }

}
