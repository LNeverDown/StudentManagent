package com.management.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.management.system.entity.StuCourse;
import com.management.system.entity.StuTeacherInfo;
import com.management.system.entity.StuTeacherTag;
import com.management.system.mapper.StuCourseMapper;
import com.management.system.mapper.StuTeacherInfoMapper;
import com.management.system.mapper.StuTeacherTagMapper;
import com.management.system.service.IStuCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.management.system.utils.ConstName;
import com.management.system.utils.PageUtils;
import com.management.system.vo.NormalQueryInfo;
import com.management.system.vo.QueryListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StuCourseServiceImpl extends ServiceImpl<StuCourseMapper, StuCourse> implements IStuCourseService {

    @Autowired
    private StuCourseMapper stuCourseMapper;

    @Autowired
    private StuTeacherTagMapper stuTeacherTagMapper;

    @Autowired
    private StuTeacherInfoMapper stuTeacherInfoMapper;

    @Override
    public QueryListResponse<StuCourse> getCourseList(NormalQueryInfo normalQueryInfo){
        List<StuCourse> res = new PageUtils<StuCourse>().getPage(normalQueryInfo,stuCourseMapper,null);
        for(StuCourse s : res){
            List<StuTeacherTag> tagList = this.getStuTeacherTagList(s.getId());
            s.setTagList(tagList.size() == 0 ? null: tagList);
        }
        return new QueryListResponse<>(res,stuCourseMapper.selectCount(null));
    }

    @Override
    public List<StuTeacherTag> getStuTeacherTagList(Integer courseId){
        QueryWrapper<StuTeacherTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sc.id",courseId);
        return stuTeacherTagMapper.getTeacherListByCourseName(queryWrapper);
    }

    @Override
    public Boolean addCourse( StuCourse stuCourse){
        stuCourse.setId(ConstName.getOnlyCourseId()+stuCourse.getId());
        stuCourse.setFinished(false);
        try{
            stuCourseMapper.insert(stuCourse);
        }catch (RuntimeException err){
            return false;
        }
        return true;
    }

    /**
     * 编辑课程基本信息
     * */
    @Override
    public Boolean editCourse(StuCourse stuCourse){
        try{
            stuCourseMapper.updateById(stuCourse);
        }catch (RuntimeException err){
            return false;
        }
        return true;
    }

    /**
     * 删除课程
     * */
    @Override
    public Boolean delCourse(StuCourse stuCourse){
        // 删除课程逻辑：首先要批量修改任课教师的任课id，然后将该课程信息删除
        if(this.updateTeacherCourseEqualNoneBatchById(stuCourse.getTagList())){
            try{
                stuCourseMapper.deleteById(stuCourse);
            }catch (RuntimeException err){
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean updateTeacherCourseEqualNoneBatchById(List<StuTeacherTag> tagList){
        for(StuTeacherTag tag: tagList){
            UpdateWrapper<StuTeacherInfo> infoUpdateWrapper = new UpdateWrapper<>();
            infoUpdateWrapper.set("course_id", -1);
            infoUpdateWrapper.eq("id", tag.getId());
            int res = stuTeacherInfoMapper.update(null, infoUpdateWrapper);
            if(res == 0) return  false;
        }
        return true;
    }
}
