package com.tt.tradein.helper;

import android.content.Context;

import com.tt.tradein.mvp.models.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by TuoZhaoBing on 2016/5/16 0016.
 */
public class UserHelper {
    public static final String TAG = "UserHelper";
    public User getCurrentUser(Context context){
        return BmobUser.getCurrentUser(context,User.class);
    }
}
