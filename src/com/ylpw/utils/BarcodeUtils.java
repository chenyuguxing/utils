package com.ylpw.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

/**
 * User:T.L
 * Date:Nov 2, 2011
 * Time:5:36:56 PM
 * Class Description:一维/二维码生成工具类
 */
public class BarcodeUtils {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BarcodeUtils.class);
	
	
	/**
	 * 
	 * User:T.L
	 * Description:
	 * @param code 需要显示的字体串.
	 * @param showTxt 是否显示条码文字
	 * @param width 宽(默认150)
	 * @param height 高(默认15)
	 * @param dpi 生成图片像素(默认300)
	 * @param wideFactor 条形码间距(默认3)
	 * @param isQuietZone 是否在条形码两边加空白(默认false)
	 * @param quietZone 条形码空白像素(默认为3,在isQuietZone为true情况下)
	 * @param out 流
	 * 
	 */
	private static byte[] Code39BeanMain(String code,boolean showTxt,int width,int height,int dpi,int wideFactor,
			boolean isQuietZone,int quietZone,ByteArrayOutputStream byteArrayOutputStream) {
		width = 0 >= width ? 150:width;//宽默认150
		height = 0 >= height ? 15:height;//高默认15
		wideFactor = 0 >= wideFactor ? 3 : wideFactor;//条形码间距默认3
		quietZone = 0 >= quietZone?3:quietZone;
		dpi = 0>=dpi? 300 : dpi;
		if(null != byteArrayOutputStream){
			try {
				Code39Bean bean = new Code39Bean();
				bean.setModuleWidth(UnitConv.in2mm(1.0f / width));//宽
				bean.setBarHeight(height);//高
				bean.setWideFactor(wideFactor);//条码间距
				bean.doQuietZone(isQuietZone);//是否留两边空白
				bean.setQuietZone(quietZone);//如果是留空白,留两边空白的像素
				
				//是否显示文字信息
				if(!showTxt){
					bean.setFontSize(0);
				}
				
				try {
					BitmapCanvasProvider canvas = new BitmapCanvasProvider(byteArrayOutputStream,
							"image/jpeg", dpi, BufferedImage.TYPE_BYTE_BINARY,
							false, 0);
	
					bean.generateBarcode(canvas, code);
					canvas.finish();
				} finally {
					byteArrayOutputStream.close();
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return byteArrayOutputStream.toByteArray();
	}
	
	/**
	 * 生成一维码 128 位
	 * @param code 需要显示的字体串.
	 * @param showTxt 是否显示条码文字
	 * @param width 宽(默认150)
	 * @param height 高(默认15)
	 * @param dpi 生成图片像素(默认300)
	 * @param wideFactor 条形码间距(默认3)
	 * @param isQuietZone 是否在条形码两边加空白(默认false)
	 * @param quietZone 条形码空白像素(默认为3,在isQuietZone为true情况下)
	 * @param out 流
	 * @return
	 *
	 */
	private static byte[] Code128BeanMain(String code,boolean showTxt,int width,int height,int dpi,int wideFactor,
			boolean isQuietZone,int quietZone,ByteArrayOutputStream byteArrayOutputStream) {
		width = 0 >= width ? 150:width;//宽默认150
		height = 0 >= height ? 15:height;//高默认15
		wideFactor = 0 >= wideFactor ? 3 : wideFactor;//条形码间距默认3
		quietZone = 0 >= quietZone?3:quietZone;
		dpi = 0>=dpi? 300 : dpi;
		if(null != byteArrayOutputStream){
			try {
				Code128Bean bean = new Code128Bean();
				bean.setModuleWidth(UnitConv.in2mm(1.0f / width));//宽
				bean.setBarHeight(height);//高
//				bean.setWideFactor(wideFactor);//条码间距
				bean.doQuietZone(isQuietZone);//是否留两边空白
				bean.setQuietZone(quietZone);//如果是留空白,留两边空白的像素
				
				//是否显示文字信息
				if(!showTxt){
					bean.setFontSize(0);
				}
				
				try {
					BitmapCanvasProvider canvas = new BitmapCanvasProvider(byteArrayOutputStream,
							"image/jpeg", dpi, BufferedImage.TYPE_BYTE_BINARY,
							false, 0);
					
					bean.generateBarcode(canvas, code);
					canvas.finish();
				} finally {
					byteArrayOutputStream.close();
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return byteArrayOutputStream.toByteArray();
	}
	
	/**
	 * 
	 * User:T.L
	 * Description:获取一维码
	 * @param code 需要显示的字体串.
	 * @param showTxt 是否显示条码文字
	 * @param width 宽(默认150)
	 * @param height 高(默认15)
	 * @param dpi 生成图片像素(默认300)
	 * @param wideFactor 条形码间距(默认3)
	 * @param isQuietZone 是否在条形码两边加空白(默认false)
	 * @param quietZone 条形码空白像素(默认为3,在isQuietZone为true情况下)
	 * @param request
	 * @param response
	 * @throws IOException
	 *
	 */
	public static void getOneCode(String code,boolean showTxt,int width,int height,int dpi,int wideFactor,
			boolean isQuietZone,int quietZone,
			HttpServletRequest request, HttpServletResponse response) {
		byte[] captchaChallengeAsJpeg;
        //输出验证码的流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        
        captchaChallengeAsJpeg = Code39BeanMain(code, showTxt,width, height, dpi,wideFactor, isQuietZone, quietZone, byteArrayOutputStream);
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        ServletOutputStream responseOutputStream;
		try {
			responseOutputStream = response.getOutputStream();
			responseOutputStream.write(captchaChallengeAsJpeg);
			responseOutputStream.flush();
			responseOutputStream.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
        
	}
	
	/**
	 * 生成一维码 128 位
	 * @param code 需要显示的字体串.
	 * @param showTxt 是否显示条码文字
	 * @param width 宽(默认150)
	 * @param height 高(默认15)
	 * @param dpi 生成图片像素(默认300)
	 * @param wideFactor 条形码间距(默认3)
	 * @param isQuietZone 是否在条形码两边加空白(默认false)
	 * @param quietZone 条形码空白像素(默认为3,在isQuietZone为true情况下)
	 * @param out 流
	 * @return
	 *
	 */
	public static void getOneCode_128(String code,boolean showTxt,int width,int height,int dpi,int wideFactor,
			boolean isQuietZone,int quietZone,
			HttpServletRequest request, HttpServletResponse response) {
		byte[] captchaChallengeAsJpeg;
		//输出验证码的流
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		captchaChallengeAsJpeg = Code128BeanMain(code, showTxt,width, height, dpi,wideFactor, isQuietZone, quietZone, byteArrayOutputStream);
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		
		ServletOutputStream responseOutputStream;
		try {
			responseOutputStream = response.getOutputStream();
			responseOutputStream.write(captchaChallengeAsJpeg);
			responseOutputStream.flush();
			responseOutputStream.close();
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
	}
	
	public static byte[] saveFileOneCode_128(String code,boolean showTxt,int width,int height,int dpi,int wideFactor,
			boolean isQuietZone,int quietZone) {
		byte[] captchaChallengeAsJpeg;
		//输出验证码的流
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		captchaChallengeAsJpeg = Code128BeanMain(code, showTxt,width, height, dpi,wideFactor, isQuietZone, quietZone, byteArrayOutputStream);

		return captchaChallengeAsJpeg;
	}
	
	public static void main(String[] args) {
		BarcodeUtils.saveFileOneCode_128("4284914", false, 0, 0,0, 0, false, 0);
	}
}
