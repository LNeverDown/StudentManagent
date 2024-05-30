<template>
    <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>课程管理</el-breadcrumb-item>
        <el-breadcrumb-item>课程信息管理</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card style="width: 100%;height: auto">
        <el-button type="primary" @click="addCourseDialogVisible = true">添加课程信息</el-button>
        <el-table :data="courseList" border stripe>
            <el-table-column  label="任课教师" type="expand" prop="tagList">
                <template #default="scope"  >
                    <el-tag v-if="scope.row.tagList[0] !== null" closable @close="removeTeacherCourseById(scope.row, item.id)"
                            v-for="item in scope.row.tagList" :disable-transitions="false" style="margin-right: 15px">{{item.name}}</el-tag>
                    <el-button class="button-new-tag" size="small" @click="addTeacherToCourse(scope.row)">+ New Tag</el-button>
                </template>
            </el-table-column>
            <el-table-column label="课程id" align="center" prop="id"></el-table-column>
            <el-table-column label="课程名称" align="center" prop="name"></el-table-column>
            <el-table-column label="分数值" align="center"  prop="scoreValue"></el-table-column>
            <el-table-column label="课时" align="center" prop="period"></el-table-column>
            <el-table-column label="是否完结" align="center" prop="finished"></el-table-column>
            <el-table-column label="操作"  align="center" >
                <template #default="scope">
                    <el-button size="mini" type="primary" @click="editCourseShowDialogShow(scope.$index, scope.row)">编辑</el-button>
                    <el-button size="mini" type="danger" @click="delCourseShowDialogShow(scope.$index, scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
    </el-card>
    <el-dialog title="添加课程信息" v-model="addCourseDialogVisible" width="30%">
        <el-form :model="addCourse">
            <el-form-item label="课程名称" label-width="120px">
                <el-input v-model="addCourse.name" ></el-input>
            </el-form-item>
            <el-form-item label="分数值" label-width="120px">
                <el-input v-model="addCourse.scoreValue" ></el-input>
            </el-form-item>
            <el-form-item label="课时" label-width="120px">
                <el-input v-model="addCourse.period" ></el-input>
            </el-form-item>
        </el-form>
        <template #footer>
                <span class="dialog-footer">
                  <el-button @click="addCourseDialogVisible = false">取 消</el-button>
                  <el-button type="primary" @click="addCourseInfoHandel">确 定</el-button>
                </span>
        </template>
    </el-dialog>
    <el-dialog title="修改课程信息" v-model="editCourseDialogVisible" width="30%">
        <el-form :model="editCourse">
            <el-form-item label="课程名称" label-width="120px">
                <el-input v-model="editCourse.name" ></el-input>
            </el-form-item>
            <el-form-item label="分数值" label-width="120px">
                <el-input v-model="editCourse.scoreValue" ></el-input>
            </el-form-item>
            <el-form-item label="课时" label-width="120px">
                <el-input v-model="editCourse.period" ></el-input>
            </el-form-item>
        </el-form>
        <template #footer>
                <span class="dialog-footer">
                  <el-button @click="editCourseDialogVisible = false">取 消</el-button>
                  <el-button type="primary" @click="editCourseInfoHandel">确 定</el-button>
                </span>
        </template>
    </el-dialog>
    <el-dialog title="确认删除" v-model="delCourseDialogVisible" width="30%" >
        <span>删除后将无法恢复！</span>
        <template #footer>
                <span class="dialog-footer">
                    <el-button @click="delCourseDialogVisible = false;delCourse={}">取 消</el-button>
                    <el-button type="danger" @click="delCourseInfoHandel">确 定</el-button>
                </span>
        </template>
    </el-dialog>
    <el-dialog title="教师选择" v-model="addTeacherToCourseDialogVisible" width="30%" >
        <el-select v-model="teacherValue.teacherId" placeholder="请选择">
            <el-option v-for="item in teacherOptions" :label="item.name" :value="item.id"></el-option>
        </el-select>
        <template #footer>
                <span class="dialog-footer">
                    <el-button @click="addTeacherToCourseDialogVisible = false;">取 消</el-button>
                    <el-button type="danger" @click="addTeacherToCourseHandel">确 定</el-button>
                </span>
        </template>
    </el-dialog>
