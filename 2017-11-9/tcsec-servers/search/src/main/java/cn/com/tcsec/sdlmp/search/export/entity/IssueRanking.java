package cn.com.tcsec.sdlmp.search.export.entity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IssueRanking {
	private List<Entry> list;
	private int levelhigh = 0;
	private int levelmid = 0;
	private int levellow = 0;

	public void add(String name, int value, String level) {
		if (list == null) {
			list = getList();
		}

		Entry entry = new Entry();
		if ("high".equals(level)) {
			entry.setLevel("高危");
			entry.setLevel2(String.valueOf(++levelhigh));
		} else if ("mid".equals(level)) {
			entry.setLevel("中危");
			entry.setLevel2(String.valueOf(++levelmid));
		} else if ("low".equals(level)) {
			entry.setLevel("低危");
			entry.setLevel2(String.valueOf(++levellow));
		}
		entry.setName(name);
		entry.count = value;
		list.add(entry);
	}

	public void genCount() {
		Iterator<Entry> it = list.iterator();
		while (it.hasNext()) {
			Entry entry = it.next();
			if ("total".equals(entry.getName())) {
				it.remove();
			} else {
				entry.value = String.valueOf(entry.count);
			}
		}
	}

	public void genPercentage() {
		DecimalFormat decimalFormat = new DecimalFormat("##.##");
		int total = 0;
		Iterator<Entry> it = list.iterator();
		while (it.hasNext()) {
			Entry entry = it.next();
			if ("total".equals(entry.getName())) {
				total = entry.count;
				it.remove();
			} else {
				entry.value = decimalFormat.format((((float) entry.count) / total) * 100);
			}
		}
	}

	public Object get() {
		return this.list;
	}

	public List<Entry> getList() {
		return list == null ? (new ArrayList<IssueRanking.Entry>()) : list;
	}

	public void setList(List<Entry> list) {
		this.list = list;
	}

	class Entry {
		private String name;
		private String value;
		private int count;
		private String level;
		private String level2;

		public String getLevel2() {
			return level2;
		}

		public void setLevel2(String level2) {
			this.level2 = level2;
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

		public String getLevel() {
			return level;
		}

		public void setLevel(String level) {
			this.level = level;
		}
	}
}
