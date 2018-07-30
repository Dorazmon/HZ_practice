package cn.com.tcsec.sdlmp.dockerwrapper.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import cn.com.tcsec.sdlmp.common.entity.ScheduledTask;
import cn.com.tcsec.sdlmp.common.util.Sha256;
import cn.com.tcsec.sdlmp.common.util.StringUtil;

@Component
public class DockerHandlerService {
	private static final Logger logger = LoggerFactory.getLogger(DockerHandlerService.class);

	@Autowired
	ReportHandlerForSearch reportHandlerForSearch;

	private static final String workDir = "/code/";

	private List<String> execCommand(List<String> cmdList) throws Exception {
		List<String> results = new ArrayList<String>();
		try {
			ProcessBuilder pBuilder = new ProcessBuilder(cmdList);
			pBuilder.directory(new File(workDir));
			pBuilder.redirectErrorStream(true);
			Process process = pBuilder.start();
			Scanner scanner = new Scanner(process.getInputStream());
			while (scanner.hasNextLine()) {
				results.add(scanner.nextLine());
			}
			scanner.close();

		} catch (Exception e) {
			logger.error("execCommand error:{}", cmdList.toString());
			logger.error("results:{}", results);
			throw e;
		}

		return results;
	}

	public List<String> execCodeScan(ScheduledTask scheduledTask) throws Exception {
		String srcDir = null;
		String url = scheduledTask.getProject_url();
		if (url.startsWith("git")) {
			srcDir = workDir + UUID.randomUUID().toString();
			List<String> cmdExecuteClone = new ArrayList<String>();
			cmdExecuteClone.add("git");
			cmdExecuteClone.add("clone");
			cmdExecuteClone.add(url);
			cmdExecuteClone.add(srcDir);
			execCommand(cmdExecuteClone);
		} else {
			srcDir = url;
		}

		List<String> cmdExecuteCode = new ArrayList<String>();
		cmdExecuteCode.add("sourcecodeanalyzer");
		cmdExecuteCode.add(srcDir);
		cmdExecuteCode.add(scheduledTask.getProject_language());
		List<String> results = execCommand(cmdExecuteCode);

		scheduledTask.setList(results);
		results = extractMainCode(results, srcDir, scheduledTask);
		reportHandlerForSearch.analyze(results, srcDir, scheduledTask);

		return results;
	}

	private List<String> extractMainCode(List<String> dataList, final String srcDir, ScheduledTask scheduledTask)
			throws Exception {
		if (dataList.isEmpty()) {
			return dataList;
		}

		List<String> resultList = new ArrayList<String>();
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Integer> pathMap = new HashMap<String, Integer>();
		List<String> lineCode = null;
		ObjectNode resultMap = null;
		String lastPath = null;
		FileReader reader = null;
		BufferedReader br = null;
		String tmpStr = null;
		String mainCode = null;

		for (String jsonStr : dataList) {
			JsonNode rootNode = mapper.readTree(jsonStr);

			String path = findPath(rootNode);
			int beginLine = findBeginLine(rootNode);
			int endLine = findEndLine(rootNode);
			String check_name = findCheckName(rootNode);

			if (pathMap.containsKey(path)) {
				pathMap.put(path, pathMap.get(path) + 1);
			} else {
				pathMap.put(path, 1);
			}

			if (lastPath == null || !path.equals(lastPath)) {
				lineCode = new ArrayList<String>();

				br = new BufferedReader(new FileReader(srcDir + "/" + path));
				while ((tmpStr = br.readLine()) != null) {
					lineCode.add(tmpStr);
				}

				if (br != null) {
					br.close();
				}
				if (reader != null) {
					reader.close();
				}
				lastPath = path;
			}

			mainCode = "";
			for (int line = beginLine; line <= endLine; line++) {
				mainCode += lineCode.get(line - 1);
			}
			scheduledTask.setIssue_key(Sha256.encrypt(path + check_name + StringUtil.extractEffectiveCode(mainCode)));

			if (beginLine < 3) {
				beginLine = 1;
			} else {
				beginLine = beginLine - 2;
			}

			if (lineCode.size() - endLine < 2) {
				endLine = lineCode.size();
			} else {
				endLine = endLine + 2;
			}

			resultMap = ObjectNode.class.cast(rootNode);

			resultMap.put("mainCodeBeginLine", beginLine);
			mainCode = "";
			for (; beginLine <= endLine; beginLine++) {
				mainCode += lineCode.get(beginLine - 1) + "\n";
			}

			resultMap.put("mainCode", mainCode);

			resultList.add(resultMap.toString());
			scheduledTask.addReport(resultMap.toString(), scheduledTask.getIssue_key());
		}

		return resultList;
	}

	protected String findPath(JsonNode rootNode) throws Exception {
		String path = rootNode.findValue("path").toString().substring(1);
		path = path.substring(0, path.length() - 1);
		return path;
	}

	protected int findBeginLine(JsonNode rootNode) throws Exception {
		return Integer.valueOf(rootNode.findValue("begin").toString());
	}

	protected int findEndLine(JsonNode rootNode) throws Exception {
		return Integer.valueOf(rootNode.findValue("end").toString());
	}

	protected String findCheckName(JsonNode rootNode) throws Exception {
		return rootNode.findValue("check_name").toString();
	}
}