<template>
    <!-- 面包屑导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>成绩管理</el-breadcrumb-item>
        <el-breadcrumb-item>成绩统计</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片视图 -->
    <el-card style="width: 100%;height: auto">
        <el-card>
            <el-button style="margin-right: 15px" icon="el-icon-refresh" @click="refreshHandle" size="small">重置</el-button>

            <el-select style="margin-right: 15px" v-model="queryValues.wayValue" placeholder="请选择统计方式">
                <el-option v-for="item in wayOptions" :key="item.value" :label="item.label" :value="item.value">
                </el-option>
            </el-select>
            <el-select style="margin-right: 15px" v-model="queryValues.subjectValue" placeholder="请选择科目">
                <el-option v-for="item in subjectOptions" :key="item.value" :label="item.label" :value="item.value">
                </el-option>
            </el-select>
            <el-select style="margin-right: 15px" v-if="queryValues.wayValue !== 'class'" v-model="queryValues.indexValue" placeholder="请选择指标">
                <el-option v-for="item in indexOptions" :key="item.value" :label="item.label" :value="item.value">
                </el-option>
            </el-select>
            <el-select style="margin-right: 15px" v-else v-model="queryValues.classValue" placeholder="请选择班级">
                <el-option v-for="item in classOptions" :key="item" :label="item" :value="item">
                </el-option>
            </el-select>
            <el-button type="primary" @click="queryValuesCheck">获取数据</el-button>
        </el-card>
        <el-card style="height: 650px;margin-top: 10px">
            <div id="main-e-chart" class="e-charts-area" v-show="queryValues.wayValue==='total'|| queryValues.wayValue===''">
                <el-empty description="暂无数据" style="margin-top: 10%"></el-empty>
            </div>
            <div style="width: 100%;" v-show="queryValues.wayValue==='class'">
                <div class="data-show" style="width: 34%; height:100%;float: left;margin-top: 20px">
                    <el-descriptions class="margin-top" :title="cardTitle" :column="2" :size="size" border>
                        <el-descriptions-item>
                            <template #label><i class="el-icon-data-line"></i>最高分</template>
                            {{classPerformanceAnalyseResultResult.maxValue}}
                        </el-descriptions-item>
                        <el-descriptions-item>
                            <template #label><i class="el-icon-data-analysis"></i>最低分</template>
                            {{classPerformanceAnalyseResultResult.minValue}}
                        </el-descriptions-item>
                        <el-descriptions-item>
                            <template #label><i class="el-icon-s-data"></i>平均分</template>
                            {{classPerformanceAnalyseResultResult.averageValue}}
                        </el-descriptions-item>
                        <el-descriptions-item>
                            <template #label><i class="el-icon-tickets"></i>及格率</template>
                            {{classPerformanceAnalyseResultResult.passRate}}%
                        </el-descriptions-item>
                        <el-descriptions-item>
                            <template #label><i class="el-icon-office-building"></i>优秀率</template>
                            {{classPerformanceAnalyseResultResult.excellentRate}}
                        </el-descriptions-item>
                        <el-descriptions-item>
                            <template #label><i class="el-icon-office-building"></i>方差</template>
                            {{classPerformanceAnalyseResultResult.variance}}
                        </el-descriptions-item>
                        <el-descriptions-item>
                            <template #label><i class="el-icon-office-building"></i>标准差</template>
                            {{classPerformanceAnalyseResultResult.standardDeviation}}
                        </el-descriptions-item>
                    </el-descriptions>
                </div>
                <div id="main-e-chart1" class="e-charts-area1">
                    <el-empty description="暂无数据" style="margin-top: 17%"></el-empty>
                </div>
            </div>
        </el-card>
    </el-card>
</template>

<script>
// 引入echarts
import * as echarts from 'echarts'
import axios from "axios";

