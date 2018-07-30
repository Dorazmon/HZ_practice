package cn.com.tcsec.sdlmp.auth.report;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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

import cn.com.tcsec.sdlmp.auth.mapper.AuthMapper;
import cn.com.tcsec.sdlmp.common.util.JFreeChartPie;

public class ReportBuilderImpl extends AbstractReportBuilder implements IEventHandler {

	private AuthMapper authMapper;
	private String taskId;

	public ReportBuilderImpl(AuthMapper authMapper, String taskId) {
		this.authMapper = authMapper;
		this.taskId = taskId;
	}

	@Override
	protected AuthMapper getAuthMapper() throws Exception {
		// TODO Auto-generated method stub
		return this.authMapper;
	}

	@Override
	protected String getTaskId() throws Exception {
		// TODO Auto-generated method stub
		return this.taskId;
	}

	@Override
	protected void init() throws Exception {
		// TODO Auto-generated method stub
		PdfDocument pdf = null;
		Document document = null;
		PdfFont helveticaBold = null;
		PdfFont helvetica = null;
		PdfFont ch_font = null;
		JFreeChartPie jfcPie = null;
		PdfWriter writer = new PdfWriter("tcsec-servers项目代码审计报告.pdf");
		pdf = new PdfDocument(writer);
		document = new Document(pdf);
		helveticaBold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
		helvetica = PdfFontFactory.createFont(FontConstants.HELVETICA);

		pdf.addEventHandler(PdfDocumentEvent.END_PAGE, (IEventHandler) this);

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
		document.add(new Paragraph("项目信息：" + getProject_name()).setFont(ch_font).addStyle(style_d2));
		document.add(new Paragraph("文件总数：" + getProject_number()).setFont(ch_font));
		document.add(new Paragraph("代码行数：" + getPlan_number()).setFont(ch_font));
		document.add(new Paragraph("扫描结果：" + getProject_desc()).setFont(ch_font).addStyle(style_d2));
		document.add(new Paragraph("问题总数：" + getIssue_count()).setFont(ch_font));
		document.add(new Paragraph("受感染文件数量：" + getFile_count()).setFont(ch_font));
		document.add(new Paragraph("项目评分：" + getGrade()).setFont(ch_font));
		// 空白
		for (int i = 0; i < 3; i++) {
			document.add(paragraph_break);
		}
		jfcPie = new JFreeChartPie();
		jfcPie.pieDate("文件总数:" + getProject_number(), Integer.parseInt(getProject_number()));
		jfcPie.pieDate("问题总数:" + getIssue_count(), Integer.parseInt(getIssue_count()));
		jfcPie.pieDate("受感染文件数量:" + getFile_count(), Integer.parseInt(getFile_count()));
		jfcPie.createPiePictrue();
		Image piePicture = new Image(ImageDataFactory.create("PieChart.png"));
		Paragraph pie = new Paragraph();
		pie.add(piePicture);
		pie.addStyle(new Style().setTextAlignment(TextAlignment.CENTER));
		document.add(pie);

		for (int i = 0; i < getReportList().size(); i++) {
			document.add(new AreaBreak());
			Table table = new Table(new float[] { 70, 30 }).setWidthPercent(100);
			Cell cell = new Cell(1, 1).add(new Paragraph("问题" + (i + 1) + "：").setFont(ch_font));
			Cell cell2 = new Cell(1, 1).add(new Paragraph(getReportList().get(i).getCheck_name()).setFont(ch_font));
			Cell cell3 = new Cell(1, 1).add(new Paragraph("漏洞等级：").setFont(ch_font));
			Cell cell4 = new Cell(1, 1).add(new Paragraph(getReportList().get(i).getSeve()).setFont(ch_font));
			Cell cell5 = new Cell(1, 1).add(new Paragraph("所在文件：").setFont(ch_font));
			Cell cell6 = new Cell(1, 1).add(new Paragraph(getReportList().get(i).getLocation()).setFont(ch_font));
			Cell cell7 = new Cell(1, 1).add(new Paragraph("问题描述：").setFont(ch_font));
			Cell cell8 = new Cell(1, 1).add(new Paragraph(getReportList().get(i).getDescription()).setFont(ch_font));
			Cell cell9 = new Cell(1, 1).add(new Paragraph("解决建议：").setFont(ch_font));
			Cell cell10 = new Cell(1, 1).add(new Paragraph(getReportList().get(i).getBody()).setFont(ch_font));
			Cell cell11 = new Cell(1, 2).add(new Paragraph("代码片段：").setFont(ch_font));
			Cell cell12 = new Cell(10, 2).add(new Paragraph(getReportList().get(i).getMainCode()).setFont(ch_font));
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

	@Override
	protected void doBuilder() throws Exception {
     
	}

	@Override
	protected byte[] getByte() throws Exception {
		// TODO Auto-generated method stub
		byte[] buffer = null;  
        try {  
            File file = new File("tcsec-servers项目代码审计报告.pdf");  
            FileInputStream fis = new FileInputStream(file);  
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);  
            byte[] b = new byte[1000];  
            int n;  
            while ((n = fis.read(b)) != -1) {  
                bos.write(b, 0, n);  
            }  
            fis.close();  
            bos.close();  
            buffer = bos.toByteArray();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return buffer;  
	}

	@Override
	public void handleEvent(Event event) {
		// TODO Auto-generated method stub
		PdfFont ch_font = null;
		try {
			ch_font = PdfFontFactory.createFont("c://windows//fonts//simsun.ttc,1", PdfEncodings.IDENTITY_H, false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
