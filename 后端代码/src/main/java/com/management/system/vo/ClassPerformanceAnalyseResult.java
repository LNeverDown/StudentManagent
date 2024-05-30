package com.management.system.vo;

import com.management.system.utils.ConstName;
import com.management.system.utils.DataCalculationUtils;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 成绩分析结果
 * */
@Data
public class ClassPerformanceAnalyseResult {

    private Double[] scoreSegmentation;
    private Double maxValue;
    private Double minValue;
    private Double averageValue;
    private Double passRate;
    private Double excellentRate;
    private Double variance;
    private Double standardDeviation;
    private Integer classNum;

    public ClassPerformanceAnalyseResult(DataCalculationUtils calculationUtils,AnalysisParameter analysisParameter){
        // 为了得到正确的索引，先减去1
        this.classNum = analysisParameter.getClassValue()-1;
        this.maxValue = calculationUtils.getMaxValue().get(classNum);
        this.minValue = calculationUtils.getMinValue().get(classNum);
        this.passRate = calculationUtils.getScoreRate(ConstName.PASS_SCORE).get(classNum);
        this.excellentRate = calculationUtils.getScoreRate(ConstName.EXCELLENT_SCORE).get(classNum);
        this.averageValue = calculationUtils.getAverageValue().get(classNum);
        this.variance = calculationUtils.getVariance().get(classNum);
        this.standardDeviation = calculationUtils.getStandardDeviation().get(classNum);
        // 为了得到正确的值，再加上1
        ++this.classNum;
        this.calculateScoreSegmentation(calculationUtils);
    }

    /**
     * 计算个分数段占比
     * */
    private void calculateScoreSegmentation(DataCalculationUtils calculationUtils){
        this.scoreSegmentation = new Double[11];//0 6,7,8,9,10
        Arrays.fill(scoreSegmentation, 0.0);
        //获取该科目的满分分值*0.1 作为十分之一
        String subject = calculationUtils.getSubject();
        int subjectOneTenth = ConstName.getSubjectFullMark(subject)/10;
        List<Map<String, Object>> performanceMaps = calculationUtils.getPerformanceMaps();
        for (Map<String,Object> item: performanceMaps) {
            Double score = (Double) item.get(subject);
            scoreSegmentation[(int)(score /subjectOneTenth)]++;
        }
        for(int i = 0;i < scoreSegmentation.length; ++i){
            scoreSegmentation[i] = calculationUtils.keepTwoDecimals(scoreSegmentation[i]/performanceMaps.size()); ;
        }
    }

    public Double[] getScoreSegmentation() {
        return scoreSegmentation;
    }
    public void setScoreSegmentation(Double[] scoreSegmentation) {
        this.scoreSegmentation = scoreSegmentation;
    }
    public Double getMaxValue() {
        return maxValue;
    }
    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }
    public Double getMinValue() {
        return minValue;
    }
    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }
    public Double getAverageValue() {
        return averageValue;
    }
    public void setAverageValue(Double averageValue) {
        this.averageValue = averageValue;
    }
    public Double getPassRate() {
        return passRate;
    }
    public void setPassRate(Double passRate) {
        this.passRate = passRate;
    }
    public Double getExcellentRate() {
        return excellentRate;
    }
    public void setExcellentRate(Double excellentRate) {
        this.excellentRate = excellentRate;
    }
    public Double getVariance() {
        return variance;
    }
    public void setVariance(Double variance) {
        this.variance = variance;
    }
    public Double getStandardDeviation() {
        return standardDeviation;
    }
    public void setStandardDeviation(Double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }
    public Integer getClassNum() {
        return classNum;
    }
    public void setClassNum(Integer classNum) {
        this.classNum = classNum;
    }
}
