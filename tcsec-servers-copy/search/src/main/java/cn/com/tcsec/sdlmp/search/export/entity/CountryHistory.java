package cn.com.tcsec.sdlmp.search.export.entity;

import java.util.ArrayList;
import java.util.List;

public class CountryHistory {
	private List<TimeCount> list;

	public List<TimeCount> getList() {
		return list;
	}

	public void setList(List<TimeCount> list) {
		this.list = list;
	}

	public void addElement(String time, String name, String desc, String value) {
		if (list == null) {
			list = new ArrayList<>();
		}

		TimeCount tmp = null;
		for (TimeCount timeCount : list) {
			if (timeCount.getTime().equals(time)) {
				tmp = timeCount;
			}
		}
		if (tmp == null) {
			tmp = new TimeCount();
			tmp.setTime(time);
			list.add(tmp);
		}

		tmp.addRegionCount(name, value, desc);
	}

	class TimeCount {
		private String time;
		private List<RegionCount> data;

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public List<RegionCount> getData() {
			return data;
		}

		public void setData(List<RegionCount> regionTime) {
			this.data = regionTime;
		}

		public void addRegionCount(String name, String value, String desc) {
			if (data == null) {
				data = new ArrayList<>();
			}
			this.data.add(new RegionCount(name, value, desc));
		}
	}

	class RegionCount {
		private String name;
		private String value;
		private String desc;

		public RegionCount(String name, String value, String desc) {
			this.name = name;
			this.value = value;
			this.desc = desc;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
	}
}
