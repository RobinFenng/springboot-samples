package com.moregx.viewresolver.view;

import java.awt.Color;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.moregx.model.Apple;

public  class PdfView extends AbstractPdfView {


	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		  PdfPTable table = new PdfPTable(2);
		  table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		  table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
		  table.getDefaultCell().setBackgroundColor(Color.LIGHT_GRAY);
		  // 这里为了测试，实际应用中，我们从model中读取值
		  Apple apple = (Apple)model.get("apple");
		  
		  //中文不显示 需要另外处理
		  table.addCell("name");
		  table.addCell("color");
		  table.addCell(apple.getName());
		  table.addCell(apple.getColor());
		  document.add(table);
	}


}
