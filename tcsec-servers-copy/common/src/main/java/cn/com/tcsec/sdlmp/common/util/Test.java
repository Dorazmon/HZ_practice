package cn.com.tcsec.sdlmp.common.util;

import java.io.IOException;

public class Test {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
       JFreeChartPie jcp=new JFreeChartPie();
       jcp.pieDate("java", 70);
       jcp.pieDate("c++", 90);
       jcp.createPiePictrue();
	}

}
