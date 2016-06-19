package com.tt.tradein.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Map;
import java.util.Map.Entry;

public class UIUtils {

    public static void showMsg(Context context, String showContent) {
        Toast.makeText(context, showContent, Toast.LENGTH_SHORT).show();
    }

    public static void showMsgLong(Context context, String showContent) {
        Toast.makeText(context, showContent, Toast.LENGTH_LONG).show();
    }

    public static DisplayMetrics getDisplayMetrics(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    public static void startAnimation(Activity activity, int animationId, ImageView imageView) {
        Animation loading = AnimationUtils.loadAnimation(activity, animationId);
        loading.setInterpolator(new LinearInterpolator());
        imageView.startAnimation(loading);
    }

    public static float calcTextSizeByScreenSize(Activity activity, int length, int reducePixes, int maxTextSize) {
        DisplayMetrics dm = getDisplayMetrics(activity);
        float textSize = ((float) ((dm.widthPixels - reducePixes) / length)) / dm.density;
        if (textSize > ((float) maxTextSize)) {
            return (float) DisplayUtil.px2sp((float) maxTextSize, dm.density);
        }
        return textSize;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static ProgressDialog loadProgress(Context activity) {
        ProgressDialog mydialog = new ProgressDialog(activity);
        mydialog.setProgressStyle(0);
        mydialog.setTitle("数据加载提示");
        mydialog.setMessage("Loading ...");
        mydialog.setIndeterminate(false);
        mydialog.show();
        return mydialog;
    }

    public static View getViewFromLayout(Context activity, int layoutID) {
        return LayoutInflater.from(activity).inflate(layoutID, null);
    }

    public static void sendMessage2Handler(Handler handler, int what) {
        handler.sendMessage(handler.obtainMessage(what));
    }

    public static void sendMessage2Handler(Handler handler, int what, int arg1) {
        handler.sendMessage(handler.obtainMessage(what, arg1, arg1));
    }

    public static void sendMessage2Handler(Handler handler, int what, int arg1, Object object) {
        handler.sendMessage(handler.obtainMessage(what, arg1, arg1, object));
    }

    public static void sendMessage2Handler(Handler handler, int what, Object object) {
        handler.sendMessage(handler.obtainMessage(what, object));
    }

    public static void setWindowKeepScreenOn(Activity context) {
        context.requestWindowFeature(1);
        context.getWindow().addFlags(128);
    }

    public static void setWindowFullScreen(Activity context) {
        context.requestWindowFeature(1);
        context.getWindow().addFlags(1024);
    }

    public static void nextPage(Context context, Class<? extends Activity> target) {
        Intent intent = new Intent();
        intent.setClass(context, target);
        context.startActivity(intent);
    }

    public static void nextPage(Context context, Class<? extends Activity> target, Entry<String, String> entry) {
        Intent intent = new Intent();
        intent.setClass(context, target);
        intent.putExtra((String) entry.getKey(), (String) entry.getValue());
        context.startActivity(intent);
    }

    public static void nextPage(Context context, Class<? extends Activity> target, Entry<String, String> entry, int flag) {
        Intent intent = new Intent();
        intent.addFlags(flag);
        intent.setClass(context, target);
        intent.putExtra((String) entry.getKey(), (String) entry.getValue());
        context.startActivity(intent);
    }

    public static void nextPage(Context context, Class<? extends Activity> target, Map<String, String> map) {
        Intent intent = new Intent();
        intent.setClass(context, target);
        for (String key : map.keySet()) {
            intent.putExtra(key, (String) map.get(key));
        }
        context.startActivity(intent);
    }

    public static void nextPage(Context context, Class<? extends Activity> target, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, target);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    public static TextView setTextToTextView(Activity context, int id, String content) {
        TextView textView = (TextView) context.findViewById(id);
        textView.setText(content);
        return textView;
    }

    public static TextView setSizeToTextView(Activity context, int id, float textSize) {
        TextView textView = (TextView) context.findViewById(id);
        textView.setTextSize(textSize);
        return textView;
    }

    public static View hiddenView(Activity context, int id) {
        View view = context.findViewById(id);
        view.setVisibility(View.INVISIBLE);
        return view;
    }
}
