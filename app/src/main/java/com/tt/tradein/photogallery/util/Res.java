package com.tt.tradein.photogallery.util;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;

/**
 * 加载R文件里面的内容
 * 
 * @author king
 * @QQ:595163260
 * @version 2014年10月18日  下午11:46:29
 */
public class Res {

	// 文件路径名
	private static String pkgName;
	// R文件的对象
	private static Resources resources;

	// 初始化文件夹路径和R资源
	public static void init(Context context) {
		pkgName = context.getPackageName(); 
		resources = context.getResources();
	}

	/**
	 * layout文件夹下的xml文件id获取
	 * 
	 */
	public static int getLayoutID(String layoutName) {
		return resources.getIdentifier(layoutName, "layout", pkgName);
	}

	// 获取到控件的ID
	public static int getWidgetID(String widgetName) {
		return resources.getIdentifier(widgetName, "id", pkgName);
	}

	/**
	 * anim文件夹下的xml文件id获取
	 * 
	 */
	public static int getAnimID(String animName) {
		return resources.getIdentifier(animName, "anim", pkgName);
	}

	/**
	 * xml文件夹下id获取
	 * 
	 */
	public static int getXmlID(String xmlName) {
		return resources.getIdentifier(xmlName, "xml", pkgName);
	}

	// 获取xml文件
	public static XmlResourceParser getXml(String xmlName) {
		int xmlId = getXmlID(xmlName);
		return (XmlResourceParser) resources.getXml(xmlId);
	}

	/**
	 * raw文件夹下id获取
	 * 
	 */
	public static int getRawID(String rawName) {
		return resources.getIdentifier(rawName, "raw", pkgName);
	}

	/**
	 * drawable文件夹下文件的id
	 * 
	 */
	public static int getDrawableID(String drawName) {
		return resources.getIdentifier(drawName, "drawable", pkgName);
	}

	// 获取到Drawable文件
	public static Drawable getDrawable(String drawName) {
		int drawId = getDrawableID(drawName);
		return resources.getDrawable(drawId);
	}

	/**
	 * value文件夹
	 * 
	 */
	// 获取到value文件夹下的attr.xml里的元素的id
	public static int getAttrID(String attrName) {
		return resources.getIdentifier(attrName, "attr", pkgName);
	}

	// 获取到dimen.xml文件里的元素的id
	public static int getDimenID(String dimenName) {
		return resources.getIdentifier(dimenName, "dimen", pkgName);
	}

	// 获取到color.xml文件里的元素的id
	public static int getColorID(String colorName) {
		return resources.getIdentifier(colorName, "color", pkgName);
	}

	// 获取到color.xml文件里的元素的id
	public static int getColor(String colorName) {
		return resources.getColor(getColorID(colorName));
	}

	// 获取到style.xml文件里的元素id
	public static int getStyleID(String styleName) {
		return resources.getIdentifier(styleName, "style", pkgName);
	}

	// 获取到String.xml文件里的元素id
	public static int getStringID(String strName) {
		return resources.getIdentifier(strName, "string", pkgName);
	}

	// 获取到String.xml文件里的元素
	public static String getString(String strName) {
		int strId = getStringID(strName);
		return resources.getString(strId);
	}

	// 获取color.xml文件里的integer-array元素
	public static int[] getInteger(String strName) {
		return resources.getIntArray(resources.getIdentifier(strName, "array",
				pkgName));
	}

}
