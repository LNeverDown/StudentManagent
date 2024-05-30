<template>
    <div>
        <!-- 面包屑导航区 -->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>系统管理</el-breadcrumb-item>
            <el-breadcrumb-item>系统用户管理</el-breadcrumb-item>
        </el-breadcrumb>
        <!-- 卡片视图 -->
        <el-card>
            <el-button type="primary" @click="addDialogVisible = true">添加用户</el-button>
            <el-table :data="userlist" border stripe>
                <el-table-column type="index" label="#"></el-table-column>
                <el-table-column prop="id" label="ID" align="center" header-align="center"></el-table-column>
                <el-table-column prop="name" label="姓名" align="center" header-align="center"></el-table-column>
                <el-table-column prop="phone" label="电话" align="center" header-align="center"></el-table-column>
                <el-table-column label="操作" align="center" header-align="center">
                    <template #default="scope">
                        <el-button type="primary" icon="el-icon-edit" size="mini" circle @click="showEditDialog(scope.row)"></el-button>
                        <el-button type="danger" icon="el-icon-delete" size="mini" circle @click="removeUserById(scope.row)"></el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="queryInfo.pagenum"
                           :page-sizes="[2, 5, 10, 15]" :page-size="queryInfo.pagesize" layout="total, sizes, prev, pager, next, jumper"
                           :total="total"></el-pagination>
        </el-card>
        <el-dialog title="添加用户" v-model="addDialogVisible" width="35%">
            <el-form :model="addUserForm" label-width="100px">
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="addUserForm.name"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input v-model="addUserForm.password"></el-input>
                </el-form-item>
                <el-form-item label="手机" prop="mobile">
                    <el-input v-model="addUserForm.phone"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
				<el-button @click="addDialogVisible = false">取 消</el-button>
				<el-button type="primary" @click="addUser">确 定</el-button>
			</span>
        </el-dialog>

        <el-dialog title="编辑学生信息" v-model="editDialogVisible" width="30%" center :close-on-click-modal="false">
            <el-form :model="editUserForm" label-width="80px">
                <el-form-item label="id">
                    <el-input v-model="editUserForm.id" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="姓名">
                    <el-input v-model="editUserForm.name"></el-input>
                </el-form-item>
                <el-form-item label="手机号">
                    <el-input v-model="editUserForm.phone"></el-input>
                </el-form-item>
                <el-form-item label="新密码">
                    <el-input v-model="editUserForm.password"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                  <el-button @click="editDialogVisible = false">取 消</el-button>
                  <el-button type="primary" @click="editUser">确 定</el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>

<script>

export default {
    name:"UserManagement",
    data() {
        return {
            // 获取用户列表查询参数对象
            queryInfo: {
                query: '',
                // 当前页数
                pagenum: 1,
                // 每页显示多少数据
                pagesize: 10
            },
            userlist: [],
            total: 0,
            // 添加用户对话框
            addDialogVisible: false,
            // 用户添加
            addUserForm: {
                name: '',
                password: '',
                phone: ''
            },
            // 修改用户
            editDialogVisible: false,
            editUserForm: {},
        }
    },
    created() {
        this.getUserList()
    },
    methods: {
        async getUserList() {
            const {data: res} = await this.$http.get('stu-user/user-list', {params: this.queryInfo})
            if (res.meta.status !== 200) {
                return this.$message.error('获取用户列表失败！')
            }
            console.log(res)
            this.userlist = res.data.infoList
            this.total = res.data.numOfTotalRecord
        },
        handleSizeChange(newSize) {
            this.queryInfo.pagesize = newSize
            this.getUserList()
        },
        handleCurrentChange(newSize) {
            this.queryInfo.pagenum = newSize
            this.getUserList()
        },
        async addUser() {
            console.log(this.addUserForm)
            const {data: res} = await this.$http.post('stu-user/save', this.addUserForm)
            if (res.meta.status !== 200) {
                return this.$message.error(res.meta.msg)
            }
            this.$message.success('添加成功！'+'用户Id:'+res.data.id)
            // 隐藏添加用户对话框
            this.addDialogVisible = false
            await this.getUserList()
        },
        async showEditDialog(row) {
            // this.getDatePicker = res.data.birthday
            // this.editUserForm = res.data
            this.editUserForm = row
            console.log(this.editUserForm)
            this.editDialogVisible = true
        },
        async editUser() {
            console.log(this.editUserForm)
            const {data: res} = await this.$http.post('/stu-user/edit',this.editUserForm)
            if (res.meta.status !== 200) {
                return this.$message.error('更新用户信息失败！')
            }
            // 隐藏添加用户对话框
            this.editDialogVisible = false
            this.$message.success('更新用户信息成功！')
            await this.getUserList()
        },
        async removeUserById(id) {
            const confirmResult = await this.$confirm(
                '此操作将永久删除该用户, 是否继续?',
                '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }
            ).catch(err => err)
            if (confirmResult !== 'confirm') {
                return this.$message.info('已取消删除')
            }
            const {data: res} = await this.$http.delete('/stu-user/del', {data:id})
            if (res.meta.status !== 200) {
                return this.$message.error('删除用户失败！')
            }
            this.$message.success('删除用户成功！')
            await this.getUserList()
        },

    }
}
</script>

<style lang="less" scoped>

.user-detail-data-label{
    padding-left: 30px;
    font-size: 17px;
}

</style>
