package com.cg.module;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: cg
 * Date: 2021/1/30 11:57
 */
public class ArgsFilter {

	// 参数格式, key: 详细参数名, value: 简写
	private static final HashMap<String, String> argsFormat = new HashMap<>();

	// 所有参数, 包含简写
	private static final HashMap<String, Boolean> argsAll = new HashMap<>();

	// 传入的参数
	private String[] args;

	private static ArgsFilter instance;

	static {
		argsFormat.put("--value", "-v");
		argsFormat.put("--help", "-H");
		argsFormat.put("--version", "-V");
		for (Map.Entry<String, String> entry : argsFormat.entrySet()) {
			argsAll.put(entry.getKey(), true);
			argsAll.put(entry.getValue(), true);
		}
	}

	public static void init(String[] args) {
		instance = new ArgsFilter();
		instance.args = args;
	}

	/**
	 * 根据传入的key值来从args中获取对应的值
	 * <p>
	 * --path="xxx"
	 * --path:"xxx"
	 *
	 * @param key 如"--path",
	 * @return String value
	 */
	public static String getValue(String key) {
		if (key == null || !argsFormat.containsKey(key)) {
			System.out.println("不存在该参数: " + key);
			return null;
		}
		String simple = argsFormat.get(key);
		for (String arg : instance.args) {
			if (arg.startsWith(key)) {
				return arg.substring(key.length() + 1);
			}
			if (arg.startsWith(simple)) {
				return arg.substring(simple.length() + 1);
			}
		}
		return null;
	}


	/**
	 * 根据key来获取后面传入的参数
	 * <p>
	 * 如格式参数： --value xxx1 xxx2
	 * 如果key为--value, 则返回[xx1, xx2]
	 *
	 * @param key String
	 * @return String[]
	 */
	public static String[] getValuesOne(String key) {
		if (key == null || !argsFormat.containsKey(key)) {
			System.out.println("不存在该参数: " + key);
			return null;
		}
		if (instance.args.length <= 0) {
			return null;
		}
		String[] value = analysisArgs(key, instance.args);
		if (value != null) {
			return value;
		}
		String simple = argsFormat.get(key);
		return analysisArgs(simple, instance.args);
	}


	/**
	 * 根据args中的第一个元素判断是否与key相同,
	 * 相同则将后面的元素返回
	 *
	 * @param key  String 如--value
	 * @param args String[]
	 * @return String[]
	 */
	private static String[] analysisArgs(String key, String[] args) {
		if (instance.args[0].startsWith(key)) {
			String[] item = new String[instance.args.length - 1];
			for (int i = 0; i < instance.args.length; i++) {
				if (i == 0) {
					continue;
				}
				item[i - 1] = instance.args[i];
			}
			return item;
		}
		return null;
	}

	/**
	 * 非简写key来判断简写和非简写key是否存在传入的args中
	 * 内部使用
	 *
	 * @param key 非简写key
	 * @return boolean true: 存在
	 */
	public static boolean inArgs(String key) {
		if (!argsFormat.containsKey(key)) {
			return false;
		}
		String value = instance.args[0];
		if (value.equals(key)) {
			return true;
		}
		String simple = argsFormat.get(key);
		return value.equals(simple);
	}

	public static boolean isReasonable(String key) {
		return argsAll.containsKey(key);
	}

	/**
	 * 根据简写来获得非简写key
	 *
	 * @param simple String
	 * @return String
	 */
	private static String getKeyBySimple(String simple) {
		for (Map.Entry<String, String> entry : argsFormat.entrySet()) {
			if (entry.getValue().equals(simple)) {
				return entry.getKey();
			}
		}
		return null;
	}

	public static String argsHelp() {
		StringBuilder sb = new StringBuilder();
		sb.append("Usage: java -jar search.jar xxx xxx \n");
		sb.append("--value  -v: path matchStr. search matchStr in path(list file)\n");
		sb.append("--version  -V: show information\n");
		return sb.toString();
	}

	public static String argsVersion() {
		StringBuilder sb = new StringBuilder();
		sb.append("search value from file in directory, list file\n");
		sb.append("version: 1.0.0\n");
		sb.append("author: cg\n");
		sb.append("time: 2021-01-30 17:41\n");
		return sb.toString();
	}
}
