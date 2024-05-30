package com.management.system.vo;


import lombok.Data;

import java.util.List;

@Data
public class QueryListResponse<T> {

    /**
     * 信息列表
     */
    private final List<T> infoList;

    /**
     * 总记录数量
     */
    private final Integer numOfTotalRecord;

    public QueryListResponse(List<T> infoList, Integer numOfTotalRecord) {
        this.infoList = infoList;
        this.numOfTotalRecord = numOfTotalRecord;
    }

    public List<T> getInfoList() {
        return infoList;
    }

    public Integer getNumOfTotalRecord() {
        return numOfTotalRecord;
    }
}
