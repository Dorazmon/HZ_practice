package cn.com.tcsec.sdlmp.user.entity;

public class Company {
	private String id;
	private String name;
	private String url;
	private String desc;
	private String state;
	private String gmt_create;
	private String gmt_modified;
	
	public Company(String name, String url, String desc) {
		super();
		this.name = name;
		this.url = url;
		this.desc = desc;
	}

	public Company() {
		super();
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", url=" + url + ", desc=" + desc + ", state=" + state
				+ ", gmt_create=" + gmt_create + ", gmt_modified=" + gmt_modified + "]";
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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

}
