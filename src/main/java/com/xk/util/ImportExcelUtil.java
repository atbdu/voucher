package com.xk.util;

import com.alibaba.fastjson.JSON;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Package: com.xk.test
 * @ClassName: ImportExcelUtil
 * @Author: ht
 * @CreateTime: 2021/12/8 16:21
 * @Description: java Excel导入、自适应版本、将Excel转成List<map>对象
 */
public class ImportExcelUtil {

    /**
     * 将流中的Excel数据转成List<Map>
     * @param xlsPath 文件名路径（判断Excel版本）
     * @param mapping  字段名称映射
     * @return List<Map<String, Object>> 数据集合
     * @throws Exception 异常捕捉
     */
    public static List<Map<String, Object>> parseExcel(String xlsPath,Map<String,String> mapping) throws Exception {
        // 根据文件名来创建Excel工作薄
        Workbook work = getWorkbook(xlsPath);
        if (null == work) {
            throw new Exception("创建Excel工作薄为空！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;
        // 返回数据
        List<Map<String, Object>> ls = new ArrayList<Map<String, Object>>();

        // 遍历Excel中所有的sheet
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }
            // 取第一行标题
            row = sheet.getRow(0);
            String[] title = null;
            if (row != null) {
                title = new String[row.getLastCellNum()];
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                    cell = row.getCell(y);
                    title[y] = (String) getCellValue(cell);
                }

            } else {
                continue;
            }
            // 遍历当前sheet中的所有行
            for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {
                row = sheet.getRow(j);
                if (row==null){
                    continue;
                }
                Map<String, Object> m = new HashMap<String, Object>();
                // 遍历所有的列
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                    cell = row.getCell(y);
                    if (getCellValue(cell) ==null || getCellValue(cell)==""){
                        continue;
                    }
                    String key = title[y];
                    if (y==0||y==1||y==2||y==3||y==5||y==6){
                        row.getCell(y).setCellType(Cell.CELL_TYPE_STRING);
                        m.put(mapping.get(key), cell.getRichStringCellValue().getString());
                    }else if (y==4){
                        m.put(mapping.get(key), getCellValue(cell));
                    }
                }
                if (m.size()>0){
                    ls.add(m);
                }
            }

        }
        work.close();
        return ls;
    }

    /**
     * 描述：根据文件后缀，自适应上传文件的版本
     *
     * @param xlsPath 文件路径
     * @return Workbook 文件对象
     * @throws Exception 文件格式异常
     */
    public static Workbook getWorkbook(String xlsPath) throws Exception {
        Workbook wb = null;
        FileInputStream fileIn = new FileInputStream(xlsPath);
        if (xlsPath.endsWith("xls")) {
            // 2003-
            wb = new HSSFWorkbook(fileIn);
        } else if (xlsPath.endsWith("xlsx")) {
            // 2007+
            wb = new XSSFWorkbook(fileIn);
        } else {
            throw new Exception("解析的文件格式有误！");
        }
        return wb;
    }

    /**
     * 描述：对表格中数值进行格式化
     *
     * @param cell
     * @return
     */
    public static Object getCellValue(Cell cell) {
        Object value = null;
        // 格式化number String字符
        DecimalFormat df = new DecimalFormat("0");
        // 日期格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
        // 格式化数字
        DecimalFormat df2 = new DecimalFormat("0.00");
        if(cell==null){
            return value;
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                    value = df.format(cell.getNumericCellValue());
                } else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
                    value = sdf.format(cell.getDateCellValue());
                } else {
                    value =df2.format(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_BLANK:
                value = "";
                break;
            default:
                break;
        }
        return value;
    }

    public static void main(String[] args) throws Exception {
//        File file = new File("H:\\ht\\OneDrive\\桌面\\佣金2(1)(5).xlsx");
        String file = "H:\\ht\\OneDrive\\桌面\\C0065_0f34e12952ab4a4c894600af0c7ef235 (1).xlsx";
        FileInputStream fis = new FileInputStream(file);
        Map<String,String> fields=new HashMap<String,String>();
        fields.put("姓名","worker_bank_person");
        fields.put("手机号","mobile");
        fields.put("身份证号/港澳通行证号","worker_cardno");
        fields.put("银行卡号","worker_bank_num");
        fields.put("实发金额（元）","order_amt");
        fields.put("出金说明","task_order_rim");
        fields.put("商户订单号","client_order_no");
        List<Map<String, Object>> ls = parseExcel(file, fields);
        System.out.println(JSON.toJSONString(ls));
    }
}


