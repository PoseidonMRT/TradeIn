package com.tt.tradein.mvp.models;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by tuozhaobing on 16-5-12.
 */
public class Goods extends BmobObject {
    private String title;
    private String description;
    private String new_degree;
    private String price;
    private String location;
    private String mount;
    private String userid;
    private String prince;
    private String kind;
    private String secondkind;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getSecondkind() {
        return secondkind;
    }

    public void setSecondkind(String secondkind) {
        this.secondkind = secondkind;
    }

    public String getPrince() {
        return prince;
    }

    public void setPrince(String prince) {
        this.prince = prince;
    }

    private List<String> images;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNew_degree() {
        return new_degree;
    }

    public void setNew_degree(String new_degree) {
        this.new_degree = new_degree;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMount() {
        return mount;
    }

    public void setMount(String mount) {
        this.mount = mount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
