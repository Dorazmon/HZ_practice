package cn.com.tcsec.sdlmp.search.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.com.tcsec.sdlmp.common.entity.IssueLevel;
import cn.com.tcsec.sdlmp.common.entity.ScheduledTask;
import cn.com.tcsec.sdlmp.common.exception.ErrorContent;
import cn.com.tcsec.sdlmp.search.db.entity.OpenHistory;
import cn.com.tcsec.sdlmp.search.db.entity.OpenProject;
import cn.com.tcsec.sdlmp.search.db.entity.OpenTotal;
import cn.com.tcsec.sdlmp.search.db.entity.Region;
import cn.com.tcsec.sdlmp.search.elasticsearch.ElasticSearchClient;
import cn.com.tcsec.sdlmp.search.export.entity.CountryHistory;
import cn.com.tcsec.sdlmp.search.export.entity.IssueRanking;
import cn.com.tcsec.sdlmp.search.export.entity.OpenProjectRanking;
import cn.com.tcsec.sdlmp.search.export.entity.TotalIssue;
import cn.com.tcsec.sdlmp.search.mapper.IssueLevelMapper;
import cn.com.tcsec.sdlmp.search.mapper.OpenHistoryMapper;
import cn.com.tcsec.sdlmp.search.mapper.OpenProjectMapper;
import cn.com.tcsec.sdlmp.search.mapper.RegionMapper;
import cn.com.tcsec.sdlmp.search.mapper.TotalCountMapper;
import redis.clients.jedis.JedisPool;

@Component
@MapperScan("cn.com.tcsec.sdlmp.search.mapper")
public class SearchService {
	private static final Logger logger = LoggerFactory.getLogger(SearchService.class);
	ObjectMapper mapper = new ObjectMapper();
	Random random = new Random();
	private final String[] levels = { "high", "mid", "low" };

	@Autowired
	OpenProjectMapper openProjectMapper;
	@Autowired
	RegionMapper regionMapper;
	@Autowired
	TotalCountMapper totalCountMapper;
	@Autowired
	OpenHistoryMapper openHistoryMapper;
	@Autowired
	IssueLevelMapper issueLevelMapper;

	@Autowired
	FlawWarning flawWarning;
	@Autowired
	ElasticSearchClient elasticSearchClient;

	@Autowired
	JedisPool jedisPool;

	@Autowired
	JmsMessagingTemplate jmsMessagingTemplate;

	public Object addOpenProject(String access_token, String project_name, String path, String language) {
		OpenProject oldOpenProject = null;
		try {
			oldOpenProject = openProjectMapper.selectProject(project_name);
		} catch (Exception e1) {
			logger.error("查询tb_open_project表 发生错误", e1);
			return ErrorContent.SERVER_ERROR;
		}

		if (oldOpenProject == null) {
			int iRegionCount = 0;
			try {
				iRegionCount = regionMapper.selectCount();
			} catch (Exception e1) {
				logger.error("查询tb_region.selectCount表 发生错误", e1);
				return ErrorContent.SERVER_ERROR;
			}
			if (iRegionCount == 0) {
				logger.error("查询tb_region.selectCount表 得到数据异常");
				return ErrorContent.SERVER_ERROR;
			}
			Region region = null;
			try {
				int k = 0;
				region = regionMapper.selectRegion(
						random.nextInt(99999999) % ((k = random.nextInt(iRegionCount)) == 0 ? iRegionCount : k));
			} catch (Exception e1) {
				logger.error("查询tb_region.selectRegion表 发生错误", e1);
				return ErrorContent.SERVER_ERROR;
			}
			if (region == null) {
				logger.error("查询tb_region.selectRegion表 得到数据异常");
				return ErrorContent.SERVER_ERROR;
			}

			OpenProject openProject = new OpenProject();
			openProject.setName(project_name);
			openProject.setLanguage(language);
			openProject.setUrl(path);
			openProject.setRegion_id(region.getId());
			try {
				openProjectMapper.insertProject(openProject);
			} catch (Exception e) {
				logger.error("插入tb_open_project表 发生错误", e);
				return ErrorContent.SERVER_ERROR;
			}
			oldOpenProject = openProject;
		}

		ScheduledTask scheduledTask = new ScheduledTask();
		scheduledTask.setProject_id(oldOpenProject.getId());
		scheduledTask.setProject_language(oldOpenProject.getLanguage());
		scheduledTask.setProject_name(oldOpenProject.getName());
		scheduledTask.setProject_url(oldOpenProject.getUrl());
		scheduledTask.setReturnMsqDestName("search.docker.addReport");

		jmsMessagingTemplate.convertAndSend("docker.addProject", scheduledTask);
		return ErrorContent.OK;
	}

