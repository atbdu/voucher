package com.xk.util;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
/**
 * 
 *********************************************************.<br>
 * [类名] ExcelUtil <br>
 * [描述] TODO(这里用一句话描述这个类的作用) <br>
 * [作者] 陈勇磊 <br>
 * [时间] 2016-1-21 上午11:18:37 <br>
 *********************************************************.<br>
 */
public class ExportExcelUtil {
	/** 读取的总行数 */
	private int totalRows = 0;
	/** 读取的总列数 */
	private int totalCells = 0;
	/** 起始读取的行数 */
	private int fstrow = 0;
	
    /**
	 * 
	 *********************************************************.<br>
	 * [方法] outputExcelFile <br>
	 * [描述] TODO(导出Excel表格) <br>
	 * [参数] TODO(对参数的描述) <br>
	 * [返回] HSSFWorkbook <br>
	 * [时间] 2014-8-18 下午05:39:56 <br>
	 *********************************************************.<br>
	 */
	@SuppressWarnings("deprecation")
	public static HSSFWorkbook outputExcelFile(List<Object[]> list,String path,String[] names,int[] width) throws Exception{ 
		FileInputStream  fs  = new  FileInputStream(path); //模板
		HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(fs));//创建Excel文档
		Map<String, HSSFCellStyle> mapstyle = createStyles(wb);//标题样式和字体样式
		HSSFCell[] firstcell = new HSSFCell[names.length];
		HSSFSheet hs = wb.getSheetAt(0);//获得一个sheet
		 
		HSSFRow firstrow = hs.createRow((short)0);//第二行的标题
		for(int i=0;i<names.length;i++){
				firstcell[i] = firstrow.createCell((short)i);
				firstcell[i].setCellStyle(mapstyle.get("cell_header_title"));
				firstcell[i].setCellValue(new HSSFRichTextString(names[i]));
				hs.setColumnWidth(i, width[i]*256);
		}
		
		hs.createFreezePane(0, 1);//冻结行
		for(int i=0;i<list.size();i++){
			firstrow = hs.createRow((short)i+2);//下标为1的开始
			Object[] obj = list.get(i);
			for(int j=0;j<names.length;j++){	//循环excel里的列数
				firstcell[j] = firstrow.createCell((short)j);
				firstcell[j].setCellStyle(mapstyle.get("cell_data_default"));//字体样式
				firstcell[j].setCellType(HSSFCell.CELL_TYPE_STRING);
				if(obj[j]==null||obj[j].equals("")){
					firstcell[j].setCellValue(" ");
				}else{
					firstcell[j].setCellValue(obj[j].toString());
				}
			}
		}
		return wb; 
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [方法] createStyles <br>
	 * [描述] TODO(设置Excel的标题和样式) <br>
	 * [参数] TODO(对参数的描述) <br>
	 * [返回] Map<String,HSSFCellStyle> <br>
	 * [时间] 2014-8-18 下午05:39:25 <br>
	 *********************************************************.<br>
	 */
	public static Map<String, HSSFCellStyle> createStyles(HSSFWorkbook wb) {  
	     Map<String, HSSFCellStyle> styles = new HashMap<String, HSSFCellStyle>();  
	     //----------------------标题样式---------------------------  
	     HSSFCellStyle cell_header_title = wb.createCellStyle();  
	     HSSFFont font2 = wb.createFont();
	     font2.setFontName("宋体");
	     font2.setFontHeightInPoints((short) 13);//设置字体大小
	     font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体  
	     cell_header_title.setFont(font2);//选择需要用到的字体格式
	     cell_header_title.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中  
	     cell_header_title.setWrapText(false);// 自动换行
	     cell_header_title.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
	     cell_header_title.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
	     cell_header_title.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
	     cell_header_title.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
	     styles.put("cell_header_title", cell_header_title);  
	        
	     //-----------------------字体样式--------------------------- 
	     HSSFCellStyle cell_data_default = wb.createCellStyle();  
	     HSSFFont font = wb.createFont();
	     font.setFontName("宋体");
	     font.setFontHeightInPoints((short) 11);//设置字体大小
	     cell_data_default.setFont(font);//选择需要用到的字体格式
	     cell_data_default.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中  
	     cell_data_default.setWrapText(true);// 自动换行
	     cell_data_default.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
	     cell_data_default.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
	     cell_data_default.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
	     cell_data_default.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
	     styles.put("cell_data_default", cell_data_default); 
	     return styles;  
	}
	
	/*******************************get and set***********************************/
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	public int getTotalCells() {
		return totalCells;
	}
	public void setTotalCells(int totalCells) {
		this.totalCells = totalCells;
	}
	public int getFstrow() {
		return fstrow;
	}
	public void setFstrow(int fstrow) {
		this.fstrow = fstrow;
	}
}

	