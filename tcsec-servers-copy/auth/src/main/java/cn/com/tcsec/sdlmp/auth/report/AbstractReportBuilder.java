package cn.com.tcsec.sdlmp.auth.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.tcsec.sdlmp.auth.mapper.AuthMapper;

public abstract class AbstractReportBuilder {

	private String project_name;
	private String project_number;
	private String project_language;
	private String project_url;
	private String project_desc;
	private String plan_number;
	private String star_time;
	private String finish_time;
	private String issue_count;
	private String file_count;
	private String extend_issue;
	private String resolution_rate;
	private String grade;
	private List<Report> reportList;

	protected AbstractReportBuilder() {
		super();
	}

	/**
	 * 获取AuthMapper，实现类需要提供AuthMapper
	 * 
	 * @return AuthMapper
	 * @throws Exception
	 */
	protected abstract AuthMapper getAuthMapper() throws Exception;

	/**
	 * 实现类需要提供 task_id
	 * 
	 * @return taskId
	 * @throws Exception
	 */
	protected abstract String getTaskId() throws Exception;

	/**
	 * 在抽象类获取报告需要的数据后会调用该方法初始化报告生成工具
	 * 
	 * @throws Exception
	 */
	protected abstract void init() throws Exception;

	/**
	 * 调用报告生成工具生成报告
	 * 
	 * @throws Exception
	 */
	protected abstract void doBuilder() throws Exception;

	/**
	 * 获取报告，并将其以byte[]格式返回
	 * 
	 * @return byte[] 格式的报告数据
	 * @throws Exception
	 */
	protected abstract byte[] getByte() throws Exception;

	public final byte[] builder() throws Exception {
		if (getTaskId() == null) {
			throw new Exception("实现类未能提供task_id");
		}

		if (getAuthMapper() == null || !(getAuthMapper() instanceof AuthMapper)) {
			throw new Exception("实现类未能提供正确的 AuthMapper");
		}

		Map<String, String> header = getAuthMapper().selectReportFileHeader(getTaskId());
		if (header == null || header.get("project_name") == null) {
			throw new Exception("获取任务头信息出错  task_id:" + getTaskId());
		}
		project_name = header.get("project_name");
		project_number = header.get("project_number");
		project_language = header.get("project_language");
		project_url = header.get("project_url");
		project_desc = header.get("project_desc");
		plan_number = header.get("plan_number");
		star_time = header.get("star_time");
		finish_time = header.get("finish_time");
		issue_count = header.get("issue_count");
		file_count = header.get("file_count");
		extend_issue = header.get("extend_issue");
		resolution_rate = header.get("resolution_rate");
		grade = header.get("grade");

		List<Map<String, String>> body = getAuthMapper().selectReportFileBody(getTaskId());
		if (body == null) {
			throw new Exception("获取任务body信息出错  task_id:" + getTaskId());
		}
		for (Map<String, String> bo : body) {
			if (reportList == null) {
				reportList = new ArrayList<>(body.size());
			}
			Report report = new Report();
			report.setCheck_name(bo.get("check_name"));
			report.setDescription(bo.get("description"));
			report.setCategories(bo.get("categories"));
			report.setLocation(bo.get("location"));
			report.setMainCode(bo.get("mainCode"));
			report.setMainCodeBeginLine(bo.get("mainCodeBeginLine"));
			report.setBegin_line(bo.get("begin_line"));
			report.setEnd_line(bo.get("end_line"));
			report.setBody(bo.get("body"));
			report.setSeve(bo.get("seve"));
			reportList.add(report);
		}

		init();
		doBuilder();

		return getByte();
	}

	protected final String getProject_name() {
		return project_name;
	}

	protected final String getProject_number() {
		return project_number;
	}

	protected final String getProject_language() {
		return project_language;
	}

	protected final String getProject_url() {
		return project_url;
	}

	protected final String getProject_desc() {
		return project_desc;
	}

	protected final String getPlan_number() {
		return plan_number;
	}

	protected final String getStar_time() {
		return star_time;
	}

	protected final String getFinish_time() {
		return finish_time;
	}

	protected final String getIssue_count() {
		return issue_count;
	}

	protected final String getFile_count() {
		return file_count;
	}

	protected final String getExtend_issue() {
		return extend_issue;
	}

	protected final String getResolution_rate() {
		return resolution_rate;
	}

	protected final String getGrade() {
		return grade;
	}

	protected final List<Report> getReportList() {
		return reportList;
	}

	protected class Report {
		private String check_name;
		private String description;
		private String categories;
		private String location;
		private String mainCode;
		private String mainCodeBeginLine;
		private String begin_line;
		private String end_line;
		private String body;
		private String seve;

		protected final String getCheck_name() {
			return check_name;
		}

		protected final String getDescription() {
			return description;
		}

		protected final String getCategories() {
			return categories;
		}

		protected final String getLocation() {
			return location;
		}

		protected final String getMainCode() {
			return mainCode;
		}

		protected final String getMainCodeBeginLine() {
			return mainCodeBeginLine;
		}

		protected final String getBegin_line() {
			return begin_line;
		}

		protected final String getEnd_line() {
			return end_line;
		}

		protected final String getBody() {
			return body;
		}

		protected final String getSeve() {
			return seve;
		}

		private final void setCheck_name(String check_name) {
			this.check_name = check_name;
		}

		private final void setDescription(String description) {
			this.description = description;
		}

		private final void setCategories(String categories) {
			this.categories = categories;
		}

		private final void setLocation(String location) {
			this.location = location;
		}

		private final void setMainCode(String mainCode) {
			this.mainCode = mainCode;
		}

		private final void setMainCodeBeginLine(String mainCodeBeginLine) {
			this.mainCodeBeginLine = mainCodeBeginLine;
		}

		private final void setBegin_line(String begin_line) {
			this.begin_line = begin_line;
		}

		private final void setEnd_line(String end_line) {
			this.end_line = end_line;
		}

		private final void setBody(String body) {
			this.body = body;
		}

		private final void setSeve(String seve) {
			this.seve = seve;
		}
	}
}
