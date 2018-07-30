package cn.com.tcsec.sdlmp.search.db.entity;

public class WarnCount {
	private String value;
	private String url;
	private String date;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "WarnCount [value=" + value + ", url=" + url + ", date=" + date + "]";
	}
}
