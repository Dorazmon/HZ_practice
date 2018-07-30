package cn.com.tcsec.sdlmp.search.export.entity;

public class OpenProjectRanking {
	private String name;
	private int total;
	private int high;
	private int middle;
	private int low;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHigh() {
		return high;
	}

	public void setHigh(int high) {
		this.high = high;
	}

	public int getMiddle() {
		return middle;
	}

	public void setMiddle(int middle) {
		this.middle = middle;
	}

	public int getLow() {
		return low;
	}

	public void setLow(int low) {
		this.low = low;
	}

	@Override
	public String toString() {
		return "OpenProjectRanking [name=" + name + ", total=" + total + ", high=" + high + ", middle=" + middle
				+ ", low=" + low + "]";
	}
}
