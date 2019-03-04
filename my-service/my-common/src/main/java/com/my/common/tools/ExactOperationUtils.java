package com.my.common.tools;

import java.math.BigDecimal;

/**
 * 精确运算帮助类
 * 
 * @project sinafenqi-common
 * @author liubo
 * @date 2016年11月25日
 * www.sinafenqi.com.
 */
public class ExactOperationUtils {

	/**
     * 提供精确的加法运算。
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1, double v2){
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.add(b2).doubleValue();
    }
    
    /**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2){
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.subtract(b2).doubleValue();
    }
    
    /**
     * 提供精确的乘法运算。
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的差
     */
    public static double mul(double v1, double v2){
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).doubleValue();
    }
    
    /**
     * 提供精确的除法运算。
     * @param v1 被除数
     * @param v2 除数
     * @param newScale 返回值精确位数
     * @return 两个参数的差
     */
    public static double div(double v1, double v2, int newScale){
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, newScale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    /**
     * 四舍五入精确
     * @param d
     * @param newScale 精确位数
     * @return
     * @create_time 2016年11月25日 下午6:12:30
     */
    public static double round(double d, int newScale) {
        BigDecimal b1 = new BigDecimal(d);
        return b1.setScale(newScale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
