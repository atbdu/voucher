package com.xk.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author penoncel
 * 
 */
public class ImportExcel {
	private String excelHeadStr;
	private String errMessage;
	public String getErrMessage() {
		return errMessage;
	}

	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}
	public String getExcelHeadStr() {
		return excelHeadStr;
	}

	public void setExcelHeadStr(String excelHeadStr) {
		this.excelHeadStr = excelHeadStr;
	}
	/**
	 * ********************************************************
	 * 
	 * @Title: read
	 * @Description:  根据文件名读取excel文件
	 * @param file
	 * @param fileName
	 * @param str
	 * @return List<ArrayList<String>>
	 * @date 2016-1-21
	 ******************************************************** 
	 */
	public List<ArrayList<String>> read(MultipartFile file) {
		List<ArrayList<String>> dataList = new ArrayList<ArrayList<String>>();
		boolean isExcel2003 = true;
		if (file != null) {
			String fileName = file.getOriginalFilename();
			if (fileName == null|| !fileName.matches("^.+\\.(?i)((xls)|(xlsx))$")) {
				return dataList;
			}
			if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
				isExcel2003 = false;
			}
	        try {
	            dataList = read(file.getInputStream() , isExcel2003);// 读取excel
	        } catch (Exception ex) {
	           return dataList;
	        }
		}
		return dataList;
	}

	/**
	 * ********************************************************
	 * 
	 * @Title: read
	 * @Description: 判断是哪个版本的Excel
	 * @param inputStream
	 * @param isExcel2003
	 * @return List<ArrayList<String>>
	 * @date 2016-1-21
	 ******************************************************** 
	 */
	public List<ArrayList<String>> read(InputStream inp, boolean isExcel2003) {
		List<ArrayList<String>> dataLst = null;
		try {
			Workbook wb = isExcel2003 ? new HSSFWorkbook(inp): new XSSFWorkbook(inp); // 根据版本选择创建Workbook的方式
			dataLst = readReport(wb);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataLst;
	}

    /**
     * ********************************************************
     * @Title: readFile
     * @Description:把读出来的数据放到list中，方便调用
     * @param file
     * @param fileName
     * @param str
     * @return List<String[]>
     * @date 2015-12-23 下午8:26:57
     ********************************************************
     */
    public List<String[]> convertArray(MultipartFile file){
    	List<String[]> list=new ArrayList<String[]>();
    	List<ArrayList<String>> dataList=read(file);
       	if(dataList==null){
    		return list;
    	}
    	for (ArrayList<String> innerLst : dataList) {
			StringBuffer rowData = new StringBuffer();
			for (String dataStr : innerLst) {
				rowData.append(",").append(dataStr.trim());
			}

			if (rowData.length() > 0) {
				String arrays[]=rowData.deleteCharAt(0).toString().split(",");
                list.add(arrays);
			}
    	}
    	return list;
    }	
	/**
	 * 
	 ********************************************************* 
	 * @Title: readReport
	 * @Description: 读取Excel
	 * @return List<ArrayList<String>>
	 * @date 2016-1-21
	 ******************************************************** 
	 */
	public List<ArrayList<String>> readReport(Workbook wb) {

		List<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
     		
			Sheet sheet = wb.getSheetAt(0);//取得第一个sheets
			for (int i = 0; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);//获取行(row)对象
				if (row == null) {
					continue; //row为空的话,不处理
				}
				ArrayList<String> rowLst = new ArrayList<String>();
				for (int j = 0; j < row.getLastCellNum(); j++) {
					Cell cell = row.getCell(j);//获得单元格(cell)对象
					String cellStr = "";
					if (cell == null) {
						rowLst.add("");
						continue;
					}
					cellStr = ConvertCellStr(cell, cellStr);//转换接收的单元格
					rowLst.add(cellStr);//将单元格的数据添加至一个对象
				}				
				if(i!=0){  //不读取表头
					list.add(rowLst);//将添加数据后的对象填充至list中
				}else{
					if(this.excelHeadStr!=null&&this.excelHeadStr!=""){ //如果传过来的头标题不为空，就需要检测头标题是否正确
						 String trueExcelHeadStr=rowLst.toString();
						 trueExcelHeadStr=trueExcelHeadStr.replaceAll(" ", "").replaceAll("\\[", "").replaceAll("\\]", "");//toString方法，是以" ,"隔开的
						 if(!excelHeadStr.equals(trueExcelHeadStr)){
							 this.setErrMessage("导入的EXCEL格式不对，模板需要的列名称依次为【"+excelHeadStr+"】");
							 return null;							 
						 }
					}
				}
			}

		return list;

	}

/**
 * 
  *********************************************************
  * @Title: ConvertCellStr
  * @Description: 解析excel数据类型
  * @return String
  * @date 2016-1-22
  ********************************************************
 */
	private String ConvertCellStr(Cell cell, String cellStr) {

		switch (cell.getCellType()) {

		case Cell.CELL_TYPE_STRING:
			cellStr = cell.getStringCellValue().toString().trim();// 读取String
			break;

		case Cell.CELL_TYPE_BOOLEAN:
			cellStr = String.valueOf(cell.getBooleanCellValue());// 得到Boolean对象的方法
			break;

		case Cell.CELL_TYPE_NUMERIC:
			// 先看是否是日期格式
			if (DateUtil.isCellDateFormatted(cell)) {

				cellStr = cell.getDateCellValue().toString();// 读取日期格式
			} else {
				cellStr = String.valueOf(cell.getNumericCellValue());// 读取数字
				if (null != cellStr && !"".equals(cellStr.trim())) {
					String[] item = cellStr.split("[.]");
					if (1 < item.length && "0".equals(item[1])) {
						cellStr = item[0];// 去掉数字后的小数点
					}
				}
			}
			break;

		case Cell.CELL_TYPE_FORMULA:
			cellStr = cell.getCellFormula().toString(); // 读取公式
			if (cellStr.equals("NaN")) {// 如果获取的数据值为非法值,则转换为获取字符串
				cellStr = cell.getStringCellValue().toString();
			}
			break;
		}
		return cellStr;
	}
}
