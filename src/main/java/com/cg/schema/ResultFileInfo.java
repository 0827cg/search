package com.cg.schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: cg
 * Date: 2021/1/28 15:56
 */
public class ResultFileInfo {

	// 文件路径
	private String filePath;

	// key: line num, value: cols
	private HashMap<Integer, List<Integer>> lines;

	// 总字符数量
	private long charNum;


	/**
	 * 获取总的出现次数
	 *
	 * @return int
	 */
	public int getTotalNum() {
		int num = 0;
		for (Map.Entry<Integer, List<Integer>> entry : lines.entrySet()) {
			List<Integer> idx = entry.getValue();
			num += idx.size();
		}
		return num;
	}

	/**
	 * 每行对应的出现的列数值
	 * 仅仅为了格式化, 并且输出有从小到达的顺序
	 *
	 * @return String
	 */
	public String getTotalLineInfo() {
		StringBuilder sb = new StringBuilder();
		List<Integer> lineIds = new ArrayList<>();
		for (Map.Entry<Integer, List<Integer>> entry : lines.entrySet()) {
			lineIds.add(entry.getKey());
		}
		// 降序
		lineIds.sort(Integer::compare);

//		lineIds.sort(new Comparator<Integer>() {
//			@Override
//			public int compare(Integer o1, Integer o2) {
//				return Integer.compare(o1, o2);
//			}
//		});
		for (Integer id : lineIds) {
			List<Integer> colList = lines.get(id);
			sb.append(id).append(":").append(colList).append(",");
		}
		return sb.toString();
	}


	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public HashMap<Integer, List<Integer>> getLines() {
		return lines;
	}

	public void setLines(HashMap<Integer, List<Integer>> lines) {
		this.lines = lines;
	}

	public long getCharNum() {
		return charNum;
	}

	public void setCharNum(long charNum) {
		this.charNum = charNum;
	}
}
