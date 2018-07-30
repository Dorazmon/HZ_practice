package cn.com.tcsec.sdlmp.search.db.entity;

public class OpenHistory {
	private String id;
	private String date;
	private String region_id;
	private double issue_count;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getRegion_id() {
		return region_id;
	}

	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}

	public double getIssue_count() {
		return issue_count;
	}

	public void setIssue_count(double issue_count) {
		this.issue_count = issue_count;
	}

	@Override
	public String toString() {
		return "OpenHist [id=" + id + ", date=" + date + ", region_id=" + region_id + ", issue_count=" + issue_count
				+ "]";
	}

}
