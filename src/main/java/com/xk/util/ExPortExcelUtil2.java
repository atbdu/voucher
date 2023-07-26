package com.xk.util;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
/**
 * 
 *********************************************************.<br>
 * [类名] ExPortExcelUtil <br>
 * [描述] 导出excel工具类 <br>
 * [作者] 郭旺 <br>
 * [时间] 2015-12-21 上午8:18:55 <br>
 *********************************************************.<br>
 */
public class ExPortExcelUtil2 {
	/**
	 * 导出excel方法
	 * @param excelName   	 excel表格内的标题名字
	 * @param pageDataList 	  导出的excel数据
	 * @param response	            当前response 用来获取下载路径
	 * @param colsCn	            导出的表格的标题列名集合
	 * @param colsWidth	            导出的表格的标题列名宽度集合
	 */
	public static void reprotExcel(String excelName,List<String[]> pageDataList,HttpServletResponse response,ArrayList<String> colsCn,ArrayList<String> colsWith){
		String fileName = excelName;
		OutputStream toClient = null;
		WritableWorkbook wbook = null;
		try {
			// 设置response获取下载路径
			response.reset();
			response.setHeader("Content-disposition","attachment; filename="+new String(fileName.getBytes("GBK"), "ISO8859_1" )+".xls");
			response.setContentType("application/vnd.ms-excel;charset=gb2312");  
			toClient = new BufferedOutputStream(response.getOutputStream()); 

			wbook = Workbook.createWorkbook(toClient);
			WritableSheet wsheet = wbook.createSheet("导出数据", 0); // sheet名称
			WritableCellFormat cellFormatNumber = new WritableCellFormat();
			cellFormatNumber.setAlignment(Alignment.RIGHT);

			WritableFont wf = new WritableFont(WritableFont.ARIAL, 12,WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.BLACK); // 定义格式、字体、粗体、斜体、下划线、颜色
			WritableCellFormat wcf = new WritableCellFormat(wf); // title单元格定义
			WritableCellFormat wcfc = new WritableCellFormat(); // 一般单元格定义
			WritableCellFormat wcfe = new WritableCellFormat(); // 一般单元格定义
			wcf.setAlignment(Alignment.CENTRE); // 设置对齐方式
			wcfc.setAlignment(Alignment.CENTRE); // 设置对齐方式

			wcf.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);//
			wcfc.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);//
			wcfe.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);//

			// 设置列宽
			for(int i = 0;i<colsWith.size();i++){
				wsheet.setColumnView(i, Integer.parseInt(colsWith.get(i)));
			}

			int rowIndex = 0;
			int columnIndex = 0;
			if (null != pageDataList) {
				// rowIndex++;
				columnIndex = 0;
				wsheet.setRowView(rowIndex, 500);// 设置标题行高
				wsheet.addCell(new Label(columnIndex++, rowIndex, fileName, wcf));
				wsheet.mergeCells(0, rowIndex,  10, rowIndex);// 合并标题所占单元格
				rowIndex++;
				columnIndex = 0;
				wsheet.setRowView(rowIndex, 380);// 设置项目名行高
				//设置列名
				for(String colCn : colsCn ){
					wsheet.addCell(new Label(columnIndex++, rowIndex, colCn, wcf));
				}
				//开始行循环  值的循环
				for (String[] array : pageDataList) { // 循环列
					rowIndex++;
					columnIndex = 0;
					for(int i = 0;i<array.length;i++){
						if("null".equals(array[i])){
							array[i]="";
						}
						wsheet.addCell(new Label(columnIndex++, rowIndex, array[i],wcfe));//
					}
				}
				rowIndex++;
				columnIndex = 0;
			}

			wbook.write();

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if (wbook != null) {
					wbook.close();
				}
				if(toClient!=null){
					toClient.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	/**
	 * 按年按月按日   转换成指定格式的list
	 * @param list
	 * @return
	 */
	public static List<String[]> parseList(List<Map<String, Object>> list){
		List<String[]> list2 = new ArrayList<String[]>();
		for(Map<String, Object> hashMap :list){
			String[] stringArr = new String[16];
			stringArr[0] = hashMap.get("AGENT_NO")+"";
			stringArr[1] = hashMap.get("CLIENT_NAME")+"("+hashMap.get("CLIENT_NO")+")";
			stringArr[2] = hashMap.get("CARD_NO")+"";
			stringArr[3] = hashMap.get("SERIAL")+"";
			stringArr[4] = hashMap.get("AUTHORCODE")+"";
			stringArr[5] = hashMap.get("AMOUNT")+"";
			stringArr[6] = hashMap.get("LOCADATE")+"";
			stringArr[7] = hashMap.get("CH_NUM")+"";
			stringArr[8] = hashMap.get("CH_NAME")+"";
			stringArr[9] = hashMap.get("REPLY_CODE")+"";
			stringArr[10] = hashMap.get("END_TIME")+"";
			stringArr[11] = hashMap.get("CUSTOM_NUM")+"";
			stringArr[12] = hashMap.get("FIRST_TIME")+"";
			stringArr[13] = hashMap.get("WORD_ORDER")+"";
			stringArr[14] = hashMap.get("ADD_TIME")+"";
			stringArr[15] = hashMap.get("ADD_MAN")+"";
			list2.add(stringArr);
		}
		return list2;
	}
	
	
	/**
	 * 导出所有批次excel 转换格式的list
	 * @param list
	 * @return
	 */
	public static List<String[]> parseMobBillList(List<Map<String, Object>> list){
		List<String[]> list2 = new ArrayList<String[]>();
		DataCache cache = new DataCache();
		for(Map<String, Object> hashMap :list){
			//到款状态
			String arrivemoney_status = ((Map)cache.getDataCache("arrivemoney_status")).get( hashMap.get("ARRIVEMONEY_STATUS")+"")+"";
			//处理状态
			String status = ((Map)cache.getDataCache("tuihuo_status")).get( hashMap.get("STATUS")+"")+"";
			//差错类型
			String mob_type = ((Map)cache.getDataCache("mob_type")).get( hashMap.get("MOB_TYPE")+"")+"";
			String[] stringArr = new String[10];
			stringArr[0] = hashMap.get("BILL_NUM")+"";
			stringArr[1] = hashMap.get("AGENT_NUM")+"";
			stringArr[2] = hashMap.get("SUMCOUNT")+"笔";
			stringArr[3] = hashMap.get("AMOUNT")+"元";
			stringArr[4] = arrivemoney_status;
			stringArr[5] = status;
			stringArr[6] = hashMap.get("ADD_NAME")+"";
			stringArr[7] = hashMap.get("ADD_TIME")+"";
			stringArr[8] = mob_type;
			stringArr[9] = hashMap.get("DISPOSE_TIME")+"";
			list2.add(stringArr);
		}
		return list2;
	}
	
	
	/**
	 * 根据传入的列名返回一个-导出excel的列名的List
	 * @return
	 */
	public static  ArrayList<String> publicGetExcelColsName(String...colsName){
		ArrayList<String> list = new ArrayList<String>();
		for(String colName : colsName){
			list.add(colName);
		}
		return list;
	}
	
	/**
	 * 根据传入的宽度返还一个表格列宽度的list
	 * @return
	 */
	public static ArrayList<String>  getColsWidth(String...string){
		ArrayList list = new ArrayList();
		for(String str :string){
			list.add(str);
		}
		return list;
	}
}
