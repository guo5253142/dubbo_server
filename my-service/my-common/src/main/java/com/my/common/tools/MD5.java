package com.my.common.tools;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5 {
	
	private static final Charset utf8 = Charset.forName("utf-8");

	public final static String encode1(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 这个方法在key中存在中文时，会造成算出来的结果不正确
	 * @param inStr
	 * @return
	 * @see com.aicaipiao.common.utils.MD5.md5Encode(String) 替代
	 * 如果之前使用这个方法进行签名，可以暂时不用更改，负负得正 不会有问题。
	 * 新方法请不要使用这个算法。
	 */
	@Deprecated
	public static String encode(String inStr) {
		// convert input String to a char[]
		// convert that char[] to byte[]
		// get the md5 digest as byte[]
		// bit-wise AND that byte[] with 0xff
		// divpend "0" to the output StringBuffer to make sure that we don't end
		// up with
		// something like "e21ff" instead of "e201ff"
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];

		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();

		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
	/**
	 * 恒朋MD5加密
	 * @param s
	 * @param encode
	 * @return
	 */
	public final static String HPEncode(String s,String encode) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] strTemp = s.getBytes(encode);
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
	
	public final static String md5Encode(String s) {
		return DigestUtils.md5Hex(s.getBytes(utf8));
	}

	public final static String encode(String s, String encode) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes(encode);
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * MD5标准计算摘要
	 * */
	public static String digest(String inbuf) {
		MessageDigest m;
		try {
	m = MessageDigest.getInstance("MD5");
			m.update(inbuf.getBytes(), 0, inbuf.length());
			return new BigInteger(1, m.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String [] arr){
//	    String str="456014201103189145766020110318172833<body><issueNotify><issue gameName=\"ssl\" number=\"2011031815\" startTime=\"2011-03-18 16:58:00\" stopTime=\"2011-03-18 17:28:00\" status=\"3\" bonusCode=\"\" salesMoney=\"-1.0\" bonusMoney=\"-1.0\"/></issueNotify></body></message>";
//        System.out.println(MD5.HPEncode(str,"GBK"));
        
//		System.out.println(MD5.encode("fq_CUSTOMER-DICT", "utf-8"));
//        System.out.println(MD5.encode("fq_SALES-DICT-CACHE-TIME", "utf-8"));
        
        System.out.println(MD5.encode("Sales-07992c00691411e66ca5ac37336b4bfc", "utf-8"));
        
//        System.out.println(MD5.encode("fq_SUPERVISOR-DICT", "utf-8"));
//        System.out.println(MD5.encode("fq_SUPERVISOR-DICT-CACHE-TIME", "utf-8"));
	}
	
}
