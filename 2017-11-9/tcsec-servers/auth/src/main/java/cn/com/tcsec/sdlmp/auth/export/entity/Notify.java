package cn.com.tcsec.sdlmp.auth.export.entity;

import java.io.Serializable;

public class Notify implements Serializable {
	private static final long serialVersionUID = 6839523396757881699L;
	public static final String TYPE_TASK_START = "task_start";
	public static final String TYPE_TASK_FINISH = "task_finish";

	public static final String DESC_TASK_START = "代码审计开始，点击查看详情";
	public static final String DESC_TASK_FINISH = "代码审计结束，点击查看详情";

	public static final String URL_REPORT = "/codesec/query-report/#/details/#{id}";

	private String type;
	private String desc;
	private String url;

	public Notify(String type, String desc, String url) {
		super();
		this.type = type;
		this.desc = desc;
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
