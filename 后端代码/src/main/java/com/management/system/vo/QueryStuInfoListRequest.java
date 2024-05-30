package com.management.system.vo;


import lombok.Data;

@Data
public class QueryStuInfoListRequest {

    /**
     * 查询内容
     */
    private String query;

    /**
     * 当前页数
     */
    private Integer pageNum;

    /**
     * 每一页的个数
     */
    private Integer pageSize;

    /**
     * 查询条件(依据)
     */
    private String condition;

    /**
     * 是否模糊搜索
     */
    private Boolean ifFuzzySearch;

    @Override
    public String toString() {
        return "StuInfoListRequest{" +
                "query='" + query + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", condition='" + condition + '\'' +
                ", ifFuzzySearch=" + ifFuzzySearch +
                '}';
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Boolean getIfFuzzySearch() {
        return ifFuzzySearch;
    }

    public void setIfFuzzySearch(Boolean ifFuzzySearch) {
        this.ifFuzzySearch = ifFuzzySearch;
    }
}
