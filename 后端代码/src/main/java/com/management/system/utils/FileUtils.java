package com.management.system.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletResponse;


public class FileUtils {

	/**
	 * 下载文件
	 * @param response
	 * @param file
	 * @param newFileName
	 */
	public static void downloadFile(HttpServletResponse response, File file, String newFileName) {
		try {
			response.setHeader("Content-Disposition", "attachment; filename=" + new String(newFileName.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
			BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
			InputStream is = new FileInputStream(file.getAbsolutePath());
			BufferedInputStream bis = new BufferedInputStream(is);
			int length = 0;
			byte[] temp = new byte[1024 * 10];
			while ((length = bis.read(temp)) != -1) {
				bos.write(temp, 0, length);
			}
			bos.flush();
			bis.close();
			bos.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String readFile(File file) throws IOException {
		// 注意这里我们已知需要读取的文件不会超过23Kb,所以fileSize强转成int
		int num,cursor = -1;
		int fileSize = (int)file.length() + 128;
		char[] stringBuffer = new char[fileSize];
		BufferedReader br = new BufferedReader(new FileReader(file));

		while ((num = br.read()) != -1) stringBuffer[++cursor] = (char) num;

		br.close();

		return new String(stringBuffer);
		//return new String(data, "GBK");//以GBK（什么编码格式）方式转
	}

}
