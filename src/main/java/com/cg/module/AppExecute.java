package com.cg.module;

import com.cg.schema.ResultInfo;
import com.cg.utils.FileUtil;

import java.io.File;
import java.util.List;

/**
 * Author: cg
 * Date: 2021/1/28 15:59
 */
public class AppExecute {

	private static AppExecute instance;

	private ResultInfo resultInfo;

	public static void init() {
		instance = new AppExecute();
		instance.resultInfo = new ResultInfo();
	}


	public static void execute(String[] args) {
		if (instance == null) {
			init();
		}
		// 参数初始化
		ArgsFilter.init(args);
		// 参数分发
		argsSwitch(args);
	}

	public static void argsSwitch(String[] args) {
		if (args.length <= 0) {
			System.out.println("please input arguments");
			System.out.println(ArgsFilter.argsHelp());
			return;
		}
		if (ArgsFilter.inArgs("--value")) {
			executeSearch();
			return;
		}
		if (ArgsFilter.inArgs("--help")) {
			System.out.println(ArgsFilter.argsHelp());
			return;
		}
		if (ArgsFilter.inArgs("--version")) {
			System.out.println(ArgsFilter.argsVersion());
			return;
		}
		System.out.println(ArgsFilter.argsHelp());
	}

	public static void executeSearch() {
		String arg = "--value";
		String[] values = ArgsFilter.getValuesOne(arg);
		if (values == null || values.length < 2) {
			System.out.println(arg + " assignment unreasonable, --value path matchStr(use --help find usage)");
			return;
		}
		String path = values[0];
		String value = values[1];
		if (path == null || path.isEmpty() || value == null || value.isEmpty()) {
			System.out.printf("arguments unreasonable, path: %s, matchStr: %s\n", path, value);
			return;
		}
		File file = new File(path);
		if (!file.exists()) {
			System.out.println("file or directory not exists, path: " + path);
			return;
		}
		instance.search(path, value);
		System.out.println(instance.resultInfo.toString());
	}


	public void search(String path, String value) {
		resultInfo.setValue(value);
		resultInfo.setPath(path);
		long bMills = System.currentTimeMillis();
		List<File> files = FileUtil.getFiles(path);
		for (File file : files) {
			SundaySearch.search(file, value, this.resultInfo);
		}
		long endMills = System.currentTimeMillis();

		double cost = (endMills - bMills) / 1000d;
		resultInfo.setTFileNum(files.size());
		resultInfo.setCost(cost);
		resultInfo.setMills(endMills);
	}

}
