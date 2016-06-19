package com.tt.tradein.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.tt.tradein.domain.service.WeatherApiService;
import com.tt.tradein.mvp.models.Goods;
import com.tt.tradein.mvp.models.User;
import com.tt.tradein.mvp.views.HomeView;
import com.tt.tradein.mvp.views.PublishView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by tuozhaobing on 16-5-12.
 */
public class PublishActivityPresenterImpl implements PublishActivityPresenter {
    private PublishView homeView;
    public PublishActivityPresenterImpl(PublishView homeView) {
        this.homeView = homeView;
    }

    @Override
    public boolean publishGoods(Context context,String title, String description, List<String> images,String kind,String secondkind, String price, String newDegree, String location,String prince) {
        Goods goods = new Goods();
        User user = BmobUser.getCurrentUser(context,User.class);
        goods.setUserid(user.getObjectId());
        goods.setTitle(title);
        goods.setDescription(description);
        goods.setImages(images);
        goods.setPrice(price);
        goods.setKind(kind);
        goods.setSecondkind(secondkind);
        goods.setNew_degree(newDegree);
        goods.setLocation(location);
        goods.setPrince(prince);
        goods.save(context, new SaveListener() {

            @Override
            public void onSuccess() {
                homeView.publishSuccess();
            }

            @Override
            public void onFailure(int code, String arg0) {
                homeView.publishError(arg0);
            }
        });
        return false;
    }

    @Override
    public boolean uploadPicture(final Context context, final String[] images) {
        BmobFile.uploadBatch(context, images, new UploadBatchListener() {
            @Override
            public void onSuccess(List<BmobFile> files,List<String> urls) {
                //1、files-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作，例如你可以将该文件保存到表中
                //2、urls-上传文件的完整url地址
                if(urls.size()==images.length){//如果数量相等，则代表文件全部上传完成
                    homeView.uploadSuccess(urls);
                }
            }

            @Override
            public void onError(int statuscode, String errormsg) {

            }

            @Override
            public void onProgress(int curIndex, int curPercent, int total,int totalPercent) {
                //1、curIndex--表示当前第几个文件正在上传
                //2、curPercent--表示当前上传文件的进度值（百分比）
                //3、total--表示总的上传文件数
                //4、totalPercent--表示总的上传进度（百分比）
                Log.e("TAG",curIndex+"    "+curPercent);
            }
        });
        return true;
    }
}
