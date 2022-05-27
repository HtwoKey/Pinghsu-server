package com.htwokey.blog.entity;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class PageUtil implements Serializable
{
    private List<?> list; //每页数据
    private int page; //当前页数
    private int size; //每页记录数
    private int totalpage; //总页数
    private int total; //总记录数

    public PageUtil(List<?> list,int page, int size, int total)
    {
        this.list= list;
        this.page= page;
        this.size = size<1 ?Integer.MAX_VALUE:size;
        this.totalpage = total%size==0?total/size:(total/size+1);
        this.total = total;
    }

    public PageUtil() {

    }

    // 获取起始位置
    public int getOffset() {
        return (page-1)*size;
    }

    public List<?> getList() {
        return list;
    }
    public void setList(List<?> list) {
        this.list = list;
    }
    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public int getTotalpage() {
        return total%size==0?total/size:(total/size+1);
    }
    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
}
