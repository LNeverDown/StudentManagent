package com.management.system.utils;

import org.springframework.security.core.parameters.P;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

// 数值计算的工具类
public class DataCalculationUtils {

    // 以班级作为索引
    private final Double[] valueList;
    private final int[] countList;
    private int index;
    private double val;
    List<Map<String, Object>> performanceMaps;
    String subject;

    public List<Map<String, Object>> getPerformanceMaps(){
        return this.performanceMaps;
    }

    public String getSubject(){
        return this.subject;
    }

    public double keepTwoDecimals(double val){
        return Math.round(val*100)/100.0;
    }

    public DataCalculationUtils(List<Map<String, Object>> performanceMaps, String subject){
        this.valueList = new Double[21];
        this.countList = new int[21];
        this.performanceMaps = performanceMaps;
        this.subject = subject;
    }

    public List<Double> getMaxValue(){
        Arrays.fill(valueList,0.0);
        for (Map<String,Object> item: this.performanceMaps) {
            index = (Integer)item.get(ConstName.CLASS_COLUMN)-1; //获取班级信息作为索引
            val = (Double)item.get(subject);
            if(val > valueList[index]) valueList[index] = val;
            if(val > valueList[20]) valueList[20] = val;
        }
        return Arrays.asList(valueList);
    }

    public List<Double> getMinValue(){
        // 以班级作为索引
        Arrays.fill(valueList,10000.0);
        for (Map<String,Object> item: performanceMaps) {
            index = (Integer)item.get(ConstName.CLASS_COLUMN)-1;
            val = (Double)item.get(subject);
            if(val < valueList[index]) valueList[index] = val;
            if(val < valueList[20]) valueList[20] = val;
        }
        return Arrays.asList(valueList);
    }

    public List<Double> getAverageValue(){
        // 以班级作为索引
        Arrays.fill(valueList,0.0);
        Arrays.fill(countList,0);
        for (Map<String,Object> item: performanceMaps) {
            index = (Integer)item.get(ConstName.CLASS_COLUMN)-1;
            val = (Double)item.get(subject);
            valueList[index]+=val;
            countList[index]++; //以该班级出现的次数表示班级人数
            valueList[20] +=val;
        }
        valueList[20] = keepTwoDecimals(valueList[20]/this.performanceMaps.size());
        for(int i = 0;i < 20; i++) valueList[i] = keepTwoDecimals(valueList[i]/countList[i]);

        return Arrays.asList(valueList);
    }


    public List<Double> getScoreRate(double scoreStandardRate){
        // 以班级作为索引
        Arrays.fill(valueList,0.0);
        Arrays.fill(countList,0);
        int scoreStandard = (int)(ConstName.getSubjectFullMark(subject) * scoreStandardRate);
        System.out.println(scoreStandard);
        for (Map<String,Object> item: performanceMaps) {
            index = (Integer)item.get(ConstName.CLASS_COLUMN)-1;
            val = (Double)item.get(subject);
            if(val > scoreStandard) {
                valueList[index]++;
                valueList[20]++;
            }
            countList[index]++; //以该班级出现的次数表示班级人数
        }
        valueList[20] = keepTwoDecimals(100*(valueList[20]/this.performanceMaps.size()));
        for(int i = 0;i < 20; i++) valueList[i] = keepTwoDecimals(100*(valueList[i]/countList[i]));

        return Arrays.asList(valueList);
    }

    //方差s^2=[(x1-x)^2 +...(xn-x)^2]/n 或者s^2=[(x1-x)^2 +...(xn-x)^2]/(n-1)
    public List<Double> getVariance() {
        Double[] dVar = new Double[21];
        Arrays.fill(valueList,0.0);
        Arrays.fill(dVar,0.0);
        Arrays.fill(countList,0);
        for (Map<String,Object> item: performanceMaps) {
            index = (Integer)item.get(ConstName.CLASS_COLUMN)-1;
            val = (Double)item.get(subject);
            valueList[index]+=val;
            valueList[20] += val;
            countList[index]++;
        }

        valueList[20] /= this.performanceMaps.size();
        for( int i = 0 ;i < 20 ;++i) valueList[i] /= countList[i];

        for (Map<String,Object> item: performanceMaps) {
            index = (Integer)item.get(ConstName.CLASS_COLUMN)-1;
            val = (Double)item.get(subject);
            dVar[index]+=(val - valueList[index])*(val - valueList[index]);
            dVar[20]+=(val - valueList[20])*(val - valueList[20]);
        }

        dVar[20] = keepTwoDecimals(dVar[20]/this.performanceMaps.size());
        for( int i = 0 ;i < 20 ;++i)  dVar[i] =keepTwoDecimals(dVar[i]/countList[i]) ;

        return Arrays.asList(dVar);
    }

    //标准差σ=sqrt(s^2)
    public List<Double>  getStandardDeviation() {
        List<Double> res = this.getVariance();
        for(int i = 0; i < res.size() ; ++i){
            res.set(i,keepTwoDecimals(Math.sqrt(res.get(i))));
        }
        return res;
    }
}
