package com.cg.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: cg
 * Date: 2021/1/28 15:58
 */
public class FileUtil {

	public static List<File> getFiles(String dir) {
		return getFiles(dir, null);
	}

	public static List<File> getFiles(String dir, List<File> res) {
		if (res == null) {
			res = new ArrayList<>();
		}
		File fileDir = new File(dir);
		if (fileDir.isFile()) {
			res.add(fileDir);
			return res;
		}
		File[] nextFiles = fileDir.listFiles();
		if (nextFiles == null) {
			return res;
		}
		for (File fileItem : nextFiles) {
			if (fileItem.isDirectory()) {
				getFiles(fileItem.getPath(), res);
			} else {
				res.add(fileItem);
			}
		}
		return res;
	}

	public static String readFile(String filePath) {
		return readFile(filePath, StandardCharsets.UTF_8);
	}

	/**
	 * 按照编码格式来读取
	 *
	 * @param filePath 文件路径
	 * @param charset  编码, 默认UTF-8
	 * @return String 内容
	 */
	public static String readFile(String filePath, Charset charset) {
		StringBuilder content = new StringBuilder();
		try {
			FileInputStream fis = new FileInputStream(filePath);
			InputStreamReader isr = new InputStreamReader(fis, charset);
			BufferedReader br = new BufferedReader(isr);
			String line;
			while ((line = br.readLine()) != null) {
				content.append(line);
				content.append(System.lineSeparator());
			}
			br.close();
			isr.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content.toString();
	}


}
