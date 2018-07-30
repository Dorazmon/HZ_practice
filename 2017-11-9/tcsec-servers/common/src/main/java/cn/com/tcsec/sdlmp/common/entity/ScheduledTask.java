package cn.com.tcsec.sdlmp.common.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ScheduledTask implements Serializable {
	private static final long serialVersionUID = -3982925407804533638L;

	private int affected_file_count;
	private String creator_id;
	private int Extend_file_count;
	private int issue_count;
	private int line_count;
	private String resolution_rate;
	private String resolution_rate_rate;
	private List<String> list;
	private String project_id;
	private String project_language;
	private String project_name;
	private String project_url;
	private String returnMsqDestName;
	private String task_id;
	private String grade;
	private String issue_key;
	private int total_file_count;
	private String type;
	private String type_percentage;
	private List<Report> reportList;

	public void addReport(String info, String key) {
		if (reportList == null) {
			reportList = new ArrayList<ScheduledTask.Report>();
		}
		Report report = new Report();
		report.setInfo(info);
		report.setKey(key);
		reportList.add(report);
	}

	public boolean containsKey(String key) {
		for (Report report : reportList) {
			if (key.equals(report.getKey())) {
				return true;
			}
		}
		return false;
	}

	public List<Report> getReportList() {
		return reportList;
	}

	public void setReportList(List<Report> reportList) {
		this.reportList = reportList;
	}

	public class Report implements Serializable {
		private static final long serialVersionUID = -2379084416045037203L;
		String info;
		String key;

		public String getInfo() {
			return info;
		}

		public void setInfo(String info) {
			this.info = info;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}
	}

	public ScheduledTask() {
		super();
	}

	public String getIssue_key() {
		return issue_key;
	}

	public void setIssue_key(String issue_key) {
		this.issue_key = issue_key;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public int getAffected_file_count() {
		return affected_file_count;
	}

	public String getCreator_id() {
		return creator_id;
	}

	public int getExtend_file_count() {
		return Extend_file_count;
	}

	public int getIssue_count() {
		return issue_count;
	}

	public int getLine_count() {
		return line_count;
	}

	public List<String> getList() {
		return list;
	}

	public String getResolution_rate() {
		return resolution_rate;
	}

	public void setResolution_rate(String resolution_rate) {
		this.resolution_rate = resolution_rate;
	}

	public String getResolution_rate_rate() {
		return resolution_rate_rate;
	}

	public void setResolution_rate_rate(String resolution_rate_rate) {
		this.resolution_rate_rate = resolution_rate_rate;
	}

	public String getProject_id() {
		return project_id;
	}

	public String getProject_language() {
		return project_language;
	}

	public String getProject_name() {
		return project_name;
	}

	public String getProject_url() {
		return project_url;
	}

	public String getReturnMsqDestName() {
		return returnMsqDestName;
	}

	public String getTask_id() {
		return task_id;
	}

	public int getTotal_file_count() {
		return total_file_count;
	}

	public String getType() {
		return type;
	}

	public String getType_percentage() {
		return type_percentage;
	}

	public void setAffected_file_count(int affected_file_count) {
		this.affected_file_count = affected_file_count;
	}

	public void setCreator_id(String creator_id) {
		this.creator_id = creator_id;
	}

	public void setExtend_file_count(int extend_file_count) {
		Extend_file_count = extend_file_count;
	}

	public void setIssue_count(int issue_count) {
		this.issue_count = issue_count;
	}

	public void setLine_count(int line_count) {
		this.line_count = line_count;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}

	public void setProject_language(String project_language) {
		this.project_language = project_language;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public void setProject_url(String project_url) {
		this.project_url = project_url;
	}

	public void setReturnMsqDestName(String returnMsqDestName) {
		this.returnMsqDestName = returnMsqDestName;
	}

	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}

	public void setTotal_file_count(int total_file_count) {
		this.total_file_count = total_file_count;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setType_percentage(String type_percentage) {
		this.type_percentage = type_percentage;
	}

	@Override
	public String toString() {
		return "ScheduledTask [affected_file_count=" + affected_file_count + ", creator_id=" + creator_id
				+ ", issue_count=" + issue_count + ", line_count=" + line_count + ", list=" + list + ", project_id="
				+ project_id + ", project_language=" + project_language + ", project_name=" + project_name
				+ ", project_url=" + project_url + ", returnMsqDestName=" + returnMsqDestName + ", task_id=" + task_id
				+ ", total_file_count=" + total_file_count + ", Extend_file_count=" + Extend_file_count + ", type="
				+ type + ", type_percentage=" + type_percentage + "]";
	}
}
