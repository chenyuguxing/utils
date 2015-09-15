package com.ylpw.utils;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;


/**
 * User:T.L
 * Date:Oct 27, 2011
 * Time:5:31:34 PM
 * Class Description:
 */
public class ExcelUtils {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ExcelUtils.class);
	
	/**
	 * 
	 * User:T.L
	 * Description:导出Excel工具类,文件标题必须有,且内容数组要与标题数组下标对应.分页标志为0时表示不分页
	 * @param fileName 文件名称
	 * @param encoding 文件名称编码(可传null或空,默认为GB2312)
	 * @param titles excel标题数组
	 * @param contents excel内容数组,内容必须与标题对应.
	 * @param pagingSize 分页(每页显示多少条,参数为 0 时不分页.)
	 * @param response
	 *
	 */
	public static void exportExcel(String fileName,String encoding,
			String[] titles, List<String[]> contents, int pagingSize,HttpServletResponse response){
		if(null==fileName || "".equals(fileName)){
			fileName="excel";
		}
		if(null==encoding || "".equals(encoding)){
			encoding = "GB2312";
		}
		String value = "";
		String[] cs = null;
		OutputStream os=null;
		int page = 1;
		int rows = 1;//行数
		try {
			os = response.getOutputStream();
			response.reset();
			response.setHeader("Content-disposition","attachment; filename=" 
					+ new String(fileName.getBytes(encoding),"ISO-8859-1")+"_"+DateUtils.formatDate(new Date(), "yyyyMMdd")+".xls");
			response.setContentType("aplication/msexcel");
			
			WritableWorkbook wwb = Workbook.createWorkbook(os);
			WritableSheet ws = wwb.createSheet("第"+page+"页", (page-1));
			Label label = null;
			for(int i=0; i<titles.length; i++){
				label = new Label(i,0,titles[i]);
				ws.addCell(label);
			}
			
			for(int i=0; i<contents.size(); i++){
				if(pagingSize!=0 && i%pagingSize==0 && i>0){
					page++;
					rows=1;
					ws = wwb.createSheet("第"+page+"页", (page-1));
					for(int ii=0; ii<titles.length; ii++){
						label = new Label(ii,0,titles[ii]);
						ws.addCell(label);
					}
				}
				for(int j=0; j<titles.length; j++){
					cs = contents.get(i);
					value = cs[j];
					if(null==value || "".equals(value) || "null".equals(value)){
						value="";
					}
					//如果是联盟订单ID，则不改成数值形式
					if(titles[j].equals("联盟单号") || titles[j].equals("快递单号") || titles[j].equals("保单号") || titles[j].equals("礼券号码") || titles[j].equals("礼券密码")  || titles[j].equals("乐通卡号")){
						label = new Label(j,rows,value);
						ws.addCell(label);
					}else{
						//如果是非联盟订单ID,且是数值形式，则转换成数值形式，方便财务导出报表后统计
						if(NumberUtils.isNumber(value)){
		       				jxl.write.Number number  =   new  jxl.write.Number( j ,  rows ,  Double.parseDouble(value.toString()));
		       				ws.addCell(number);
		       			}else{
		       				label = new Label(j,rows,value);
							ws.addCell(label);
		       			}
					}
				}
				rows++;
			}
			
			wwb.write();
			wwb.close();
			os.flush();
			os.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	/**
	 * 
	 * User:T.L
	 * Description:导出Excel存储指定地址,文件标题必须有,且内容数组要与标题数组下标对应.分页标志为0时表示不分页
	 * @param filePath 文件存储路径
	 * @param fileName 文件名称(不包含后缀名称)
	 * @param encoding 文件名称编码(可传null或空,默认为GB2312)
	 * @param titles excel标题数组
	 * @param contents excel内容数组,内容必须与标题对应.
	 * @param pagingSize 分页(每页显示多少条,参数为 0 时不分页.)
	 * @param response
	 *
	 */
	public static void exportExcelToFile(String filePath, String fileName,String encoding,
			String[] titles, List<String[]> contents, int pagingSize){
		if(null==fileName || "".equals(fileName)){
			fileName="excel.xls";
		}else{
			fileName = fileName+".xls";
		}
		if(null==encoding || "".equals(encoding)){
			encoding = "GB2312";
		}
		File file = new File(filePath+fileName);
		String value = "";
		String[] cs = null;
		int page = 1;
		int rows = 1;//行数
		try {
			WritableWorkbook wwb = Workbook.createWorkbook(file);
			WritableSheet ws = wwb.createSheet("第"+page+"页", (page-1));
			Label label = null;
			for(int i=0; i<titles.length; i++){
				label = new Label(i,0,titles[i]);
				ws.addCell(label);
			}
			
			for(int i=0; i<contents.size(); i++){
				if(pagingSize!=0 && i%pagingSize==0 && i>0){
					page++;
					rows=1;
					ws = wwb.createSheet("第"+page+"页", (page-1));
					for(int ii=0; ii<titles.length; ii++){
						label = new Label(ii,0,titles[ii]);
						ws.addCell(label);
					}
				}
				for(int j=0; j<titles.length; j++){
					cs = contents.get(i);
					value = cs[j];
					if(null==value || "".equals(value) || "null".equals(value)){
						value="";
					}
					label = new Label(j,rows,value);
					ws.addCell(label);
				}
				rows++;
			}
			
			wwb.write();
			wwb.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * User:T.L
	 * Description:从流读取Excel数据(目前只支付office1997-office2003版本的Excel)
	 * @param inputStream Excel流
	 * @param totalSheet 总页数(默认总共1页)
	 * @param startSheet 起始页(默认从第1页读起)
	 * @param startRow 起始行数(默认从第1行读起)
	 * @return
	 *
	 */
	public static List<String[]> readExcelByInputStream(InputStream inputStream,int totalSheet,int startSheet,int startRow){
		Workbook workbook;
		List<String[]> list = new ArrayList<String[]>();
		try {
			workbook = Workbook.getWorkbook(inputStream);
			list = readExcel(workbook, totalSheet,startSheet,  startRow);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return list;
	}
	/**
	 * 
	 * User:T.L
	 * Description:从文件读取Excel数据(目前只支付office1997-office2003版本的Excel)
	 * @param file Excel文件地址
	 * @param totalSheet 总页数(默认总共1页)
	 * @param startSheet 起始页(默认从第1页读起)
	 * @param startRow 起始行数(默认从第1行读起)
	 * @return
	 *
	 */
	public static List<String[]> readExcelByFile(File file,int totalSheet,int startSheet,int startRow){
		Workbook workbook;
		List<String[]> list = new ArrayList<String[]>();
		try {
			workbook = Workbook.getWorkbook(file);
			list = readExcel(workbook, totalSheet, startSheet,startRow);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return list;
	}
	
	
	/**
	 * 
	 * User:T.L
	 * Description:读取Excel数据(目前只支付office1997-office2003版本的Excel)
	 * @param workbook Excel工作薄
	 * @param totalSheet 总页数(默认总共1页)
	 * @param startSheet 起始页(默认从第1页读起)
	 * @param startRow 起始行数(默认从第1行读起)
	 * @return
	 *
	 */
	public static List<String[]> readExcel(Workbook workbook,int totalSheet,int startSheet,int startRow){
		List<String[]> list = new ArrayList<String[]>();
		
		if(totalSheet <= 0){
			totalSheet=1;
		}
		
		if(startSheet <= 0){
			startSheet=0;
		}else{
			startSheet = startSheet-1;
		}
		if(startRow <= 0){
			startRow = 0;
		}else{
			startRow = startRow-1;
		}
	
		Cell cell = null;
		//文档格内容
		String content = null;
		
		
		for(int t=startSheet; t<totalSheet; t++){
			Sheet sheet = workbook.getSheet(startSheet);
			//总列数
			int columns = sheet.getColumns();
			//总行数
			int rows = sheet.getRows();
			for(int i=startRow; i<rows; i++){
				String[] cs = new String[columns];
				for(int j=0; j<columns; j++){
					cell = sheet.getCell(j, i);
					content = cell.getContents();
					cs[j] = content;
				}
				list.add(cs);
			}
			startSheet++;
		}
		
		return list;
	}
	
	public static void main(String[] args) {
//		String regex="^(0|[1-9]\\d*)$|^(0|([1-9]\\d{0,}))\\.\\d{1,}$";
//		//System.out.println(regex.matches("345345"));
//		String s="123123";
//		System.out.println(regex.matches("13"));
	}
}
