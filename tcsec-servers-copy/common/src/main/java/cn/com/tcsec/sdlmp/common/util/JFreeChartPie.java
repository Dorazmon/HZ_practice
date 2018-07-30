package cn.com.tcsec.sdlmp.common.util;

import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.data.general.DefaultPieDataset;

public class JFreeChartPie {
	private Map<String, Double> map = new HashMap<>();

	public Map<String, Double> pieDate(String key, int wide) {
		map.put(key, new Double(wide));
		return this.map;
	}

	public void createPiePictrue() throws IOException {
		Set set = map.keySet();
		Iterator<String> iter = set.iterator();
		DefaultPieDataset dataset = new DefaultPieDataset();
		while (iter.hasNext()) {
			String str = iter.next();
			dataset.setValue(str, map.get(str));
		}
		StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
		// 设置标题字体
		standardChartTheme.setExtraLargeFont(new Font("微软雅黑", Font.BOLD, 15));
		// 设置图例的字体
		standardChartTheme.setRegularFont(new Font("微软雅黑", Font.PLAIN, 10));
		// 设置轴向的字体
		standardChartTheme.setLargeFont(new Font("微软雅黑", Font.PLAIN, 10));
		// 应用主题样式
		ChartFactory.setChartTheme(standardChartTheme);
		JFreeChart chart = ChartFactory.createPieChart("扫描结果", // chart title
				dataset, // data
				true, // include legend
				true, false);

		int width = 400; /* Width of the image */
		int height = 400; /* Height of the image */
		File pieChart = new File("PieChart.png");
		ChartUtilities.saveChartAsPNG(pieChart, chart, width, height);
	}

}
