<template>
    <!-- 面包屑导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>学生管理</el-breadcrumb-item>
        <el-breadcrumb-item>学生成绩列表</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片视图 -->
    <el-card>
        <el-card class="box-card" style="font-size: 16px;background-color: #FAFAD2;">
            <div>
                <el-button-group>
                    <el-button type="primary" @click="getStudentPerformanceDialog" icon="el-icon-search"
                               style="width: 200px;border-top-left-radius: 25px;border-bottom-left-radius: 25px">条件查询</el-button>
                    <el-button type="primary" @click="freshCondition" icon="el-icon-refresh"
                                 style="border-top-right-radius: 25px;border-bottom-right-radius: 25px">清空</el-button>
                </el-button-group>
                <el-button v-if="ifChangeable" type="danger" @click="changeStudentPerformanceDialogShow"
                           style="border-radius: 25px;float: right" >修改成绩</el-button>
                <el-button v-else type="danger" @click="addStudentPerformanceDialogShow"
                           style="border-radius: 25px;float: right" >录入成绩</el-button>
                <span style="margin-left: 20px">当前查询条件：{{queryStatus}}</span>
            </div>
        </el-card>
        <el-table :data="stuPerformanceList"  border stripe style="width: 100%;margin-top: 20px">
            <el-table-column prop="ranking" label="排名" sortable align="center" width="70"></el-table-column>
            <el-table-column prop="name" label="姓名" sortable align="center"></el-table-column>
            <el-table-column prop="number" label="学号" sortable align="center" min-width="100"></el-table-column>
            <el-table-column prop="classNum" label="班级" sortable  align="center" ></el-table-column>
            <el-table-column prop="noc" label="计算机网络" sortable align="center"  ></el-table-column>
            <el-table-column prop="os" label="操作系统" sortable  align="center" ></el-table-column>
            <el-table-column prop="pocc" label="计算机组成原理" sortable  align="center" min-width="100"></el-table-column>
            <el-table-column prop="english" label="英语" sortable align="center"  ></el-table-column>
            <el-table-column prop="math" label="数学" sortable align="center"  ></el-table-column>
            <el-table-column prop="cpp" label="C++" sortable  align="center" ></el-table-column>
            <el-table-column prop="golang" label="Golang" sortable align="center"  ></el-table-column>
            <el-table-column prop="rust" label="Rust" sortable align="center" ></el-table-column>
            <el-table-column prop="java" label="Java" sortable align="center" ></el-table-column>
            <el-table-column prop="total" label="总分" sortable align="center"></el-table-column>
        </el-table>
        <el-pagination v-if="ifShowPagination" @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="queryInfo.pageNum"
                       :page-sizes="[15, 20, 30, 40]" :page-size="queryInfo.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
        </el-pagination>
        <div v-else style="font-size: 16px ;margin-top:20px">
            <span> 共查询到{{total}}条记录</span>
        </div>
    </el-card>
    <el-dialog title="条件查询" v-model="queryConditionDialogShow" width="30%" :close-on-click-modal="false">
        <el-tabs v-model="activeName">
            <el-tab-pane label="查询个人学生成绩信息" name="first">
                <div style="width: 60%; margin: auto; padding: 20px;">
                    <el-form :model="singleStudentInfos" label-width="50px">
                        <el-form-item label="姓名">
                            <el-input placeholder="请输入姓名" v-model="singleStudentInfos.name"></el-input>
                        </el-form-item>
                        <el-form-item label="学号">
                            <el-input placeholder="请输入学号" v-model="singleStudentInfos.number"></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button @click="queryConditionDialogShow = false">取消</el-button>
                            <el-button type="primary" @click="singleStudentInfosHandle" icon="el-icon-search" >查询</el-button>
                        </el-form-item>
                    </el-form>
                </div>
            </el-tab-pane>
            <el-tab-pane label="综合查询" name="second">
                <div style="width: 60%; margin: auto; padding: 20px;">
                    <el-form :model="queryInfo" >
                        <el-form-item label="选择班级：">
                            <el-input-number v-model="queryInfo.classNum" :min="0" :max="20" label="选择班级"></el-input-number>
                        </el-form-item>
                        <el-form-item label="选择科目：">
                            <el-select v-model="queryInfo.subject" placeholder="请选择科目">
                                <el-option v-for="item in subjectOption" :key="item.value" :label="item.label" :value="item.value"></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="最低分数线：">
                            <el-input placeholder="请输入最低分数线" v-model="queryInfo.minimumScore" style="width: 210px"></el-input>
                        </el-form-item>
                        <el-form-item label="最高分数线：">
                            <el-input placeholder="请输入高分数线" v-model="queryInfo.maximumScore" style="width: 210px"></el-input>
                        </el-form-item>
                        <span style="color: red">
                            注：若不做选择，则查找时不做限制
                        </span>
                        <el-form-item style="margin-top: 20px;margin-left: 60px">
                            <el-button @click="queryConditionDialogShow = false">取消</el-button>
                            <el-button type="primary" @click="getStudentPerformanceList" icon="el-icon-search" >查询</el-button>
                        </el-form-item>
                    </el-form>
                </div>
            </el-tab-pane>
        </el-tabs>
    </el-dialog>
    <el-dialog title="学生成绩信息" v-model="descriptionsDialogShow" width="30%" :close-on-click-modal="false">
        <el-descriptions class="margin-top" :column="2" border>
            <el-descriptions-item>
                <template #label>姓名</template>{{studentPerformance.name}}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>班级</template>{{studentPerformance.classNum}}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>学号</template>{{studentPerformance.number}}
            </el-descriptions-item>
        </el-descriptions>
        <el-form :model="studentPerformance" style="margin-top: 20px">
            <div class="for-item-area" style="width: 100%;height: 350px">
                <el-form-item label="计算机网络"  label-width="130px" style="margin-left:40px;float:left;">
                    <el-input :placeholder="studentPerformance.noc+''" v-model="studentPerformance.noc" style="width: 70px"></el-input>
                </el-form-item>
                <el-form-item label="操作系统"  label-width="130px" style="margin-left:40px;float:left;">
                    <el-input  :placeholder="studentPerformance.os+''" v-model="studentPerformance.os" style="width: 70px"></el-input>
                </el-form-item>
                <el-form-item label="计算机组成原理"  label-width="130px" style="margin-left:40px;float:left;">
                    <el-input  :placeholder="studentPerformance.pocc+''" v-model="studentPerformance.pocc" style="width: 70px"></el-input>
                </el-form-item>
                <el-form-item label="英语"  label-width="130px" style="margin-left:40px;float:left;">
                    <el-input  :placeholder="studentPerformance.english+''" v-model="studentPerformance.english" style="width: 70px"></el-input>
                </el-form-item>
                <el-form-item label="数学"  label-width="130px" style="margin-left:40px;float:left;">
                    <el-input  :placeholder="studentPerformance.math+''" v-model="studentPerformance.math" style="width: 70px"></el-input>
                </el-form-item>
                <el-form-item label="C++"  label-width="130px" style="margin-left:40px;float:left;">
                    <el-input  :placeholder="studentPerformance.cpp+''" v-model="studentPerformance.cpp" style="width: 70px"></el-input>
                </el-form-item>
                <el-form-item label="Golang"  label-width="130px" style="margin-left:40px;float:left;">
                    <el-input  :placeholder="studentPerformance.golang+''" v-model="studentPerformance.golang" style="width: 70px"></el-input>
                </el-form-item>
                <el-form-item label="Rust"  label-width="130px" style="margin-left:40px;float:left;">
                    <el-input  :placeholder="studentPerformance.rust+''" v-model="studentPerformance.rust" style="width: 70px"></el-input>
                </el-form-item>
                <el-form-item label="Java"  label-width="130px" style="margin-left:40px;float:left;">
                    <el-input  :placeholder="studentPerformance.java+''" v-model="studentPerformance.java" style="width: 70px"></el-input>
                </el-form-item>
            </div>
            <div style="width: 40%; margin: auto; padding: 20px;">
                <el-form-item>
                    <el-button @click="queryConditionDialogShow = false">取消</el-button>
                    <el-button type="primary" @click="changeStudentPerformanceHandle" icon="el-icon-edit" >更新</el-button>
                </el-form-item>
            </div>
        </el-form>
    </el-dialog>
    <el-dialog title="录入成绩信息" v-model="addStudentPerformanceShowDialog" width="30%" :close-on-click-modal="false">
        <el-form :model="studentPerformance" style="margin-top: 20px">
            <el-form-item label="姓名"  label-width="130px" style="margin-left:40px;" >
                <el-input v-model="studentPerformance.name" style="width: 200px"></el-input>
            </el-form-item>
            <el-form-item label="班级"  label-width="130px" style="margin-left:40px;">
                <el-input  v-model="studentPerformance.classNum" style="width: 200px"></el-input>
            </el-form-item>
            <div class="for-item-area" style="width: 100%;height: 350px">
                <el-form-item label="计算机网络"  label-width="130px" style="margin-left:40px;float:left;">
                    <el-input v-model="studentPerformance.noc" style="width: 70px"></el-input>
                </el-form-item>
                <el-form-item label="操作系统"  label-width="130px" style="margin-left:40px;float:left;">
                    <el-input  v-model="studentPerformance.os" style="width: 70px"></el-input>
                </el-form-item>
                <el-form-item label="计算机组成原理"  label-width="130px" style="margin-left:40px;float:left;">
                    <el-input   v-model="studentPerformance.pocc" style="width: 70px"></el-input>
                </el-form-item>
                <el-form-item label="C++"  label-width="130px" style="margin-left:40px;float:left;">
                    <el-input  v-model="studentPerformance.cpp" style="width: 70px"></el-input>
                </el-form-item>
                <el-form-item label="英语"  label-width="130px" style="margin-left:40px;float:left;">
                    <el-input  v-model="studentPerformance.english" style="width: 70px"></el-input>
                </el-form-item>
                <el-form-item label="数学"  label-width="130px" style="margin-left:40px;float:left;">
                    <el-input  v-model="studentPerformance.math" style="width: 70px"></el-input>
                </el-form-item>
                <el-form-item label="Golang"  label-width="130px" style="margin-left:40px;float:left;">
                    <el-input  v-model="studentPerformance.golang" style="width: 70px"></el-input>
                </el-form-item>
                <el-form-item label="Rust"  label-width="130px" style="margin-left:40px;float:left;">
                    <el-input  v-model="studentPerformance.rust" style="width: 70px"></el-input>
                </el-form-item>
                <el-form-item label="Java"  label-width="130px" style="margin-left:40px;float:left;">
                    <el-input  v-model="studentPerformance.java" style="width: 70px"></el-input>
                </el-form-item>
            </div>
            <div style="width: 40%; margin: auto; padding: 20px;">
                <el-form-item>
                    <el-button @click="addStudentPerformanceShowDialog = false">取消</el-button>
                    <el-button type="primary" @click="addStudentPerformanceDialog" icon="el-icon-edit" >录入</el-button>
                </el-form-item>
            </div>
        </el-form>
    </el-dialog>
