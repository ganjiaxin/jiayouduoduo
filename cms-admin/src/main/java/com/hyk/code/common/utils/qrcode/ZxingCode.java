package com.hyk.code.common.utils.qrcode;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

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
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.hyk.code.common.utils.SystemPath;
import com.hyk.code.common.utils.wechatUtils;

public class ZxingCode {
	
	private static final int BLACK = 0xff000000;
	private static final int WHITE = 0xFFFFFFFF;
	/**
	 * 生成QRCode二维码
	 */
	public String encode(Entity_Zxing zxing){
		String url="";
		try {
			Hashtable<EncodeHintType,Object> hints = new Hashtable<EncodeHintType,Object>();
			/*设置纠错级别(L 7%~M 15%~Q 25%~H 30%),纠错级别越高存储的信息越少*/
			hints.put(EncodeHintType.ERROR_CORRECTION, zxing.getErrorCorrectionLevel());
			/*设置编码格式*/
			hints.put(EncodeHintType.CHARACTER_SET, zxing.getCharacterSet());
			/*设置边缘空白*/
			hints.put(EncodeHintType.MARGIN, zxing.getMargin());
			
			BitMatrix bitMatrix = new MultiFormatWriter().encode(zxing.getContents(),BarcodeFormat.QR_CODE,zxing.getWidth(),zxing.getHeight(),hints);
			
			File isFile = new File(zxing.getPath());
			if(!isFile.exists()){
				isFile.mkdirs();
			}
			
			String fileName =zxing.getFilename() + "." + zxing.getFormat();
			url = zxing.getPath() + fileName;
			
			writeToFile(bitMatrix,zxing.getFormat(),new File(url),zxing.isFlag(),zxing.getLogoPath());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}
	/**
	 * 生成二维码图片
	 * @param bitMatrix	
	 * @param format 图片格式
	 * @param file	生成二维码图片位置
	 * @param isLogo 是否要加logo图
	 * @param logoPath logo图片地址
	 * @throws IOException 
	 */
	public static void writeToFile(BitMatrix bitMatrix,String format,File file,boolean isLogo,String logoPath) throws IOException{
		BufferedImage bi = toBufferedImageContents(bitMatrix);
		if(isLogo){
			int width_4 = bitMatrix.getWidth() / 4;
			int width_8 = width_4 / 2;
			int height_4 = bitMatrix.getHeight() / 4;
			int height_8 = height_4 / 2;
			/*返回由指定矩形区域定义的子图像*/
			BufferedImage bi2 = bi.getSubimage(width_4 + width_8, height_4 + height_8, width_4, height_4);
			/*获取一个绘图工具笔*/
			Graphics2D g2 = bi2.createGraphics();
			/*读取logo图片信息*/
			Image img = ImageIO.read(new File(logoPath));//实例化一个Image对象。
			/*当前图片的宽与高*/
			int currentImgWidth = img.getWidth(null);
			int currentImgHeight = img.getHeight(null);
			/*处理图片的宽与高*/
			int resultImgWidth = 0;
			int resultImgHeight = 0;
			if(currentImgWidth != width_4){
				resultImgWidth = width_4;
			}
			if(currentImgHeight != width_4){
				resultImgHeight = width_4;
			}
	        /*绘制图片*/
			g2.drawImage(img,0,0, resultImgWidth,resultImgHeight,null);
	        g2.dispose();  
	        bi.flush();
		}
		ImageIO.write(bi, format, file);
	}
	/**
	 * 生成二维码内容
	 * @param bitMatrix
	 * @return
	 */
	public static BufferedImage toBufferedImageContents(BitMatrix bitMatrix){
		int width = bitMatrix.getWidth();
		int height = bitMatrix.getHeight();
		BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		for(int x=0;x<width;x++){
			for(int y=0;y<height;y++){
				image.setRGB(x, y, bitMatrix.get(x, y) == true ? BLACK : WHITE);
			}
		}
		return image;
	}
	/**
	 * 解析二维码
	 * @param path 图片的绝对路径
	 */
	public void decode(String path){
		try{
			if(path == null || path.equals("")) {
				System.out.println("文件路径不能为空!");
			}
		    File file = new File(path);
		    BufferedImage image = ImageIO.read(file);
		    /*判断是否是图片*/
		    if (image == null) { 
		    	System.out.println("Could not decode image"); 
		    }
		    /*解析二维码用到的辅助类*/
		    LuminanceSource source = new BufferedImageLuminanceSource(image); 
		    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source)); 
		    Hashtable<DecodeHintType,Object> hints= new Hashtable<DecodeHintType,Object>(); 
		    /*解码设置编码方式为：UTF-8*/
		    hints.put(DecodeHintType.CHARACTER_SET, "UTF-8"); 
		    
		    Result result = new MultiFormatReader().decode(bitmap,hints); 
		    String resultStr = result.getText(); 
		    System.out.println("解析后内容："+resultStr);
		}catch(Exception ex){
		    System.out.println("不能解析该二维码");
		}
	}
	
	public static String createQR(String path,String logoPath,String filename) {
		Entity_Zxing zxing = new Entity_Zxing();
		zxing.setContents(wechatUtils.wxSaler_url+filename);
		zxing.setCharacterSet("UTF-8");
		zxing.setErrorCorrectionLevel(ErrorCorrectionLevel.H);
		zxing.setFlag(true);
		zxing.setFormat("png");
		zxing.setMargin(0);
		zxing.setWidth(300);
		zxing.setHeight(300);
		zxing.setPath(path);
		zxing.setLogoPath(logoPath);
		zxing.setFilename(filename);
		ZxingCode code = new ZxingCode();
		String codeUrl=code.encode(zxing);
		System.out.println("生成二维码成功："+codeUrl);
		return codeUrl;
	}
	
	public static void main(String[] args) throws Exception {
		Entity_Zxing zxing = new Entity_Zxing();
		zxing.setContents(wechatUtils.wxSaler_url+"");
		zxing.setCharacterSet("UTF-8");
		zxing.setErrorCorrectionLevel(ErrorCorrectionLevel.H);
		zxing.setFlag(true);
		zxing.setFormat("png");
		zxing.setMargin(0);
		zxing.setWidth(300);
		zxing.setHeight(300);
		zxing.setPath(SystemPath.getSysPath()+"/userfiles/qrcode/");
		zxing.setLogoPath("d:/qrcode/1.png");
		zxing.setFilename("000000");
		ZxingCode code = new ZxingCode();
		String codeUrl=code.encode(zxing);
		System.out.println("生成二维码成功："+codeUrl);
		String path = "f:/qrcode/20130827090659.png";
		//code.decode(path);
	}
}
