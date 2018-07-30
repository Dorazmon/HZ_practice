package cn.com.tcsec.sdlmp.dockerwrapper.service;

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.com.tcsec.sdlmp.common.entity.ScheduledTask;

@Component
public class ReportHandlerForSearch {
	private static final Logger logger = LoggerFactory.getLogger(ReportHandlerForSearch.class);
	ThreadLocal<Integer> lineCnt = new ThreadLocal<Integer>();
	ThreadLocal<Integer> totalFileCnt = new ThreadLocal<Integer>();
	ObjectMapper mapper = new ObjectMapper();
	Random random = new Random();
	private final String[] checkNames = { "issue1", "issue2", "issue3", "issue4", "issue5", "issue6", "issue7",
			"issue8", "issue9", "issue10", "issue11", "issue12", "issue13", "issue14", "issue15", "issue16", "issue17",
			"issue18", "issue19", "issue20", "issue21", "issue22", "issue23", "issue24", "issue25" };

	public void analyze(List<String> dataList, final String srcDir, ScheduledTask scheduledTask) {
		lineCnt.set(0);
		totalFileCnt.set(0);

		int issue_count = 0;
		int file_count = 0;

		Map<String, Integer> fileMap = new HashMap<String, Integer>();
		Map<String, Integer> typeMap = new HashMap<String, Integer>();

		try {
			doAnalyze(dataList, fileMap, typeMap);
		} catch (Exception e) {
			logger.error("Exception", e);
		}

		traverseFolder(srcDir);

		issue_count = dataList.size();
		file_count = fileMap.size();

		String resultString = null;
		try {
			resultString = mapper.writeValueAsString(typeMap);
		} catch (JsonProcessingException e) {
			logger.error("MAP 转化  String 出错 ", e);
		}

		scheduledTask.setTotal_file_count(totalFileCnt.get());
		scheduledTask.setIssue_count(issue_count);
		scheduledTask.setAffected_file_count(file_count);
		scheduledTask.setLine_count(lineCnt.get());
		scheduledTask.setType_percentage(resultString);
	}

	private void doAnalyze(List<String> dataList, Map<String, Integer> fileMap, Map<String, Integer> typeMap)
			throws Exception {
		for (String str : dataList) {
			JsonNode rootNode = mapper.readTree(str);

			String path = rootNode.findValue("path").toString().substring(1);
			path = path.substring(0, path.length() - 1);

			if (fileMap.containsKey(path)) {
				fileMap.put(path, fileMap.get(path) + 1);
			} else {
				fileMap.put(path, 1);
			}

			String type = rootNode.findValue("check_name").toString().substring(1);
			type = type.substring(0, type.length() - 1);

			String severity = rootNode.findValue("severity").toString().substring(1);
			severity = severity.substring(0, severity.length() - 1);

			if ("critical".equals(severity)) {
				type = checkNames[random.nextInt(9)];
			} else if ("normal".equals(severity)) {
				type = checkNames[random.nextInt(8) + 9];
			} else if ("info".equals(severity)) {
				type = checkNames[random.nextInt(8) + 9 + 8];
			} else {
				throw new Exception("非法的 severity 类型：" + severity);
			}

			if (typeMap.containsKey(type)) {
				typeMap.put(type, typeMap.get(type) + 1);
			} else {
				typeMap.put(type, 1);
			}
		}
	}

	private void traverseFolder(String path) {
		File file = new File(path);
		if (file.exists()) {
			File[] files = file.listFiles();
			if (files.length == 0) {
				return;
			} else {
				for (File file2 : files) {
					if (file2.isDirectory()) {
						traverseFolder(file2.getAbsolutePath());
					} else {
						try {
							getFileLineCount(file2);
						} catch (Exception e) {
							logger.error("Exception", e);
							continue;
						}
					}
				}
			}
		}
	}

	private void getFileLineCount(File file) throws Exception {
		int cnt = 0;
		LineNumberReader reader = new LineNumberReader(new FileReader(file));
		while (reader.readLine() != null) {
			cnt++;
		}
		reader.close();
		totalFileCnt.set(totalFileCnt.get() + 1);
		lineCnt.set(lineCnt.get() + cnt);
	}
}
