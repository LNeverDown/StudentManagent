<template>
	<div class="login_container">
		<div class="login_box">
			<!--头像区域 -->
			<div class="avatar_box">
				<img src="../assets/image1.jpg" alt="" />
			</div>

			<el-tabs v-model="activeName"  class ="loginTags">
                <el-tab-pane label="密码登录" name="first">
                    <!-- 普通密码登录表单区域 -->
                    <el-form ref="loginByPasswordRef" :model="loginForm" :rules="loginByPasswordRules" label-width="70px" class="login_form">
                        <!-- 用户名、密码区域 -->
                        <el-form-item label="用户名" prop="username">
                            <el-input v-model="loginForm.username" class="login_username" prefix-icon="el-icon-s-custom" type="username" style="width: 325px;"></el-input>
                        </el-form-item>
                        <el-form-item label="密码" prop="password">
                            <el-form-item class="login_username" style="display: flex">
                                <el-input v-model="loginForm.password" prefix-icon="el-icon-lock" type="password" style="width: 325px;"></el-input>
                            </el-form-item>
                        </el-form-item>
                        <!-- 按钮区域-->
                        <el-form-item class="btns">
                            <el-button type="primary" @click="loginByPassword">登录</el-button>
                            <el-button type="info" @click="resetLoginFrom">重置</el-button>
                        </el-form-item>
                    </el-form>
                </el-tab-pane>
			   

			</el-tabs>
			

		</div>
	</div>
</template>

<script>
	import axios from "axios";

    export default {
		data() {
			return {
				loginForm: {
					phone: "185952506321",
					code: "",
					username:"EdmundShelby",//1575079696
					password:"123456",
				},
				//表单的验证规则对象
				loginByPhoneRules: {
					//验证手机号和验证码、用户名和密码是否合法
					phone: [
						{ required: true,message: "请输入手机号",trigger: "blur"},
						{ min: 5,max: 19,message: "长度在5-19位之间",trigger: "blur"}
					],
					code: [
						{ required: true,message: "请输入短信验证码",trigger: "blur"},
						{ min: 6,max: 16,message: "短信验证码不能为空",trigger: "blur"}
					],
				},
				loginByPasswordRules:{
					username: [
						{ required: true,message: "请输入用户名",trigger: "blur"},
						{ min: 5,max: 19,message: "用户名不能为空",trigger: "blur"}
					],
					password: [
						{ required: true,message: "请输入密码",trigger: "blur"},
						{ min: 6,max: 16,message: "长度在5-19位之间",trigger: "blur"}
					]
				},
				smsCode: {
					is_disabled: false, //控制按钮是否可以点击（false:可以点击，true:不可点击）
					content: '获取短信验证码', // 发送验证码按钮的初始显示文字
					timer: null,
					count: '',
					model: {},
				},
				activeName:'first',
			};
		},
		methods: {
			//重置表单内容
			resetLoginFrom() {
				this.$refs.loginByPasswordRef.resetFields(); //.loginFormRef.re
			},
			//通过不同方式登录 way=true使用短信验证码，way=false使用密码
			async login(validWay,user,valid){
				//提取data重命名res
                if(validWay){
                //    短信验证方式登录
                    return this.$message.success("阿里云服务欠费了，短信验证登录暂时无法使用 :（ ")
                }else{
                    const {data: res} = await axios.post("/stu-user/login",{"account":user,"password": valid,"captcha": "no captcha"})
                    if (res.meta.status !== 200) {
                        return this.$message.error(res.meta.msg);
                    }
				    this.$message.success("登陆成功,欢迎 " + res.data.account + " !");
                    //将token保存到sessionStorage中
                    window.sessionStorage.setItem("token", res.data.token);
                    //通过编程式导航实现页面跳转到主页面
                    await this.$router.push('/home');
                }
			},
			// 通过短信验证码登录
			loginBySmsCode() {
				this.$refs.loginByPhoneRef.validate(async valid => {
					if (!valid) return;
					await this.login(true, this.loginForm.phone, this.loginForm.code);
				});
			},
			// 通过密码登录
			loginByPassword(){
				this.$refs.loginByPasswordRef.validate(async valid => {
					if (!valid) return;
					await this.login(false, this.loginForm.username, this.loginForm.password);
				});
			},
			// 发送验证码
			sendSms() {
				let vm = this.smsCode
				// 校验手机号
				if (!this.loginForm.phone || this.loginForm.phone === '') {
					return this.$message.error("请输入手机号");
				}
				if (!(/^1[34578]\d{9}$/.test(this.loginForm.phone))) {
					return this.$message.error("请输入正确的手机号");
				}
				// 请求后台发送验证码至手机
                /*
                    移除请求,只保留基本效果
				    this.$http.get("sendcode?phone=" + this.loginForm.phone); //GET
				*/
				// 控制倒计时及按钮是否可以点击

				const TIME_COUNT = 60
				vm.count = TIME_COUNT
				vm.timer = window.setInterval(() => {
					if (vm.count > 0 && vm.count <= TIME_COUNT) {
						// 倒计时时不可点击
						vm.is_disabled = true
						// 计时秒数
						vm.count--
						// 更新按钮的文字内容
						vm.content = vm.count + 's后重新获取'
					} else {
						// 倒计时完，可点击
						vm.is_disabled = false
						// 更新按钮文字内容
						vm.content = '获取短信验证码'
						// 清空定时器!!!
						clearInterval(vm.timer)
						vm.timer = null
					}
				}, 1000)
                return this.$message.success("阿里云服务欠费了，短信验证登录暂时无法使用 :（ ")
			},
		}
	};
</script>

<style lang="less" scoped>
	.login_container {
		background-image: url(../assets/bg2.png);
		background-size: 100% 100%;
		height: 100%;
	}

	.login_box {
		width: 530px;
		height: 370px;
		background-color: #FFFFFF;
		border-radius: 15px;
		left: 50%;
		top: 50%;
		position: absolute;
		transform: translate(-50%, -50%);

		.avatar_box {
			padding: 10px;
			height: 130px;
			width: 130px;
			box-shadow: 0 0 10px #ddd;
			border: 1px, solid #eee;
			border-radius: 50%;
			position: absolute;
			left: 50%;
			transform: translate(-50%, -50%);
			background-color: #fff;

			img {
				width: 100%;
				height: 100%;
				border-radius: 50%;
				background-color: #eee;
			}
		}
	}
	.loginTags{
		margin-top: 80px;
		margin-left: 40px;
		width: 450px;
		text-align: center;
	}
	.login_form {
		//position: absolute;
		bottom: 0;
		width: 100%;
		padding: 0 20px;
		margin-top: 20px;
		box-sizing: border-box;
		//设置组件都在盒子里
	}

	.login_username {
		 display: flex;
	//justify-content: flex-end; */
	}

	.btns {
		display: flex;
		justify-content: flex-end;
	}
</style>
