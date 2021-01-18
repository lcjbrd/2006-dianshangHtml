package com.fh.model.vo;

public class AttValueVo {

    private Integer currPage;
    private Integer size;

    private String name;
    private Integer id;
    private Integer attid;


    public Integer getCurrPage() {
        return currPage;
    }

    public void setCurrPage(Integer currPage) {
        this.currPage = currPage;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAttid() {
        return attid;
    }

    public void setAttid(Integer attid) {
        this.attid = attid;
    }
}
