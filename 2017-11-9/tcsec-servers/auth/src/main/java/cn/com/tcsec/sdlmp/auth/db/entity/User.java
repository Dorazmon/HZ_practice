package cn.com.tcsec.sdlmp.auth.db.entity;

public class User {

	private Integer id;
	private String name;
	private String phone;
	private String email;
	private String passwd;
	private String type;
	private String regtime;
	private String state;

	public User() {
		super();
	}

	public User(String name, String phone, String email) {
		this.name = name;
		this.phone = phone;
		this.email = email;
	}

	public User(String name, String phone, String email, String passwd, String type, String state) {
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.passwd = passwd;
		this.type = type;
		this.state = state;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRegtime() {
		return regtime;
	}

	public void setRegtime(String regtime) {
		this.regtime = regtime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void clearForWeb() {
		this.setEmail(null);
		this.setId(null);
		this.setPasswd(null);
		this.setPhone(null);
		this.setRegtime(null);
		this.setState(null);
	}
}
