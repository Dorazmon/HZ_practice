package cn.com.tcsec.sdlmp.search.db.entity;

public class OpenTotal {
	private int file_count;
	private int line_count;
	private int high_issue_count;
	private int mid_issue_count;
	private int low_issue_count;

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

	public int getHigh_issue_count() {
		return high_issue_count;
	}

	public void setHigh_issue_count(int high_issue_count) {
		this.high_issue_count = high_issue_count;
	}

	public int getMid_issue_count() {
		return mid_issue_count;
	}

	public void setMid_issue_count(int mid_issue_count) {
		this.mid_issue_count = mid_issue_count;
	}

	public int getLow_issue_count() {
		return low_issue_count;
	}

	public void setLow_issue_count(int low_issue_count) {
		this.low_issue_count = low_issue_count;
	}

	@Override
	public String toString() {
		return "OpenTotal [file_count=" + file_count + ", line_count=" + line_count + ", high_issue_count="
				+ high_issue_count + ", mid_issue_count=" + mid_issue_count + ", low_issue_count=" + low_issue_count
				+ "]";
	}
}
