package cn.com.tcsec.sdlmp.auth.db.entity;

public class Project {
	private String id;
	private String creator_id;
	private String type;
	private String name;
	private String number;
	private String url;
	private String desc;
	private String language;
	private String state;
	private String gmt_create;
	private String gmt_modified;

	public Project() {
		super();
	}

	public Project(String creator_id, String type, String name, String number, String url, String desc, String language,
			String state) {
		super();
		this.creator_id = creator_id;
		this.type = type;
		this.name = name;
		this.number = number;
		this.url = url;
		this.desc = desc;
		this.language = language;
		this.state = state;
	}

	public Project(String id, String name, String url, String number, String desc, String language, String state) {
		super();
		this.id = id;
		this.name = name;
		this.url = url;
		this.number = number;
		this.desc = desc;
		this.language = language;
		this.state = state;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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
		return "Project [id=" + id + ", creator_id=" + creator_id + ", type=" + type + ", name=" + name + ", number="
				+ number + ", url=" + url + ", desc=" + desc + ", language=" + language + ", state=" + state
				+ ", gmt_create=" + gmt_create + ", gmt_modified=" + gmt_modified + "]";
	}
}
