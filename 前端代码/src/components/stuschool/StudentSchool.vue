<template>
  <el-breadcrumb separator-class="el-icon-arrow-right">
    <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
    <el-breadcrumb-item>学籍管理</el-breadcrumb-item>
    <el-breadcrumb-item>学生信息管理</el-breadcrumb-item>
  </el-breadcrumb>

  <el-card style="width: 100%;height: auto">
    <el-button type="primary" @click="addStudentDialogVisible = true">添加学生信息</el-button>
    <el-table :data="studentList" border stripe>
      <el-table-column type="index"></el-table-column>
      <el-table-column label="姓名" align="center" prop="name"></el-table-column>
      <el-table-column label="学号" align="center" prop="studentId"></el-table-column>
      <el-table-column label="性别" align="center" prop="gender"></el-table-column>
      <el-table-column label="年龄" align="center" prop="age"></el-table-column>
      <el-table-column label="班级" align="center" prop="class"></el-table-column>
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button size="mini" type="primary" @click="editStudentInfoShowDialog(scope.$index, scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="delStudentInfoShowDialog(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="queryInfo.pageNum"
                   :page-sizes="[15, 20, 30, 40]" :page-size="queryInfo.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
    </el-pagination>
  </el-card>

  <el-dialog title="添加学生信息" v-model="addStudentDialogVisible" width="30%">
    <el-form :model="addStudent">
      <el-form-item label="姓名" label-width="120px">
        <el-input v-model="addStudent.name"></el-input>
      </el-form-item>
      <el-form-item label="学号" label-width="120px">
        <el-input v-model="addStudent.studentId"></el-input>
      </el-form-item>
      <el-form-item label="性别" label-width="120px">
        <el-input v-model="addStudent.gender"></el-input>
      </el-form-item>
      <el-form-item label="年龄" label-width="120px">
        <el-input v-model="addStudent.age"></el-input>
      </el-form-item>
      <el-form-item label="班级" label-width="120px">
        <el-input v-model="addStudent.class"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="addStudentDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="addStudentInfoHandel">确定</el-button>
      </span>
    </template>
  </el-dialog>

  <!-- 编辑学生信息对话框 -->
  <!-- 删除学生信息对话框 -->
</template>

<script>
import axios from "axios";

export default {
  name: "StudentSchool",
  data() {
    return {
      queryInfo: {
        pageNum: 1,
        pageSize: 20
      },
      studentList: [],
      total: 0,
      addStudentDialogVisible: false,
      addStudent: {
        name: '',
        studentId: '',
        gender: '',
        age: '',
        class: ''
      }
    }
  },
  created() {
    this.getStudentInfoList()
  },
  methods: {
    async getStudentInfoList() {
      const { data: res } = await axios.get("api/student-info/get", { params: this.queryInfo })
      if (res.meta.status !== 200) {
        return this.$message.error(res.meta.msg)
      }
      this.studentList = res.data.infoList
      this.total = res.data.numOfTotalRecord
    },
    // 其他方法
  }
}
</script>

<style scoped lang="less">

</style>
