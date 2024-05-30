<template>
    <!-- 在这里写 H5标签 -->
    <div>
        <!-- 面包屑导航区 -->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>学生管理</el-breadcrumb-item>
            <el-breadcrumb-item>学生信息列表</el-breadcrumb-item>
        </el-breadcrumb>
        <!-- 卡片视图 -->
        <el-card>
            <el-row :gutter="5">
                <el-col :span="15">
                    <div style="display: flex">
                        <div class="dropdown-menu" style="height:30px; width:120px">
                            <el-dropdown  @command="dropdownHandleCommand" trigger="click" style="margin-top: 10px">
                                <span>选择搜索依据<i class="el-icon-arrow-down el-icon--right"></i></span>
                                <template #dropdown>
                                    <el-dropdown-menu>
                                        <el-dropdown-item :command="key" v-for="(val,key) in queryConditionMap">{{val}}</el-dropdown-item>
                                    </el-dropdown-menu>
                                </template>
                            </el-dropdown>
                        </div>
                        <el-input :placeholder="inputTip" v-model="queryInfo.query" clearable style="width: 300px"></el-input>
                        <el-button slot="append" icon="el-icon-search" @click="getStudentListByCondition">搜索</el-button>
                        <div style="width: 200px;height: 40px;">
                            <el-switch style="display: block;margin-top: 10px;margin-left: 20px;" v-model="switchValue" inactive-color="#13ce66"
                                       @click="switchHandel" :disabled="switchDisabled" active-text="模糊查询" inactive-text="精确查询">
                            </el-switch>
                        </div>
                    </div>
                </el-col>
                <el-col :span="4">
                    <el-button type="primary" @click="addStudentInfoShowDialog">添加学生信息</el-button>
                </el-col>
            </el-row>
            <el-table :data="studentList" border stripe style="margin-top: 20px">
                <el-table-column type="index"></el-table-column>
                <el-table-column label="学号" prop="number" align="center" width="150px"></el-table-column>
                <el-table-column label="姓名" prop="name"  align="center" width="150px"></el-table-column>
                <el-table-column label="班级" prop="classNum"  align="center" width="70px"></el-table-column>
                <el-table-column label="身份证号" prop="id"  align="center" width="250px"></el-table-column>
                <el-table-column label="手机号" prop="phone"  align="center" width="200px"></el-table-column>
                <el-table-column label="性别" prop="sex"  align="center" width="70px"></el-table-column>
                <el-table-column label="年龄" prop="age"  align="center" width="70px"></el-table-column>
                <el-table-column label="是否违纪" prop="ifViolated" width="70px">
                    <template #default="scope">
                        <el-switch v-model="scope.row.ifViolated" @change="studentStateChangedHandel(scope.row)"></el-switch>
                    </template>
                </el-table-column>
                <el-table-column label="操作"  align="center" >
                    <template #default="scope">
                        <el-button size="mini" type="primary" @click="editStudentInfoShowDialog(scope.$index, scope.row)">编辑</el-button>
                        <el-button size="mini" type="danger" @click="delStudentInfoShowDialog(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <!-- 分页区域 -->
            <el-pagination v-if="ifShowPagination" @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="queryInfo.pageNum"
                :page-sizes="[15, 20, 30, 40]" :page-size="queryInfo.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
            </el-pagination>
        </el-card>
        <el-dialog title="编辑学生信息" v-model="centerDialogVisible" width="30%" center :close-on-click-modal="false">
            <el-form :model="editStudentInfo" label-width="80px">
                <el-form-item label="学号">
                    <el-input v-model="editStudentInfo.number" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="姓名">
                    <el-input v-model="editStudentInfo.name" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="班级">
                    <el-input v-model="editStudentInfo.classNum" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="身份证号">
                    <el-input v-model="editStudentInfo.id" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="手机号">
                    <el-input v-model="editStudentInfo.phone"></el-input>
                </el-form-item>
                <el-form-item label="性别">
                    <el-input v-model="editStudentInfo.sex"></el-input>
                </el-form-item>
                <el-form-item label="年龄">
                    <el-input v-model="editStudentInfo.age" :disabled="true"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                  <el-button @click="centerDialogVisible = false">取 消</el-button>
                  <el-button type="primary" @click="editStudentInfoHandel">确 定</el-button>
                </span>
            </template>
        </el-dialog>
        <el-dialog title="添加学生信息" v-model="addStudentDialogVisible" width="30%">
            <el-form :model="addStudent">
                <el-form-item label="姓名" label-width="120px">
                    <el-input v-model="addStudent.name" ></el-input>
                </el-form-item>
                <el-form-item label="班级" label-width="120px">
                    <el-input v-model="addStudent.classNum" ></el-input>
                </el-form-item>
                <el-form-item label="身份证号" label-width="120px">
                    <el-input v-model="addStudent.id" ></el-input>
                </el-form-item>
                <el-form-item label="手机号" label-width="120px">
                    <el-input v-model="addStudent.phone" ></el-input>
                </el-form-item>
                <el-form-item label="性别" label-width="120px">
                    <el-input v-model="addStudent.sex" ></el-input>
                </el-form-item>
                <el-form-item label="年龄" label-width="120px">
                    <el-input v-model="addStudent.age"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                  <el-button @click="addStudentDialogVisible = false">取 消</el-button>
                  <el-button type="primary" @click="addStudentInfoHandel">确 定</el-button>
                </span>
            </template>
        </el-dialog>
        <el-dialog title="确认删除" v-model="delStudentDialogVisible" width="30%" >
            <span>删除后将无法恢复！</span>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="delStudentDialogVisible = false;delStudent={}">取 消</el-button>
                    <el-button type="danger" @click="delStudentInfoHandel">确 定</el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>

