package com.tt.tradein.mvp.models;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by TuoZhaoBing on 2016/5/17 0017.
 */
public class SecondKind extends BmobObject {
    public static final String TAG = "SecondKind";
    private String kind;
    private String parentid;
    private BmobFile image;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }
}
