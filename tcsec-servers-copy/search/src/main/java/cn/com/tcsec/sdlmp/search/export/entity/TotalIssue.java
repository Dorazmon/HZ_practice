package cn.com.tcsec.sdlmp.search.export.entity;

public class TotalIssue {
	private int file_count;
	private int line_count;
	private int issue_count;
	private int dangerCoefficient;

	public int getFile_count() {
		return file_count;
	}

	public void setFile_count(int file_count) {
		this.file_count = file_count;
	}

	public int getLine_count() {
		return line_count;
	}

	public void setLine_count(int line_count) {
		this.line_count = line_count;
	}

	public int getIssue_count() {
		return issue_count;
	}

	public void setIssue_count(int issue_count) {
		this.issue_count = issue_count;
	}

	public int getDangerCoefficient() {
		return dangerCoefficient;
	}

	public void setDangerCoefficient(int dangerCoefficient) {
		this.dangerCoefficient = dangerCoefficient;
	}

	@Override
	public String toString() {
		return "TotalIssue [file_count=" + file_count + ", line_count=" + line_count + ", issue_count=" + issue_count
				+ ", dangerCoefficient=" + dangerCoefficient + "]";
	}
}
