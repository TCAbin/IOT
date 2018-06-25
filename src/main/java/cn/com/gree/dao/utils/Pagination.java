package cn.com.gree.dao.utils;

import java.util.List;

public class Pagination<T> {

    /** 当前页 */
    private int currentPage = 1;
    /** 每页记录数 */
    private int pageSize = 30;
    /** 总记录数 */
    private long totalCount = 1L;
    /** 总页数 */
    private int pageCount;
    /** 记录集合 */
    private List<T> pageRecordList;
    /** 偏移量 */
    private int offset;
    /** 上一页 */
    private int prePage;
    /** 下一页 */
    private int nextPage;
    /** 是否有上一页 */
    private boolean hasPrePage;
    /** 是否有下一页 */
    private boolean hasNextPage;

    /** 默认空参数构造函数 */
    public Pagination() {}

    public Pagination(int currentPage,int pageSize, long totalCount, List<T> pageRecordList){
        if(currentPage < 1){
            this.currentPage = 1;
        } else {
            this.currentPage = currentPage;
        }
        if(pageSize < 1){
            this.pageSize = 1;
        } else {
            this.pageSize = pageSize;
        }
        this.totalCount = totalCount;
        this.pageRecordList = pageRecordList;


        this.pageCount = (int)Math.ceil(totalCount / (long) this.pageSize);
        if(this.currentPage > this.pageCount){
            this.currentPage = this.pageCount;
        }

        this.prePage = this.currentPage - 1;
        if(this.prePage < 1){
            this.hasPrePage = false;
            this.prePage = 1;
        } else {
            this.hasPrePage = true;
        }

        this.nextPage = this.currentPage + 1;
        if(this.nextPage > this.pageCount){
            this.hasNextPage = false;
            this.nextPage = this.pageCount;
        } else {
            this.hasNextPage = true;
        }

        this.offset = (this.currentPage - 1) * pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<T> getPageRecordList() {
        return pageRecordList;
    }

    public void setPageRecordList(List<T> pageRecordList) {
        this.pageRecordList = pageRecordList;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public boolean isHasPrePage() {
        return hasPrePage;
    }

    public void setHasPrePage(boolean hasPrePage) {
        this.hasPrePage = hasPrePage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }
}