<script>
//js Code
import axios from "axios";

export default{
    //这一页面的名称
    name:'StudentInfoPage',
    data(){
        return{
            //定义这一页面中所需的变量
            queryInfo: {
                query: '',
                pageNum: 1,
                pageSize: 20,
                condition:'',
                // 是否为模糊搜索
                ifFuzzySearch:false,
            },
            // 总数
            total: 1306,
            studentList:[],
            inputTip:"请选择查询依据",
            queryConditionShow:'',
            ifShowPagination:true,
            queryConditionList:['全部','学号','姓名','班级','身份证号','手机号','是否违纪','年龄','性别'],
            queryConditionMap:{
                "all":"全部",
                "number"     :"学号",
                "name"       :"姓名",
                "class_num"   :"班级",
                // "classNum"   :"班级",
                "id"         :"身份证号",
                "phone"      :"手机号",
                "sex"        :"是否违纪",
                "age"        :"年龄",
                "if_violated" :"性别",
                // "ifViolated" :"性别",
            },
            // false是精确查询   true是模糊查询
            switchValue:false,
            // 开关是否可以修改
            switchDisabled:false,
            centerDialogVisible: false,
            editStudentInfo:{},
            addStudentDialogVisible:false,
            delStudentDialogVisible:false,
            delStudent:{},
            addStudent: {
                name: '',
                id: '',
                phone: '',
                sex: '',
                age: '',
                classNum: '',
            },
        }
    },
    created() {
        this.getStudentList()
    },
    methods:{
        async getStudentList() {
            const {data: res} = await this.$http.get('stu-info/get', {params: this.queryInfo})
            if (res.meta.status !== 200) {
                return this.$message.error('获取学生信息列表失败！')
            }
            this.studentList = res.data.infoList
            this.total = res.data.numOfTotalRecord
            this.ifShowPagination = true;
        },
        // 通过条件搜索
        async getStudentListByCondition() {
            this.queryInfo.ifFuzzySearch = this.switchValue
            if(this.queryInfo.query === '' ) return this.$message.error("请输入查询依据")
            const {data: res} = await this.$http.get('stu-info/get-test', {params: this.queryInfo})
            if (res.meta.status !== 200) {
                return this.$message.error('获取学生信息列表失败！')
            }
            this.studentList = res.data.infoList;
            this.total = res.data.numOfTotalRecord;
            if(this.queryInfo.condition === 'class_num'){
                this.ifShowPagination = false;
            }else{
                this.ifShowPagination = true;
            }
        },
        dropdownHandleCommand(command){
            let commandKey = this.queryConditionMap[command]
            if(commandKey === '全部'){
                // 显示开关
                this.switchDisabled = true
                // 显示分页
                this.ifShowPagination = true;
                this.queryInfo.pageNum = 1
                this.queryInfo.pageSize = 20
                this.getStudentList()
                return
            }
            // ”班级“、”是否违纪“、“年龄”、“性别”，只支持精确查询
            if(commandKey === '班级' || commandKey === '是否违纪'|| commandKey === '年龄'|| commandKey === '性别'){
                this.switchValue = false
                this.switchDisabled = true
            }else{
                this.switchDisabled = false
            }
            this.queryInfo.condition = command
            this.queryInfo.ifFuzzySearch = this.switchValue
            this.inputTip = "搜索依据:"+ commandKey
        },
        handleSizeChange(newSize) {
            this.queryInfo.pageSize = newSize
            this.getStudentList()
        },
        handleCurrentChange(newSize) {
            this.queryInfo.pageNum = newSize
            this.getStudentList()
        },
        addStudentInfoShowDialog() {
            this.addStudentDialogVisible = true
            // this.getCateList()
            //this.$router.push('/goods/add')
        },
        editStudentInfoShowDialog(index,row){
            this.centerDialogVisible = true
            this.editStudentInfo = row
        },
        async editStudentInfoHandel() {
            if(this.editStudentInfo.phone.length !== 11){
                return this.$message.error("手机号号输入有误")
            }
            if(this.editStudentInfo.sex !== '男' && this.editStudentInfo.sex !== '女'){
                return this.$message.error("性别输入有误")
            }
            const {data: res} = await this.$http.post('stu-info/edit',this.editStudentInfo)
            if (res.meta.status !== 200) {
                return this.$message.error('更新学生信息状态失败')
            }
            this.$message.success('更新学生信息状态成功')
            this.centerDialogVisible = false
            await this.getStudentList()
        },
        // 监听 switch开关 状态改变，改变学生状态
        async studentStateChangedHandel(stuInfo) {
            const {data: res} = await this.$http.post('stu-info/change',{"ifViolated":stuInfo.ifViolated,"id":stuInfo.id})
            if (res.meta.status !== 200) {
                stuInfo.ifViolated = !stuInfo.ifViolated
                return this.$message.error('更新用户状态失败')
            }
            this.$message.success('更新用户状态成功！')
        },
        //添加学生信息
        async addStudentInfoHandel(){
            this.addStudent.age = Number(this.addStudent.age)
            if(this.addStudent.name === '' )return this.$message.error("姓名不能为空")
            if(this.addStudent.classNum <= 0 || this.addStudent.classNum >20 )return this.$message.error("班级信息输入有误")
            if(this.addStudent.id.length !== 18)return this.$message.error("身份证号输入有误")
            if(this.addStudent.phone.length !== 11) return this.$message.error("手机号号输入有误")
            if(this.addStudent.sex !== '男' && this.addStudent.sex !== '女')return this.$message.error("性别输入有误")
            if(this.addStudent.age <= 0 || this.addStudent.age >30 )return this.$message.error("年龄信息输入有误")

            const {data:res} = await axios.post('stu-info/add', {
                "name":this.addStudent.name,
                "classNum":this.addStudent.classNum,
                "id":this.addStudent.id,
                "phone":this.addStudent.phone,
                "sex":this.addStudent.sex,
                "age":this.addStudent.age,
            })
            //添加成功后清楚数据
            this.addStudent.name = this.addStudent.number =  this.addStudent.id = this.addStudent.classNum = ''
            this.addStudent.phone = this.addStudent.age = this.addStudent.sex = ''
            //根据状态码判断提示
            if(res.meta.status !== 200){
                return this.$message.error(res.meta.msg)
            }else{
                this.$message.success(res.meta.msg)
            }
            this.addStudentDialogVisible = false
        },
        // 删除学生信息
        delStudentInfoShowDialog(index,row){
            this.delStudentDialogVisible = true
            this.delStudent = row
        },
        async delStudentInfoHandel(){
            const {data:res} = await axios.delete("stu-info/delete",{data:this.delStudent})
            if(res.meta.status !== 200){
                return this.$message.error(res.meta.msg)
            }
            this.delStudentDialogVisible = false
            this.$message.success("删除学生信息成功！")
            await this.getStudentList()
        },
        switchHandel(){
            // this.$message.success(this.switchValue)
        },
    }
}
</script>

<style scoped lang="less">
//在这里写css
    .el-card{
        margin-top: 20px;
    }
</style>
