package com.tt.tradein.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

@SuppressLint("SimpleDateFormat")
public class TimeUtils {

	/**
	 * 服务端给的时间，经常会以.0结尾，所以去除之
	 * @param datetime
	 * @return
	 */
	public static String RemoveLastZero(String datetime) {
		if (TextUtils.isEmpty(datetime))
			return "";

		if (datetime.length() > 19)
			return datetime.substring(0, 19);
		else
			return datetime;
	}

	//-------------------------------------------------------------------------------------------------
	/**
	 * 将yyyy-MM-dd HH:mm:ss格式的时间，与当前时间相比，时间差转换为口头上的术语，如几天几小时几分几�?
	 * 
	 * @return
	 */
	public static String convert_between(String datetime) {
		try {
			long time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datetime).getTime();
			return convert_between((int) ((time - System.currentTimeMillis()) / 1000));
		} catch (ParseException e) {
			e.printStackTrace();
			return "未知";
		}
	}

	/**
	 * 将yyyy-MM-dd HH:mm:ss格式的时间，两个时间相比，时间差转换为口头上的术语，如几天几小时几分几秒
	 * 
	 * @return
	 */
	public static String convert_between(String starttime, String endtime) {
		try {
			long ttime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(starttime).getTime();
			long etime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endtime).getTime();
			return convert_between((int) ((etime - ttime) / 1000));
		} catch (ParseException e) {
			e.printStackTrace();
			return "未知";
		}
	}

	/**
	 * 将时长秒，转换为口头上的术语，如几天几小时几分几�? 1天：86400s 1时：3600s 1分：60s
	 * 
	 * @param sec 相差的间隔，单位为秒
	 * @return
	 */
	public static String convert_between(long sec) {
		if (sec < 0)
			return "时间超了";
		StringBuffer buf = new StringBuffer();
		if (sec >= 86400) {
			int day = (int) (sec / 86400);
			int hour = (int) ((sec % 86400) / 3600);
			int min = (int) ((sec % 86400 % 3600) / 60);
			int second = (int) (sec % 86400 % 3600 % 60);
			buf.append(day).append("天").append(hour).append("小时").append(min).append("分钟").append(second).append("秒");
		} else if (sec > 3600) {
			int hour = (int) (sec / 3600);
			int min = (int) ((sec % 3600) / 60);
			int second = (int) (sec % 3600 % 60);
			buf.append(hour).append("小时").append(min).append("分钟").append(second).append("秒");
		} else if (sec > 60) {
			int min = (int) (sec / 60);
			int second = (int) (sec % 60);
			buf.append(min).append("分钟").append(second).append("秒");
		} else {
			buf.append(sec).append("秒");
		}

		return buf.toString();
	}

	/**
	 * 将时长秒，转换为几分几秒，�?�用于�?�话时长之类的，�?2'30''
	 * @param sec
	 * @return
	 */
	public static String convert_between_len(long sec) {
		if (sec < 0)
			return String.valueOf(sec);

		StringBuffer buf = new StringBuffer();
		if (sec > 60) {
			int min = (int) (sec / 60);
			int second = (int) (sec % 60);
			buf.append(min).append("'").append(second).append("''");
		} else {
			buf.append(sec).append("''");
		}

		return buf.toString();
	}

	//-------------------------------------------------------------------------------------------------
	/**
	 * 将EEE MMM dd HH:mm:ss zzz yyyy格式的时间，同当前时间相比，格式化为：xx分钟前，xx小时前和日期
	 * 
	 * @param datetime
	 * @return
	 */
	public static String convert_before_timezone(String datetime) {
		Log.v("info", datetime);
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy", Locale.ENGLISH);
		dateFormat.setLenient(false);
		Date created = null;
		try {
			created = dateFormat.parse(datetime);
		} catch (Exception e) {
			return "";
		}

		return convert_before(created.getTime());
	}

	/**
	 * 将yyyy-MM-dd HH:mm:ss格式的时间，同当前时间比对，格式化为：xx分钟前，xx小时前和日期
	 * 
	 * @param datetime �?比对的时�?
	 * @return
	 */
	public static String convert_before(String datetime) {
		if (TextUtils.isEmpty(datetime)) {
			return "";
		}

		try {
			long time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datetime).getTime();
			return convert_before(time);
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 将对比后的时间，格式化为：xx分钟前，xx小时前和日期
	 * @param time �?比对的时�?
	 * @return
	 */
	public static String convert_before(long time) {
		if (time < 0)
			return String.valueOf(time);
		
		int difftime = (int) ((System.currentTimeMillis() - time) / 1000);
		if (difftime < 86400 && difftime > 0) {
			if (difftime < 3600) {
				int min = (int) (difftime / 60);
				if (min == 0)
					return "刚刚";
				else
					return (int) (difftime / 60) + "分钟前";
			} else {
				return (int) (difftime / 3600) + "小时前";
			}
		} else {
			Calendar now = Calendar.getInstance();
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(time);
			if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR) && c.get(Calendar.MONTH) == now.get(Calendar.MONTH)
					&& c.get(Calendar.DATE) == now.get(Calendar.DATE)) {
				return new SimpleDateFormat("HH:mm").format(c.getTime());
			}
			if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR) && c.get(Calendar.MONTH) == now.get(Calendar.MONTH)
					&& c.get(Calendar.DATE) == now.get(Calendar.DATE) - 1) {
				return new SimpleDateFormat("昨天 HH:mm").format(c.getTime());
			} else if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR)
					&& c.get(Calendar.MONTH) == now.get(Calendar.MONTH)
					&& c.get(Calendar.DATE) == now.get(Calendar.DATE) - 2) {
				return new SimpleDateFormat("前天 HH:mm").format(c.getTime());
			} else if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
				return new SimpleDateFormat("M月d日 HH:mm").format(c.getTime());
			} else {
				return new SimpleDateFormat("yy年M月d日").format(c.getTime());
			}
		}
	}

	/**
	 * 指定的时间，在时间条件范围内的，返回true，不在该时间范围内，返回false
	 * 
	 * @param sDate �?始日期，yyyy-MM-dd hh:mm:ss
	 * @param eDate 结束时间，yyyy-MM-dd hh:mm:ss
	 * @param checkTime �?查时间，yyyy-MM-dd hh:mm:ss
	 * @return
	 */
	public static boolean timeCompare(String sDate, String eDate, String checkTime) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			long sTime = sdf.parse(sDate).getTime();
			long eTime = sdf.parse(eDate).getTime();
			long sec = sdf.parse(checkTime).getTime();
			if (sec > sTime && sec < eTime)
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 当前时间，在时间条件范围内的，返回true，不在该时间范围内，返回false
	 * 
	 * @param sDate �?始日期，hh:mm
	 * @param eDate 结束时间，hh:mm
	 * @return
	 */
	public static boolean timeCompa(String sDate, String eDate) {
		try {
			long sec = System.currentTimeMillis();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			long sTime = sdf.parse(df.format(sec) + " " + sDate).getTime();
			long eTime = sdf.parse(df.format(sec) + " " + eDate).getTime();
			if (sec > sTime && sec < eTime)
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 判断两个时间的大�?
	 * 
	 * @param sDate �?始日期，yyyy-MM-dd hh:mm:ss
	 * @param eDate 结束时间，yyyy-MM-dd hh:mm:ss
	 * @return
	 */
	public static boolean timeCompare(String sDate, String eDate) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			long sTime = sdf.parse(sDate).getTime();
			long eTime = sdf.parse(eDate).getTime();
			if (sTime > eTime)
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 将传入时间添加秒钟数
	 * 
	 * @param date 时间
	 * @param sec 秒数，正数为添加秒，负数是减少秒 
	 * @return
	 */
	public static String addSec(String date, int sec) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long reminTime = sdf.parse(date).getTime() + 1000 * sec;
			return sdf.format(reminTime);
		} catch (Exception e) {
			return "";
		}
	}
	
	public static String parseTime(String date) {
		Calendar c=Calendar.getInstance();
		c.setTimeInMillis(Long.parseLong(date));
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return sdf.format(c.getTime());
	
	}

	/**
	 * 格式化取当前时间
	 * @return
	 */
	public static String getThisDateTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
	}
}
