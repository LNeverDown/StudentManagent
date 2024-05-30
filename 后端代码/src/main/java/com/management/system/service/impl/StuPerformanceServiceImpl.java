package com.management.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.management.system.entity.StuPerformance;
import com.management.system.mapper.StuPerformanceMapper;
import com.management.system.service.IStuPerformanceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.management.system.utils.ConstName;
import com.management.system.utils.DataCalculationUtils;
import com.management.system.vo.AnalysisParameter;
import com.management.system.vo.ClassPerformanceAnalyseResult;
import com.management.system.vo.QueryListResponse;
import com.management.system.vo.QueryStuPerformanceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
public class StuPerformanceServiceImpl extends ServiceImpl<StuPerformanceMapper, StuPerformance> implements IStuPerformanceService {

    @Autowired
    private StuPerformanceMapper stuPerformanceMapper;


    // 默认时： QueryStuPerformanceRequest{pageNum=1, pageSize=20, minimumScore=null, subject='', classNum=0, maximumScore=null}
    @Override
    public QueryListResponse<StuPerformance> getStuPerformanceList(QueryStuPerformanceRequest queryStuPerformanceRequest){
        // 基础逻辑：首先构造条件，其次判断是否需要分页
        String subject = queryStuPerformanceRequest.getSubject();
        Integer minimumScore = queryStuPerformanceRequest.getMinimumScore();
        Integer maximumScore = queryStuPerformanceRequest.getMaximumScore();
        QueryWrapper<StuPerformance> queryWrapper = new QueryWrapper<>();
        // 如果为空字符串，则默认为total字段
        if(subject.equals("")) subject = ConstName.TOTAL_COLUMN;
        // 按照所选科目排列
        queryWrapper.orderByDesc(subject);
        // 大于等于最低分
        if(minimumScore != null) queryWrapper.ge(subject,minimumScore);
        // 小于等于最高分
        if(maximumScore != null) queryWrapper.le(subject,maximumScore);
        Integer classNum = queryStuPerformanceRequest.getClassNum();
        // 判断需不需要按班级进行查询
        if(classNum == 0){
            // 不按班级查询，构造分页器，返回结果
            IPage<StuPerformance> stuPerformanceIPage = new Page<>(
                    queryStuPerformanceRequest.getPageNum(), queryStuPerformanceRequest.getPageSize()
            );//参数一是当前页，参数二是每页个数
            stuPerformanceIPage = stuPerformanceMapper.selectPage(stuPerformanceIPage, queryWrapper);
            return new QueryListResponse<>(
                    stuPerformanceIPage.getRecords(),stuPerformanceMapper.selectCount(queryWrapper)
            );

        }else{
            queryWrapper.eq(ConstName.CLASS_COLUMN,classNum);
            //按照班级查询，直接查询，并返回
            return new QueryListResponse<>(
                    stuPerformanceMapper.selectList(queryWrapper),stuPerformanceMapper.selectCount(queryWrapper)
            );
        }
    }

