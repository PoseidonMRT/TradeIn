package com.tt.tradein.mvp.models;

import java.io.File;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by TuoZhaoBing on 2016/5/18 0018.
 */
public class Kind extends BmobObject {
    public static final String TAG = "Kind";
    private String description;
    private String kind;
    private BmobFile image;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }
}