</template>

<script>
import axios from "axios";

export default {
    name: "StudentPerformance",
    data(){
        return {
            total : 0,
            ifShowPagination : true,
            inputTip : "请输入学号或姓名",
            stuPerformanceList : [],
            queryConditionDialogShow : false,
            activeName : 'first',
            queryInfo : {
                classNum:0,
                subject:'',
                minimumScore:'',
                maximumScore:'',
                pageNum: 1,
                pageSize: 20,
            },
            singleStudentInfos:{
                name:'',//姓名
                number:'',//学号
            },
            subjectOption: [
                { value: 'noc', label: '计算机网络'},
                { value: 'os', label: '操作系统'},
                { value: 'pocc', label: '计算机组成原理'},
                { value: 'english', label: '英语'},
                { value: 'math', label: '数学'},
                { value: 'cpp', label: 'C++'},
                { value: 'golang', label: 'Golang'},
                { value: 'rust', label: 'Rust'},
                { value: 'java', label: 'Java'}
            ],
            subjectOptionMap: {
                'noc': '计算机网络',
                'os': '操作系统',
                'pocc': '计算机组成原理',
                'english': '英语',
                'math': '数学',
                'cpp': 'C++',
                'golang': 'Golang',
                'rust': 'Rust',
                'java': 'Java',
                'classNum':"班级"
            },
            ifChangeable:false,
            queryStatus:'无',
            descriptionsDialogShow:false,
            studentPerformance:{},
            addStudentPerformanceShowDialog: false
        }
    },
    created() {
        this.getStudentPerformanceList()
    },
    methods:{
        async getStudentPerformanceList(){
            const {data:res} = await axios.get("stu-performance/get",{params:this.queryInfo})
            if(res.meta.status !== 200){
                return this.$message.error(res.meta.msg)
            }
            this.stuPerformanceList = res.data.infoList
            this.total = res.data.numOfTotalRecord
            // 0 表示不查询班级信息，若大于0则表示查询班级信息，则不进行分页
            this.ifShowPagination = this.queryInfo.classNum === 0
            // 是否显示修改个人信息的按钮
            this.ifChangeable = false
            let status = ''
            if(this.queryInfo.classNum > 0) status +='【班级】：'+this.queryInfo.classNum+'班 '
            if(this.queryInfo.subject !== '') status +=' 【科目】：'+this.subjectOptionMap[this.queryInfo.subject]
            if(this.queryInfo.minimumScore !== '') status +=' 【分数下限】：'+this.queryInfo.minimumScore+'分 '
            if(this.queryInfo.maximumScore !== '') status +=' 【分数上限】：'+this.queryInfo.maximumScore+'分 '
            this.queryStatus = (status === '')? '无' : status
            this.queryConditionDialogShow = false
        },
        getStudentPerformanceDialog(){
            this.singleStudentInfos.number = ''
            this.singleStudentInfos.name = ''
            this.queryConditionDialogShow = true
        },
        addStudentPerformanceDialogShow(){
            this.addStudentPerformanceShowDialog = true
            this.studentPerformance = {}
        },
        async addStudentPerformanceDialog(){
            if(this.studentPerformance.name === ''){
                return this.$message.error('姓名不能为空')
            }
            // 进行校验
            this.studentPerformanceObjectVerify()
            for (let key in this.studentPerformance) {
                if(this.studentPerformance[key] === null){
                    return this.$message.error(this.subjectOptionMap[key]+'输入错误')
                }
            }
            this.$message.info("成绩录入中....！")
            const {data:res} = await axios.post("stu-performance/add",this.studentPerformance)
            if(res.meta.status !== 200 ) return this.$message.error(res.meta.msg)
            this.addStudentPerformanceShowDialog = false
            this.$message.success("成绩录入成功！")
            this.freshCondition()
        },
        studentPerformanceObjectVerify(){
            this.studentPerformance.classNum = this.toNumber(this.studentPerformance.classNum,20)
            this.studentPerformance.noc = this.toNumber(this.studentPerformance.noc,150)
            this.studentPerformance.os = this.toNumber(this.studentPerformance.os,150)
            this.studentPerformance.pocc = this.toNumber(this.studentPerformance.pocc,150)
            this.studentPerformance.cpp = this.toNumber(this.studentPerformance.cpp,100)
            this.studentPerformance.english = this.toNumber(this.studentPerformance.english,100)
            this.studentPerformance.golang = this.toNumber(this.studentPerformance.golang,100)
            this.studentPerformance.java = this.toNumber(this.studentPerformance.java,100)
            this.studentPerformance.rust = this.toNumber(this.studentPerformance.rust,100)
            this.studentPerformance.math = this.toNumber(this.studentPerformance.math,100)
        },
        toNumber(val,max){
            if(val === '' || val === null) return null
            let val1 = Number(val)
            if(isNaN(val1) || val > max || val < 0) return null
            else return val1
        },
        handleSizeChange(newSize) {
            this.queryInfo.pageSize = newSize
            this.getStudentPerformanceList()
        },
        handleCurrentChange(newSize) {
            this.queryInfo.pageNum = newSize
            this.getStudentPerformanceList()
        },
        freshCondition(){
            // 清空条件
            this.queryConditionDialogClose()
            // 重新查一遍
            this.getStudentPerformanceList()
        },
        async singleStudentInfosHandle(){

            if(this.singleStudentInfos.number === '') return this.$message.error("学号输入有误")
            this.singleStudentInfos.number = Number(this.singleStudentInfos.number)
            if(isNaN(this.singleStudentInfos.number) || this.singleStudentInfos.number > 161432099
                || this.singleStudentInfos.number < 161430100)
                return this.$message.error("学号输入有误")
            const {data:res} = await axios.get('stu-performance/get-student',{params:this.singleStudentInfos})
            if(res.meta.status !== 200){
                return this.$message.error(res.meta.msg)
            }
            this.stuPerformanceList = res.data.infoList
            this.total = res.data.numOfTotalRecord
            this.ifShowPagination = false
            //关闭对话框
            this.queryConditionDialogShow = false
            this.ifChangeable = true
            this.queryStatus = '【姓名】：'+this.singleStudentInfos.name+'【学号】：'+this.singleStudentInfos.number
        },
        queryConditionDialogClose(){
            this.queryInfo = {
                classNum:0,
                subject:'',
                minimumScore:'',
                maximumScore:'',
                pageNum: 1,
                pageSize: 20,
            }
            this.singleStudentInfos = {}
        },
        changeStudentPerformanceDialogShow(){
            this.descriptionsDialogShow = true
            this.studentPerformance = this.stuPerformanceList[0]
            //console.log('',this.studentPerformance)
        },
        async changeStudentPerformanceHandle(){
            this.descriptionsDialogShow = true
            this.studentPerformance = this.stuPerformanceList[0]//浅拷贝
            this.studentPerformanceObjectVerify()
            for (let key in this.studentPerformance) {
                if(this.studentPerformance[key] === null){
                    this.studentPerformance[key] = this.stuPerformanceList[0][key]
                    return this.$message.error(this.subjectOptionMap[key]+'输入错误')
                }
            }
            //console.log('',this.studentPerformance)
            this.$message.info("更新中.....")
            const {data:res} = await axios.post('stu-performance/change' ,this.studentPerformance)
            if(res.meta.status !== 200 ){
                return this.$message.error(res.meta.msg)
            }
            this.singleStudentInfos.name = this.studentPerformance.name
            this.singleStudentInfos.number = this.studentPerformance.number
            await this.singleStudentInfosHandle()
            this.descriptionsDialogShow = false
            this.$message.success("更新完成")
            // console.log(res)
        },
    }
}
</script>

<style scoped>

</style>

<!--f2e8ade52bd29d5c2119399871e03b840b3c3c7a-->
<!--f2e8ade52bd29d5c2119399871e03b840b3c3c7a-->