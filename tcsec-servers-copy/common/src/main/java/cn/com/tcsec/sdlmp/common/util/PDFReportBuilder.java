package cn.com.tcsec.sdlmp.common.util;



import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfOutline;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.navigation.PdfExplicitDestination;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

public class PDFReportBuilder implements IEventHandler {
	PdfDocument pdf = null;
	Document document = null;
	PdfFont helveticaBold = null;
	PdfFont helvetica = null;
	PdfFont ch_font = null;
	JFreeChartPie jfcPie=null;

	private void builder() throws Exception {
		PdfWriter writer = new PdfWriter("D:\\Web-company\\tcsec\\tcsec-servers\\tcsec-servers项目代码审计报告.pdf");
		pdf = new PdfDocument(writer);
		document = new Document(pdf);
		jfcPie=new JFreeChartPie();
		jfcPie.pieDate("问题总数:98", 98);
        jfcPie.pieDate("受感染文件数量:40", 40);
        jfcPie.pieDate("受感染代码行数:100", 100);
        jfcPie.createPiePictrue();

		helveticaBold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
		helvetica = PdfFontFactory.createFont(FontConstants.HELVETICA);

		pdf.addEventHandler(PdfDocumentEvent.END_PAGE, this);

		// 标题
		Paragraph paragraph = new Paragraph("tcsec-servers项目代码审计报告");
		ch_font = PdfFontFactory.createFont("c://windows//fonts//simsun.ttc,1", PdfEncodings.IDENTITY_H, false);
		Style style_title = new Style().setFontSize(20).setTextAlignment(TextAlignment.CENTER);
		paragraph.setFont(ch_font);
		paragraph.addStyle(style_title);
		document.add(paragraph);

		// 空白
		Paragraph paragraph_break = new Paragraph("\n");
		for (int i = 0; i < 3; i++) {
			document.add(paragraph_break);
		}

		Image logo = new Image(ImageDataFactory.create("tcsec_logo.png"));
		Paragraph p = new Paragraph();
		p.add(logo);
		p.addStyle(new Style().setTextAlignment(TextAlignment.CENTER));
		document.add(p);

		Paragraph p1 = new Paragraph();
		BarcodeQRCode qrcode = new BarcodeQRCode("www.tcsec.com.cn");
		Image mgd = new Image(qrcode.createFormXObject(pdf));
		mgd.setHeight(160);
		mgd.setWidth(160);
		p1.addStyle(new Style().setTextAlignment(TextAlignment.CENTER));
		p1.add(mgd);
		document.add(p1);
		document.add(new Paragraph("扫码关注").setFont(ch_font)
				.addStyle(new Style().setFontSize(10).setTextAlignment(TextAlignment.CENTER)).setMarginTop(-20));

		// 空白
		for (int i = 0; i < 3; i++) {
			document.add(paragraph_break);
		}

		// 基本信息
		Style style_d2 = new Style().setFontSize(16);
		document.add(new Paragraph("\t\t\t项目名称：tcsec-servers").setFont(ch_font));
		document.add(new Paragraph("\t\t\t负责人：xiong").setFont(ch_font));
		document.add(new Paragraph("\t\t\t扫描时间：2017-10-22 18 : 29 : 57").setFont(ch_font));
		document.add(new Paragraph("\t\t\t版本：225").setFont(ch_font));
		document.add(new Paragraph("\t\t\t分支：xxxx").setFont(ch_font));
		

		document.add(new AreaBreak());

		document.add(new Paragraph("项目信息：tcsec-servers").setFont(ch_font).addStyle(style_d2));
		document.add(new Paragraph("文件总数：100").setFont(ch_font));
		document.add(new Paragraph("代码行数：3000").setFont(ch_font));
		document.add(new Paragraph("扫描结果：存在98个错误，其中40个文件受影响").setFont(ch_font).addStyle(style_d2));
		document.add(new Paragraph("问题总数：98").setFont(ch_font));
		document.add(new Paragraph("受感染文件数量：40").setFont(ch_font));
		document.add(new Paragraph("受感染代码行数：100").setFont(ch_font));
		document.add(new Paragraph("项目评分：60分").setFont(ch_font));
		// 空白
				for (int i = 0; i < 3; i++) {
					document.add(paragraph_break);
				}
		Image piePicture = new Image(ImageDataFactory.create("PieChart.png"));
		Paragraph pie = new Paragraph();
		pie.add(piePicture);
		pie.addStyle(new Style().setTextAlignment(TextAlignment.CENTER));
		document.add(pie);

		document.add(new AreaBreak());
//		document.add(new Paragraph("问题1：私有构造函数的类不是final").setFont(ch_font));
//		document.add(new Paragraph("漏洞等级：较高").setFont(ch_font));
//		document.add(
//				new Paragraph("所在文件：common/src/main/java/cn/com/tcsec/sdlmp/common/util/Sha256.java").setFont(ch_font));
//		document.add(new Paragraph("问题描述：只有私有构造函数的类应该是final类型的").setFont(ch_font));
//		document.add(new Paragraph("解决建议：用final修饰类").setFont(ch_font));
//		document.add(new Paragraph("代码片段：").setFont(ch_font));
//		document.add(new Paragraph("import java.security.NoSuchAlgorithmException;\r\n" + "\r\n"
//				+ "public class Sha256 {\r\n"+"private Sha256() {\r\n" + "	}\r\n" + "\r\n"
//				+ "	public static String Encrypt(String strSrc) {\r\n" + "		MessageDigest md = null;\r\n"
//				+ "		String strDes = null;\r\n" + "\r\n" + "		try {\r\n"
//				+ "			md = MessageDigest.getInstance(\\SHA-256\\);\r\n"
//				+ "			md.update(strSrc.getBytes());\r\n" + "			strDes = bytes2Hex(md.digest());\r\n"
//				+ "		} catch (NoSuchAlgorithmException e) {\r\n" + "			return null;\r\n" + "		}\r\n"
//				+ "		return strDes;\r\n" + "	}").setFont(ch_font));
		Table table = new Table(new float[] { 70,30 }).setWidthPercent(100);
		Cell cell=new Cell(1,1).add(new Paragraph("问题1：").setFont(ch_font));
		Cell cell2=new Cell(1,1).add(new Paragraph("私有构造函数的类不是final").setFont(ch_font));
		Cell cell3=new Cell(1,1).add(new Paragraph("漏洞等级：").setFont(ch_font));
		Cell cell4=new Cell(1,1).add(new Paragraph("较高").setFont(ch_font));
		Cell cell5=new Cell(1,1).add(new Paragraph("所在文件：").setFont(ch_font));
		Cell cell6=new Cell(1,1).add(new Paragraph("common/src/main/java/cn/com/tcsec/sdlmp/common/util/Sha256.java").setFont(ch_font));
		Cell cell7=new Cell(1,1).add(new Paragraph("问题描述：").setFont(ch_font));
		Cell cell8=new Cell(1,1).add(new Paragraph("只有私有构造函数的类应该是final类型的").setFont(ch_font));
		Cell cell9=new Cell(1,1).add(new Paragraph("解决建议：").setFont(ch_font));
		Cell cell10=new Cell(1,1).add(new Paragraph("用final修饰类").setFont(ch_font));
		Cell cell11=new Cell(1,2).add(new Paragraph("代码片段：").setFont(ch_font));
		Cell cell12=new Cell(10,2).add(new Paragraph("import java.security.NoSuchAlgorithmException;\r\n" + "\r\n"
				+ "public class Sha256 {\r\n"+"private Sha256() {\r\n" + "	}\r\n" + "\r\n"
				+ "	public static String Encrypt(String strSrc) {\r\n" + "		MessageDigest md = null;\r\n"
				+ "\tString strDes = null;\r\n" + "\r\n" + "		try {\r\n"
				+ "			md = MessageDigest.getInstance(\\SHA-256\\);\r\n"
				+ "			md.update(strSrc.getBytes());\r\n" + "			strDes = bytes2Hex(md.digest());\r\n"
				+ "		} catch (NoSuchAlgorithmException e) {\r\n" + "			return null;\r\n" + "		}\r\n"
				+ "		return strDes;\r\n" + "	}").setFont(ch_font));
		table.addCell(cell);
		table.addCell(cell2);
		table.addCell(cell3);
		table.addCell(cell4);
		table.addCell(cell5);
		table.addCell(cell6);
		table.addCell(cell7);
		table.addCell(cell8);
		table.addCell(cell9);
		table.addCell(cell10);
		table.addCell(cell11);
		table.addCell(cell12);
        document.add(table);



//		document.add(new AreaBreak());
//		document.add(new Paragraph("问题2：没有使用不定参数来代替数组参数").setFont(ch_font));
//		document.add(new Paragraph("漏洞等级：较高").setFont(ch_font));
//		document.add(new Paragraph("所在文件：common/src/main/java/cn/com/tcsec/sdlmp/common/client/RestClient.java")
//				.setFont(ch_font));
//		document.add(new Paragraph("问题描述：建议使用不定参数来代替数组参数").setFont(ch_font));
//		document.add(new Paragraph("解决建议：使用不定参数来代替数组参数").setFont(ch_font));
//		document.add(new Paragraph("代码片段：").setFont(ch_font));
//		document.add(new Paragraph("public class RestClient extends AbstractRestClient {\r\n" + "\r\n"
//				+ "	public Object send(String methodName, Object[] args) throws Throwable {\r\n"
//				+ "		return doSend(methodName, args);\r\n" + "	}").setFont(ch_font));
//
//		document.add(new AreaBreak());
//		document.add(new Paragraph("问题3：remoteInterface没有用final修饰").setFont(ch_font));
//		document.add(new Paragraph("漏洞等级：较高").setFont(ch_font));
//		document.add(new Paragraph("所在文件：main/src/main/java/cn/com/tcsec/sdlmp/main/JsonServer/JsonRpcProxyServer.java")
//				.setFont(ch_font));
//		document.add(new Paragraph("问题描述：私有的字段 'remoteInterface' 可以置为 final类型的;因为它只在构造函数中初始化了一次").setFont(ch_font));
//		document.add(new Paragraph("解决建议：remoteInterface用final修饰").setFont(ch_font));
//		document.add(new Paragraph("代码片段：").setFont(ch_font));
//		document.add(new Paragraph("public class JsonRpcProxyServer extends JsonRpcServer {\r\n" + "\r\n"
//				+ "	private Class<?>[] remoteInterface;\r\n" + "\r\n"
//				+ "	public JsonRpcProxyServer(ObjectMapper mapper, Object handler, Class<?>[] remoteInterface) {")
//						.setFont(ch_font));

		document.add(new AreaBreak());
		document.add(new Paragraph("建议：完善修改有安全隐患的代码").setFont(ch_font));
		// 目录
		// 代码片段

		// pdf.getPage(1).setPageLabel(PageLabelNumberingStyleConstants.LOWERCASE_ROMAN_NUMERALS,
		// null, 1);

		PdfOutline outline = createOutline(null, pdf, "概要", 1);
		PdfOutline outline1 = createOutline(outline, pdf, "问题1", 2);
//		PdfOutline outline2 = createOutline(outline, pdf, "问题2", 3);
//		PdfOutline outline3 = createOutline(outline, pdf, "问题3", 4);

		document.close();
	}

