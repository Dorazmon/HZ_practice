package cn.com.tcsec.sdlmp.auth.report;

import java.io.ByteArrayOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

import cn.com.tcsec.sdlmp.auth.mapper.AuthMapper;

public class PDFReportBuilder extends AbstractReportBuilder implements IEventHandler {
	private final static Logger logger = LoggerFactory.getLogger(PDFReportBuilder.class);

	private AuthMapper authMapper;
	private String taskId;
	ByteArrayOutputStream out = new ByteArrayOutputStream();
	PdfFont ch_font = null;

	public PDFReportBuilder(AuthMapper authMapper, String taskId) {
		this.authMapper = authMapper;
		this.taskId = taskId;
	}

	@Override
	protected AuthMapper getAuthMapper() throws Exception {
		return this.authMapper;
	}

	@Override
	protected String getTaskId() throws Exception {
		return this.taskId;
	}

	@Override
	protected void init() throws Exception {
		PdfDocument pdf = null;
		Document document = null;
		// JFreeChartPie jfcPie = null;
		PdfWriter writer = new PdfWriter(out);
		pdf = new PdfDocument(writer);
		document = new Document(pdf);

		if (System.getProperty("user.dir").startsWith("/")) {
			ch_font = PdfFontFactory.createFont(System.getProperty("user.dir") + "/simsun.ttc,1",
					PdfEncodings.IDENTITY_H, false);
		} else {
			ch_font = PdfFontFactory.createFont("c://windows//fonts//simsun.ttc,1", PdfEncodings.IDENTITY_H, false);
		}

		// ch_font = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H", false);
		pdf.addEventHandler(PdfDocumentEvent.END_PAGE, (IEventHandler) this);

		// 标题
		Paragraph paragraph = new Paragraph(getProject_name() + "项目代码审计报告");
		Style style_title = new Style().setFontSize(20).setTextAlignment(TextAlignment.CENTER).setBold();
		paragraph.setFont(ch_font);
		paragraph.addStyle(style_title);
		document.add(paragraph);

		// 空白
		Paragraph paragraph_break = new Paragraph("\n");
		for (int i = 0; i < 3; i++) {
			document.add(paragraph_break);
		}

		Image logo = new Image(ImageDataFactory.create("classpath:/image/tcsec_logo.png"));
		Paragraph p = new Paragraph();
		p.add(logo);
		p.addStyle(new Style().setTextAlignment(TextAlignment.CENTER));
		document.add(p);

		Image qrcode = new Image(ImageDataFactory.create("classpath:/image/qrCode.png"));
		qrcode.setHeight(160);
		qrcode.setWidth(160);
		Paragraph qrcodep = new Paragraph();
		qrcodep.add(qrcode);
		qrcodep.addStyle(new Style().setTextAlignment(TextAlignment.CENTER));
		document.add(qrcodep);

		// Paragraph p1 = new Paragraph();
		// BarcodeQRCode qrcode = new BarcodeQRCode("www.tcsec.com.cn");
		// Image mgd = new Image(qrcode.createFormXObject(pdf));
		// mgd.setHeight(160);
		// mgd.setWidth(160);
		// p1.addStyle(new Style().setTextAlignment(TextAlignment.CENTER));
		// p1.add(mgd);
		// document.add(p1);

		document.add(new Paragraph("扫码关注").setFont(ch_font)
				.addStyle(new Style().setFontSize(10).setTextAlignment(TextAlignment.CENTER)).setMarginTop(-1));

		// 空白
		for (int i = 0; i < 3; i++) {
			document.add(paragraph_break);
		}

		// 基本信息
		Style style_d2 = new Style().setFontSize(16);
		document.add(new Paragraph("项目名称：" + getProject_name()).setFont(ch_font));
		// document.add(new Paragraph("负责人："+get).setFont(ch_font));
		document.add(new Paragraph("扫描时间：" + getStar_time()).setFont(ch_font));
		// document.add(new Paragraph("版本：225").setFont(ch_font));
		// document.add(new Paragraph("分支：master").setFont(ch_font));

		document.add(new AreaBreak());
		document.add(new Paragraph("项目信息：" + getProject_name()).setFont(ch_font).addStyle(style_d2));
		// document.add(new Paragraph("文件总数：" + getProject_number()).setFont(ch_font));
		// document.add(new Paragraph("代码行数：" + getPlan_number()).setFont(ch_font));
		document.add(new Paragraph("项目描述：" + getProject_desc()).setFont(ch_font).addStyle(style_d2));
		document.add(new Paragraph("问题总数：" + getIssue_count()).setFont(ch_font));
		document.add(new Paragraph("受感染文件数量：" + getFile_count()).setFont(ch_font));
		document.add(new Paragraph("项目评分：" + getGrade()).setFont(ch_font));
		// 空白
		for (int i = 0; i < 3; i++) {
			document.add(paragraph_break);
		}
		// jfcPie = new JFreeChartPie();
		// // jfcPie.pieDate("文件总数:" + getProject_number(),
		// // Integer.parseInt(getProject_number()));
		// jfcPie.pieDate("问题总数:" + getIssue_count(),
		// Integer.parseInt(getIssue_count()));
		// jfcPie.pieDate("受感染文件数量:" + getFile_count(),
		// Integer.parseInt(getFile_count()));
		// Image piePicture = new
		// Image(ImageDataFactory.create(jfcPie.createPiePictrue()));
		// Paragraph pie = new Paragraph();
		// pie.add(piePicture);
		// pie.addStyle(new Style().setTextAlignment(TextAlignment.CENTER));
		// document.add(pie);

		String str = null;
		String pathStr = null;
		for (int i = 0; i < getReportList().size(); i++) {
			document.add(new AreaBreak());
			Table table = new Table(new float[] { 65, 30 }).setWidthPercent(100);
			Cell cell = new Cell(1, 1).add(new Paragraph("问题" + (i + 1) + "：").setFont(ch_font));
			str = getReportList().get(i).getCheck_name();
			Cell cell2 = new Cell(1, 1).add(new Paragraph(str.substring(1, str.length() - 1)).setFont(ch_font));
			Cell cell3 = new Cell(1, 1).add(new Paragraph("漏洞等级：").setFont(ch_font));
			str = getReportList().get(i).getSeve();
			Cell cell4 = new Cell(1, 1).add(new Paragraph(str.substring(1, str.length() - 1)).setFont(ch_font));
			Cell cell5 = new Cell(1, 1).add(new Paragraph("所在文件：").setFont(ch_font));
			str = getReportList().get(i).getLocation();
			str = str.substring(1, str.length() - 1);
			pathStr = "";
			if (str.length() / 75 == 0 || str.length() == 75) {
				pathStr = str;
			} else {
				for (int ib = 0; ib < ((str.length() % 75) > 0 ? (str.length() / 75 + 1) : str.length() / 75); ib++) {
					pathStr = pathStr + ((ib * 75 + 75 > str.length()) ? (str.substring(ib * 75))
							: (str.substring(ib * 75, ib * 75 + 75) + "\n"));
				}
			}
			Cell cell6 = new Cell(1, 1).add(new Paragraph(pathStr).setFont(ch_font));
			Cell cell7 = new Cell(1, 1).add(new Paragraph("问题描述：").setFont(ch_font));
			str = getReportList().get(i).getDescription();
			Cell cell8 = new Cell(1, 1).add(new Paragraph(str.substring(1, str.length() - 1)).setFont(ch_font));
			Cell cell9 = new Cell(1, 1).add(new Paragraph("解决建议：").setFont(ch_font));
			str = getReportList().get(i).getMessage();
			Cell cell10 = new Cell(1, 1).add(new Paragraph(str.substring(1, str.length() - 1)).setFont(ch_font));
			Cell cell11 = new Cell(1, 2).add(new Paragraph("代码片段：").setFont(ch_font));
			Cell cell12 = new Cell(10, 2);
			formatCode(cell12, getReportList().get(i).getMainCode());
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
		}
		document.add(new AreaBreak());
		document.add(new Paragraph("建议：完善修改有安全隐患的代码").setFont(ch_font));

		PdfOutline outline = pdf.getOutlines(false);
		outline = outline.addOutline("概要");
		outline.addDestination(PdfExplicitDestination.createFitH(pdf.getPage(1), 10));
		for (int i = 0; i < getReportList().size(); i++) {
			PdfOutline kid = outline.addOutline("问题" + (i + 1));
			kid.addDestination(PdfExplicitDestination.createFitH(pdf.getPage(i + 2), 10));

		}

		document.close();
	}

