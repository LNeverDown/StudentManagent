package com.management.system.controller;


import com.management.system.entity.StuTeacherInfo;
import com.management.system.service.IStuTeacherInfoService;
import com.management.system.utils.HttpResponse;
import com.management.system.vo.NormalQueryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/stu-teacher-info")
public class StuTeacherInfoController {

    @Autowired
    private IStuTeacherInfoService iStuTeacherInfoService;

    @GetMapping("/get")
    public HttpResponse getTeacherInfosList(NormalQueryInfo normalQueryInfo){
        return HttpResponse.success(iStuTeacherInfoService.getTeacherInfosList(normalQueryInfo));
    }

    @PostMapping("/add")
    public HttpResponse addTeacherInfosList(@RequestBody StuTeacherInfo stuTeacherInfo){
        String res = iStuTeacherInfoService.addTeacherInfosList(stuTeacherInfo);
        return res == null ? HttpResponse.success() : HttpResponse.error(res) ;
    }

    @PostMapping("/edit")
    public HttpResponse editTeacherInfosList(@RequestBody StuTeacherInfo stuTeacherInfo){
        return HttpResponse.success(iStuTeacherInfoService.editTeacherInfosList(stuTeacherInfo));
    }

    @DeleteMapping("/del")
    public HttpResponse delTeacherInfosList(@RequestBody StuTeacherInfo stuTeacherInfo){
        return HttpResponse.success(iStuTeacherInfoService.delTeacherInfosList(stuTeacherInfo));
    }

    @GetMapping("/remove-course-teacher")
    public HttpResponse delCourse(@RequestParam("teacherId") Integer teacherId){
        return HttpResponse.success(iStuTeacherInfoService.setCourseTeacherById(teacherId,-1));
    }

    @GetMapping("/get-no-course")
    public HttpResponse getAllTeacherInfosListNoCourse(){
        return HttpResponse.success(iStuTeacherInfoService.getAllTeacherInfosListNoCourse());
    }

    @GetMapping("/set-course")
    public HttpResponse setCourseTeacher(@RequestParam(value = "teacherId") Integer teacherId,
                                         @RequestParam(value = "courseId") Integer courseId){
        return HttpResponse.success(iStuTeacherInfoService.setCourseTeacherById(teacherId,courseId));
    }

}

