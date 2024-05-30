package com.management.system.controller;


import com.management.system.entity.StuCourse;
import com.management.system.service.IStuCourseService;
import com.management.system.utils.HttpResponse;
import com.management.system.vo.NormalQueryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/stu-course")
public class StuCourseController {

    @Autowired
    private IStuCourseService iStuCourseService;

    @GetMapping("/hello")
    public String getHello(){
        return "Hello";
    }

    @GetMapping("/get")
    public HttpResponse getCourseList(NormalQueryInfo normalQueryInfo){
        return HttpResponse.success(iStuCourseService.getCourseList(normalQueryInfo));
    }

    @GetMapping("/tags")
    public HttpResponse getTeacherTags(@RequestParam(value = "courseId") Integer courseId){
        return HttpResponse.success(iStuCourseService.getStuTeacherTagList(courseId));
    }

    @PostMapping("/add")
    public HttpResponse addCourse(@RequestBody StuCourse stuCourse){
        return HttpResponse.success(iStuCourseService.addCourse(stuCourse));
    }

    @PostMapping("/edit")
    public HttpResponse editCourse(@RequestBody StuCourse stuCourse){
        return HttpResponse.success(iStuCourseService.editCourse(stuCourse));
    }

    @DeleteMapping("/del")
    public HttpResponse delCourse(@RequestBody StuCourse stuCourse){
        return HttpResponse.success(iStuCourseService.delCourse(stuCourse));
    }
}