export default {
    data(){
        return {
            myData:[],
            mainChart:null,
            mainChart1:null,
            classList:['1', '2', '3', '4', '5', '6', '7','8', '9', '10', '11', '12', '13', '14', '15', '16', '17','18', '19', '20','级部'],
            subjectOptions: [
                {value: 'total', label: '总分'},
                {value: 'pocc', label: '计算机组成原理'},
                {value: 'os', label: '操作系统'},
                {value: 'noc', label: '计算机网络'},
                {value: 'math', label: '数学'},
                {value: 'english', label: '英语'},
                {value: 'golang', label: 'Golang'},
                {value: 'java', label: 'Java'},
                {value: 'cpp', label: 'C++'},
                {value: 'rust', label: 'Rust'},
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
                'total':"总分"
            },
            wayOptions: [
                {value: 'total', label: '成绩信息总计'},
                {value: 'class', label: '班级信息统计'},
            ],
            indexOptions: [
                {value: 'a', label: '最高分'},
                {value: 'ab', label: '最低分'},
                {value: 'abc', label: '平均分'},
                {value: 'abcd', label: '及格率'},
                {value: 'abcde', label: '优秀率'},
                {value: 'abcdef', label: '方差'},
                {value: 'abcdefg', label: '标准差'},
            ],

            indexOptionsMap: {
                'a':'最高分',
                'ab':'最低分',
                'abc':'平均分',
                'abcd':'及格率',
                'abcde':'优秀率',
                'abcdef':'方差',
                'abcdefg':'标准差',

            },
            classOptions:[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20],
            queryValues:{
                classValue:'',
                subjectValue: '',
                indexValue:'',
                wayValue: ''
            },
            classPerformanceAnalyseResultResult: {},
            cardTitle:''
        }
    },
    created() {
        // this.getPerformanceDataList()
    },
    methods:{
        async getTotalPerformanceAnalyseResult(){
            const {data:res} = await axios.get('stu-performance/get-total-info',{params: this.queryValues})
            if(res.meta.status !== 200){
                return this.$message.error(res.meta.msg)
            }
            if(this.mainChart !== null) this.mainChart.clear()
            this.mainEChartShow({
                title: {
                    text: '全部班级-'+this.subjectOptionMap[this.queryValues.subjectValue] +'-' + this.indexOptionsMap[this.queryValues.indexValue] ,
                    // subtext: '分析结果'，
                    left: 'center'
                },
                xAxis: {
                    type: 'category',
                    data: this.classList
                },
                yAxis: {
                    type: 'value'
                },
                series: [{
                    data: res.data,
                    type: 'bar',
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,  //开启显示
                                position: 'top',  //在上方显示
                                textStyle: {  //数值样式
                                    color: 'black',
                                    fontSize: 12
                                }
                            }
                        }
                    },
                }]
            })

        },
        async getClassPerformanceAnalyseResult(){
            const {data:res} = await axios.get('stu-performance/get-total-info',{params: this.queryValues})
            if(res.meta.status !== 200){
                return this.$message.error(res.meta.msg)
            }
            if(this.mainChart1 !== null) this.mainChart1.clear()
            this.classPerformanceAnalyseResultResult = res.data
            let tmpStr = this.classPerformanceAnalyseResultResult.classNum+'班-'+this.subjectOptionMap[this.queryValues.subjectValue]
            this.cardTitle = tmpStr+'科目成绩详细数据'
            this.mainEChartShow1({
                title: {
                    text: tmpStr + '-成绩分段' ,
                    subtext: '图中不显示的数据即为0',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                },
                series: [
                    {
                        name: '不同分数段占比',
                        type: 'pie',
                        radius: '50%',
                        data: [
                            {value: res.data.scoreSegmentation[0] === 0 ? null : res.data.scoreSegmentation[0], name: '0% ~ 10%'},
                            {value: res.data.scoreSegmentation[1] === 0 ? null : res.data.scoreSegmentation[1], name: '11% ~ 20%'},
                            {value: res.data.scoreSegmentation[2] === 0 ? null : res.data.scoreSegmentation[2], name: '21% ~ 30%'},
                            {value: res.data.scoreSegmentation[3] === 0 ? null : res.data.scoreSegmentation[3], name: '31% ~ 40%'},
                            {value: res.data.scoreSegmentation[4] === 0 ? null : res.data.scoreSegmentation[4], name: '41% ~ 50%'},
                            {value: res.data.scoreSegmentation[5] === 0 ? null : res.data.scoreSegmentation[5], name: '51% ~ 60%'},
                            {value: res.data.scoreSegmentation[6] === 0 ? null : res.data.scoreSegmentation[6], name: '61% ~ 70%'},
                            {value: res.data.scoreSegmentation[7] === 0 ? null : res.data.scoreSegmentation[7], name: '71% ~ 80%'},
                            {value: res.data.scoreSegmentation[8] === 0 ? null : res.data.scoreSegmentation[8], name: '81% ~ 90%'},
                            {value: res.data.scoreSegmentation[9] === 0 ? null : res.data.scoreSegmentation[9], name: '91% ~ 100%'},
                            {value: res.data.scoreSegmentation[10] === 0 ? null : res.data.scoreSegmentation[10], name: '满分'},
                        ],
                        emphasis: {
                            itemStyle: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            })
        },
        mainEChartShow(options){
            this.mainChart = echarts.init(document.getElementById("main-e-chart"));
            // 绘制图表
            this.mainChart.setOption(options);
            window.onresize = function () { // 自适应大小
                this.mainChart1.resize();
            };
        },
        mainEChartShow1(options){
            this.mainChart1 = echarts.init(document.getElementById("main-e-chart1"));
            // 绘制图表
            this.mainChart1.setOption(options);
            window.onresize = function () { // 自适应大小
                this.mainChart1.resize();
            };
        },
        refreshHandle(){
            this.queryValues.classValue = ''  // 目标班级
            this.queryValues.indexValue = '' // 计算指标的值
            this.queryValues.wayValue = ''  // 计算方式
            this.queryValues.subjectValue = '' // 计算科目
        },
        async queryValuesCheck(){
            if(this.queryValues.wayValue === '' ) return this.$message.error('请选择基本方式')
            if(this.queryValues.subjectValue === '' ) return this.$message.error('请选择科目')
            // 分化请求
            if(this.queryValues.wayValue === 'total') {
                if(this.indexValue === '' ) return this.$message.error('请选择指标')
                await this.getTotalPerformanceAnalyseResult()
            }
            if(this.queryValues.wayValue === 'class') {
                if(this.queryValues.classValue === '') return this.$message.error('请选择班级')
                await this.getClassPerformanceAnalyseResult()
            }
            const {data:res} = await axios.get('stu-performance/get-total-info',{params: this.queryValues})
        },
    }
}
</script>

<style scoped lang="less">
    .e-charts-area{
        width: 100%;
        height: 600px;
        //border-style: solid;
        margin-top: 20px;
        margin-bottom: 20px;
        //box-shadow: 10px 10px 5px #888888;

    }
    .e-charts-area1{
        width: 65%;
        height: 600px;
        //border-style: solid;
        margin-top: 20px;
        margin-bottom: 20px;
        float: right;
        //box-shadow: 10px 10px 5px #888888;

    }
</style>
