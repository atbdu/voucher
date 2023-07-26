package com.xk.util;

import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author ht -难而为之-
 * @version 007yyds
 * @date 2020/12/30 10:34
 */
public class JxlsUtils {
    public static void toPackageOs(HttpServletResponse response, String fileName) throws Exception {
        response.setContentType("application/octet-stream;charset=utf-8");
        String outFileName = fileName + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date().getTime()) + "-" + UUID.randomUUID().toString().replace("-", "");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(outFileName.getBytes("GBK"), "ISO8859_1") + ".xlsx");
        response.setContentType("application/vnd.ms-excel;charset=gb2312");
    }

    /**
     * 生成模板输入流
     *
     * @param temPath
     * @return
     * @throws Exception
     */
    public static InputStream toPackageIn(String temPath) throws Exception {
        return new FileInputStream(temPath);
    }

    public static <T> void exportExcel(List<T> list, String title, OutputStream os,
                                       InputStream in) throws Exception {
        Context context = new Context();
        context.putVar("title", title);
        context.putVar("list", list);
        JxlsHelper.getInstance().processTemplate(in, os, context);
        os.flush();
    }
}
