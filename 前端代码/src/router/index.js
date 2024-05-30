import { createRouter, createWebHashHistory } from 'vue-router'



import StudentInfo from '../components/student/StudentInfos'
import StudentPerformance from "@/components/student/StudentPerformance";
import Home from '../views/Home'
import PerformanceAnalyse from "@/components/performance/PerformanceAnalyse";
import RoleManagement from "@/components/management/RoleManagement";
import UserManagement from "@/components/management/UserManagement";
import CourseInfo from "@/components/course/CourseInfo";
import TeacherCourse from "@/components/teacher/TeacherCourse";
import TeacherInfos from "@/components/teacher/TeacherInfos";
import StudentSchool from "@/components/stuschool/StudentSchool";
import HelloWorld from "@/components/HelloWorld";
import Login from "../views/Login"

// 项目发布时替换为，路由懒加载
// const Login = () => import(/* webpackChunkName: "Login_Home_Welcome" */ '../components/Login.vue')



const routes = [
  { path: '/', redirect: '/login' },
  { path: '/login', component: Login },
  { path: '/home',
    name:"Home",
    component: Home,
    redirect: '/HelloWorld',
    children: [

      { path: '/home',name:"my_home", component: HelloWorld },
      { path: '/student_info', component: StudentInfo },
      { path: '/student_performance', component: StudentPerformance },
      { path: '/performance_analyse', component: PerformanceAnalyse },
      { path: '/role_management', component: RoleManagement },
      { path: '/user_management', component: UserManagement },
      { path: '/course_info', component: CourseInfo },
      { path: '/teacher_course', component: TeacherCourse },
      { path: '/teacher_infos', component: TeacherInfos },
      { path: '/student_school', component: StudentSchool }
    ] }
]



const router = createRouter({
  history: createWebHashHistory(),
  routes
})

// 挂载路由导航守卫,to表示将要访问的路径，from表示从哪里来，next是下一个要做的操作 next('/login')强制跳转login
router.beforeEach((to, from, next) => {
  // 访问登录页，放行
  if (to.path === '/login') return next()
  // 获取token
  const tokenStr = window.sessionStorage.getItem('token')
  // 没有token, 强制跳转到登录页
  if (!tokenStr) return next('/login')
  next()
})

export default router
