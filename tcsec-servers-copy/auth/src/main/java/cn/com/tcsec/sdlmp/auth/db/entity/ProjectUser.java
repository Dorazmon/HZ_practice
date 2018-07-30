package cn.com.tcsec.sdlmp.auth.db.entity;

public class ProjectUser {
	private String id;
	private String project_id;
	private String user_id;
	private String type;
	private String flag;
	private String gmt_create;
	private String gmt_modified;
	
	public ProjectUser(String project_id, String user_id, String type, String flag) {
		super();
		this.project_id = project_id;
		this.user_id = user_id;
		this.type = type;
		this.flag = flag;
	}
	public ProjectUser() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProject_id() {
		return project_id;
	}
	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Override
	public String toString() {
		return "Project_user [id=" + id + ", project_id=" + project_id + ", user_id=" + user_id + ", type=" + type
				+ ", gmt_create=" + gmt_create + ", gmt_modified=" + gmt_modified + "]";
	}
	
	
}