	@SuppressWarnings("unchecked")
	public void addScanResult(ScheduledTask scheduledTask) {
		List<IssueLevel> issueList = null;
		try {
			issueList = issueLevelMapper.selectIssueLevelList();
		} catch (Exception e) {
			logger.error("Select tb_issue_level 出错", e);
			return;
		}

		if (issueList.isEmpty()) {
			logger.error("Select tb_issue_level 为空");
			return;
		}

		int high = 0;
		int mid = 0;
		int low = 0;

		Map<String, Integer> map;
		try {
			map = mapper.readValue(scheduledTask.getType_percentage(), Map.class);
		} catch (IOException e1) {
			logger.error("jackson 解析percentage 出错 {}", scheduledTask.getType_percentage(), e1);
			return;
		}

		for (Entry<String, Integer> entry : map.entrySet()) {
			IssueLevel issueLevel = issueList.get(Integer.valueOf(entry.getKey().substring(5)) - 1);
			if (levels[0].equals(issueLevel.getLevel())) {
				high++;
			} else if (levels[1].equals(issueLevel.getLevel())) {
				mid++;
			} else if (levels[2].equals(issueLevel.getLevel())) {
				low++;
			} else {
				logger.error("出现了非法的漏洞等级");
				return;
			}
		}

		OpenTotal openTotal = null;
		try {
			openTotal = totalCountMapper.selectOpenTotal();
		} catch (Exception e) {
			logger.error("Select tb_open_total 出错", e);
			return;
		}

		if (openTotal == null) {
			openTotal = new OpenTotal();
			openTotal.setFile_count(scheduledTask.getTotal_file_count());
			openTotal.setLine_count(scheduledTask.getLine_count());
			openTotal.setHigh_issue_count(high);
			openTotal.setMid_issue_count(mid);
			openTotal.setLow_issue_count(low);

			try {
				totalCountMapper.insertOpenTotal(openTotal);
			} catch (Exception e) {
				logger.error("insert tb_open_total 出错", e);
				return;
			}
		} else {
			openTotal.setFile_count(scheduledTask.getTotal_file_count() + openTotal.getFile_count());
			openTotal.setLine_count(scheduledTask.getLine_count() + openTotal.getLine_count());
			openTotal.setHigh_issue_count(openTotal.getHigh_issue_count() + high);
			openTotal.setMid_issue_count(openTotal.getMid_issue_count() + mid);
			openTotal.setLow_issue_count(openTotal.getLow_issue_count() + low);
			try {
				totalCountMapper.updateOpenTotal(openTotal);
			} catch (Exception e) {
				logger.error("update tb_open_total 出错", e);
				return;
			}
		}

		int iResult = 0;
		try {
			iResult = openProjectMapper.updateProject(scheduledTask.getType_percentage(), scheduledTask.getProject_id(),
					levels[random.nextInt(2)], scheduledTask.getIssue_count());
		} catch (Exception e) {
			logger.error("update tb_open_project 出错", e);
			return;
		}
		if (iResult != 1) {
			logger.error("update tb_open_project 未找到对应数据：{}{}", scheduledTask.getProject_id(),
					scheduledTask.getProject_name());
		}

		OpenProject openProject = null;
		try {
			openProject = openProjectMapper.selectProjectByid(scheduledTask.getProject_id());
		} catch (Exception e) {
			logger.error("select tb_open_project 出错", e);
			return;
		}
		if (openProject == null) {
			logger.error("select tb_open_project 结果为空");
			return;
		}

		OpenHistory openHistory = null;
		try {
			openHistory = openHistoryMapper.selectOpenHistory(String.valueOf(LocalDate.now().getYear()),
					openProject.getRegion_id());
		} catch (Exception e) {
			logger.error("select tb_open_history 出错", e);
			return;
		}
		if (openHistory == null) {
			openHistory = new OpenHistory();
			openHistory.setDate(String.valueOf(LocalDate.now().getYear()));
			openHistory.setIssue_count((double) scheduledTask.getIssue_count());
			openHistory.setRegion_id(openProject.getRegion_id());
			try {
				openHistoryMapper.insertOpenHistory(openHistory);
			} catch (Exception e) {
				// TODO 资源锁
				logger.error("insert tb_open_history 出错 ", e);
				return;
			}
		}

		iResult = 0;
		try {
			iResult = openHistoryMapper.updateOpenHistory(openHistory.getIssue_count() + scheduledTask.getIssue_count(),
					openHistory.getId());
		} catch (Exception e) {
			logger.error("select tb_open_history 出错", e);
			return;
		}

		if (iResult != 1) {
			logger.error("update tb_open_history 未找到对应数据：{}", openHistory.getId());
		}

		// try {
		// elasticSearchClient.update(scheduledTask.getProject_name(),
		// scheduledTask.getList());
		// } catch (Exception e) {
		// logger.error("elasticSearch update 出错", e);
		// return;
		// }
	}

