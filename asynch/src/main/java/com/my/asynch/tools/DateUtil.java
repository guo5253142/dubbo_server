package com.my.asynch.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 日期辅助类
 * 
 * @project sinafenqi-asynch
 * @author liubo
 * @date 2016年8月13日
 * www.sinafenqi.com.
 */
public class DateUtil {

	public static String toTimeStampFm() {
		Calendar result = Calendar.getInstance();
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(result.getTime());
    }
}
