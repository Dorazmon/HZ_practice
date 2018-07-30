package cn.com.tcsec.sdlmp.user.entity;

public class User {

	private String id;
	private String user_id;
	private String phone;
	private String email;
	private String passwd;
	private String name;
	private String company_id;
	private String role;
	private String state;
	private String gmt_create;
	private String gmt_modified;
	
	public User(String user_id, String phone, String email, String passwd) {
		super();
		this.user_id = user_id;
		this.phone = phone;
		this.email = email;
		this.passwd = passwd;
	}
	
	public User(String user_id, String phone, String email, String passwd, String name, String company_id, String role,
			String state) {
		super();
		this.user_id = user_id;
		this.phone = phone;
		this.email = email;
		this.passwd = passwd;
		this.name = name;
		this.company_id = company_id;
		this.role = role;
		this.state = state;
	}

	public User(String user_id, String phone, String email, String passwd, String state) {
		super();
		this.user_id = user_id;
		this.phone = phone;
		this.email = email;
		this.passwd = passwd;
		this.state = state;
	}

	public User() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompany_id() {
		return company_id;
	}
	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
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
		return "User [id=" + id + ", user_id=" + user_id + ", phone=" + phone + ", email=" + email + ", passwd="
				+ passwd + ", name=" + name + ", company_id=" + company_id + ", role=" + role + ", state=" + state
				+ ", gmt_create=" + gmt_create + ", gmt_modified=" + gmt_modified + "]";
	}
	
	
}
