package com.ylpw.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.ylpw.utils.Constants;
import com.ylpw.utils.FileOperate;

/**
 * 通用的图片上传类
 * User:T.L
 * Date:Oct 24, 2011
 * Time:3:02:03 PM
 * Class Description:
 */
public class UploadImgUtils {
	
	//日志
	private static final Log logger = LogFactory.getLog(UploadImgUtils.class);
	/**
	 * 图片上传
	 * @param request
	 * @param model
	 * @return
	 */
	public static String upimgtest(MultipartHttpServletRequest request ,Model model){
		String picurl = "";
		StringBuffer picUrl=new StringBuffer();
		logger.info("[上传图片测试:] method=upimgtest");
		//model.addAttribute("testCustomers", testCustomers);
		boolean rt = false;
		
		List<MultipartFile> files = request.getFiles("imgfile");
		
		if(null==files || files.size() < 1){
			return "";
		}
		for(int i=0; i<files.size() ;i++){
	        if(!files.get(i).isEmpty()) {
				 String filename = files.get(i).getOriginalFilename();//文件名
				 String hz = filename.substring(filename.lastIndexOf(".")).toLowerCase();//后缀
				 String regx = Constants.propertiesUtils.jsp_getValue("picregx");//后缀正则
				   
				 System.out.println("\r上传文件："+filename);  
				 System.out.println("上传文件后缀："+hz + " ,后缀正则:"+regx);
				 System.out.println(".jpg|.jpeg|.gif|.bmp|.png --:"+hz.matches(regx));
				 System.out.println("文件大小:"+files.get(i).getSize());
				 System.out.println("上传文件类型:"+files.get(i).getContentType()+"\r");
				 if(!hz.matches(regx)){
					 rt = true;
					 model.addAttribute("message", "图片格式只能为jpg,jpeg,gif,bmp,png");
					 break;
				 }
				 
				 //上传图片大小限制
				 int picsize = Integer.valueOf(Constants.propertiesUtils.jsp_getValue("picsize"));
				 
				 if(files.get(i).getSize() > picsize){
				   model.addAttribute("message", "图片大小应小于200K");
				   rt = true;
				   break;
				 }
	        }else{
	        	rt = true;
	        	model.addAttribute("message", "请选择要上传的图片!");
	        	break;
	        }
		}
		//如图片校验没有通过.则返回""
		if(rt){
			return "";
		}
		//通过验证后上传图片到服务器.
        for(int i=0; i<files.size() ;i++){
            if(! files.get(i).isEmpty()) {
            	
            	String filename = files.get(i).getOriginalFilename();//文件名
				filename.substring(filename.lastIndexOf(".")).toLowerCase();//后缀
            	
				//获取项目绝对路径
            	String path = request.getSession().getServletContext().getRealPath("/");
            	
            	//上传图片存储路径(用于数据库记录上传目录第一级目录)
            	String readPath = Constants.propertiesUtils.jsp_getValue("readPath");
				
            	// readPath 后边的目录层次,此级目录可根据需要自行创建名称.
            	String newFile = new SimpleDateFormat("yyMMddHHSSMM").format(new Date());
            	//保存目录(用于无目录时创建目录用)
            	String savePath = path+readPath+newFile;
            	//数据库保存用此完整地址.(filename 文件名称也可以根据需求自定义)
            	picurl = "/"+readPath +  newFile + "/"+ filename;
            	System.out.println("---- 数据库保存用此完整地址.:"+picurl);
            	picUrl.append(picurl).append(",");
            	System.out.print(picUrl);
            	System.out.println("---- 文件上传的全地址为:"+path+picurl);
                try {
                	//新建目录(如保存目录不存在)
                	FileOperate.newFolder(savePath);
                	//写文件到指定地址
                	FileOperate.copyFile(files.get(i).getInputStream(), path+picurl);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
            }
        }
        return picUrl.toString();
	}
	

	
	/**
	 * 图片上传
	 * @param cutImage 是否切割图片
	 * @param type 图片的宽 高 [ 此参数必须添加 ]
	 * @return 返回list的 map 类型 
	 * 其中 Map 的key  ：  x 表示小图片 d 表示大图片
	 */
	public static List<Map<String,String>> uploadFile1(Boolean cutImage,ServletContext sc ,MultipartFile[] ut,Integer... type) throws IOException{
		List<Map<String,String>> listimgpath = new ArrayList<Map<String,String>>();
		Map<String,String> map = new TreeMap<String,String>();
		String savepath = DateUtils.getDate2Str("yyyy/MM/dd",new Date());
		String basepath = sc.getRealPath("/");
		String path = basepath +"upload/"+ savepath;
		
		String postfix = ".jpg";
		
		for(MultipartFile uts : ut){
			String imgName = uts.getOriginalFilename();
			//T.L 文件名当时时间_随机数 2012-10-14
			if(null!=imgName){
				postfix = getPostfix(imgName.toLowerCase());//获取文件后缀
			}
			imgName = new Date().getTime()+"_"+StringUtil.createPass(3)+postfix;
			
			String oldPath = path + "/" + imgName;
			File oldfile = new File(path);
			if(!oldfile.exists()){
				oldfile.mkdirs();
		    }
			File fileOldPath = new File(oldPath);
			uts.transferTo(fileOldPath);
			map.put("d", oldPath.replace(basepath, "/"));
			//上传图片默认转换一次jpg格式进行压缩,T.L 2013-02-04
			map.put("m1", ImageUtil.copyImageToJpg(oldPath, "m1",null).replace(basepath, "/"));
		    if(cutImage){
				String npath = path + "/" + "x/";
				String newPath =  npath + imgName;
				System.out.println(newPath);
				File newfile = new File(npath); 
				if(!newfile.exists()){
			       newfile.mkdirs();
			    }
				map.put("x", ImageUtil.cutImage(oldPath,type[0], type[1]).replace(basepath, "/"));
			}
		    listimgpath.add(map);   
		}
		
		return listimgpath;
	}

	/**
	 * 图片上传
	 * @param cutImage 是否切割图片
	 * @param e 图片的宽 高 [ 此参数必须添加 ]
	 * @return 返回list的 map 类型 
	 * 其中 Map 的key  ：  x 表示小图片 d 表示大图片
	 * 传入  uploadname,uploadFileName true  int[][] img ={{200,300},{93,120}};//
	 * 返回  获得原图 m.get(0).get("d") 获得img中要是的图  m.get(0).get("x0")("x1")以此类推
	 */
	public static List<Map<String,String>> uploadFileNew1(Boolean cutImage,MultipartFile[] ut,ServletContext sc,int[][] type) throws IOException{
		List<Map<String,String>> listimgpath = new ArrayList<Map<String,String>>();
		Map<String,String> map = new TreeMap<String,String>();
		String savepath = DateUtils.getDate2Str("yyyy/MM/dd",new Date());
		String basepath = sc.getRealPath("/");
		String path = basepath +"upload/"+ savepath;
		String postfix = ".jpg";
		for (MultipartFile uts : ut) {
			String imgName = uts.getOriginalFilename();
			
			if(null!=imgName){
				postfix = getPostfix(imgName.toLowerCase());//获取文件后缀
			}
			imgName = new Date().getTime()+"_"+StringUtil.createPass(3)+postfix;
			String oldPath = path + "/" + imgName;
			File oldfile = new File(path);
			if(!oldfile.exists()){
				oldfile.mkdirs();
		    }
			File fileOldPath = new File(oldPath);
			uts.transferTo(fileOldPath);
			map.put("d", oldPath.replace(basepath, "/"));
		    if(cutImage){
				String npath = path + "/" + "AfterTreatment/";
				String newPath =  npath + imgName;
				System.out.println(newPath);
				File newfile = new File(npath); 
				if(!newfile.exists()){
			       newfile.mkdirs();
			    }
				for(int j=0;j<type.length;j++){
					System.out.println(oldPath+"===="+type[j][0]+"===="+type[j][1]+" <<<<<<<<<<<<<<<<<<<<<");
					map.put("x"+j, ImageUtil.cutImageNew(oldPath,type[j][0],type[j][1],j).replace(basepath, "/"));	
				}
			}
		    listimgpath.add(map);   
		}
		return listimgpath;
	}
	
	/**
	 * 图片上传
	 * @param cutImage 是否切割图片
	 * @param e 图片的宽 高 [ 此参数必须添加 ]
	 * @return 返回list的 map 类型 
	 * 其中 Map 的key  ：  x 表示小图片 d 表示大图片
	 * 传入  uploadname,uploadFileName true  int[][] img ={{200,300},{93,120}};//
	 * 返回  获得原图 m.get(0).get("d") 获得img中要是的图  m.get(0).get("x0")("x1")以此类推
	 */
	public static List<Map<String,String>> uploadFileNew1(Boolean cutImage,List<MultipartFile> ut,ServletContext sc,int[][] type) throws IOException{
		List<Map<String,String>> listimgpath = new ArrayList<Map<String,String>>();
		Map<String,String> map = new TreeMap<String,String>();
		String savepath = DateUtils.getDate2Str("yyyy/MM/dd",new Date());
		String basepath = sc.getRealPath("/");
		String path = basepath +"upload/"+ savepath;
		String postfix = ".jpg";
		for (MultipartFile uts : ut) {
			String imgName = uts.getOriginalFilename();
			
			if(null!=imgName){
				postfix = getPostfix(imgName.toLowerCase());//获取文件后缀
			}
			imgName = new Date().getTime()+"_"+StringUtil.createPass(3)+postfix;
			String oldPath = path + "/" + imgName;
			File oldfile = new File(path);
			if(!oldfile.exists()){
				oldfile.mkdirs();
		    }
			File fileOldPath = new File(oldPath);
			uts.transferTo(fileOldPath);
			map.put("d", oldPath.replace(basepath, "/"));
		    if(cutImage){
				String npath = path + "/" + "AfterTreatment/";
				String newPath =  npath + imgName;
				System.out.println(newPath);
				File newfile = new File(npath); 
				if(!newfile.exists()){
			       newfile.mkdirs();
			    }
				for(int j=0;j<type.length;j++){
					System.out.println(oldPath+"===="+type[j][0]+"===="+type[j][1]+" <<<<<<<<<<<<<<<<<<<<<");
					map.put("x"+j, ImageUtil.cutImageNew(oldPath,type[j][0],type[j][1],j).replace(basepath, "/"));	
				}
			}
		    listimgpath.add(map);   
		}
		return listimgpath;
	}
	
	
	
	/**
	 * 图片上传
	 * @param cutImage 是否切割图片
	 * @param type 图片的宽 高 [ 此参数必须添加 ]
	 * @return 返回list的 map 类型 
	 * 其中 Map 的key  ：  x 表示小图片 d 表示大图片
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public static List<Map<String,String>> uploadFileNOimgName(String savepath,MultipartFile[] ut,ServletContext sc,boolean cutImage,String[] uploadFileName,Integer... type) throws IllegalStateException, IOException{
		List<Map<String,String>> listimgpath = new ArrayList<Map<String,String>>();
		Map<String,String> map = new TreeMap<String,String>();
		String basepath = sc.getRealPath("/");
		String path = basepath +"upload/"+ savepath;
		 for(int i=0;i<ut.length;i++ ){
			 if(!StringUtils.isEmpty(ut[i].getOriginalFilename())){
				 String imgname = ImageUtil.generateFileName(uploadFileName[0]);
				 String oldPath = path + "/" + imgname;
				 File oldfile = new File(path);
					if(!oldfile.exists()){
						oldfile.mkdirs();
					}
					File fileOldPath = new File(oldPath);
					ut[i].transferTo(fileOldPath);
					map.put("d", oldPath.replace(basepath, "/"));
					//上传图片默认转换一次jpg格式进行压缩,T.L 2013-02-04
					map.put("m1", ImageUtil.copyImageToJpg(oldPath, "m1",null).replace(basepath, "/"));
				    if(cutImage){
						String npath = path + "/" + "x/";
						String newPath =  npath + imgname;
						System.out.println(newPath);
						File newfile = new File(npath); 
						if(!newfile.exists()){
					       newfile.mkdirs();
					    }
						map.put("x", ImageUtil.cutImage(oldPath,type[0], type[1]).replace(basepath, "/"));
					}
				    listimgpath.add(map);
			 }
	 
			 
		 }
		
		return listimgpath;
	}
	
	
	public static Map<String,String> uploadFile(Boolean cutImage,ServletContext sc ,MultipartFile ut,Integer... type) throws IOException{
		String imgName = ut.getOriginalFilename();
		String postfix = ".jpg";
		if(null!=imgName){
			postfix = getPostfix(imgName.toLowerCase());//获取文件后缀
		}
		imgName = new Date().getTime()+"_"+StringUtil.createPass(3)+postfix;//图片名称
		
		Map<String,String> map = new TreeMap<String,String>();
		String savepath = DateUtils.getDate2Str("yyyy/MM/dd",new Date());
		String basepath = sc.getRealPath("/");
		String path = basepath +"upload/"+ savepath;
		String oldPath = path + "/" + imgName;
		File oldfile = new File(path);
		if(!oldfile.exists()){
			oldfile.mkdirs();
	    }
		File fileOldPath = new File(oldPath);
		ut.transferTo(fileOldPath);
		map.put("d", oldPath.replace(basepath, "/"));
		//上传图片默认转换一次jpg格式进行压缩,T.L 2013-02-04
		map.put("m1", ImageUtil.copyImageToJpg(oldPath, "m1",null).replace(basepath, "/"));
	    if(cutImage){
			String npath = path + "/" + "x/";
			String newPath =  npath + imgName;
			System.out.println(newPath);
			File newfile = new File(npath); 
			if(!newfile.exists()){
		       newfile.mkdirs();
		    }
			map.put("x", ImageUtil.cutImage(oldPath,type[0], type[1]).replace(basepath, "/"));
		}
		return map;
	}
	/**
	 * 
	 * 描述                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
	 * @param cutImage
	 * @param uploadName
	 * @param imgName
	 * @param sc
	 * @param type
	 * @return
	 * @throws IOException 
	 */
	public static Map<String,String> uploadFileNew(Boolean cutImage,MultipartFile ut,ServletContext sc,int[][] type) throws IOException{
		String imgName = ut.getOriginalFilename();
		String postfix = ".jpg";
		if(null!=imgName){
			postfix = getPostfix(imgName.toLowerCase());//获取文件后缀
		}
		imgName = new Date().getTime()+"_"+StringUtil.createPass(3)+postfix;
		
		Map<String,String> map = new TreeMap<String,String>();
		String savepath = DateUtils.getDate2Str("yyyy/MM/dd",new Date());
		String basepath = sc.getRealPath("/");
		String path = basepath +"upload/"+ savepath;
		String oldPath = path + "/" + imgName;
		File oldfile = new File(path);
		if(!oldfile.exists()){
			oldfile.mkdirs();
	    }
		File fileOldPath = new File(oldPath);
		ut.transferTo(fileOldPath);
		map.put("d", oldPath.replace(basepath, "/"));
	    if(cutImage){
			String npath = path + "/" + "AfterTreatment/";
			String newPath =  npath + imgName;
			System.out.println(newPath);
			File newfile = new File(npath); 
			if(!newfile.exists()){
		       newfile.mkdirs();
		    }
			for(int j=0;j<type.length;j++){
				System.out.println(oldPath+"===="+type[j][0]+"===="+type[j][1]+" <<<<<<<<<<<<<<<<<<<<<");
				map.put("x"+j, ImageUtil.cutImageNew(oldPath,type[j][0],type[j][1],j).replace(basepath, "/"));	
			}
		}
		return map;
		
		
	}

	
	public static Map<String,String> uploadFileOther(Boolean cutImage,ServletContext sc ,MultipartFile ut,String foldername,Integer... type) throws IOException{
		String imgName = ut.getOriginalFilename();
		Map<String,String> map = new TreeMap<String,String>();
		String savepath = DateUtils.getDate2Str("yyyy/MM/dd",new Date());
		String basepath = sc.getRealPath("/");
		String postfix = ".jpg";
		if(null!=imgName){
			postfix = getPostfix(imgName.toLowerCase());//获取文件后缀
		}
		imgName = new Date().getTime()+"_"+StringUtil.createPass(3)+postfix;//图片名称
		String path = basepath +"upload/"+foldername+ savepath;
		String oldPath = path + "/" + imgName;
		File oldfile = new File(path);
		if(!oldfile.exists()){
			oldfile.mkdirs();
	    }
		File fileOldPath = new File(oldPath);
		ut.transferTo(fileOldPath);
		map.put("d", oldPath.replace(basepath, "/"));
		//上传图片默认转换一次jpg格式进行压缩,T.L 2013-02-04
		map.put("m1", ImageUtil.copyImageToJpg(oldPath, "m1",null).replace(basepath, "/"));
	    if(cutImage){
			String npath = path + "/" + "x/";
			String newPath =  npath + imgName;
			System.out.println(newPath);
			File newfile = new File(npath); 
			if(!newfile.exists()){
		       newfile.mkdirs();
		    }
			map.put("x", ImageUtil.cutImage(oldPath,type[0], type[1]).replace(basepath, "/"));
		}
		return map;
	}
	
	/**巡演一览上传图片用*/
	public static Map<String,String> uploadFileTour(Boolean cutImage,ServletContext sc ,MultipartFile ut,String foldername,String pathImgName,Integer... type) throws IOException{
//		String imgName = ut.getOriginalFilename();
//		Map<String,String> map = new TreeMap<String,String>();
//		String basepath = sc.getRealPath("/");
//		imgName = pathImgName;//图片名称
//		String path = basepath +"upload/"+foldername;
//		String oldPath = path + "/" + imgName;
//		File oldfile = new File(path);
		//T.L 全国巡演图片改为非固定存储方式 2013-08-05
		String imgName = ut.getOriginalFilename();
		Map<String,String> map = new TreeMap<String,String>();
		String savepath = DateUtils.getDate2Str("yyyy/MM/dd",new Date());
		String basepath = sc.getRealPath("/");
		String postfix = ".jpg";
		if(null!=imgName){
			postfix = getPostfix(imgName.toLowerCase());//获取文件后缀
		}
		imgName = new Date().getTime()+"_"+StringUtil.createPass(3)+postfix;//图片名称
		String path = basepath +"upload/"+foldername+ savepath;
		String oldPath = path + "/" + imgName;
		File oldfile = new File(path);
		if(!oldfile.exists()){
			oldfile.mkdirs();
	    }
		File fileOldPath = new File(oldPath);
		ut.transferTo(fileOldPath);
		map.put("d", oldPath.replace(basepath, "/"));
		//上传图片默认转换一次jpg格式进行压缩,T.L 2013-02-04
		map.put("m1", ImageUtil.copyImageToJpg(oldPath, "m1",null).replace(basepath, "/"));
	    if(cutImage){
			String npath = path + "/" + "x/";
			String newPath =  npath + imgName;
			System.out.println(newPath);
			File newfile = new File(npath); 
			if(!newfile.exists()){
		       newfile.mkdirs();
		    }
			map.put("x", ImageUtil.cutImage(oldPath,type[0], type[1]).replace(basepath, "/"));
		}
		return map;
	}
	
	/**
	 *
	 * 获取后缀
	 * @Author:T.L
	 * @Date:2012-9-5 下午5:40:22
	 * @param fileName
	 * @return
	 *
	 */
	public static String getPostfix(String fileName){
		Pattern reg=Pattern.compile("[.]jpg|png|jpeg|gif|bmp$");
		Matcher matcher=reg.matcher(fileName);
		if(!matcher.find()) {
			return ".jpg";
		}
		String postfix = matcher.group();
		if(-1==postfix.indexOf(".")){//判断是否有.
			postfix = "."+postfix;
		}
		return postfix;
	}
	
	/**
	 *
	 * 一维码生成,保存图片,返回保存后的目录,文件名为: 订单ID.jpg(文件名自己页面拼装)
	 * @Author:T.L
	 * @Date:2012-9-24 下午6:51:22
	 * @param ordersid 订单ID
	 * @param sc
	 * @return
	 *
	 */
	public static String saveFileOneCode_128(String ordersid,ServletContext sc ){
		
		String savepath = DateUtils.getDate2Str("yyyy",new Date());
		String basepath = sc.getRealPath("/");
		//目录取当前年份+订单前三位
		String path = basepath +"upload/onecode/"+ savepath+"/"+ordersid.substring(0,3);
		String oldPath = path + "/" + ordersid+".jpg";
		File oldfile = new File(path);
		if(!oldfile.exists()){
			oldfile.mkdirs();
	    }
		
		byte[] b = BarcodeUtils.saveFileOneCode_128(ordersid, false, 0, 0, 0, 0, false, 0);
		
		try {
	       File outputFile = new File(oldPath);
	       OutputStream out = new FileOutputStream(outputFile);
	       
			out.write(b,0,b.length);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
		return path;
	}
	/**
	 *
	 * 积分订单一维码生成,保存图片,返回保存后的目录,文件名为: 订单ID.jpg(文件名自己页面拼装)
	 * @Author:L.X
	 * @Date:2013-1-17 下午16:40:22
	 * @param ordersid 订单ID
	 * @param sc
	 * @return
	 */
	public static String saveFileInteralOneCode(String ordersid,ServletContext sc ){
		String savepath = DateUtils.getDate2Str("yyyy",new Date());
		String basepath = sc.getRealPath("/");
		//目录取当前年份+订单前三位
		String path = basepath +"upload/interalonecode/"+ savepath+"/"+ordersid.substring(0,3);
		String oldPath = path + "/" + ordersid+".jpg";
		File oldfile = new File(path);
		if(!oldfile.exists()){
			oldfile.mkdirs();
	    }
		byte[] b = BarcodeUtils.saveFileOneCode_128(ordersid, false, 0, 0, 0, 0, false, 0);
		
		try {
	       File outputFile = new File(oldPath);
	       OutputStream out = new FileOutputStream(outputFile);
	       out.write(b,0,b.length);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
		return path;
	}
	
	
	/**
	 * 多个图片上传
	 * @param cutImage 是否切割图片
	 * @param type 图片的宽 高 [ 此参数必须添加 ]
	 * @return 返回list的 map 类型 
	 * 其中 Map 的key  ：  x 表示小图片 d 表示大图片
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public static List<Map<String,String>> uploadFilesReturnListMap(String savepath,MultipartFile[] ut,ServletContext sc,boolean cutImage,Integer... type) throws IllegalStateException, IOException
	{
		List<Map<String,String>> listimgpath = new ArrayList<Map<String,String>>();
		String basepath = sc.getRealPath("/");
		String path = basepath +"upload/"+ savepath;
		for(int i=0;i<ut.length;i++ )
		{
			 if(!StringUtils.isEmpty(ut[i].getOriginalFilename()))
			 {
				String imgname = ImageUtil.generateFileName(ut[i].getOriginalFilename());
				String oldPath = path + "/" + imgname;
				File oldfile = new File(path);
				if(!oldfile.exists())
				{
					oldfile.mkdirs();
				}
				File fileOldPath = new File(oldPath);
				ut[i].transferTo(fileOldPath);
				Map<String,String> map = new TreeMap<String,String>();
				map.put("d", oldPath.replace(basepath, "/"));
				//上传图片默认转换一次jpg格式进行压缩,T.L 2013-02-04
				map.put("m1", ImageUtil.copyImageToJpg(oldPath, "m1",null).replace(basepath, "/"));
				if(cutImage)
				{
					String npath = path + "/" + "x/";
					String newPath =  npath + imgname;
					System.out.println(newPath);
					File newfile = new File(npath); 
					if(!newfile.exists())
					{
					   newfile.mkdirs();
					}
					map.put("x", ImageUtil.cutImage(oldPath,type[0], type[1]).replace(basepath, "/"));
				}
				listimgpath.add(map);
			 }
			 else
			 {
			 	Map<String,String> map = new TreeMap<String,String>();
				map.put("d", "");
				map.put("m1", "");
				listimgpath.add(map);
			 }
		}
		return listimgpath;
	}
	/**
	 * 全国全国巡演一览
	 * 图片上传
	 * @param cutImage 是否切割图片
	 * @param type 图片的宽 高 [ 此参数必须添加 ]
	 * @return 返回list的 map 类型 
	 * 其中 Map 的key  ：  x 表示小图片 d 表示大图片
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public static List<Map<String,String>> uploadFileNOimgNameTour(String savepath,MultipartFile[] ut,ServletContext sc,boolean cutImage,String[] uploadFileName,Integer... type) throws IllegalStateException, IOException{
		List<Map<String,String>> listimgpath = new ArrayList<Map<String,String>>();
		Map<String,String> map = new TreeMap<String,String>();
		String basepath = sc.getRealPath("/");
		String path = basepath +"upload/"+ savepath;
		 for(int i=0;i<ut.length;i++ ){
			 if(!StringUtils.isEmpty(ut[i].getOriginalFilename())){
				 String imgname = uploadFileName[i];
				 String oldPath = path + "/" + imgname;
				 File oldfile = new File(path);
					if(!oldfile.exists()){
						oldfile.mkdirs();
					}
					File fileOldPath = new File(oldPath);
					try{
					ut[i].transferTo(fileOldPath);
					}catch(Exception e){
						logger.error("上传文件： "+imgname, e);
					}
					map.put("d", oldPath.replace(basepath, "/"));
				    if(cutImage){
						String npath = path + "/" + "x/";
						String newPath =  npath + imgname;
						System.out.println(newPath);
						File newfile = new File(npath); 
						if(!newfile.exists()){
					       newfile.mkdirs();
					    }
						map.put("x", ImageUtil.cutImage(oldPath,type[0], type[1]).replace(basepath, "/"));
					}
				    listimgpath.add(map);
			 }
	 
			 
		 }
		
		return listimgpath;
	}
	
	/**
	 *
	 * 上传文件,非图片文件
	 * @Author:T.L
	 * @Date:2013-5-15 下午6:05:41
	 * @param cutImage
	 * @param sc
	 * @param ut
	 * @param foldername
	 * @param type
	 * @return
	 * @throws IOException
	 *
	 */
	public static Map<String,String> uploadFileNoPic(Boolean cutImage,ServletContext sc ,MultipartFile ut,String foldername,Integer... type) throws IOException{
		String imgName = ut.getOriginalFilename();
		Map<String,String> map = new TreeMap<String,String>();
		String savepath = DateUtils.getDate2Str("yyyy/MM/dd",new Date());
		String basepath = sc.getRealPath("/");
		String path = basepath +"upload/"+foldername+ savepath;
		String oldPath = path + "/" + imgName;
		File oldfile = new File(path);
		if(!oldfile.exists()){
			oldfile.mkdirs();
	    }
		File fileOldPath = new File(oldPath);
		ut.transferTo(fileOldPath);
		map.put("d", oldPath.replace(basepath, "/"));
		return map;
	}
	
}
