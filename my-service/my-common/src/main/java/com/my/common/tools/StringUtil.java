package com.my.common.tools;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

/**
 * 处理字符串的类
 *
 * @project commons
 * @author Administrator
 * @date 2010-12-8 Copyright (C) 2010-2012 www.2caipiao.com Inc. All rights
 *       reserved.
 */
public class StringUtil {

	/**
	 * 把形如[01 02 04 05] 数组转为 "01,02,04,05"字符串
	 * 
	 * @param strs
	 * @param spliter
	 * @return
	 */
	public static String toSplitString(String[] strs, String spliter) {
		if (strs == null) {
			return "";
		}
		StringBuffer returnStrBuff = new StringBuffer();
		for (int i = 0; i < strs.length; i++) {
			returnStrBuff.append(strs[i]);
			if (i < strs.length - 1) {
				returnStrBuff.append(spliter);
			}
		}
		return returnStrBuff.toString();
	}

	/*
	 * 将汉字转换为Unicode Converts unicodes to encoded &#92;uxxxx and escapes special
	 * characters with a preceding slash
	 */
	public static String toUnicode(String theString, boolean escapeSpace, boolean escapeUnicode) {
		int len = theString.length();
		int bufLen = len * 2;
		if (bufLen < 0) {
			bufLen = Integer.MAX_VALUE;
		}
		StringBuffer outBuffer = new StringBuffer(bufLen);

		for (int x = 0; x < len; x++) {
			char aChar = theString.charAt(x);
			// Handle common case first, selecting largest block that
			// avoids the specials below
			if ((aChar > 61) && (aChar < 127)) {
				if (aChar == '\\') {
					outBuffer.append('\\');
					outBuffer.append('\\');
					continue;
				}
				outBuffer.append(aChar);
				continue;
			}
			switch (aChar) {
			case ' ':
				if (x == 0 || escapeSpace)
					outBuffer.append('\\');
				outBuffer.append(' ');
				break;
			case '\t':
				outBuffer.append('\\');
				outBuffer.append('t');
				break;
			case '\n':
				outBuffer.append('\\');
				outBuffer.append('n');
				break;
			case '\r':
				outBuffer.append('\\');
				outBuffer.append('r');
				break;
			case '\f':
				outBuffer.append('\\');
				outBuffer.append('f');
				break;
			case '=': // Fall through
			case ':': // Fall through
			case '#': // Fall through
			case '!':
				outBuffer.append('\\');
				outBuffer.append(aChar);
				break;
			default:
				if (((aChar < 0x0020) || (aChar > 0x007e)) & escapeUnicode) {
					outBuffer.append('\\');
					outBuffer.append('u');
					outBuffer.append(toHex((aChar >> 12) & 0xF));
					outBuffer.append(toHex((aChar >> 8) & 0xF));
					outBuffer.append(toHex((aChar >> 4) & 0xF));
					outBuffer.append(toHex(aChar & 0xF));
				} else {
					outBuffer.append(aChar);
				}
			}
		}
		return outBuffer.toString();
	}

	/**
	 * Convert a nibble to a hex character
	 * 
	 * @param nibble
	 *            the nibble to convert.
	 */
	private static char toHex(int nibble) {
		return hexDigit[(nibble & 0xF)];
	}

	/** A table of hex digits */
	private static final char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	public static void main(String[] ars) {
		System.out.print(toUnicode("人  生\u4EBA", false, true));
	}

