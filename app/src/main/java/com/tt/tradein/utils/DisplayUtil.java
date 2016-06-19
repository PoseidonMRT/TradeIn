package com.tt.tradein.utils;

public class DisplayUtil {
    public static int px2dip(float pxValue, float scale) {
        return (int) ((pxValue / scale) + 0.5f);
    }

    public static int dip2px(float dipValue, float scale) {
        return (int) ((dipValue * scale) + 0.5f);
    }

    public static int px2sp(float pxValue, float fontScale) {
        return (int) ((pxValue / fontScale) + 0.5f);
    }

    public static int sp2px(float spValue, float fontScale) {
        return (int) ((spValue * fontScale) + 0.5f);
    }
}
