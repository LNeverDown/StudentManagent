<template>
    <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>课程管理</el-breadcrumb-item>
        <el-breadcrumb-item>教师信息管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片视图 -->

    <el-card style="width: 100%;height: auto">
        <el-button type="primary" @click="addTeacherDialogVisible = true">添加教师信息</el-button>
        <el-table :data="teacherList" border stripe>
            <el-table-column type="index"></el-table-column>
            <el-table-column label="姓名" align="center" prop="name"></el-table-column>
            <el-table-column label="ID" align="center" prop="id"></el-table-column>
            <el-table-column label="身份证号" align="center"  prop="idNum"></el-table-column>
            <el-table-column label="手机号" align="center" prop="phone"></el-table-column>
            <el-table-column label="邮箱" align="center" prop="email"></el-table-column>
            <el-table-column label="学历" align="center" prop="ed" width="100px"></el-table-column>
            <el-table-column label="性别" align="center" prop="sex" width="100px"></el-table-column>
            <el-table-column label="年龄" align="center" prop="age" width="100px"></el-table-column>
            <el-table-column label="操作"  align="center" >
                <template #default="scope">
                    <el-button size="mini" type="primary" @click="editTeacherInfoShowDialog(scope.$index, scope.row)">编辑</el-button>
                    <el-button size="mini" type="danger" @click="delTeacherInfoShowDialog(scope.$index, scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="queryInfo.pageNum"
                       :page-sizes="[15, 20, 30, 40]" :page-size="queryInfo.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
        </el-pagination>
    </el-card>
    <el-dialog title="添加教师信息" v-model="addTeacherDialogVisible" width="30%">
        <el-form :model="addTeacher">
            <el-form-item label="姓名" label-width="120px">
                <el-input v-model="addTeacher.name" ></el-input>
            </el-form-item>
            <el-form-item label="身份证号" label-width="120px">
                <el-input v-model="addTeacher.idNum" ></el-input>
            </el-form-item>
            <el-form-item label="手机号" label-width="120px">
                <el-input v-model="addTeacher.phone" ></el-input>
            </el-form-item>
            <el-form-item label="邮箱" label-width="120px">
                <el-input v-model="addTeacher.email" ></el-input>
            </el-form-item>
            <el-form-item label="学历" label-width="120px">
                <el-input v-model="addTeacher.ed" ></el-input>
            </el-form-item>
            <el-form-item label="性别" label-width="120px">
                <el-input v-model="addTeacher.sex" ></el-input>
            </el-form-item>
            <el-form-item label="年龄" label-width="120px">
                <el-input v-model="addTeacher.age"></el-input>
            </el-form-item>
        </el-form>
        <template #footer>
                <span class="dialog-footer">
                  <el-button @click="addTeacherDialogVisible = false">取 消</el-button>
                  <el-button type="primary" @click="addTeacherInfoHandel">确 定</el-button>
                </span>
        </template>
    </el-dialog>
    <el-dialog title="编辑教师信息" v-model="centerDialogVisible" width="25%" center :close-on-click-modal="false">
        <el-form :model="editTeacherInfo" label-width="80px">
            <el-form-item label="姓名">
                <el-input v-model="editTeacherInfo.name" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="ID">
                <el-input v-model="editTeacherInfo.id" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="身份证号">
                <el-input v-model="editTeacherInfo.idNum" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="手机号">
                <el-input v-model="editTeacherInfo.phone" ></el-input>
            </el-form-item>
            <el-form-item label="邮箱">
                <el-input v-model="editTeacherInfo.email"></el-input>
            </el-form-item>
            <el-form-item label="学历">
                <el-input v-model="editTeacherInfo.ed"></el-input>
            </el-form-item>
            <el-form-item label="年龄">
                <el-input v-model="editTeacherInfo.age"></el-input>
            </el-form-item>
            <el-form-item label="性别">
                <el-input v-model="editTeacherInfo.sex"></el-input>
            </el-form-item>
        </el-form>
        <template #footer>
                <span class="dialog-footer">
                  <el-button @click="centerDialogVisible = false">取 消</el-button>
                  <el-button type="primary" @click="editTeacherInfoHandel">确 定</el-button>
                </span>
        </template>
    </el-dialog>
    <el-dialog title="确认删除" v-model="delTeacherDialogVisible" width="30%" >
        <span>删除后将无法恢复！</span>
        <template #footer>
                <span class="dialog-footer">
                    <el-button @click="delTeacherDialogVisible = false;delTeacher={}">取 消</el-button>
                    <el-button type="danger" @click="delTeacherInfoHandel">确 定</el-button>
                </span>
        </template>
    </el-dialog>
