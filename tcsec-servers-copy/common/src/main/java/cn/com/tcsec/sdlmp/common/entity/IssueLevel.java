package cn.com.tcsec.sdlmp.common.entity;

public class IssueLevel {
	private String issue;
	private String desc_english;
	private String desc_chinese;
	private String level;

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getDesc_english() {
		return desc_english;
	}

	public void setDesc_english(String desc_english) {
		this.desc_english = desc_english;
	}

	public String getDesc_chinese() {
		return desc_chinese;
	}

	public void setDesc_chinese(String desc_chinese) {
		this.desc_chinese = desc_chinese;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "IssueLevel [issue=" + issue + ", desc_english=" + desc_english + ", desc_chinese=" + desc_chinese
				+ ", level=" + level + "]";
	}
}