</template>

<script>
import axios from "axios";

export default{
    //这一页面的名称
    name:'CourseInfo',
    data(){
        return {
            //定义这一页面中所需的变量
            queryInfo:{
                pageNum:1,
                pageSize:20
            },
            courseList:[],
            total:0,
            addCourseDialogVisible:false,
            addCourse:{
                name:'',
                scoreValue:'',
                period:''
            },
            editCourseDialogVisible:false,
            editCourse:{
                name:'',
                scoreValue:'',
                period:''
            },
            delCourseDialogVisible:false,
            delCourse:{
                name:'',
                scoreValue:'',
                period:''
            },
            addTeacherToCourseDialogVisible:false,
            teacherOptions: [],
            teacherValue: {
                teacherId:'',
                courseId:''
            }
        }
    },
    created() {
        this.getCourseList()
    },
    methods:{
        //定义这一页面中所需的方法
        async getCourseList(){
            const {data:res} = await axios.get("/stu-course/get",{params:this.queryInfo})
            if(res.meta.status !== 200){
                return this.$message.error(res.meta.msg)
            }
            this.courseList = res.data.infoList
            this.total = res.data.numOfTotalRecord
        },
        async addCourseInfoHandel(){
            if(this.addCourse.name === '')return this.$message.error("课程名不能为空")
            if(this.addCourse.scoreValue === '')return this.$message.error("课程分数值不能为空")
            if(this.addCourse.period === '')return this.$message.error("课程学时不能为空")
            if(isNaN(Number(this.addCourse.scoreValue))) return this.$message.error("课程分数值输入有误")
            if(isNaN(Number(this.addCourse.period))) return this.$message.error("课程学时输入有误")
            this.addCourse.id = this.total
            const {data:res} = await axios.post("stu-course/add",this.addCourse)
            if(res.meta.status !== 200){
                return this.$message.error(res.meta.msg)
            }
            this.addCourseDialogVisible = false
            this.$message.success("添加课程信息成功")
            await this.getCourseList()
        },
        async removeTeacherCourseById(row,id){
            const {data:res} = await axios.get("stu-teacher-info/remove-course-teacher", {params:{"teacherId":id}})
            if(res.meta.status !== 200){
                return this.$message.error(res.meta.msg)
            }
            this.$message.success("移除课程教师成功")
            await this.getCourseList()
        },
        editCourseShowDialogShow(id,row) {
            this.editCourse = row
            this.editCourseDialogVisible = true
        },
        async editCourseInfoHandel(){
            const {data:res} = await axios.post("stu-course/edit",this.editCourse)
            if(res.meta.status !== 200){
                return this.$message.error(res.meta.msg)
            }
            this.editCourseDialogVisible = false
            this.$message.success("编辑课程信息成功")
        },
        async delCourseInfoHandel(){
            const {data:res} = await axios.delete("stu-course/del", {data:this.delCourse})
            if(res.meta.status !== 200){
                return this.$message.error(res.meta.msg)
            }
            this.delCourseDialogVisible = false
            this.$message.success("删除课程信息成功")
            await this.getCourseList()
        },
        async delCourseShowDialogShow(id,row){
            this.delCourse = row
            this.delCourseDialogVisible = true
        },
        async addTeacherToCourse(row){
            const {data:res} = await axios.get("stu-teacher-info/get-no-course")
            if(res.meta.status !== 200){
                return this.$message.error(res.meta.msg)
            }
            this.teacherOptions = res.data.infoList
            this.teacherValue.courseId = row.id
            this.addTeacherToCourseDialogVisible = true
        },
        async addTeacherToCourseHandel(){
            if(this.teacherValue.teacherId === '')return this.$message.error('请选择一个没有任课信息的教师')
            const {data:res} = await axios.get("stu-teacher-info/set-course",{params:this.teacherValue})
            if(res.meta.status !== 200){
                return this.$message.error(res.meta.msg)
            }
            this.teacherValue={}
            this.addTeacherToCourseDialogVisible = false
            this.$message.success("设置课程教师成功")
            await this.getCourseList()
        }
    }
}
</script>

<style scoped lang="less">
//在这里写css
</style>
