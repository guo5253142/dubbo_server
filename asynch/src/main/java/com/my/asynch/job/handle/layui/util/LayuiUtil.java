package com.my.asynch.job.handle.layui.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


/**
 * layui自动签到
 * @author guopeng1
 *
 */
public class LayuiUtil {

	public  CloseableHttpClient httpClient = HttpClients.custom().build();
	private final Logger logger = LoggerFactory.getLogger(getClass());
	/*private static String DATA_SWAP="D:\\code\\result.txt";
	private static String imgCodePath="D:\\img\\code.png";
	private static String pyUrl="python D:\\gp\\damo\\damo.py";*/
	private  String DATA_SWAP="/data/layuiSignIn/result.txt";//识别之后的验证码保存地址
	private  String imgCodePath="/data/layuiSignIn/code.png";//下载下来的验证码图片
	private  String pyUrl="python3 /data/layuiSignIn/damo/damo.py";//解析验证码的python脚本地址
	
	/**
	 * 获取cookie
	 */
	public  void prelogin(){
		try {
			String content=HttpUtils.sendGet(httpClient, "https://fly.layui.com/user/login/");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取验证码
	 */
	public  String getCodeImg(){
		String img="";
		try {
			String url="https://fly.layui.com/auth/imagecode?t="+(new Date()).getTime();
			String content=HttpUtils.sendGet(httpClient, url);
			img=content.substring(content.indexOf("<svg"),content.indexOf("</svg>")+6);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	/**
	 * 登录
	 * @param imagecode
	 * @return
	 */
	public  boolean login(String imagecode){
		
		String url = "https://fly.layui.com/user/login";
		List<NameValuePair> parms = new ArrayList<NameValuePair>();
		parms.add(new BasicNameValuePair("loginName", "18684860817"));
		parms.add(new BasicNameValuePair("pass", "ad5331317"));
		parms.add(new BasicNameValuePair("imagecode", imagecode));
		logger.info("调用登录接口："+JSON.toJSONString(parms));
		try {
			String content = HttpUtils.sendPOST(httpClient, url, parms);
			JSONObject json=(JSONObject)JSON.parse(content);
			logger.info("登录返回结果:"+content);
			if(json.getInteger("status")==0){
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 访问主页
	 */
	public  void vistmain(){
		try {
			String content=HttpUtils.sendGet(httpClient, "https://fly.layui.com/user");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取签到token
	 */
	public  String getSignToken(){
		String token="";
		String url = "https://fly.layui.com/sign/status";
		List<NameValuePair> parms = new ArrayList<NameValuePair>();
		parms.add(new BasicNameValuePair("encoding", "UTF-8"));
		try {
			String content = HttpUtils.httpsPost(url,null);
			logger.info("获取签到token返回结果："+content);
			JSONObject json=(JSONObject)JSON.parse(content);
			JSONObject json2=(JSONObject)json.get("data");
			if(json2.getBoolean("signed")){
				return "";
			}
			token=json2.get("token").toString();
			
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}	
		return token;
	}
	/**
	 * 签到
	 */
	public  void signIn(String token){
		String url = "https://fly.layui.com/sign/in";
		Map<String, String> requestText=new HashMap<>();
		requestText.put("token", token);
		try {
			String content = HttpUtils.httpsPost(url,requestText);
			logger.info("签到返回结果："+content);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	/**
	 * 读取识别后的验证码
	 * @return
	 */
	 public String readAnswer() {
	        BufferedReader br;
	        String answer = null;
	        try {
	            br = new BufferedReader(new FileReader(new File(DATA_SWAP)));
	            answer = br.readLine();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        return answer;
	    }
	 /**
	  * 获取验证码并识别
	  * @throws Exception
	  */
	public  void imgORC() throws Exception{
		String svg=getCodeImg();
		SvgToImgUtl.saveImg(svg,imgCodePath);
		//调用python程序识别验证码
		 Process proc = null;
         proc = Runtime.getRuntime().exec(pyUrl);
         proc.waitFor();
	}
	
	/**
	 * 自动签到主函数
	 * @throws Exception
	 */
	public void autoSignIn() throws Exception{
		try {
			//获取cookie
			logger.info("获取cookie......");
			prelogin();
			logger.info("获取验证码并识别......");
			imgORC();
			
			String imagecode=readAnswer();
			logger.info("读取识别验证码："+imagecode);
			int faileCount=1;
			logger.info("停留0.4秒后登录......");
			Thread.sleep(400);
			boolean checkin=true;
			while(!login(imagecode)){
				logger.info("登录失败"+(faileCount++)+"次");
				if(faileCount>30){
					logger.info("登录失败次数过多，终止本次自动签到！");
					checkin=false;
					break;
				}
				logger.info("重新获取验证码");
				imgORC();
				logger.info("读取识别验证码："+imagecode);
				imagecode=readAnswer();
				logger.info("停留0.4秒后登录......");
				Thread.sleep(400);
				
			}
			if(checkin){
				logger.info("登录成功!");
				//避免被查到是程序自动签到，访问一下主页
				vistmain();
				logger.info("开始获取token......");
				String token=getSignToken();
				if(!"".equals(token)){
					logger.info("停留1秒后签到......");
					Thread.sleep(1000);
					signIn(token);
					logger.info("自动签到成功！");
				}
			}
		} catch (Exception e) {
			logger.error("layui自动签到程序异常",e);
		}finally{
			//清除本次操作记录的cookie，这一步很重要,不然会影响第二次程序执行
			HttpUtils.clearCookies();
		}
		
	}
	
	
	

}