	private void formatCode(Cell cell, String code) {
		int len = 90;
		if (code.isEmpty()) {
			return;
		}
		String pathStr = null;
		code = code.substring(1, code.length() - 1);
		for (String str : code.split("\\\\n")) {
			int idd = 0;
			while (str.startsWith("\\t")) {
				str = str.substring(2);
				idd = idd + 40;
			}
			System.out.println("11fds11" + (idd + str.length()) / len);
			idd = idd / 10;
			if ((idd + str.length()) / len == 0 || (idd + str.length()) == len) {
				pathStr = str;
			} else {
				System.out.println(str);
				pathStr = "";
				for (int ib = 0; ib < (((str.length() + idd) % len) > 0 ? ((str.length() + idd) / len + 1)
						: (str.length() + idd) / len); ib++) {
					System.out.println((ib * len + len));
					System.out.println(str.length() + idd);
					System.out.println(ib * len - idd);
					System.out.println(ib == 0);
					System.out.println(ib * len);
					System.out.println(ib * len - idd);
					System.out.println(ib * len + len - idd);
					pathStr = pathStr + (((ib * len + len) > (str.length() + idd)) ? (str.substring(ib * len - idd))
							: (str.substring((ib == 0) ? ib * len : (ib * len - idd), ib * len + len - idd) + "\n"));
				}
			}
			Paragraph paragraph1 = new Paragraph(pathStr).setFont(ch_font);
			cell.add(paragraph1.setMarginLeft(idd * 10));
		}
	}

	@Override
	protected void doBuilder() throws Exception {

	}

	@Override
	protected byte[] getByte() throws Exception {
		return out.toByteArray();
	}

	@Override
	public void handleEvent(Event event) {
		PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
		PdfDocument pdfDoc = docEvent.getDocument();
		PdfPage page = docEvent.getPage();
		int pageNumber = pdfDoc.getPageNumber(page);
		Rectangle pageSize = page.getPageSize();
		PdfCanvas pdfCanvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);
		pdfCanvas.beginText().setFontAndSize(ch_font, 9).moveText(pageSize.getWidth() / 2 - 40, pageSize.getTop() - 20)
				.showText("杭州孝道科技有限公司").moveText(-220, -pageSize.getTop() + 30)
				.showText(
						"地址:浙江省杭州市中国智慧产业园H座506              网址:www.tcsec.com.cn                       联系电话:0571-85358276")
				.endText();
		pdfCanvas.release();

	}

}
