package com.management.system.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 常用字段名
 * */
public class ConstName {

    public static final String NAME_COLUMN = "name";

    public static final String CLASS_COLUMN = "class_num";

    public static final String NUMBER_COLUMN = "number";

    public static final String TOTAL_COLUMN = "total";

    public static final Double PASS_SCORE = 0.6;

    public static final Double EXCELLENT_SCORE = 0.85;

    public static final String AGE_COLUMN = "age";

    public static final String IF_VIOLATED_COLUMN = "if_violated";

    public static final String ID_COLUMN = "id";

    private static Map<String,Integer> subjectFullMark = null;

    public static int getSubjectFullMark(String subject){
        if(subjectFullMark == null){
            subjectFullMark = new HashMap<>();
            subjectFullMark.put("noc",150);
            subjectFullMark.put("os",150);
            subjectFullMark.put("pocc",150);
            subjectFullMark.put("cpp",100);
            subjectFullMark.put("rust",100);
            subjectFullMark.put("golang",100);
            subjectFullMark.put("java",100);
            subjectFullMark.put("english",100);
            subjectFullMark.put("math",100);
            subjectFullMark.put("total",1050);
        }

        return subjectFullMark.get(subject);
    }

    public static int TEACHER_ID_START = 1725468;
    public static int COURSE_ID_START = 0;
    public static int getOnlyTeacherId(){
        return TEACHER_ID_START;
    }

    public static int getOnlyCourseId(){
        return COURSE_ID_START;
    }


}