	/**
	 * 获取本机所有IP
	 */
	public static String[] getAllLocalHostIP() {
		List<String> res = new ArrayList<String>();
		Enumeration netInterfaces;
		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
				Enumeration nii = ni.getInetAddresses();
				while (nii.hasMoreElements()) {
					ip = (InetAddress) nii.nextElement();
					if (ip.getHostAddress().indexOf(":") == -1) {
						res.add(ip.getHostAddress());
						// System.out.println("本机的ip=" + ip.getHostAddress());
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return (String[]) res.toArray(new String[0]);
	}

	/**
	 * 取最后一个IP
	 * 
	 * @return
	 * @create_time 2011-3-17 下午05:41:09
	 */
	public static String getLocalLastIp() {
		String ip[] = getAllLocalHostIP();
		return ip[ip.length - 1];
	}

	/**
	 * 把形如 abc_cbd的字符串转换成形如abcCbd的字符串
	 * 
	 * @param sourceStr
	 * @return
	 * @create_time 2012-9-13 下午11:26:47
	 */
	public static String changeDbcloumStyleToJavaStyle(String sourceStr) {
		if (null == sourceStr) {
			return null;
		} else {
			sourceStr = sourceStr.trim();
		}
		if (sourceStr.length() == 0) {
			return sourceStr;
		}
		if (sourceStr.indexOf("_") < 0) {
			return sourceStr.toLowerCase();
		}
		StringBuilder sb = new StringBuilder();
		String[] partsOfStr = sourceStr.split("_");
		for (int i = 0; i < partsOfStr.length; i++) {
			if (i == 0) {
				sb.append(partsOfStr[i].toLowerCase());
			} else {
				sb.append(changeThefirstCharToUppercase(partsOfStr[i]));
			}
		}
		return sb.toString();
	}

	/**
	 * 把字符串首字母变成大写，去掉两边空格
	 * <ol>
	 * <li>2aadaAwc=2aadaAwc</li>
	 * <li>aadaAwc=AadaAwc</li>
	 * <li>AadaAwc=AadaAwc</li>
	 * </ol>
	 * 
	 * @param sourceStr
	 * @return
	 * @create_time 2012-9-13 下午11:22:39
	 */
	public static String changeThefirstCharToUppercase(String sourceStr) {
		if (null == sourceStr) {
			return null;
		} else {
			sourceStr = sourceStr.trim();
		}
		if (sourceStr.length() == 0) {
			return sourceStr;
		}
		if (sourceStr.length() == 1) {
			return sourceStr.toUpperCase();
		}
		StringBuilder sb = new StringBuilder();
		char[] charsOfStr = sourceStr.toCharArray();
		for (int i = 0; i < charsOfStr.length; i++) {
			char c = charsOfStr[i];
			if (i == 0) {
				if (c >= 'a' && c <= 'z') {
					c -= 32;
				}
			} else {
				if (c >= 'A' && c <= 'Z') {
					c += 32;
				}
			}
			sb.append(c);
		}
		return sb.toString();
	}

	/**
	 * 格式化字符串 s->s; ss->s*; ss..s->s*s;
	 * 
	 * @param str
	 * @return
	 * @create_time 2012-12-21 下午4:16:38
	 */
	public static String formatStr(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}
		if (str.length() == 1) {
			return str;
		}
		if (str.length() == 2) {
			return str.charAt(0) + "*";
		}
		return str.substring(0, 1) + "*" + str.substring(str.length() - 1, str.length());
	}

	public static String translateNumberToChinese(String number) {
		if (number == null || "".equals(number) || !StringUtils.isNumeric(number)) {
			return "";
		}

		Integer temp = Integer.parseInt(number);
		switch (temp) {
		case 1:
			return "一";
		case 2:
			return "二";
		case 3:
			return "三";
		case 4:
			return "四";
		case 5:
			return "五";
		case 6:
			return "六";
		case 7:
			return "七";
		case 8:
			return "八";
		case 9:
			return "九";
		case 10:
			return "十";
		case 11:
			return "十一";
		case 12:
			return "十二";
		case 13:
			return "十三";
		case 14:
			return "十四";
		case 15:
			return "十五";
		case 16:
			return "十六";
		case 17:
			return "十七";
		default:
			return "无";
		}
	}

	public static String getRandomPassword() {
		StringBuffer password = new StringBuffer();

		Random random = new Random();
		// 生成6个整数
		for (int i = 0; i < 6; i++) {
			password.append(random.nextInt(10));
		}

		int max = 122;
		int min = 97;
		// 生成两个字母
		for (int i = 0; i < 2; i++) {
			char c = (char) (random.nextInt(max) % (max - min + 1) + min);
			password.append(c);
		}

		return password.toString();
	}

	public static boolean nullOrBlank(String param) {
		return (param == null || param.trim().equals("")) ? true : false;
	}

	public static String notNull(String param) {
		return param == null ? "" : param.trim();
	}

	public static boolean isEmpty(String s) {
		return s == null || "".equals(s);
	}

	public static boolean isBlank(String s) {
		return s == null || "".equals(s.trim());
	}
	/**
	 * 格式数字长度，不足formatLength的前面补0
	 * 长度大于formatLength的直接返回，不进行格式化
	 * @param number
	 * @param formatLength
	 * @return
	 * @create_time 2016年8月6日 
	 */
	public static String formatNumberLen(Long number,Integer formatLength){
		if (String.valueOf(number).length() > formatLength) {
			return String.valueOf(number);
		}
		StringBuffer strB = new StringBuffer();
		for (int i = 0; i < formatLength; i++) {
			strB.append("0");
		}
		DecimalFormat df1 = new DecimalFormat(strB.toString());
		return df1.format(number);
	}
	public static String trimToNull(String value)
	{
		return StringUtils.trimToNull(value);
	}
	//拼装in sql
	public static String sqlInutil(String tids,int num){
		String[] strArray = tids.split(",");
		String tidssql = "in(";
		
		for(int j = 0;j<(strArray.length/num)+1;j++){
			
			for(int i = (j*num) ;i<(j+1)*num;i++){
				
				if(i>=strArray.length){
					break;
				}else{
					
					if(i == ((j+1)*num-1) || i== (strArray.length-1) ){
						tidssql += strArray[i]+")";
					}else{
						tidssql += strArray[i]+",";
					}
				}
			}
			if(j < (strArray.length/num)){
				tidssql += " or in(";
			}
		}
		
		return tidssql;
	}
	   //拼装in sql
		public static String sqlutil(List<Long> list,int num){
			
			String tidssql = "in(";
			
			for(int j = 0;j<(list.size()/num)+1;j++){
				
				for(int i = (j*num) ;i<(j+1)*num;i++){
					
					if(i>=list.size()){
						break;
					}else{
						
						if(i == ((j+1)*num-1) || i== (list.size()-1) ){
							tidssql += list.get(i)+")";
						}else{
							tidssql += list.get(i)+",";
						}
					}
				}
				if(j < (list.size()/num)){
					tidssql += " or in(";
				}
			}
			
			return tidssql;
		}
		
		/**
		 * 对象为空则返回null，否则返回对象toString()
		 * @param object
		 * @return
		 */
		public static String parseObject(Object object){
			return object==null?"":object.toString();
		}
}
