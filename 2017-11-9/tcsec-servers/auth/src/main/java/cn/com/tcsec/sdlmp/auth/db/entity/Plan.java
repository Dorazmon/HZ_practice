package cn.com.tcsec.sdlmp.auth.db.entity;

public class Plan {
	private String id;
	private String project_id;
	private String creator_id;
	private String type;
	private String number;
	private String immediately_flag;
	private String period_flag;
	private String period;
	private String time;
	private String next_time;
	private String state;
	private String desc;
	private String gmt_create;
	private String gmt_modified;

	public Plan(String id, String immediately_flag, String next_time, String state) {
		super();
		this.id = id;
		this.immediately_flag = immediately_flag;
		this.next_time = next_time;
		this.state = state;
	}

	public Plan(String id, String period_flag, String immediately_flag, String period, String time, String state,
			String next_time, String desc) {
		super();
		this.id = id;
		this.period_flag = period_flag;
		this.immediately_flag = immediately_flag;
		this.period = period;
		this.time = time;
		this.state = state;
		this.next_time = next_time;
		this.desc = desc;
	}

	public Plan(String project_id, String creator_id, String type, String number, String period_flag,
			String immediately_flag, String period, String time, String state, String desc) {
		super();
		this.project_id = project_id;
		this.creator_id = creator_id;
		this.type = type;
		this.number = number;
		this.period_flag = period_flag;
		this.immediately_flag = immediately_flag;
		this.period = period;
		this.time = time;
		this.state = state;
		this.desc = desc;
	}

	public Plan() {
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

	public String getCreator_id() {
		return creator_id;
	}

	public void setCreator_id(String creator_id) {
		this.creator_id = creator_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImmediately_flag() {
		return immediately_flag;
	}

	public void setImmediately_flag(String immediately_flag) {
		this.immediately_flag = immediately_flag;
	}

	public String getPeriod_flag() {
		return period_flag;
	}

	public void setPeriod_flag(String period_flag) {
		this.period_flag = period_flag;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getNext_time() {
		return next_time;
	}

	public void setNext_time(String next_time) {
		this.next_time = next_time;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "Plan [id=" + id + ", project_id=" + project_id + ", creator_id=" + creator_id + ", type=" + type
				+ ", number=" + number + ", immediately_flag=" + immediately_flag + ", period_flag=" + period_flag
				+ ", period=" + period + ", time=" + time + ", next_time=" + next_time + ", state=" + state + ", desc="
				+ desc + ", gmt_create=" + gmt_create + ", gmt_modified=" + gmt_modified + "]";
	}

}