	public Object getTotalCount() {
		OpenTotal openTotal = null;
		try {
			openTotal = totalCountMapper.selectOpenTotal();
		} catch (Exception e) {
			logger.error("select tb_open_total 出错", e);
			return ErrorContent.SERVER_ERROR;
		}

		if (openTotal == null) {
			openTotal = new OpenTotal();
			openTotal.setFile_count(0);
			openTotal.setHigh_issue_count(0);
			openTotal.setMid_issue_count(0);
			openTotal.setLow_issue_count(0);
			openTotal.setLine_count(0);
		}

		TotalIssue totalIssue = new TotalIssue();
		totalIssue.setFile_count(openTotal.getFile_count());
		totalIssue.setLine_count(openTotal.getLine_count());
		totalIssue.setIssue_count(
				openTotal.getHigh_issue_count() + openTotal.getMid_issue_count() + openTotal.getLow_issue_count());
		totalIssue.setDangerCoefficient((openTotal.getLine_count() == 0) ? 0
				: ((openTotal.getHigh_issue_count() + openTotal.getMid_issue_count() + openTotal.getLow_issue_count())
						* 10000 / openTotal.getLine_count()));

		return totalIssue;
	}

	public Object getCountryCount() {
		List<Map<String, String>> list = null;
		try {
			list = openHistoryMapper.selectOpenHistoryForCountry();
		} catch (Exception e) {
			logger.error("select tb_open_history 出错", e);
			return ErrorContent.SERVER_ERROR;
		}

		if (list == null) {
			return null;
		}

		CountryHistory country = new CountryHistory();
		for (Map<String, String> map : list) {
			country.addElement(map.get("date"), map.get("country"), map.get("region"),
					String.valueOf(map.get("issue_count")));
		}

		return country.getList();
	}

