package com.tt.tradein.mvp.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.tt.tradein.R;
import com.tt.tradein.mvp.views.PersonCenterView;
import com.tt.tradein.widget.CircleImageView;

/**
 * Created by tuozhaobing on 16-5-14.
 */
public class PersonCenterFragmentPresenterImpl implements PersonCenterFragmentPresenter {
    private PersonCenterView personCenterView;

    public PersonCenterFragmentPresenterImpl(PersonCenterView personCenterView) {
        this.personCenterView = personCenterView;
    }

    @Override
    public void loadPhoto(final Context context, String url) {

        Glide.with(context).load(url).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                personCenterView.showPhoto(resource);
            }
        });
    }
}
