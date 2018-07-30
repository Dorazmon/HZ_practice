package cn.com.tcsec.sdlmp.search.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.com.tcsec.sdlmp.search.db.entity.WarnCount;
import cn.com.tcsec.sdlmp.search.mapper.FlawWarningMapper;

@Component
public class FlawWarning {
	private static final Logger logger = LoggerFactory.getLogger(FlawWarning.class);
	private static final String cnnvdUrl = "http://www.cnnvd.org.cn/web/cnnvdnotice/querylistform.tag?pageno=";
	private int count = 0;

	@Autowired
	FlawWarningMapper flawWarningMapper;

	@Scheduled(fixedRate = 1000 * 60 * 5)
	public void getWarn() {
		if (count == 0) {
			try {
				count = flawWarningMapper.selectTotalCount();
			} catch (Exception e) {
				logger.error("查询 tb_Warn表 获取数据总数异常", e);
				return;
			}
		}

		Elements list = null;
		int newPage = 0;
		int page = 1;

		while (true) {
			Document doc = null;
			try {
				doc = Jsoup.connect(cnnvdUrl + String.valueOf(page++)).get();
			} catch (IOException e) {
				return;
			}
			if (newPage == 0) {
				newPage = Integer.valueOf(doc.select("#pagecount").next().html().split("：")[1]) - count;
				if (newPage == 0) {
					break;
				} else if (newPage > 0) {

				} else {
					logger.error("漏洞预警个数计算异常");
					return;
				}
			}

			Elements listEle = doc.select(".list_list");

			if (!listEle.isEmpty()) {
				if (list == null) {
					list = listEle;
				} else {
					list.addAll(listEle);
				}
			}

			if (list.size() >= newPage) {
				break;
			}
		}

		if (list != null && !list.isEmpty()) {
			Elements flList = list.select(".fl > a");
			Elements frList = list.select(".fr");
			List<WarnCount> warnCounts = new ArrayList<>(flList.size());
			for (int i = 0; i < newPage; i++) {
				Element flEle = flList.get(i);
				Element frEle = frList.get(i);
				WarnCount warnCount = new WarnCount();
				warnCount.setDate(frEle.text());
				warnCount.setUrl(flEle.attr("abs:href"));
				warnCount.setValue(flEle.text());
				warnCounts.add(warnCount);
			}

			warnCounts.sort(new Comparator<WarnCount>() {
				@Override
				public int compare(WarnCount o1, WarnCount o2) {
					return o1.getDate().compareTo(o2.getDate());
				}
			});

			for (WarnCount warncount : warnCounts) {
				try {
					flawWarningMapper.insertCountList(warncount);
				} catch (Exception e) {
					logger.error("插入 tb_Warn表 数据异常", e);
					return;
				}
				count++;
			}
		}
	}

	public List<WarnCount> getWarnCount() {
		List<WarnCount> list = null;
		try {
			list = flawWarningMapper.selectWarnCountList();
		} catch (Exception e) {
			logger.error("查询 tb_Warn表 得到数据异常", e);
			return null;
		}
		if (list == null) {
			logger.error("查询 tb_Warn表 得到数据异常");
			return null;
		}

		return list;
	}
}
