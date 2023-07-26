package com.xk.util.sm3;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


/**
 * 关于文件夹以及文件的操作均定义于该类中
 * 
 * @author 曹
 * 
 */

public class FileUtil {
//	private static Logger logger = LoggerFactory.getLogger(FileUtil.class);
	/**
	 * 遍历文件夹中的文件，返回文件列表
	 * 
	 * @param filePath
	 *            需要遍历的文件夹路径
	 * @return 文件夹下所有文件的列表
	 * @throws IOException
	 */
	public static List<String> listFileInDirectory(String filePath)
			throws IOException {
		// 用户保存文件名列表的List
		List<String> listOfFileName = new ArrayList<String>();
		// 根路径
		File rootPath = new File(filePath);
		if (!rootPath.exists()) {
			throw new IOException("输入路径不存在");
		}
		// 根路径下的文件数组
		File[] filesOfRoot = rootPath.listFiles();
		// 遍历根路径下的文件，如果是文件夹，则递归调用listFileInDirectory；如果是文件，则添加到文件名列表中
		for (File file : filesOfRoot) {
			if (file.isDirectory()) {
				listOfFileName.addAll(listFileInDirectory(file
						.getAbsolutePath()));
			} else {
				listOfFileName.add(file.getAbsolutePath());
			}
		}
		return listOfFileName;
	}

	/**
	 * 生成文件（夹）并设置权限
	 * 
	 * @param filePath
	 *            需要创建的文件（夹）路径
	 * @param directoryFlag
	 *            文件夹标志：true-是文件夹，false-不是文件夹
	 * @return true-创建成功，false-创建失败
	 * @throws IOException
	 */
	public static boolean createFile(String filePath, boolean directoryFlag)
			throws IOException {
		File file = new File(filePath);
		// 如果路径不存在，递归调用createFile，创建父路径
		if (!file.exists()) {
			if (createFile(file.getParentFile().getAbsolutePath(), true)) {
				// 创建父路径成功，如果路径是文件夹，创建文件夹；否则创建文件
				if (directoryFlag) {
					if (!file.mkdir()) {
						throw new IOException("创建文件夹" + file.getAbsolutePath()
								+ "失败");
					}
				} else {
					if (!file.createNewFile()) {
						throw new IOException("创建文件" + file.getAbsolutePath()
								+ "失败");
					}
				}
				// 设置权限为所有用户可读写
				if (!file.setReadable(true, false)) {
					throw new IOException("设置路径" + file.getAbsolutePath()
							+ "读权限失败");
				}
				if (!file.setWritable(true, false)) {
					throw new IOException("设置路径" + file.getAbsolutePath()
							+ "写权限失败");
				}
				if (!file.setExecutable(true, false)) {
					throw new IOException("设置路径" + file.getAbsolutePath()
							+ "可执行权限失败");
				}
			} else {
				return false;
			}
		}
		return true;
	}

