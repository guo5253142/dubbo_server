package com.my.common.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtils {

    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);
    /**代理商**/
    public static final String related_agent = "agent";
    /**商户**/
    public static final String related_retailer = "retailer";
    /**门店**/
    public static final String related_store = "store";
    /**店员/销售**/
    public static final String  related_sales = "sales";
    /**销售管理**/
    public static final String  related_salesmanager = "salesmanager";
    /**客户**/
    public static final String  related_customer = "customer";
    /**申请单**/
    public static final String  related_application = "application";
    /**第三方数据源**/
    public static final String  related_thirdParty = "thirdParty";
    /**代理商，商户合同协议**/
    public static final String  related_chagreement = "chagreement";
    /**上上签**/
    public static final String  related_ssq = "ssq";
    /**保证金收据**/
    public static final String  ch_Deposit_Receipt = "chDepositReceipt";
    /**商户合作协议源文件**/
    public static final String  CH_RETAILER_AGREEMENT = "chRetailerAgreement";
    /**商户合作协议条形码文件**/
    public static final String  CH_RETAILER_AGREEMENT_BAR = "chRetailerAgreementBar";
    
    /**
     * 构建文件路径
     * @param relatedObj
     * 关联对象 代理商，商户，门店，销售经理，店员
     * @param souceFileName
     * 文件名称 如 aa.jpg
     * @return
     * @create_time 2016年7月14日
     */
    public static String buildFilePath(String relatedObj,String souceFileName){
    	StringBuilder sBuilder = new StringBuilder();
    	if (StringUtils.isBlank(relatedObj)) {
    		relatedObj = "00000";
		}
    	Calendar calendar = Calendar.getInstance();
    	sBuilder.append(relatedObj);
    	sBuilder.append(File.separator);
    	sBuilder.append(calendar.get(Calendar.YEAR));
    	sBuilder.append(File.separator);
    	sBuilder.append(calendar.get(Calendar.MONTH)+1);
    	sBuilder.append(File.separator);
    	sBuilder.append(calendar.get(Calendar.DATE));
    	sBuilder.append(File.separator);
    	if (souceFileName.indexOf(".") == -1) {
    		souceFileName = souceFileName +".jpg";
		}
    	sBuilder.append(souceFileName.replaceAll("(.+)(?=\\..*)", UUID.randomUUID().toString()));
    	return sBuilder.toString();
    }
    
    /**
     * 构建文件路径
     * @param relatedObj
     * 关联对象 代理商，商户，门店，销售经理，店员
     * @param souceFileName
     * 文件名称 如 aa.jpg
     * @return
     * @create_time 2016年7月14日
     */
    public static String buildFilePath2(String relatedObj,String souceFileName){
    	StringBuilder sBuilder = new StringBuilder();
    	if (StringUtils.isBlank(relatedObj)) {
    		relatedObj = "00000";
		}
    	Calendar calendar = Calendar.getInstance();
    	sBuilder.append(relatedObj);
    	sBuilder.append(File.separator);
    	sBuilder.append(calendar.get(Calendar.YEAR));
    	sBuilder.append(File.separator);
    	sBuilder.append(calendar.get(Calendar.MONTH)+1);
    	sBuilder.append(File.separator);
    	sBuilder.append(calendar.get(Calendar.DATE));
    	sBuilder.append(File.separator);
    	sBuilder.append(souceFileName);
    	return sBuilder.toString();
    }
    
    /**
     * 文件上传
     * @param is 
     * 文件输入流
     * @param filePath
     * 前置路径 + FileUtils.buildFilePath方法生成
     * @return
     * @create_time 2016年7月14日 
     */
    public static boolean uploadFile(InputStream is, String filePath) {
		boolean retCode = false;
		byte[] buffer = new byte[1024];
		FileOutputStream fos = null;
		try {
			File file = new File(filePath.substring(0,filePath.lastIndexOf(File.separator)));
			if (!file.exists()) {
				file.mkdirs();
			}
			fos = new FileOutputStream(new File(filePath));
			int n = -1;
			while ((n = is.read(buffer, 0, buffer.length)) != -1) {
				fos.write(buffer, 0, n);
			}
			fos.flush();
			retCode = true;
			logger.info("文件上传成功：{}",filePath);
		} catch (Exception e) {
			logger.error(String.format("文件上传失败:%s", new Object[]{filePath}),e);
		} finally {
			if (fos != null) {
				try {
					fos.close();
					fos = null;
				} catch (IOException e) {
					logger.error(String.format("文件上传关闭流失败:%s", new Object[]{filePath}),e);
				}
			}
			if (is != null) {
				try {
					is.close();
					is = null;
				} catch (IOException e) {
					logger.error(String.format("文件上传关闭流失败:%s", new Object[]{filePath}),e);
				}
			}
		}
		return retCode;
	}
    public static void main(String[] args) {
        System.out.println(buildFilePath(FileUtils.related_sales, "stored"));
        
        File file = new File("d:\\testupload\\00000\\20160714\\ddd");
        if (!file.exists()) {
			file.mkdirs();
		}
    }


}
