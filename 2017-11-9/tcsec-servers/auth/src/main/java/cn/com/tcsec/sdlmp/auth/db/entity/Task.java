package cn.com.tcsec.sdlmp.auth.db.entity;

public class Task {
	private String creator_id;
	private String extend_count;
	private String file_count;
	private String finish_time;
	private String gmt_create;
	private String gmt_modified;
	private String id;
	private String info;
	private String issue_count;
	private String plan_id;
	private String project_id;

	private String resolution_rate;

	private String star_time;

	private String state;
	private String type;
	public Task() {
		super();
	}
	public Task(String id, String state, String finish_time, String issue_count, String file_count, String extend_count,
			String info) {
		super();
		this.id = id;
		this.state = state;
		this.finish_time = finish_time;
		this.issue_count = issue_count;
		this.file_count = file_count;
		this.extend_count = extend_count;
		this.info = info;
	}

	public String getCreator_id() {
		return creator_id;
	}

	public String getExtend_count() {
		return extend_count;
	}

	public String getFile_count() {
		return file_count;
	}

	public String getFinish_time() {
		return finish_time;
	}

	public String getGmt_create() {
		return gmt_create;
	}

	public String getGmt_modified() {
		return gmt_modified;
	}

	public String getId() {
		return id;
	}

	public String getInfo() {
		return info;
	}

	public String getIssue_count() {
		return issue_count;
	}

	public String getPlan_id() {
		return plan_id;
	}

	public String getProject_id() {
		return project_id;
	}

	public String getResolution_rate() {
		return resolution_rate;
	}

	public String getStar_time() {
		return star_time;
	}

	public String getState() {
		return state;
	}

	public String getType() {
		return type;
	}

	public void setCreator_id(String creator_id) {
		this.creator_id = creator_id;
	}

	public void setExtend_count(String extend_count) {
		this.extend_count = extend_count;
	}

	public void setFile_count(String file_count) {
		this.file_count = file_count;
	}

	public void setFinish_time(String finish_time) {
		this.finish_time = finish_time;
	}

	public void setGmt_create(String gmt_create) {
		this.gmt_create = gmt_create;
	}

	public void setGmt_modified(String gmt_modified) {
		this.gmt_modified = gmt_modified;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public void setIssue_count(String issue_count) {
		this.issue_count = issue_count;
	}

	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}

	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}

	public void setResolution_rate(String resolution_rate) {
		this.resolution_rate = resolution_rate;
	}

	public void setStar_time(String star_time) {
		this.star_time = star_time;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setType(String type) {
		this.type = type;
	}

}
