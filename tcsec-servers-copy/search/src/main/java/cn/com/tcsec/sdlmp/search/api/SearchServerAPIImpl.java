package cn.com.tcsec.sdlmp.search.api;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;

import cn.com.tcsec.sdlmp.common.api.SearchServerAPI;
import cn.com.tcsec.sdlmp.search.export.entity.IssueRanking;
import cn.com.tcsec.sdlmp.search.service.SearchService;

@Service
@AutoJsonRpcServiceImpl
public class SearchServerAPIImpl implements SearchServerAPI {
	@Autowired
	SearchService searchService;

	@Override
	public Object addOpenProject(String access_token, String project_name, String path, String language) {
		return searchService.addOpenProject(access_token, project_name, path, language);
	}

	@Override
	public Object search(String access_token, String value) {
		return searchService.search(value);
	}

	@Override
	public Object getTotalCount(String access_token) {
		return searchService.getTotalCount();
	}

	@Override
	public Object getCountryCount(String access_token) {
		return searchService.getCountryCount();
	}

	@Override
	public Object getChinaCount(String access_token) {
		return searchService.getChinaCount();
	}

	@Override
	public Object getProjectCount(String access_token, String risk_type) {
		return searchService.getProjectCount(risk_type);
	}

	@Override
	public Object getIssueCount(String access_token, String risk_type) {
		IssueRanking IssueRanking = searchService.getSortIssueCount(risk_type);
		IssueRanking.genCount();
		return IssueRanking.get();
	}

	@Override
	public Object getWarnCount(String access_token) {
		return searchService.getWarnCount();
	}

	@Override
	public Object getLanguageCount(String access_token, String risk_type) {
		return searchService.getLanguageCount(risk_type);
	}

	@Override
	public Object getIssuePercentage(String access_token, String risk_type) {
		IssueRanking IssueRanking = searchService.getSortIssueCount(risk_type);
		IssueRanking.genPercentage();
		return IssueRanking.get();
	}

	@Override
	public Object getDangerCoefficient(String access_token) {
		return new HashMap<String, String>() {
			private static final long serialVersionUID = 1L;
			{
				put("value", "68.9%");
			}
		};
	}
}