    @Override
    public QueryListResponse<StuPerformance> getSingleStudentPerformance(String name,Integer number){
        QueryWrapper<StuPerformance> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ConstName.NAME_COLUMN,name);
        queryWrapper.eq(ConstName.NUMBER_COLUMN,number);
        List<StuPerformance> res = stuPerformanceMapper.selectList(queryWrapper);
        return new QueryListResponse<>(res,res.size());
    }

    @Override
    public Boolean changeStudentPerformance(StuPerformance stuPerformance){
        // 首先基础逻辑,把所有列表全部按照学号查询出来，找到那个人的位置
        // 更新数据，再更新到数据库
        List<StuPerformance> res = stuPerformanceMapper.selectList(
                new QueryWrapper<StuPerformance>().orderByAsc(ConstName.NUMBER_COLUMN));
        stuPerformance.updateTotal();
        res.set(binarySearchBuNumber(res,stuPerformance.getNumber()),stuPerformance);
        Collections.sort(res);
        int ranking = 1;
        for(StuPerformance s: res) {
            s.setRanking(ranking++);
        }
        this.updateBatchById(res);
        return true;
    }

    public Boolean addSingleStudentPerformance(StuPerformance stuPerformance){
        Integer classNum = stuPerformance.getClassNum();
        Integer indexInClass = stuPerformanceMapper.selectCount(new QueryWrapper<StuPerformance>().eq(ConstName.CLASS_COLUMN,classNum));
        stuPerformance.setNumber(161430000 + (classNum*100) + indexInClass + 1);
        stuPerformance.setRanking(0);
        stuPerformance.updateTotal();
        // 插入 条目
        stuPerformanceMapper.insert(stuPerformance);
        // 查询总表,按照成绩降序
        List<StuPerformance> res = stuPerformanceMapper.selectList(new QueryWrapper<StuPerformance>().orderByDesc(ConstName.TOTAL_COLUMN));
        // 计算排名
        int ranking = 1;
        for(StuPerformance s: res) {
            s.setRanking(ranking++);
            // stuPerformanceMapper.update(s,new QueryWrapper<StuPerformance>().eq("number",s.getNumber()));
        }
        this.updateBatchById(res);
        return true;
    }

    /**
     * 二分查找
     * */
    public int binarySearchBuNumber (List<StuPerformance> nums, int target) {
        // 这个index变量看似无用，但对这种需求至关重要
        int index = -1;
        int Left = 0, right = nums.size()-1;
        while (Left <= right) {
            int mid = Left + (right-Left) / 2;
            if (nums.get(mid).getNumber() == target) {
                index = mid;
                right = mid-1;
            } else if (target >nums.get(mid).getNumber()) {
                Left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return index;
    }

    @Override
    public ClassPerformanceAnalyseResult classPerformanceAnalyse(AnalysisParameter analysisParameter){
        List<Map<String, Object>> performanceMaps = stuPerformanceMapper.selectMaps(null);
        List<Map<String,Object>> classInfos = new ArrayList<>();
        Integer classNum = analysisParameter.getClassValue();
        for (Map<String,Object> item: performanceMaps) {
            if(item.get(ConstName.CLASS_COLUMN) == classNum){
                classInfos.add(item);
            }
        }
        return new ClassPerformanceAnalyseResult(
                new DataCalculationUtils(classInfos,analysisParameter.getSubjectValue()),analysisParameter
        );
    }

    /**
     * 这个函数的主要作用时对前端提供的信息的分化，将不同的需求分化到其他计算函数中
     * */
    @Override
    public List<Double> totalPerformanceAnalyse(AnalysisParameter analysisParameter){
        // 首先查询总列表，根据前端数据进行不同类型的分化计算
        List<Map<String, Object>> performanceMaps = stuPerformanceMapper.selectMaps(null);
        DataCalculationUtils calculationUtils = new DataCalculationUtils(performanceMaps,analysisParameter.getSubjectValue());
        // 获取计算指标,计算20个班级中每一个班级的指标项 共有5中类型，按照长度来区分
        int calculationIndex = analysisParameter.getIndexValue().length();
        if(calculationIndex == 1 ) {
            return calculationUtils.getMaxValue();
        }else if(calculationIndex == 2 ){
            return calculationUtils.getMinValue();
        }else if(calculationIndex == 3 ){
            return calculationUtils.getAverageValue();
        }else if(calculationIndex == 4 ){
            return calculationUtils.getScoreRate(ConstName.PASS_SCORE);
        }else if(calculationIndex == 5 ){
            return calculationUtils.getScoreRate(ConstName.EXCELLENT_SCORE);
        }else if(calculationIndex == 6 ){
            return calculationUtils.getVariance();
        }else {
            return calculationUtils.getStandardDeviation();
        }
    }
}
