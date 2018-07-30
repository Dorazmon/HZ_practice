package cn.com.tcsec.sdlmp.notify.entity;

public class Message {
	private String id;
	private String type;
	private String user_id;
	private String contact;
	private String subject;
	private String message;
	private String approve;
	private String gmt_create;
	private String gmt_modified;
	
	public Message(String type, String user_id, String contact, String subject, String message) {
		super();
		this.type = type;
		this.user_id = user_id;
		this.contact = contact;
		this.subject = subject;
		this.message = message;
	}
	public Message() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getApprove() {
		return approve;
	}
	public void setApprove(String approve) {
		this.approve = approve;
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
		return "Message [id=" + id + ", type=" + type + ", user_id=" + user_id + ", contact=" + contact + ", subject="
				+ subject + ", message=" + message + ", approve=" + approve + ", gmt_create=" + gmt_create
				+ ", gmt_modified=" + gmt_modified + "]";
	}


}
