package com.my.common.tools;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Hex;

import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 * DES通用类
 * 
 * @since 2013.01.15
 * @version 1.0.0_1
 * 
 */
public class DES {
	/**
	 * 生成密钥方法
	 * 
	 * @param seed
	 *            密钥种子
	 * @return 密钥 BASE64
	 * @throws Exception
	 */
	public static String key(String seed) throws Exception {
		byte[] seedBase64DecodeBytes = Base64.decode(seed);

		SecureRandom secureRandom = new SecureRandom(seedBase64DecodeBytes);
		KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
		keyGenerator.init(secureRandom);
		SecretKey secretKey = keyGenerator.generateKey();
		byte[] bytes = secretKey.getEncoded();
		String keyBase64EncodeString = Base64.encode(bytes);

		return keyBase64EncodeString;
	}

	/**
	 * 加密方法
	 * 
	 * @param text
	 *            明文
	 * @param key
	 *            密钥 BASE64
	 * @param charset
	 *            字符集
	 * @return 密文
	 * @throws Exception
	 */
	public static String encrypt(String text, String key, String charset)
			throws Exception {
		byte[] keyBase64DecodeBytes = Base64.decode(key);//base64解码key
		DESKeySpec desKeySpec = new DESKeySpec(keyBase64DecodeBytes);//前8个字节做为密钥
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] textBytes = text.getBytes(charset);//明文UTF-8格式
		byte[] bytes = cipher.doFinal(textBytes);//DES加密

		String encryptBase64EncodeString = Base64.encode(bytes);//base64编码

		return encryptBase64EncodeString;
	}
	
	public static String key(){
		 KeyGenerator keyGenerator=null;
		try {
			keyGenerator = KeyGenerator.getInstance("DES");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
         keyGenerator.init(56);// 设置密钥的长度为56位  
         // 生成一个Key  
         SecretKey generateKey = keyGenerator.generateKey();  
         // 转变为字节数组  
         byte[] encoded = generateKey.getEncoded();  
         // 生成密钥字符串  
         String encodeHexString = Hex.encodeHexString(encoded);  
         return encodeHexString;
	}

	
	public static void main(String[] args) {
		try {
			String key=key();
			System.out.println(key);
			System.out.println(encrypt("f", "xBxXFRV2KrzsokwmyyZJ5qQCxJcEW7y8" ,"UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
//		try {
//			byte[] keyBase64DecodeBytes = Base64.decode("xBxXFRV2KrzsokwmyyZJ5qQCxJcEW7y8");
//			String sd = bytesToHexString(keyBase64DecodeBytes);
//			System.out.println(sd);
//			char[] chs = sd.toCharArray();
//			for(char ch : chs){
//				System.out.print((int)ch+",");
//			}
//		} catch (Base64DecodingException e) {
//			e.printStackTrace();
//		}
		
//		String sec = "N64mYYKJlowxXcDqdJsLqFz1EP1fxld0jgGVmKkabOFWxOlMNUXwApcJkn3j6NXM9yTr8gHmocv4O+1O1+Us1VMpW4ZQabf7ex3Pea32J9Uno+mKE9Fby31paUqSZQWDXb8bQ9O4yp9X+/ty7SAKS6wDWiXR1gHrNMTRJaFAYcGko1iONo/AtK0VDN9B6JPg97w0xrbeKx9ybgiJNqzQtf1FeXENYeU6YSiUZOB8rU/qQogJrv/g+ABscR0kKd3n+DvtTtflLNXwIL8whsriLAWpk+4Buo6hvlWu79zpYBXuG9UdYz6nKkuYt5DHIY+cNMTRJaFAYcGtjIYVPO7yaPLK8tF8OJ9SV8iCsYViA6JWDh4wJh1U+AC2XXBh2oGe14w+fCm0IlrkSY2tqMEsS2JfD3PkDbB7gYBG0UdGaxrHB3qJz1+v1o8sQZmwOogrEgo98cM6UpoBfnf+iUroK9rHpq146W5UHKk8XmNxNEI=";
//		String key = "xBxXFRV2KrzsokwmyyZJ5qQCxJcEW7y8";
//		try {
//			System.out.println(decrypt(sec, key , "UTF-8"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	 public static String bytesToHexString(byte[] src) {  
	        StringBuilder stringBuilder = new StringBuilder("");  
	        if (src == null || src.length <= 0) {  
	            return null;  
	        }  
	        for (int i = 0; i < src.length; i++) {  
	            int v = src[i] & 0xFF;  
	            String hv = Integer.toHexString(v);  
	            if (hv.length() < 2) {  
	                stringBuilder.append(0);  
	            }  
	            stringBuilder.append(hv);  
	        }  
	        return stringBuilder.toString();  
    }  
	/**
	 * 解密方法
	 * 
	 * @param text
	 *            密文
	 * @param key
	 *            密钥 BASE64
	 * @param charset
	 *            字符集
	 * @return 明文
	 * @throws Exception
	 */
	public static String decrypt(String text, String key, String charset)
			throws Exception {
		byte[] keyBase64DecodeBytes = Base64.decode(key);

		DESKeySpec desKeySpec = new DESKeySpec(keyBase64DecodeBytes);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] textBytes = Base64.decode(text);
		byte[] bytes = cipher.doFinal(textBytes);

		String decryptString = new String(bytes, charset);

		return decryptString;
	}
}