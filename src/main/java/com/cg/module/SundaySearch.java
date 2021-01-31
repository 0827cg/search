package com.cg.module;

import com.cg.schema.ResultInfo;

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
 * Date: 2021/1/28 15:57
 */
public class SundaySearch {

	public static void search(File file, String value, ResultInfo resultInfo) {
		search(file, value, resultInfo, StandardCharsets.UTF_8);
	}

	public static void search(File file, String value, ResultInfo resultInfo, Charset charset) {
		if (file == null) {
			return;
		}
		String path = file.getPath();
		try {
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, charset);
			BufferedReader br = new BufferedReader(isr);
			int lineNum = 0;
			String line;
			char[] valueChar = value.toCharArray();
			while ((line = br.readLine()) != null) {
				lineNum++;
				char[] lineChar = line.toCharArray();
				List<Integer> idx = search(lineChar, valueChar);
				// 未存在结果的行也进行更新(只为了更新总字符数)
				resultInfo.updateFileInfo(path, lineNum, idx, lineChar.length);
			}
			br.close();
			isr.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 一组字符串中查找value出现过的位置
	 *
	 * @param total 待从此处查找的字符串
	 * @param value 待查找的字符串
	 * @return List<Integer></Integer>
	 */
	public static List<Integer> search(String total, String value) {
		if (total == null || total.isEmpty() || value == null || value.isEmpty()) {
			return null;
		}
		char t[] = total.toCharArray();
		char v[] = value.toCharArray();
		return search(t, v);
	}

	public static List<Integer> search(char[] t, char[] v) {
		if (t == null || t.length <= 0 || v == null || v.length <= 0) {
			return null;
		}
		List<Integer> res = new ArrayList<>();
		int tIdx = 0;
		int vIdx = 0;
		while (tIdx < t.length) {
			if (t[tIdx] == v[vIdx]) {

				tIdx++;
				if (vIdx != v.length - 1) {
					vIdx++;
					continue;
				}
				res.add(tIdx - vIdx);
				vIdx = 0;
				continue;
			}
			int gap = v.length - (vIdx + 1);
			// 末尾+1
			int tryMax = tIdx + gap + 1;
			if (tryMax >= t.length) {
				break;
			}
			// 查找下一个字符是否在value中存在
			int itemIndex = getCharInListFirst(v, t[tryMax]);
			vIdx = 0;
			if (itemIndex == -1) {
				//不存在则
				tIdx = tryMax + 1;
				continue;
			}
			tIdx = tryMax - itemIndex;
		}
		return res;
	}

	private static int getCharInListFirst(char[] arr, char v) {
		// 查找下一个字符是否在value中存在
		for (int i = 0; i < arr.length; i++) {
			if (v == arr[i]) {
				return i;
			}
		}
		return -1;
	}


	public static int searchOld(String strTotal, String strSearch) {

		char charTotal[] = strTotal.toCharArray();
		char charSearch[] = strSearch.toCharArray();

		int t = 0;
		int s = 0;
		int existCount = 0;
		while (s < charSearch.length && t < charTotal.length) {

			if (charSearch[s] == charTotal[t]) {

				if ((s + 1) != charSearch.length) {
					s++;
					t++;
				} else {
					existCount++;
					if (charTotal.length - (t + 1) >= charSearch.length) {
						s = 0;
						t++;
					} else {
						break;
					}
				}
			} else {
				int num = t + charSearch.length;
				int index = -1;
				if (num < charTotal.length) {

					for (int i = 0; i < charSearch.length; i++) {
						if (charTotal[num] == charSearch[i]) {
							index = i;
							break;
						}
					}
					if (index != -1) {
						t = t + (charSearch.length - index);
						s = 0;

					} else {
						t = num + 1;
						s = 0;
					}
				} else {
					break;
				}
			}
			if (t >= charTotal.length) {
				break;
			}
		}
		return existCount;

	}

}
