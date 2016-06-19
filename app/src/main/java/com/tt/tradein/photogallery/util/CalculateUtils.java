package com.tt.tradein.photogallery.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tuozhaobing on 15-10-22.
 */
public class CalculateUtils {

    private static SimpleDateFormat mSimpleDateFormat_Minutes = new SimpleDateFormat("mm:ss");
    private static SimpleDateFormat mSimpleDateFormat_Hours = new SimpleDateFormat("HH:mm:ss");
    private static SimpleDateFormat mSimpleDateFormat_Date = new SimpleDateFormat("yyyy-MM-dd");

    //input time unit is micro second.
    public static String getStringDurationTimeFromLong(long duration) {
        if(duration < 1000){
            duration = 1000;
        }
        return mSimpleDateFormat_Minutes.format(new Date(duration)); //millisecond value
    }

    //input time unit is second.
    public static String getDateTimeStringFromSeconds(long time){
        return mSimpleDateFormat_Date.format(new Date(time*1000L));
    }

}
