package cn.com.tcsec.sdlmp.auth.export.entity;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BugInfo {
	private List<Entry> issueRanking;
	private List<Entry> issueClassify;
	private List<Entry> issueLevel;
	DecimalFormat decimalFormat = new DecimalFormat("##.##");
	private int totalCount;

	public BugInfo(int totalCount) {
		issueLevel = new ArrayList<>(3);
		issueLevel.add(new Entry("高危", "0.00", null, null));
		issueLevel.add(new Entry("中危", "0.00", null, null));
		issueLevel.add(new Entry("低危", "0.00", null, null));
		this.totalCount = totalCount;
	}

	public void add(String issue, int count, String level) {
		if (issueRanking == null) {
			issueRanking = new ArrayList<>();
		}
		if (issueClassify == null) {
			issueClassify = new ArrayList<>();
		}
		String val = decimalFormat.format(((float) count / totalCount) * 100);

		int level2 = 0;
		String level_cn = null;
		BigDecimal newVal = new BigDecimal(val);
		if ("high".equals(level)) {
			issueLevel.get(0).setValue(decimalFormat.format(newVal.add(new BigDecimal(issueLevel.get(0).getValue()))));
			level_cn = "高危";
		} else if ("mid".equals(level)) {
			issueLevel.get(1).setValue(decimalFormat.format(newVal.add(new BigDecimal(issueLevel.get(1).getValue()))));
			level_cn = "中危";
		} else if ("low".equals(level)) {
			issueLevel.get(2).setValue(decimalFormat.format(newVal.add(new BigDecimal(issueLevel.get(2).getValue()))));
			level_cn = "低危";
		}

		issueRanking.add(new Entry(issue, String.valueOf(count), level_cn, String.valueOf(level2)));
		issueClassify.add(new Entry(issue, val, level_cn, String.valueOf(level2)));
	}

	public Object getClassify(String risk_type) {
		if (issueClassify == null) {
			issueClassify = new ArrayList<>();
		}
		issueClassify.sort(new Comparator<Entry>() {
			@Override
			public int compare(Entry o1, Entry o2) {
				int iRet = o1.getLevel().compareTo(o2.getLevel());
				if (iRet == 0) {
					iRet = o2.getValue().compareTo(o1.getValue());
				}
				return iRet;
			}
		});
		int highlevel = 1;
		int midlevel = 1;
		int lowlevel = 1;
		for (Entry entry : issueClassify) {
			if ("高危".equals(entry.level)) {
				entry.setLevel2(String.valueOf(highlevel++));
			} else if ("中危".equals(entry.level)) {
				entry.setLevel2(String.valueOf(midlevel++));
			} else if ("低危".equals(entry.level)) {
				entry.setLevel2(String.valueOf(lowlevel++));
			}
		}
		// issueClassify.sort(new Comparator<Entry>() {
		// @Override
		// public int compare(Entry o1, Entry o2) {
		// int iRet = o2.getLevel().compareTo(o1.getLevel());
		// if (iRet == 0) {
		// iRet = o2.getValue().compareTo(o1.getValue());
		// } else {
		// iRet = 0;
		// }
		// return iRet;
		// }
		// });
		// if ("total".equals(risk_type)) {
		// BigDecimal other = new BigDecimal("0");
		// ListIterator<Entry> it = issueClassify.listIterator();
		// while (it.hasNext()) {
		// Entry entry = it.next();
		// if ("2.00".compareTo(entry.getValue()) > 0) {
		// other = other.add(new BigDecimal(entry.getValue()));
		// it.remove();
		// }
		// }
		// it.add(new Entry("其他", decimalFormat.format(other), null));
		// }
		return issueClassify;

	}

	public Object getRanking() {
		if (issueRanking == null) {
			issueRanking = new ArrayList<>();
		}
		issueRanking.sort(new Comparator<Entry>() {
			@Override
			public int compare(Entry o1, Entry o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		return issueRanking;
	}

	public Object getLevel() {
		return issueLevel;
	}

	public List<Entry> getIssueRanking() {
		return issueRanking;
	}

	public void setIssueRanking(List<Entry> issueRanking) {
		this.issueRanking = issueRanking;
	}

	public List<Entry> getIssueClassify() {
		return issueClassify;
	}

	public void setIssueClassify(List<Entry> issueClassify) {
		this.issueClassify = issueClassify;
	}

	public List<Entry> getIssueLevel() {
		return issueLevel;
	}

	public void setIssueLevel(List<Entry> issueLevel) {
		this.issueLevel = issueLevel;
	}

	class Entry {
		String name;
		String value;
		String level;
		String level2;

		public String getLevel2() {
			return level2;
		}

		public void setLevel2(String level2) {
			this.level2 = level2;
		}

		public Entry(String name, String value, String level, String level2) {
			this.name = name;
			this.value = value;
			this.level = level;
			this.level2 = level2;
		}

		public String getLevel() {
			return level;
		}

		public void setLevel(String level) {
			this.level = level;
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
	}

	@Override
	public String toString() {
		return "BugInfo [issueRanking=" + issueRanking + ", issueClassify=" + issueClassify + ", issueLevel="
				+ issueLevel + "]";
	}

}