	public PdfOutline createOutline(PdfOutline outline, PdfDocument pdf, String title, int page) {
		if (outline == null) {
			outline = pdf.getOutlines(false);
			outline = outline.addOutline(title);
			outline.addDestination(PdfExplicitDestination.createFitH(pdf.getPage(page), 10));
			return outline;
		}
		PdfOutline kid = outline.addOutline(title);
		kid.addDestination(PdfExplicitDestination.createFitH(pdf.getPage(page), 10));
		return outline;
	}

	public void handleEvent(Event event) {
		PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
		PdfDocument pdfDoc = docEvent.getDocument();
		PdfPage page = docEvent.getPage();
		int pageNumber = pdfDoc.getPageNumber(page);
		Rectangle pageSize = page.getPageSize();
		PdfCanvas pdfCanvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);

		// // Set background
		// Color limeColor = new DeviceCmyk(0.208f, 0, 0.584f, 0);
		// Color blueColor = new DeviceCmyk(0.445f, 0.0546f, 0, 0.0667f);
		// pdfCanvas.saveState().setFillColor(pageNumber % 2 == 1 ? limeColor :
		// blueColor)
		// .rectangle(pageSize.getLeft(), pageSize.getBottom(), pageSize.getWidth(),
		// pageSize.getHeight()).fill()
		// .restoreState();

		// Add header and footer
		pdfCanvas.beginText().setFontAndSize(ch_font, 9).moveText(pageSize.getWidth() / 2 - 40, pageSize.getTop() - 20)
				.showText("杭州孝道科技有限公司").moveText(-220, -pageSize.getTop() + 30)
				.showText(
						"地址:浙江省杭州市中国智慧产业园H座506              网址:www.tcsec.com.cn                       联系电话:0571-85358276")
				.endText();

		// Add watermark
//		if (pageNumber != 1) {
//			Canvas canvas = new Canvas(pdfCanvas, pdfDoc, page.getPageSize());
//			// canvas.setFontColor(Color.BLUE);
//			canvas.setFontColor(new DeviceRgb(135, 206, 235));
//			canvas.setProperty(Property.FONT_SIZE, 60);
//			canvas.setProperty(Property.FONT, helveticaBold);
//			canvas.showTextAligned(new Paragraph("TCSEC"), 298, 421, pdfDoc.getPageNumber(page), TextAlignment.CENTER,
//					VerticalAlignment.MIDDLE, 45);
//		}
		pdfCanvas.release();
	}

	public static void main(String[] args) {
		PDFReportBuilder builder = new PDFReportBuilder();
		try {
			builder.builder();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
