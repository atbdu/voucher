package com.xk.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;


/**
 *  验证码
 * @author zyy
 *
 */
public class RandomCodeUtil {
	
	/**
	 * 创建静态类
	 * @return
	 */
	public static RandomCodeUtil instance(){
        return new RandomCodeUtil();
    }
	
    /**
     * 初始化imag
     */
	public BufferedImage initImag(HttpServletRequest request){
		//在内存中创建图像
		int width = 120, height = 40;	//设置大小
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		//获取图形上下文
		Graphics g = image.getGraphics();
		
		//生成随机类
		Random random = new Random();
		
		//设定背景色
		g.setColor(getRandomColor(210, 260));
		g.fillRect(0, 0, width, 200);
		
		//设定字体
		//g.setFont(new Font("Time News Roman",Font.ROMAN_BASELINE,17));
		g.setFont(new Font("楷体",Font.BOLD,30));
		
		//随机产生100条干扰线，使图像中的认证码不易被其它程序探测到
		g.setColor(getRandomColor(160, 200));
		for (int i = 0; i < 100; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int x1 = random.nextInt(12);
			int y1 = random.nextInt(12);
			g.drawLine(x, y, x+x1, y+y1);
		}
		
		//取随机产生的认证码
		String sRand = "";
		for (int i = 0; i < 4; i++) {
			String rand = getRandomChar();
			sRand = sRand.concat(rand);
			
			//将认证码显示到图像中
			g.setColor(new Color(20+random.nextInt(110), 20+random.nextInt(110), 20+random.nextInt(110)));
			
			//调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			g.drawString(rand, 13*i+32,30);
		}
		//图文生效
		g.dispose();
		//保存数据信息
		request.getSession().setAttribute("randomCode", sRand);
//		ByteArrayInputStream input = null;
//		ByteArrayOutputStream output = new ByteArrayOutputStream();
//		try {
//			ImageOutputStream imageOut = ImageIO.createImageOutputStream(output);
//			ImageIO.write(image, "JPEG", imageOut);
//			imageOut.close();
//			input = new ByteArrayInputStream(output.toByteArray());
//		} catch (Exception e) {
//		}
       return image;
	}
	
	/**
	 * 获取随机数字或字符
	 * @return
	 */
	public static String getRandomChar () {
		
		int index = (int)Math.round(Math.random() * 2);
		String randChar = "";
		
		switch(index) {
			case 0 :	//大写字符
				randChar = String.valueOf((char)Math.round(Math.random() * 25 + 65));
				break;
			case 1 :	//小写字符
				randChar = String.valueOf((char)Math.round(Math.random() * 25 + 97));
				break;
			default :	//数字
				randChar = String.valueOf(Math.round(Math.random() * 9));
				break;
		}
		return randChar;
	}
	
	/**
	 * 给定范围获得随机色
	 */
	public Color getRandomColor (int fc, int bc) {
		
		Random random = new Random();
		if (fc > 255) fc = 255;
		if ( bc > 255) bc = 255;
		
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		
		return new Color(r, g, b);
	}
	
	
	public static void main(String[] args) {
		 Random ne=new Random();//实例化一个random的对象ne
	        int x =ne.nextInt(9999-1000+1)+1000;//为变量赋随机值1000-9999
	        System.out.println(x);
	}
}
