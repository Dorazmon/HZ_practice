package cn.com.tcsec.sdlmp.auth.db.entity;

public class Report {
	private String id;
	private String plan_id;
	private String project_id;
	private String creator_id;
	private String report_type;
	private String task_id;
	private String info;
	private String gmt_create;
	private String gmt_modified;
	
	public Report() {
		super();
	}
	
	public Report(String plan_id, String project_id, String creator_id, String report_type, String task_id,
			String info) {
		super();
		this.plan_id = plan_id;
		this.project_id = project_id;
		this.creator_id = creator_id;
		this.report_type = report_type;
		this.task_id = task_id;
		this.info = info;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlan_id() {
		return plan_id;
	}

	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}

	public String getProject_id() {
		return project_id;
	}

	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}

	public String getCreator_id() {
		return creator_id;
	}

	public void setCreator_id(String creator_id) {
		this.creator_id = creator_id;
	}

	public String getReport_type() {
		return report_type;
	}

	public void setReport_type(String report_type) {
		this.report_type = report_type;
	}

	public String getTask_id() {
		return task_id;
	}

	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getGmt_create() {
		return gmt_create;
	}

	public void setGmt_create(String gmt_create) {
		this.gmt_create = gmt_create;
	}

	public String getGmt_modified() {
		return gmt_modified;
	}

	public void setGmt_modified(String gmt_modified) {
		this.gmt_modified = gmt_modified;
	}

	@Override
	public String toString() {
		return "Report [id=" + id + ", plan_id=" + plan_id + ", project_id=" + project_id + ", creator_id=" + creator_id
				+ ", report_type=" + report_type + ", task_id=" + task_id + ", info=" + info + ", gmt_create="
				+ gmt_create + ", gmt_modified=" + gmt_modified + "]";
	}

}