	public Object getChinaCount() {
		List<Map<String, String>> list = null;
		try {
			list = openHistoryMapper.selectOpenHistoryForChina();
		} catch (Exception e) {
			logger.error("select tb_open_history 出错", e);
			return ErrorContent.SERVER_ERROR;
		}

		if (list == null) {
			return null;
		}

		CountryHistory country = new CountryHistory();
		for (Map<String, String> map : list) {
			country.addElement(map.get("date"), map.get("region"), map.get("region"),
					String.valueOf(map.get("issue_count")));
		}

		return country.getList();
	}

	public IssueRanking getSortIssueCount(String risk_type) {
		Map<String, Double> map = null;
		try {
			map = openProjectMapper.selectIssueMap();
		} catch (Exception e) {
			logger.error("select tb_open_project 出错", e);
			return null;
		}
		if (map == null) {
			return null;
		}

		List<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(map.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
			@Override
			public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
				return (int) (o2.getValue() - o1.getValue());
			}
		});

		IssueRanking issueRanking = new IssueRanking();
		for (Map.Entry<String, Double> entry : list) {
			if ("total".equals(entry.getKey())) {
				issueRanking.add(entry.getKey(), entry.getValue().intValue(), null);
				continue;
			}
			if (entry.getValue().intValue() == 0) {
				continue;
			}
			IssueLevel issueLevel = null;
			try {
				issueLevel = issueLevelMapper.selectIssueLevel(entry.getKey());
			} catch (Exception e) {
				logger.error("select tb_issue_level 出错", e);
				return null;
			}

			if (("high".equals(risk_type)
					&& ("mid".equals(issueLevel.getLevel()) || "low".equals(issueLevel.getLevel())))
					|| ("middle".equals(risk_type)
							&& ("high".equals(issueLevel.getLevel()) || "low".equals(issueLevel.getLevel())))
					|| ("low".equals(risk_type)
							&& ("mid".equals(issueLevel.getLevel()) || "high".equals(issueLevel.getLevel())))) {
				continue;
			}

			issueRanking.add(issueLevel.getDesc_chinese(), entry.getValue().intValue(), issueLevel.getLevel());
		}

		return issueRanking;
	}

	public Object getWarnCount() {
		try {
			return flawWarning.getWarnCount();
		} catch (Exception e) {
			logger.error("getWarnCount 出错", e);
			return ErrorContent.SERVER_ERROR;
		}
	}

	public Object getProjectCount(String risk_type) {
		List<OpenProjectRanking> list = null;
		try {
			list = openProjectMapper.selectProjectByName(risk_type);
		} catch (Exception e) {
			logger.error("select tb_open_project by name 出错", e);
			return ErrorContent.SERVER_ERROR;
		}

		for (OpenProjectRanking obj : list) {
			if ("high".equals(risk_type)) {
				obj.setMiddle(0);
				obj.setLow(0);
			} else if ("middle".equals(risk_type)) {
				obj.setLow(0);
				obj.setHigh(0);
			} else if ("low".equals(risk_type)) {
				obj.setMiddle(0);
				obj.setHigh(0);
			}
		}

		return list;
	}

	public Object getLanguageCount(String risk_type) {
		List<OpenProjectRanking> list = null;
		try {
			list = openProjectMapper.selectProjectByLanguage(risk_type);
		} catch (Exception e) {
			logger.error("select tb_open_project by Language 出错", e);
			return ErrorContent.SERVER_ERROR;
		}

		for (OpenProjectRanking obj : list) {
			if ("high".equals(risk_type)) {
				obj.setMiddle(0);
				obj.setLow(0);
			} else if ("middle".equals(risk_type)) {
				obj.setLow(0);
				obj.setHigh(0);
			} else if ("low".equals(risk_type)) {
				obj.setMiddle(0);
				obj.setHigh(0);
			}
		}

		return list;
	}

	public Object search(String value) {
		try {
			return elasticSearchClient.search(value);
		} catch (Exception e) {
			logger.error("select tb_issue_level 出错", e);
			return ErrorContent.SERVER_ERROR;
		}
	}
}
