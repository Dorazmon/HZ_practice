package cn.com.tcsec.sdlmp.search.scheduled;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.com.tcsec.sdlmp.common.entity.ScheduledTask;
import cn.com.tcsec.sdlmp.search.db.entity.OpenProject;
import cn.com.tcsec.sdlmp.search.service.SearchService;

@Component
public class DataWorker {

	@Autowired
	SearchService searchService;

	private final String[] checkNames = { "issue1", "issue2", "issue3", "issue4", "issue5", "issue6", "issue7",
			"issue8", "issue9", "issue10", "issue11", "issue12", "issue13", "issue14", "issue15", "issue16", "issue17",
			"issue18", "issue19", "issue20", "issue21", "issue22", "issue23", "issue24", "issue25" };

	private Random random = new Random();
	private ObjectMapper objectMapper = new ObjectMapper();

	@Scheduled(initialDelay = 1000 * 60, fixedRate = 1000 * 60 * 5)
	public void scheduled_scan() throws Exception {
		// 每天执行288次
		// 总代码行数 每天增加 288 万行
		// 总扫描文件数 每天增加 288 * 0.25 = 72 万个
		// 每天增加问题数  288 * 110 = 31680 个
		
		OpenProject openProject = searchService.getRandomProject(random.nextInt(30) + 1);
		ScheduledTask scheduledTask = new ScheduledTask();

		scheduledTask.setProject_id(openProject.getId());
		scheduledTask.setProject_name(openProject.getName());

		scheduledTask.setTotal_file_count(random.nextInt(4) < 3 ? 0 : 1);
		scheduledTask.setLine_count(random.nextInt(3));

		int count = random.nextInt(181) + 20;
		Map<String, Integer> map = new HashMap<>();
		for (int i = 0; i < count; i++) {
			String key = checkNames[random.nextInt(25)];
			if (map.containsKey(key)) {
				map.put(key, map.get(key) + 1);
			} else {
				map.put(key, 1);
			}
		}

		String str = objectMapper.writeValueAsString(map);

		scheduledTask.setIssue_count(count);
		scheduledTask.setType_percentage(str);

		searchService.addScanResult(scheduledTask);
	}
}