	/**
	 * 校验文件汇总行和明细行是否一致（总笔数和总金额）
	 * 
	 * @param fileName
	 *            文件名
	 * @param indexOfTotalAmt
	 *            汇总行总金额位置（第一位为0，依次类推）
	 * @param indexOfTotalCount
	 *            汇总行总数量位置（第一位为0，依次类推）
	 * @param indexOfDetailAmt
	 *            明细行金额位置（第一位为0，依次类推）
	 * @return true-一致，false-不一致
	 */
	public static boolean checkTotalLineAndDetailLine(String fileName,
			Integer indexOfTotalAmt, Integer indexOfTotalCount,
			Integer indexOfDetailAmt) {
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(fileName, "r");

			// 汇总行总笔数
			Integer totalCount = 0;
			// 汇总行总金额
			BigDecimal totalAmt = BigDecimal.ZERO;
			// 明细行总笔数
			Integer detailCount = 0;
			// 明细行总金额
			BigDecimal detailAmt = BigDecimal.ZERO;

			// 校验文件汇总行与明细行数据是否一致
			// 得到汇总行数据
			String line = raf.readLine();
			String[] totalArray = line.split("\\|");
			totalCount = Integer.parseInt(totalArray[indexOfTotalCount]);
			totalAmt = new BigDecimal(totalArray[indexOfTotalAmt])
					.multiply(BigDecimal.valueOf(100));
			// 循环读取明细行数据
			String[] detailArray;
			for (line = raf.readLine(); line != null; line = raf.readLine()) {
				detailArray = line.split("\\|");
				// 累计明细行总数量和总金额
				detailCount++;
				detailAmt = detailAmt.add(new BigDecimal(
						detailArray[indexOfDetailAmt]).multiply(BigDecimal
						.valueOf(100)));
			}
			if (totalCount.compareTo(detailCount) != 0
					|| totalAmt.compareTo(detailAmt) != 0) {
				return false;
			}
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			if (raf != null)
				try {
					raf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	/**
	 * 校验文件汇总行和明细行是否一致（总笔数）
	 * 
	 * @param fileName
	 *            文件名
	 * @param indexOfTotalCount
	 *            汇总行总金额位置（第一位为0，依次类推）
	 * @param signedFlag
	 *            是否包含签名行
	 * @return
	 */
	public static boolean checkTotalLineAndDetailLine(String fileName,
			Integer indexOfTotalCount, boolean signedFlag) {
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(fileName, "r");
			// 最后一行的位置
			long lastLinePos = -1;
			// 汇总行总笔数
			Integer totalCount = 0;
			// 明细行总笔数
			Integer detailCount = 0;

			// 校验文件汇总行与明细行数据是否一致
			// 得到汇总行数据
			String line = raf.readLine();
			String[] totalArray = line.split("\\|");
			totalCount = Integer.parseInt(totalArray[indexOfTotalCount]);
			// 明细行开始的位置
			long startPos = raf.getFilePointer();
			// 定位最后一行的位置
			for (line = raf.readLine(); line != null; line = raf.readLine()) {
				lastLinePos = raf.getFilePointer();
			}
			// 回到明细行开始的位置
			raf.seek(startPos);
			// 循环读取明细行数据
			for (line = raf.readLine(); line != null; line = raf.readLine()) {
				// 到达最后一行时，若包含签名行，跳出；若不包含签名行，继续统计
				if (raf.getFilePointer() == lastLinePos && signedFlag)
					break;
				// 累计明细行总数量
				detailCount++;
			}
			if (totalCount.compareTo(detailCount) != 0) {
				return false;
			}
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			if (raf != null)
				try {
					raf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	/**
	 * 压缩文件
	 * @param filePath
	 * @param zipFilePath
	 */
	public static void createZipFile(String filePath, String zipFilePath) {
		ZipOutputStream zos = null;
		try{
			File srcFile = new File(filePath);
			FileOutputStream out = new FileOutputStream(new File(zipFilePath));
			zos = new ZipOutputStream(out);
			byte[] buf = new byte[1024];
			zos.putNextEntry(new ZipEntry(srcFile.getName()));
			int len;
			FileInputStream in = new FileInputStream(srcFile);
			while ((len = in.read(buf)) != -1){
				zos.write(buf, 0, len);
			}
			zos.closeEntry();
			in.close();
		}catch(Exception e){
			
		}finally{
			if(zos != null){
				try{
					zos.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}

	}
	
	/**
	 * 该方法用于解压一个zip文件，并返回解压后的文件名
	 * @param destPath DOC文件路径(不包含文件名)
	 * @param fileName 全路径文件名
	 * @return zip文件解压后的文件名
	 */
	public static String umZipFile(String fileName,String destPath) throws Exception{
		int buffer = 1024;
		BufferedOutputStream dest = null;
		String returnFileName = null;
		try {
			FileInputStream fis = new FileInputStream(fileName);
			ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
			ZipEntry entry = null;
			while ((entry = zis.getNextEntry()) != null) {
				if(entry.getName().startsWith("/")){
					//创建文件夹
					createFile(destPath+ entry.getName().substring(1, entry.getName().lastIndexOf("/")), true);
					returnFileName = destPath + entry.getName().substring(1, entry.getName().length());
				}else {
					returnFileName = destPath + entry.getName();
				}
				int count;
				byte data[] = new byte[1024];
				FileOutputStream fos = new FileOutputStream(returnFileName);
				dest = new BufferedOutputStream(fos, buffer);
				while ((count = zis.read(data, 0, buffer)) != -1) {
					dest.write(data, 0, count);
				}
				dest.flush();
				dest.close();
			}
			zis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		logger.info(returnFileName);
		return returnFileName;
	}
	
	
	 /**
	
	     * zip解压  
	
	     * @param srcFile        zip源文件
	
	     * @param destDirPath     解压后的目标文件夹
	
	     * @throws RuntimeException 解压失败会抛出运行时异常
	
	     */
	
	    public static void unZip(File srcFile, String destDirPath) throws RuntimeException {
	    	int BUFFER_SIZE=10*1024;
	        long start = System.currentTimeMillis();
	
	        // 判断源文件是否存在
	
	        if (!srcFile.exists()) {
	
	            throw new RuntimeException(srcFile.getPath() + "所指文件不存在");
	
	        }
	
	        // 开始解压
	
	        ZipFile zipFile = null;
	
	        try {

	            zipFile = new ZipFile(srcFile);
	
	            Enumeration<?> entries = zipFile.entries();
	
	            while (entries.hasMoreElements()) {
	
	                ZipEntry entry = (ZipEntry) entries.nextElement();
	
	                System.out.println("解压" + entry.getName());
	
	                // 如果是文件夹，就创建个文件夹
	
	                if (entry.isDirectory()) {
	
	                    String dirPath = destDirPath + "/" + entry.getName();
	
	                    File dir = new File(dirPath);
	
	                    dir.mkdirs();
	
	                } else {
	
	                    // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
	
	                    File targetFile = new File(destDirPath + "/" + entry.getName());
	
	                    // 保证这个文件的父文件夹必须要存在
	
	                    if(!targetFile.getParentFile().exists()){
	
	                        targetFile.getParentFile().mkdirs();
	
	                    }

	                    targetFile.createNewFile();
	
	                    // 将压缩文件内容写入到这个文件中
	
	                    InputStream is = zipFile.getInputStream(entry);
	
	                    FileOutputStream fos = new FileOutputStream(targetFile);
	
	                    int len;
	
	                    byte[] buf = new byte[BUFFER_SIZE];
	
	                    while ((len = is.read(buf)) != -1) {
	
	                        fos.write(buf, 0, len);
	
	                    }
	
	                    // 关流顺序，先打开的后关闭
	
	                    fos.close();
	
	                    is.close();
	
	                }
	
	            }
	
	            long end = System.currentTimeMillis();
	
	            System.out.println("解压完成，耗时：" + (end - start) +" ms");
	
	        } catch (Exception e) {
	
	            throw new RuntimeException("unzip error from ZipUtils", e);
	
	        } finally {
	
	            if(zipFile != null){
	
	                try {
	
	                    zipFile.close();
	
	                } catch (IOException e) {
	
	                    e.printStackTrace();
	
	                }
	
	            }
	
	        }
	
	    }
}
