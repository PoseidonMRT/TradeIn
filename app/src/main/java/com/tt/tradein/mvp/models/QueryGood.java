package com.tt.tradein.mvp.models;

import java.util.List;

/**
 * Created by TuoZhaoBing on 2016/5/16 0016.
 */
public class QueryGood {
    public static final String TAG = "QueryGoods";
    private String title;
    private String description;
    private String new_degree;
    private String price;
    private String location;
    private String mount;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNew_degree() {
        return new_degree;
    }

    public void setNew_degree(String new_degree) {
        this.new_degree = new_degree;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private User user;
    private List<String> images;
}