</template>

<script>
import axios from "axios";

export default {
    name: "TeacherInfos",
    data(){
        return{
            //定义这一页面中所需的变量
            queryInfo:{
                pageNum:1,
                pageSize:20
            },
            teacherList:[],
            total:0,
            centerDialogVisible:false,
            editTeacherInfo:{},
            delTeacherDialogVisible:false,
            delTeacher:{},
            addTeacherDialogVisible:false,
            addTeacher:{
                name:'',
                phone:'',
                idNum:'',
                email:'',
                age:'',
                sex:'',
                ed:'',
            }
        }
    },
    created() {
        this.getTeacherInfoList()
    },
    methods:{
        async getTeacherInfoList(){
            const {data:res} = await axios.get("stu-teacher-info/get",{params:this.queryInfo})
            if(res.meta.status !== 200){
                return this.$message.error(res.meta.msg)
            }
            this.teacherList = res.data.infoList
            this.total = res.data.numOfTotalRecord
        },
        handleSizeChange(newSize) {
            this.queryInfo.pageSize = newSize
            this.getTeacherInfoList()
        },
        handleCurrentChange(newSize) {
            this.queryInfo.pageNum = newSize
            this.getTeacherInfoList()
        },
        editTeacherInfoShowDialog(index,row){
            this.centerDialogVisible = true
            this.editTeacherInfo = row
        },
        // 删除教师信息
        delTeacherInfoShowDialog(index,row){
            this.delTeacherDialogVisible = true
            this.delTeacher = row
        },
        async editTeacherInfoHandel(){
            if(this.editTeacherInfo.phone.length !== 11)return this.$message.error("手机号号输入有误")
            if(this.editTeacherInfo.sex !== '男' && this.editTeacherInfo.sex !== '女')return this.$message.error("性别输入有误")
            if(this.editTeacherInfo.ed !=='本科' && this.editTeacherInfo.ed !=='硕士' && this.editTeacherInfo.ed !=='博士'){
                return this.$message.error("学历输入有误")
            }
            if(this.editTeacherInfo.age < 0 || this.editTeacherInfo.age > 120)return this.$message.error("年龄输入有误")
            const {data: res} = await this.$http.post('stu-teacher-info/edit',this.editTeacherInfo)
            if (res.meta.status !== 200) {
                return this.$message.error('更新教师信息状态失败')
            }
            this.$message.success('更新教师信息状态成功')
            this.centerDialogVisible = false
            await this.getTeacherInfoList()
        },
        async delTeacherInfoHandel(){

            const {data:res} = await axios.delete("stu-teacher-info/del",{data:this.delTeacher})
            if(res.meta.status !== 200){
                return this.$message.error("删除教师信息失败")
            }
            this.delTeacherDialogVisible = false
            this.$message.success("删除教师信息成功！")
            await this.getTeacherInfoList()
        },
        //添加教师信息
        async addTeacherInfoHandel(){
            if(this.addTeacher.name === '') return this.$message.error("姓名不能为空")
            if(this.addTeacher.idNum.length !== 18)return this.$message.error("身份证号输入有误")
            if(this.addTeacher.phone.length !== 11) return this.$message.error("手机号输入有误")
            if(this.addTeacher.email === '') return this.$message.error("邮箱输入有误")
            if(this.addTeacher.ed !=='本科' && this.addTeacher.ed !=='硕士' && this.addTeacher.ed !=='博士'){
                return this.$message.error("学历输入有误")
            }
            if(this.addTeacher.sex !== '男' && this.addTeacher.sex !== '女')return this.$message.error("性别输入有误")
            this.addTeacher.age = Number(this.addTeacher.age)
            if(this.addTeacher.age < 1 || this.addTeacher.age > 120)return this.$message.error("年龄输入有误")
            this.addTeacher.id = this.total
            const {data:res} = await axios.post('stu-teacher-info/add', this.addTeacher)
            //根据状态码判断提示
            if(res.meta.status !== 200){
                return this.$message.error(res.meta.msg)
            }
            this.$message.success("添加教师信息成功")
            //添加成功后清楚数据
            this.addTeacher.name = this.addTeacher.idNum =  this.addTeacher.phone = this.addTeacher.email = ''
            this.addTeacher.age = this.addTeacher.ed = this.addTeacher.sex = ''
            await this.getTeacherInfoList()
            this.addTeacherDialogVisible = false
        },
    }
}
</script>

<style scoped lang="less">

</style>