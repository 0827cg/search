package com.cg.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: cg
 * Date: 2021/1/30 17:27
 */
public class TimeUtil {

	private static final String COMMON_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 将毫秒转换为标准日期格式
	 *
	 * @param mills long
	 * @return String
	 */
	public static String millsToDate(long mills) {
		return millsToDate(mills, COMMON_FORMAT);
	}

	/**
	 * 将毫秒（Java下时间戳）转换为日期格式，指定格式
	 *
	 * @param mills  long
	 * @param format String
	 * @return String
	 */
	public static String millsToDate(long mills, String format) {
		Date date = new Date(mills);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		return simpleDateFormat.format(date);
	}


}

