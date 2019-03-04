package com.my.common.tools;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.time.DateUtils;

public class DateUtil {
    public static final int WEEK_DAYS = 7;

    public static final long SECOND = 1000;

    public static final long MINUTE = 60 * SECOND;

    public static final long HOUR = 60 * MINUTE;

    public static final long DAY = 24 * HOUR;

    public static final long WEEK = WEEK_DAYS * DAY;

    /**
     * 比较两个日期之间相差多少秒
     *
     */
    public static Long compareSecond(Calendar c1, Calendar c2) {
        assert (c1 != null);
        assert (c2 != null);
        return (Long) ((c1.getTimeInMillis() - c2.getTimeInMillis()) / 1000);
    }

    
    /**
     * 比较两个Long型日期日期之间相差多少秒
     * @param c1 大日期值
     * @param c2 小日期值
     */
    public static Long compareSecond(Long c1, Long c2) {
        assert (c1 != null);
        assert (c2 != null);
        return (Long) ((c1 - c2) / 1000);
    }
    
    /**
     * 比较两个日期之间相差多少毫秒
     *
     */
    public static Long compareTimeInMillis(Calendar c1, Calendar c2) {
        assert (c1 != null);
        assert (c2 != null);
        return (Long) (c1.getTimeInMillis() - c2.getTimeInMillis());
    }

    /**
     * 比较两个日期之间相差多少分钟
     *
     */
    public static int compareMinutes(Calendar c1, Calendar c2) {
        assert (c1 != null);
        assert (c2 != null);
        return (int) ((c1.getTimeInMillis() - c2.getTimeInMillis()) / 1000 / 60);
    }

    /**
     * 根据所给年份，返回此年的1月1日零时零分零秒日历对象
     *
     * @param year
     */
    public static Calendar getYearCalendar(int year) {
        Calendar result = DateUtil.now();
        result.clear();
        result.set(Calendar.YEAR, year);
        return result;
    }

    public static Calendar getYearCalendar() {
        Calendar c = DateUtil.now();
        return getYearCalendar(c.get(Calendar.YEAR));
    }

    public static Calendar getNextYearCalendar(int years) {
        Calendar c = DateUtil.now();
        return getYearCalendar(c.get(Calendar.YEAR) + years);
    }

    /**
     * 比较c1,c2相差多少天 返回0 则代表同一天<br>
     *
     */
    public static int interval(final Calendar date1, final Calendar date2) {
        if (isSameDay(date1, date2))
            return 0;
        Calendar bigCopy = (Calendar) date1.clone();
        Calendar smallCopy = (Calendar) date2.clone();
        setTimeToMidnight(bigCopy);
        setTimeToMidnight(smallCopy);
        long day = (bigCopy.getTimeInMillis() - smallCopy.getTimeInMillis()) / DAY;
        return (int) day;
    }

    public static int daysRented(final Calendar end, final Calendar start) {
        int days = interval(end, start);
        if (days == 0)
            return 1;
        return days;

    }

