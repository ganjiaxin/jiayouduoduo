package com.hyk.code.common.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
/**
 * 条形码和二维码编码解码
 * 
 * @author wyw
 * @version 2014-02-28
 */
public class ZxingHandler {

	/**
	 * 条形码编码
	 * 
	 * @param contents
	 * @param width
	 * @param height
	 * @param imgPath
	 */
	public static void encode(String contents, int width, int height, String imgPath) {
		int codeWidth = 3 + // start guard
				(7 * 6) + // left bars
				5 + // middle guard
				(7 * 6) + // right bars
				3; // end guard
		codeWidth = Math.max(codeWidth, width);
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,
					BarcodeFormat.EAN_13, codeWidth, height, null);

			MatrixToImageWriter
					.writeToFile(bitMatrix, "png", new File(imgPath));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 条形码解码
	 * 
	 * @param imgPath
	 * @return String
	 */
	public static String decode(String imgPath) {
		BufferedImage image = null;
		Result result = null;
		try {
			image = ImageIO.read(new File(imgPath));
			if (image == null) {
				System.out.println("the decode image may be not exit.");
			}
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

			result = new MultiFormatReader().decode(bitmap, null);
			return result.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 二维码编码
	 * 
	 * @param contents
	 * @param width
	 * @param height
	 * @param imgPath
	 */
	public static void encode2(String contents, int width, int height, String imgPath) {
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		// 指定纠错等级
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		// 指定编码格式
		hints.put(EncodeHintType.CHARACTER_SET, "GBK");
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,BarcodeFormat.QR_CODE, width, height, hints);
			File realPathDirectory = new File(imgPath);
	           if (realPathDirectory == null || !realPathDirectory.exists()) {
	               realPathDirectory.mkdirs();
	           }
			MatrixToImageWriter.writeToFile(bitMatrix, "png", new File(imgPath));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 二维码解码
	 * 
	 * @param imgPath
	 * @return String
	 */
	public static String decode2(String imgPath) {
		BufferedImage image = null;
		Result result = null;
		try {
			image = ImageIO.read(new File(imgPath));
			if (image == null) {
				System.out.println("the decode image may be not exit.");
			}
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

			Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
			hints.put(DecodeHintType.CHARACTER_SET, "GBK");

			result = new MultiFormatReader().decode(bitmap, hints);
			return result.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param args
	 */
	public static void main1(String[] args) {

		/*// 条形码
		String imgPath = "target\\zxing_EAN13.png";
		String contents = "6923450657713";
		int width = 105, height = 50;
		
		ZxingHandler.encode(contents, width, height, imgPath);
		System.out.println("finished zxing EAN-13 encode.");

		String decodeContent = ZxingHandler.decode(imgPath);
		System.out.println("解码内容如下：" + decodeContent);
		System.out.println("finished zxing EAN-13 decode.");
		
		// 二维码
		String imgPath2 = "target\\zxing.png";
		String contents2 = "Hello Gem, welcome to Zxing!"
				+ "\nBlog [ http://thinkgem.iteye.com ]"
				+ "\nEMail [ thinkgem@163.com ]";
		int width2 = 300, height2 = 300;

		ZxingHandler.encode2(contents2, width2, height2, imgPath2);
		System.out.println("finished zxing encode.");

		String decodeContent2 = ZxingHandler.decode2(imgPath2);
		System.out.println("解码内容如下：" + decodeContent2);
		System.out.println("finished zxing decode.");
		
		String imgPath3 = SystemPath.getSysPath()+"/userfiles/qrcode/"+"111111"+".png";
		String contents3= wechatUtils.wxSaler_url+"111111";
		int width3 = 300, height3 = 300;
		ZxingHandler.encode2(contents3, width3, height3, imgPath3);*/
		
		
		String url=ZxingHandler.decode2("D:\\wx.png");
		
		System.out.println(url);
		
	}

	public static String getUUID() {
		UUID uuid =UUID.randomUUID();
		String str = uuid.toString();
		// 去掉"-"符号
		String temp = str.substring(0, 8) +str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) +str.substring(24);
		return temp;
	}

	/**
	 * 生成订单号
	 * @return
	 */
	public static String getOrderStr(String userId) {
		String str=System.currentTimeMillis() / 1000+""+userId;
		return str;
	}

	/**
	 * 生成n位验证码
	 * @return
	 */
	public static String getVerificationCode(Integer n) {
		String str="0123456789";
		Random r=new Random();
		String arr[]=new String [n];
		String b="";
		for(int i=0;i<n;i++)
		{
			int num=r.nextInt(10);

			arr[i]=str.substring(num,num+1);
			b+=arr[i];

		}

		System.out.println("验证码："+b);
		return b;
	}


	public static void main(String[] args) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try
		{
			Date d1 = df.parse("2004-03-26 14:00:20");
			Date d2 = df.parse("2004-03-26 14:00:00");
			long diff =d2.getTime()- d1.getTime() ;//这样得到的差值是微秒级别
			long days = diff / (1000 * 60 * 60 * 24);

			long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
			long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
			System.out.println(""+days+"天"+hours+"小时"+minutes+"分");
		}catch (Exception e)
		{
		}
	}
}