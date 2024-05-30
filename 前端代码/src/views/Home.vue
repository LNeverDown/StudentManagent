<template>


    <el-container>
        <el-header style="height: 80px">
            <div class="head-area">
                <img src="../assets/weixin.jpg" style="height: 50px;width: 50px">
                <span class="head-txt">学生学籍管理系统</span>
            </div>
            <div class="toolbar">
                <el-menu class="el-menu-demo" background-color="#373f41" text-color="#14889A" active-text-color="#14889A" mode="horizontal" style="height: 80px;">
                    <el-menu-item index="1" style="padding-top:10px;height: 80px;">
                        <!-- 用户信息 -->
                        <span class="user-info"><img src="@/assets/ai.jpg" />账号</span>
						<span class="user-info" @click="logout" >退出</span>
                    </el-menu-item>
                </el-menu>
            </div>
        </el-header>
        <el-container>
            <el-aside :width="isCollapse ? '64px' : '200px'">
                <div class="toggle-button" @click="togleCollapse" :v-model="lr">{{lr}}</div>
                <el-menu :uniqueOpened="true" default-active="2" :collapse="isCollapse" :collapse-transition="false" router
                    background-color="#333744" text-color="#fff" active-text-color="#409FFF">
                    <el-submenu :index="subItem.id+''" v-for="subItem in menuList" :key="subItem.id">
                        <template #title>
                            <i :class="iconMap[subItem.id]"></i>
                            <span>{{subItem.auth_name}}</span>
                        </template>
                        <el-menu-item :index="menuItem.path" v-for="menuItem in subItem.children" :key="menuItem.id" @click="saveNavState( menuItem.path)">
                            <i class="el-icon-menu"></i>
                            <span>{{menuItem.auth_name}}</span>
                        </el-menu-item>
                    </el-submenu>
                </el-menu>
				
            </el-aside>
            <el-main>
                <router-view></router-view>
            </el-main>
        </el-container>
    </el-container>
</template>

<script>
// @ is an alias to /src
import axios from "axios";

export default {
    name: 'Home',
    data(){
        return {
            menuList: [],
            // 默认不折叠
            isCollapse: false,
            lr: '<',
            iconMap:{
                0:"el-icon-user-solid",
                1:"el-icon-s-custom",
                2:"el-icon-notebook-1",
                3:"el-icon-s-data",
                24:"el-icon-s-check",
            }
        }
    },
    created() {
        this.getMenuLists()
    },
    methods:{
		logout: function () {
		  // this.$store.commit('adminLogout')
		  this.$router.push('/login')
		
		  localStorage.removeItem('trace-last-time')
		},
        async getMenuLists(){
            const {data:res} = await axios.get("stu-permission/menus")
            if(res.meta.status !== 200){
                return this.$message.error(res.meta.msg)
            }else{
                this.$message.success("获取菜单信息成功！")
            }
            this.menuList = res.data
        },
        // 菜单的折叠与展开
        togleCollapse() {
            if (this.isCollapse) {
                this.lr = '<'
            } else {
                this.lr = '>'
            }
            this.isCollapse = !this.isCollapse
        },
        // 保存连接的激活地址
        saveNavState(activePath) {
            window.sessionStorage.setItem('activePath', activePath)
        }
    }
}
</script>

<style scoped lang="less">
    .el-container {
        height: 100%;
        max-height: 100%;
        overflow-y: hidden;
    }
    .el-header {
        background-color: #373f41;
        display: flex;
        justify-content: space-between;
        padding-left: 0;
        align-items: center;
        color: #fff;
        font-size: 20px;
    }
    .el-aside {
        background-color: #333744;
        .el-menu {
        	border: none;
        }
    }
    .el-main {
        background-color: #eaedf1;
    }

    .head-area{
        display: flex;
        padding-left: 10px;
    }
    .head-txt{
        font-size: 24px;
        padding-left: 15px;
        margin-top: 10px;
        color: #FFFFFF;
    }
    .toggle-button {
        background-color: #4A5064;
        font-size: 20px;
        line-height: 24px;
        color: #bdbdbd;
        text-align: center;
        letter-spacing: 0.2em;
        cursor: pointer;
    }
    .toolbar {
        float: right;
    }
    .user-info {
        font-size: 20px;
        color: #fff;
        cursor: pointer;
        img {
            width: 40px;
            height: 40px;
            border-radius: 10px;
            margin: 10px 0px 10px 10px;
            float: right;
        }
    }
</style>