    private static void setTimeToMidnight(Calendar calendar) {
        Calendar clone = (Calendar) calendar.clone();
        calendar.clear();
        calendar.set(Calendar.YEAR, clone.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, clone.get(Calendar.MONTH));
        calendar.set(Calendar.DATE, clone.get(Calendar.DATE));
    }

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        return DateUtils.isSameDay(cal1, cal2);
    }

    /**
     *
     * 与现在比较相差多少小时
     */
    public static int comparehours(Calendar small) {
        return compareMinutes(DateUtil.now(), small) / 60;
    }

    /**
     * 与今天相差多少天 返回0 则代表今天
     */
    public static int compareTody(Calendar small) {
        return interval(DateUtil.now(), small);
    }

    /**
     * 比较c1,c2相差多少月
     */
    public static int compareMonth(Calendar c1, Calendar c2) {
        return compareYear(c1, c2) * 12 + (c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH));
    }

    /**
     * 比较c1,c2相差多少年
     */
    public static int compareYear(Calendar c1, Calendar c2) {
        return c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR);
    }

    public static int getCurrentYear() {
        Calendar c1 = DateUtil.now();
        return c1.get(Calendar.YEAR);
    }

    public static int getCurrentMonth() {
        return DateUtil.now().get(Calendar.MONTH);
    }

    /**
     * 判断此时是否在两时间之间
     */
    public static boolean isNowBetween(Calendar start, Calendar end) {
        Calendar now = now();
        return now.after(start) && now.before(end);
    }

    /**
     * 是否是今天
     */
    public static boolean isToday(Calendar date) {
        return DateUtil.interval(date, DateUtil.now()) == 0;
    }

    /**
     * 使用实例:
     *
     * <pre>
     * parseYYYYMMDD(&quot;20071011&quot;, null);//正确返回Calendar
     * parseYYYYMMDD(&quot;2007911&quot;, defaultDate);//返回defaultDate
     * </pre>
     *
     * @param parameterValue
     * @param defaultValue
     */
    public static Calendar parseYYYYMMDD(String parameterValue, Calendar defaultValue) {
        try {
            return parseYYYYMMDD(parameterValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 将一个 年年年年月月日日 的字符串转换为日历类
     *
     * @param parameterValue
     */
    public static Calendar parseYYYYMMDD(String parameterValue) {
        String format = "yyyyMMdd";
        return parse(parameterValue, format);
    }
    
    /**
     * 字符串转为长
     *
     * @param parameterValue
     */
    public static Calendar parseYYYY_MM_DD_HH_MM_SS(String parameterValue) {
        String format = "yyyy-MM-dd HH:mm:ss";
        return parse(parameterValue, format);
    }

    public static Calendar parse(String string, String... format) {
        Calendar result = Calendar.getInstance();
        try {
            result.setTime(DateUtils.parseDate(string, format));
            return result;
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 将一个 年年年年月月日日 的字符串转换为日历类
     *
     * @param parameterValue
     */
    public static Calendar parseYYYY_MM_DD(String parameterValue) {
        String format = "yyyy-MM-dd";
        return parse(parameterValue, format);
    }

    private static Calendar formerDate;

    public static void setSystemCurrentDate(Calendar date) {
        if (formerDate == null)
            formerDate = DateUtil.now();
        try {
            String command = String.format("cmd.exe /c date %s", toYYYY_MM_DD(date));
            Process cc = Runtime.getRuntime().exec(command);
            cc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void resetTime() {
        if (formerDate == null)
            return;
        setSystemCurrentDate(formerDate);
    }

    public static Calendar getOneYearBeforeNow() {
        Calendar result = now();
        result.add(Calendar.YEAR, -1);
        return result;
    }

    public static Calendar getOneWeekBeforeNow() {
        return addDate(-WEEK_DAYS);
    }

    public static Calendar getTwoWeekBeforeNow() {
        return addDate(-WEEK_DAYS * 2);
    }

    public static Calendar getThreeMonthBeforeNow() {
        return getTheDayZero(addDate(-30 * 3));
    }

    public static Calendar getOneMonthBeforeNow() {
        return getTheDayZero(addDate(-30 * 1));
    }

    public static Calendar getMidNightFutureAfter(Calendar date, int days) {
        Calendar future = (Calendar) date.clone();
        future.add(Calendar.DATE, days);
        return future;
    }

    public static Calendar addDate(int days) {
        Calendar result = now();
        result.add(Calendar.DATE, days);
        return result;
    }

    /**
     * 返回此日期的零点整，如2007-3-14 19:00:35 返回 2007-3-14 00:00:00
     *
     * @param date
     */
    public static Calendar getTheDayZero(Calendar date) {
        Calendar result = (Calendar) date.clone();
        result.set(Calendar.HOUR_OF_DAY, 0);
        result.set(Calendar.MINUTE, 0);
        result.set(Calendar.SECOND, 0);
        result.set(Calendar.MILLISECOND, 0);
        return result;
    }

    public static Calendar getTheDayZero() {
        return getTheDayZero(DateUtil.now());
    }

    public static Calendar getTheDayMidnight() {
        return getTheDayMidnight(DateUtil.now());
    }

    /**
     * 返回此日期的午夜，如2007-3-14 19:00:35 返回 2007-3-14 23:59:59
     *
     * @param date
     */
    public static Calendar getTheDayMidnight(Calendar date) {
        Calendar result = (Calendar) date.clone();
        result.set(Calendar.HOUR_OF_DAY, 23);
        result.set(Calendar.MINUTE, 59);
        result.set(Calendar.SECOND, 59);
        result.set(Calendar.MILLISECOND, 999);
        return result;
    }

    public static Calendar getTheMonthCalendar() {
        Calendar result = Calendar.getInstance();
        result.set(Calendar.DAY_OF_MONTH, 1);
        result.set(Calendar.HOUR_OF_DAY, 0);
        result.set(Calendar.MINUTE, 0);
        result.set(Calendar.SECOND, 0);
        result.set(Calendar.MILLISECOND, 0);
        return result;
    }

    public static String nowTimeStamp() {
        return toTimeStamp(now());
    }

    public static String toMMDD(Calendar date) {
        return new SimpleDateFormat("MMdd").format(date.getTime());
    }

    public static String toYYYYMMDD(Calendar date) {
        return new SimpleDateFormat("yyyyMMdd").format(date.getTime());
    }

    public static String toYYMMDD(Calendar date) {
        return new SimpleDateFormat("yyMMdd").format(date.getTime());
    }

    public static String toYYYY_MM_DD(Calendar date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date.getTime());
    }

    public static String toYYYYMMDDHHMM(Calendar date) {
        return new SimpleDateFormat("yyyyMMddHHmm").format(date.getTime());
    }

    public static String toMM_DD_HH_mm_ss(Calendar date) {
        return new SimpleDateFormat("MM-dd HH:mm:ss").format(date.getTime());
    }

    public static String toYYYY_MM_DDZH(Calendar date) {
        return new SimpleDateFormat("yyyy年MM月dd日").format(date.getTime());
    }

    public static String toTimeStamp(Calendar calendar) {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(calendar.getTime());
    }

    public static String toTimeStampFm(Calendar calendar) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
    }

    public static void toOracleDateStrForMap(Map<String, Object> values) {
        for (Entry<String, Object> entry : values.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (null != value) {
                System.out.println("the class of the value is"+value.getClass());
                if (Calendar.class.isAssignableFrom(value.getClass())) {
                    value = toOracleDateStr((Calendar) value);
                    values.put(key, value);
                }
            }
        }
    }

    public static String toOracleDateStr(Calendar calendar) {
        if (calendar == null) {
            return null;
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
    }

    public static String toyyyy_MM_dd_HH_mm(Calendar calendar) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(calendar.getTime());
    }
    public static String toyyyyMMddHHmmss(Calendar calendar) {
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(calendar.getTime());
    }
    public static String toYYYY_MM(Calendar date) {
        return new SimpleDateFormat("yyyy-MM").format(date.getTime());
    }
    public static String toYYYYMM(Calendar date) {
        return new SimpleDateFormat("yyyyMM").format(date.getTime());
    }
    
    public static String toCNyyyy_MM_dd_HH_mm(Calendar calendar) {
        return new SimpleDateFormat("yyyy年MM月dd日 HH时mm分").format(calendar.getTime());
    }
    public static String toCNyyyy_MM_dd_HH_mm_ss(Calendar calendar) {
        return new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(calendar.getTime());
    }
    public static String toDateStamp(Calendar calendar) {
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
    }

    public static String toHHmm(Calendar date) {
        return new SimpleDateFormat("HH:mm").format(date.getTime());
    }

    public static String toHHmmss(Calendar date) {
        return new SimpleDateFormat("HH:mm:ss").format(date.getTime());
    }

    public static String toHHmmss_(Calendar date) {
        return new SimpleDateFormat("HHmmss").format(date.getTime());
    }

    public static String toHHmmssSSS(Calendar date) {
        return new SimpleDateFormat("HH:mm:ss SSS").format(date.getTime());
    }

    public static String toDd_HHmm(Calendar date) {
        return new SimpleDateFormat("dd/HH:mm").format(date.getTime());
    }

    private static String[] parsePatterns;

    static {
        parsePatterns = new String[] { "yyyy/MM/dd", "yyyy-MM-dd", "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm",
                "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm" };
    }

    public static Calendar parseTimeStamp(String string) {
        Calendar result = Calendar.getInstance();
        try {
            result.setTime(DateUtils.parseDate(string, parsePatterns));
            return result;
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static Calendar add(Calendar date, int field, int i) {
        Calendar copy = ((Calendar) date.clone());
        copy.add(field, i);
        return copy;
    }

    /**
     * @param time
     *            如15:30或15:30:20
     * @param offset
     *            时区
     */
    public static Calendar setTodayTime(String time, int offset) {
        Calendar result = Calendar.getInstance();
        String[] info = time.split(":");
        int hour = Integer.valueOf(info[0]);
        int minute = Integer.valueOf(info[1]);

        hour -= offset;
        if (hour < 0)
            hour += 24;

        result.set(Calendar.HOUR_OF_DAY, hour);
        result.set(Calendar.MINUTE, minute);
        result.set(Calendar.SECOND, 0);

        return result;
    }


    public static Calendar getCalendarFromSqlDate(java.sql.Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }

    /**
     * date类型时间转换成 Calendar类型
     * @param date
     * @return
     * @create_time 2016年7月25日 下午4:55:06
     */
    public static Calendar getCalendarFromSqlDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }
    /**
     * 获取Calendar的当前时间
     * @return Calendar的当前时间
     * @create_time 2016年7月25日 下午4:56:51
     */
    public static Calendar getNowTime() {
    	return getCalendarFromSqlDate(new Date());
    }
    
    public static Calendar now() {
        return Calendar.getInstance();
    }

    public static Calendar getMixDate() {
        Calendar date = now();
        date.clear();
        return date;
    }

    /**
     * 获取当前日期属于一年第几周
     * @return
     * @create_time 2011-3-29 上午11:53:19
     */
    public static int getWeekNumber() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 星期日返回1，星期一返回2，依次类推
     * @param cal
     * @return
     * @create_time 2011-5-6 下午08:12:23
     */
    public static int getWeekIndex(Calendar cal) {
        int week = cal.get(Calendar.DAY_OF_WEEK);//星期日返回1，星期一返回2，依次类推
        if (week == 1) {
            return 0;
        } else {
            return week - 2;
        }
    }

    public static String unitFormat(long i) {  
        String retStr = null;  
        if (i >= 0 && i < 10)  
            retStr = "0" + Long.toString(i);  
        else  
            retStr = "" + i;  
        return retStr;  
    } 
    
    /**
     * 时长转换成  XX:XX:XX 形式
     * @param interval
     * @return
     */
    public static String secToTime(Long interval) {
    	StringBuffer bf = null;
		long hour = 0; //小时
    	long minute = 0; //分钟
    	long second =  0; //秒
    	if(null != interval) {
    		if(interval <= 0) {
    			return "00:00:00";
    		} else {
    			minute = interval/60;
    			if(minute < 60) {
    				bf = new StringBuffer();
    				second = interval % 60;
    				bf.append("00:")
    				  .append(unitFormat(minute))
    				  .append(":").append(unitFormat(second));
    			} else {
    				bf = new StringBuffer();
    				hour = minute / 60;
    				if(hour > 72) {
    					return "超出72小时";
    				}
    				minute = minute % 60;  
                    second = interval - hour * 3600 - minute * 60;
                    bf.append(hour)
  				      .append(":").append(unitFormat(minute))
  				      .append(":").append(unitFormat(second));
    			}
    		}      	
    	}
    	if(null == bf) {
    		return null;
    	}
    	return bf.toString();
    }
    

    
    /**
     * 时长转换成  XX:XX:XX 形式
     * @param c1 大日期值
     * @param c2 小日期值
     * @return
     */
    public static String compareSecondToTime(Long c1, Long c2) {
    	return secToTime(compareSecond(c1, c2));
    }
    
	public static String subDays(String dateTime, String days) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		String remindTime = null;
		try {
			date = sdf.parse(dateTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.set(calendar.DATE, calendar.get(calendar.DATE) - Integer.parseInt(days));
		// 返回减去后的最终时间
		remindTime = sdf.format(calendar.getTime());
		return remindTime;
	}
    
    public static void main(String args[]) {

	/*	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("getLastMonthFristDay: " + sdf.format(getLastMonthFristDay(-1).getTime()));
		System.out.println("getLastMonthEndDay: " + sdf.format(getLastMonthEndDay(0).getTime()));
		
		System.out.println("4 1: " + sdf.format(getLastMonthFristDay(-4).getTime()));
		System.out.println("4 2: " + sdf.format(getLastMonthEndDay(-3).getTime()));*/
    	
    	
//    	System.out.println(toYYYYMM(Calendar.getInstance()));
//    	
//        String date = "20110816";
//        System.out.println(getWeekDayByDateStr(date));
//        date = "20110817";
//        System.out.println(getWeekDayByDateStr(date));
//        date = "20110818";
//        System.out.println(getWeekDayByDateStr(date));
//        date = "20110819";
//        System.out.println(getWeekDayByDateStr(date));
//        date = "20110820";
//        System.out.println(getWeekDayByDateStr(date));
//        date = "20110821";
//        System.out.println(getWeekDayByDateStr(date));
//        date = "20110822";
//        System.out.println(getWeekDayByDateStr(date));
//        date = "20110823";
//        System.out.println(getWeekDayByDateStr(date));
//        System.out.println(getCnWeekByDateStr(date,"yyyyMMdd" ));
//		System.out.println(DateUtil.interval(DateUtil.parse("2016-08-15", "yyyy-MM-dd"),
//				DateUtil.parse("2016-08-09", "yyyy-MM-dd")));
        //时间日期转换
    }

    /**
     * 得到某年某周的第一天
     * 
     * @param year
     * @param week
     * @return
     */
    public static Date getFirstDayOfWeek(int year, int week) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);

        Calendar cal = (Calendar) c.clone();
        cal.add(Calendar.DATE, week * 7);

        return getFirstDayOfWeek(cal.getTime());
    }

    /**
     * 得到某年某周的最后一天
     * 
     * @param year
     * @param week
     * @return
     */
    public static Date getLastDayOfWeek(int year, int week) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);

        Calendar cal = (Calendar) c.clone();
        cal.add(Calendar.DATE, week * 7);

        return getLastDayOfWeek(cal.getTime());
    }

    /**
     * 取得当前日期所在周的第一天
     * 
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        return c.getTime();
    }

    /**
     * 取得当前日期所在周的最后一天
     * 
     * @param date
     * @return
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        return c.getTime();
    }

    /** 竞彩足球周几的时间转换  key = 周日,value = 110812*/
    public static Map<String, String> getOneWeekDay() {
        // 获得一周的key-value key=周日,value=110812
        Map<String, String> weekDayMap = new HashMap<String, String>();
        String weekArr[] = new String[] { "", "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
        Calendar now = Calendar.getInstance();
        weekDayMap.put(weekArr[now.get(Calendar.DAY_OF_WEEK)], DateUtil.toYYMMDD(now));
        for (int i = 1; i <= 6; i++) {
            now.add(Calendar.DAY_OF_MONTH, 1);
            weekDayMap.put(weekArr[now.get(Calendar.DAY_OF_WEEK)], DateUtil.toYYMMDD(now));
        }
        return weekDayMap;
    }

    /** 竞彩足球周几的时间转换  key = 周日,value = 110812*/
    public static Map<String, String> getNumOneWeekDay() {
        // 获得一周的key-value key=周日,value=110812
        Map<String, String> weekDayMap = new HashMap<String, String>();
        //1:周一
        String weekArr[] = new String[] { "", "7", "1", "2", "3", "4", "5", "6" };
        Calendar now = Calendar.getInstance();
        weekDayMap.put(weekArr[now.get(Calendar.DAY_OF_WEEK)], DateUtil.toYYMMDD(now));
        for (int i = 1; i <= 6; i++) {
            now.add(Calendar.DAY_OF_MONTH, 1);
            weekDayMap.put(weekArr[now.get(Calendar.DAY_OF_WEEK)], DateUtil.toYYMMDD(now));
        }
        return weekDayMap;
    }

    /** 竞彩足球周几的时间转换  key = 110812,value = 周日*/
    public static Map<String, String> getOtherOneWeekDay() {
        // 获得一周的key-value key=周日,value=110812
        Map<String, String> weekDayMap = new HashMap<String, String>();
        String weekArr[] = new String[] { "", "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
        Calendar now = Calendar.getInstance();
        weekDayMap.put(DateUtil.toYYMMDD(now), weekArr[now.get(Calendar.DAY_OF_WEEK)]);
        for (int i = 1; i <= 6; i++) {
            now.add(Calendar.DAY_OF_MONTH, 1);
            weekDayMap.put(DateUtil.toYYMMDD(now), weekArr[now.get(Calendar.DAY_OF_WEEK)]);
        }
        //这里多加一个当前天跨度之前的一周
        now = Calendar.getInstance();
        for (int i = 1; i <= 6; i++) {
            now.add(Calendar.DAY_OF_MONTH, -1);
            weekDayMap.put(DateUtil.toYYMMDD(now), weekArr[now.get(Calendar.DAY_OF_WEEK)]);
        }
        return weekDayMap;
    }

    /**根据日期取得周几*/
    public static int getWeekDayByDateStr(String dateStr) {
        Calendar d = parseYYYYMMDD(dateStr);
        Date tempDate = d.getTime();
        return tempDate.getDay() == 0 ? 7 : tempDate.getDay();
    }

    public static Calendar convertSqlDate(java.sql.Date date) {
        long mill = date.getTime();
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(mill);
        return now;
    }

    public static List<String> getDateBetweenStr(Calendar startTime, Calendar endTime) {
        Calendar start = (Calendar) startTime.clone();
        Calendar end = (Calendar) endTime.clone();
        List<String> list = new ArrayList<String>();
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(start.getTime()));
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(end.getTime()));
        if (start.get(Calendar.YEAR) == end.get(Calendar.YEAR)) {//同年
            if (start.get(Calendar.DAY_OF_YEAR) == end.get(Calendar.DAY_OF_YEAR)) {
                list.add(new SimpleDateFormat("yyyy-MM-dd").format(start.getTime()));
            } else {
                for (int day = start.get(Calendar.DAY_OF_YEAR); day < end.get(Calendar.DAY_OF_YEAR); start.add(
                        Calendar.DAY_OF_YEAR, 1)) {
                    list.add(new SimpleDateFormat("yyyy-MM-dd").format(start.getTime()));
                    day = start.get(Calendar.DAY_OF_YEAR);
                }
            }

        } else {//跨年
                //开始时间当前年最后一天
            Calendar last = (Calendar) start.clone();
            last.set(Calendar.MONTH, 11);
            last.set(Calendar.DAY_OF_MONTH, last.getActualMaximum(Calendar.DAY_OF_MONTH));

            for (int day = start.get(Calendar.DAY_OF_YEAR); day < last.get(Calendar.DAY_OF_YEAR); start.add(
                    Calendar.DAY_OF_YEAR, 1)) {
                list.add(new SimpleDateFormat("yyyy-MM-dd").format(start.getTime()));
                day = start.get(Calendar.DAY_OF_YEAR);
            }

            //结束时间同年第一天
            Calendar first = (Calendar) end.clone();
            first.set(Calendar.MONTH, 0);
            first.set(Calendar.DAY_OF_MONTH, first.getActualMinimum(Calendar.DAY_OF_MONTH));

            for (int day = first.get(Calendar.DAY_OF_YEAR); day < end.get(Calendar.DAY_OF_YEAR); first.add(
                    Calendar.DAY_OF_YEAR, 1)) {
                list.add(new SimpleDateFormat("yyyy-MM-dd").format(first.getTime()));
                day = first.get(Calendar.DAY_OF_YEAR);
            }

        }

        return list;

    }
    /**
     * 通过字符串格式的时间参数返回中文的周几
     * 20140101
     * 140101
     * @param dateStr 
     *  字符格式的时间参数
     * @param format
     * 时间参数格式  yyyMMdd
     * @return
     */
    public static String getCnWeekByDateStr(String dateStr,String format){
    	return getCNWeekByCalendar(parse(dateStr, format));
    }
    /**
     * 获取中文 周几
     * @param c calendar
     * @return
     */
    public static String getCNWeekByCalendar(Calendar c){
    	String weekArr[] = new String[] { "", "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
    	if (c != null) {
    		return  weekArr[c.get(Calendar.DAY_OF_WEEK)];
		}
    	return "";
    }
    
    /**
     * 获取下个月的第一天
     * @return
     */
    public static Calendar nextMonthFirstDate(Calendar calendar) {
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        return calendar;
    }
    
    /**
     * 当前日期累加N 个月
     * @param calendar
     * @param i
     * @return
     */
    public static Calendar addMonths(Calendar calendar, int i) {
        calendar.setTime(calendar.getTime());
        calendar.add(Calendar.MONTH, i);
        return calendar;
    }
    
    /**
     * 当前日期累加N 天
     * @param calendar
     * @param i
     * @return
     */
    public static Calendar addDays(Calendar calendar, int i) {
        calendar.setTime(calendar.getTime());
        calendar.add(Calendar.DATE, i);
        return calendar;
    }
   

    /**
     * 获取上个月第一天时间
     * @return
     * @create_time 2016年8月27日 下午3:17:08
     * @author 汪小萍
     */
	public static Calendar getLastMonthFristDay(int interval)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, interval);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 1);
		return calendar;
	}

	/**
	 * 获取上个月最后一天时间
	 * @return
	 * @create_time 2016年8月27日 下午3:17:23
	 * @author 汪小萍
	 */
	public static Calendar getLastMonthEndDay(int interval)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, interval);
		calendar.set(Calendar.DAY_OF_MONTH, 1); 
		calendar.add(Calendar.DATE, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar;
	}
	/**
	 * 获取当前时间
	 * @return
	 */
	public static String  getNowDate(String datetype){
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat(datetype);// 设置你想要的格式
		String dateStr = df.format(calendar.getTime());
		return dateStr;
	}
	
	
}
