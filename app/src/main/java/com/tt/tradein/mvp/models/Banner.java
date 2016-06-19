package com.tt.tradein.mvp.models;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by tuozhaobing on 16-5-9.
 */
public class Banner extends BmobObject {
    private String description;
    private BmobFile file;

    public BmobFile getFile() {
        return file;
    }

    public void setFile(BmobFile file) {
        this.file = file;
    }
//
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
