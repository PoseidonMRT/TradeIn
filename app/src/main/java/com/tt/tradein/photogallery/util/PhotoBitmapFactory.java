package com.tt.tradein.photogallery.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.media.ThumbnailUtils;
import android.os.Trace;
import android.provider.MediaStore;
import android.util.Log;
import android.util.LruCache;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.MathContext;

/**
 * Created by tuozhaobing on 15-10-22.
 */
public class PhotoBitmapFactory {

    private LruCache<String,Bitmap> mLruCache = null;
    private static PhotoBitmapFactory mPhotoBitmapFactory = null;

    private PhotoBitmapFactory(){}

    public static PhotoBitmapFactory getInstance(){
        if (mPhotoBitmapFactory == null){
            mPhotoBitmapFactory = new PhotoBitmapFactory();
        }
        return mPhotoBitmapFactory;
    }

    public synchronized void addBitmapToLruCache(String key,Bitmap bitmap){
        if(mLruCache != null) {
            if (mLruCache.get(key) == null) {
                if (key != null && bitmap != null)
                    mLruCache.put(key, bitmap);
            }
        }
    }

    public synchronized void deleteBitmapFromLruCache(String key){
        if (mLruCache != null){
            if (key != null){
                Bitmap tmpBitmap = mLruCache.remove(key);
                if (tmpBitmap != null){
                    tmpBitmap.recycle();
                    tmpBitmap = null;
                }
            }
        }
    }

    public synchronized Bitmap getBitmapFromLruCache(String key){
        if (null == mLruCache){
            return null;
        }
        if (key != null){
            Bitmap bitmap = mLruCache.get(key);
            return bitmap;
        }else{
            return null;
        }
    }

    public synchronized void clearLruCache(){
        if (mLruCache != null){
            if (mLruCache.size() > 0){
                mLruCache.evictAll();
            }
            mLruCache = null;
        }
    }

    public synchronized void initLruCache(){

    }

    public static Bitmap decodeFile(String pathName, BitmapFactory.Options opts) {
        Bitmap bm = null;
        InputStream stream = null;
        try {
            stream = new FileInputStream(pathName);
            bm =BitmapFactory.decodeStream(stream, null, opts);
        } catch (Exception e) {
            /*  do nothing.
                If the exception happened on open, bm will be null.
            */
            Log.e("BitmapFactory", "Unable to decode stream: " + e);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    // do nothing here
                }
            }
        }
        return bm;
    }

    public Bitmap getImageThumbnail(int imageId,String imagePath, int width, int height, boolean isFull) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        if (bitmap == null ) {
            options.inJustDecodeBounds = true;
            bitmap = BitmapFactory.decodeFile(imagePath, options);
            options.inJustDecodeBounds = false; // 设为 false
            int h = options.outHeight;
            int w = options.outWidth;
            int beWidth = w / width;
            int beHeight = h / height;
            int be = 1;
            if (beWidth < beHeight) {
                be = beWidth;
            } else {
                be = beHeight;
            }
            if (be <= 0) {
                be = 1;
            }
            options.inSampleSize = be;
            bitmap = BitmapFactory.decodeFile(imagePath, options);
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        if (bitmap == null) {
            return null;
        }
        return bitmap;
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

}
