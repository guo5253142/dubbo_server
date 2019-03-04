package com.my.admin.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("serial")
public class RandomImageCode extends HttpServlet {

	private static final Log logger = LogFactory.getLog(RandomImageCode.class);

	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// 设置页面不缓存
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			HttpSession session = request.getSession();
			
			// 在内存中创建图象
			int width = 60, height = 20;
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

			// 获取图形上下文
			Graphics g = image.getGraphics();

			// 生成随机类
			Random random = new Random();

			// 设定背景色
			g.setColor(getRandColor(200, 250));
			g.fillRect(0, 0, width, height);

			// 设定字体
			g.setFont(new Font("Times New Roman", Font.PLAIN, 18));

			// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到

			g.setColor(getRandColor(160, 200));
			for (int i = 0; i < 155; i++) {
				int x = random.nextInt(width);
				int y = random.nextInt(height);
				int xl = random.nextInt(12);
				int yl = random.nextInt(12);
				g.drawLine(x, y, x + xl, y + yl);
			}

			// 取随机产生的认证码(4位数字)
			String sRand = "";
			for (int i = 0; i < 4; i++) {
//				String rand = String.valueOf(random.nextInt(10));
				String rand =randomCode(1);
				sRand += rand;
				// 将认证码显示到图象中
				g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110),
						20 + random.nextInt(110)));
				// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成

				g.drawString(rand, 13 * i + 6, 16);
			}

			// 将认证码存入SESSION
			session.setAttribute("randomCode", sRand);

			logger.info("验证码：" + sRand);
			// 图象生效
			g.dispose();

			// 输出图象到页面

			ImageIO.write(image, "JPEG", response.getOutputStream());
			image = null;
		} catch (Exception e) {
			logger.error("获取验证码，发生异常：", e);
		}

	}

	Color getRandColor(int fc, int bc) {
		// 给定范围获得随机颜色
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
	
	
	/**
     * 获得一个随机的数字+字母的字符串
     * @param length(随机字符长度)
     * @return
     * @create_time 2011-7-28 下午05:32:22
     */
    public static String randomCode(int length) {
        StringBuffer stB = new StringBuffer();
        Random random = new Random();
        int r = 0;
        for (int i = 0; i < length; i++) {
            r = random.nextInt(2);
            if (r == 0) {//数字
                stB.append(randomChar());
            } else if (r == 1) {//字母
                stB.append(randomInt(10));
            }
        }
        return stB.toString();
    }

    /**
     * 获得一个随机数字
     * @param max
     * @return
     * @create_time 2011-7-28 下午05:41:35
     */
    public static int randomInt(int max) {
        Random random = new Random();
        return random.nextInt(max);
    }

    /**
     * 获得一个随机字母
     * @return
     * @create_time 2011-7-28 下午05:41:54
     */
    public static char randomChar() {
        Random random = new Random();
        int r = 0;
        char temp;
        while (true) {
            r = random.nextInt(123);
          if (r >= 65 && r <= 90) {//大写字母
                break;
            } else if (r >= 97 && r <= 122) {//小写字母
                break;
            }
        }
        temp = (char) r;
        return temp;
    }
}
