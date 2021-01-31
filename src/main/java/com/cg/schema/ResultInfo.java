package com.cg.schema;

import com.cg.utils.TimeUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: cg
 * Date: 2021/1/28 15:56
 */
public class ResultInfo {

	// key: path, value: ResultFileInfo
	private final HashMap<String, ResultFileInfo> infos;

	// 待查找的字符串
	private String value;

	// 待查找的路径
	private String path;

	// 总的已经查找的字符数量
	private long tCharNum;

	// 总的已经查找的文件数量
	private long tFileNum;

	// 消耗时间, 秒
	private double cost;

	// 结束时间戳
	private long mills;

	public ResultInfo() {
		infos = new HashMap<>();
	}

	/**
	 * 更新有存在匹配结果的文件信息
	 *
	 * @param path    改文件路径
	 * @param lineNum 行数
	 * @param idx     该行所出现的列集合
	 * @param charNum 该行的总字符数
	 */
	public void updateFileInfo(String path, int lineNum, List<Integer> idx, int charNum) {
		if (idx == null || idx.size() <= 0) {
			// 该行未发现, 仅更新总字符数
			tCharNum += charNum;
			return;
		}
		if (!infos.containsKey(path)) {
			ResultFileInfo fileInfo = this.addFileInfo(path, lineNum, idx, charNum);
			infos.put(path, fileInfo);
			return;
		}
		ResultFileInfo info = infos.get(path);
		info.getLines().put(lineNum, idx);

		long tmpNum = info.getCharNum();
		info.setCharNum(tmpNum + charNum);

		tCharNum += charNum;
	}

	private ResultFileInfo addFileInfo(String path, int lineNum, List<Integer> idx, long charNum) {
		ResultFileInfo info = new ResultFileInfo();
		info.setFilePath(path);
		info.setLines(new HashMap<>());
		info.setCharNum(charNum);

		info.getLines().put(lineNum, idx);
		return info;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("path: ").append(path).append("\n");
		sb.append("match: ").append(value).append("\n");
		sb.append("==========result==========\n");
		int id = 1;
		for (Map.Entry<String, ResultFileInfo> entry : infos.entrySet()) {
			ResultFileInfo fileInfo = entry.getValue();
			sb.append("---> id: ").append(id).append("\n");
			sb.append("file path: ").append(fileInfo.getFilePath()).append("\n");
			sb.append("line info: ").append(fileInfo.getTotalLineInfo()).append("\n");
			sb.append("total num: ").append(fileInfo.getTotalNum()).append("\n");
			id++;
		}
		sb.append("--->> total").append("\n");
		sb.append("cost: ").append(cost).append(" s\n");
		sb.append("total file num: ").append(tFileNum).append("\n");
		sb.append("total char num: ").append(tCharNum).append("\n");
		sb.append("time: ").append(TimeUtil.millsToDate(mills)).append("\n");
		sb.append("==========================").append("\n");
		return sb.toString();
	}


	public HashMap<String, ResultFileInfo> getInfos() {
		return infos;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getTCharNum() {
		return tCharNum;
	}

	public void setTCharNum(long tCharNum) {
		this.tCharNum = tCharNum;
	}

	public long getTFileNum() {
		return tFileNum;
	}

	public void setTFileNum(long tFileNum) {
		this.tFileNum = tFileNum;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public long getMills() {
		return mills;
	}

	public void setMills(long mills) {
		this.mills = mills;
	}
}
