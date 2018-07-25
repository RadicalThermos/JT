package com.jt.manage.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

public class UploadUtil {
	/**
	 * 按比例缩放图片
	 * @param img 原始图片
	 * @param width 缩放后的宽度
	 * @param isScale 是否是按照比例缩放
	 *        true:按实际比例缩放
	 *        false:按1:1比例缩放
	 * @return  缩放后的BufferedImage的对象
	 */
	private static BufferedImage resizeByWidth(BufferedImage img,int width,boolean isScale){
		BufferedImage saveImage=null;
		if(isScale){
			int height=(int)((float)img.getHeight()/img.getWidth()*width);
			saveImage=new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D g=saveImage.createGraphics();
			g.drawImage(img, 0, 0, width, height, null);
		}else{
			//1:1
			int height=width;
			saveImage=new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D g=saveImage.createGraphics();
			g.drawImage(img, 0, 0, width, height, null);
		}
		return saveImage;
	}
	/**
	 * 上传图片的工具方法
	 * @param picture 要上传的图片对象MultipartFile
	 * @param uuid  上传后图片的名称
	 * @param isScale 是否是按照比例缩放
	 *        true:按实际比例缩放
	 *        false:按1:1比例缩放
	 * @param width 缩放后的宽度
	 * @param path 缩放后的存储路径
	 * @return Boolean
	 *         true:上传成功
	 *         false:上传失败
	 */
	public static boolean uploadImage(MultipartFile picture,String uuid,boolean isScale,int width,String path){
		boolean flag=false;
		try {
			InputStream stream=picture.getInputStream();
			//转换输入流对象为BufferedImage对象
			BufferedImage img=ImageIO.read(stream);
			//按照原始图片比例缩放,加水印
			img=resizeByWidth(img,width,isScale);
			Graphics2D g=(Graphics2D)img.getGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(new Color(0x000000));
			g.drawString("JT", 50, 50);
			
			String originalFileName=picture.getOriginalFilename();
			String originalExtendName=originalFileName.substring(originalFileName.lastIndexOf(".")+1);
			String imageFileName=uuid+"."+originalExtendName;
			
			File realPath=new File(path);
			if(!realPath.exists()){
				realPath.mkdir();
			}
			
			//写出文件
			ImageIO.write(img, originalExtendName, new File(realPath,imageFileName));
			flag=true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return flag;
	}
}
