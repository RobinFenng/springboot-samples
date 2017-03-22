package com.moregx.view;

import java.awt.Color;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Element;
import com.lowagie.text.pdf.PdfPTable;
import com.moregx.model.Apple;

public  class PdfView extends AbstractPdfView {


	@Override
	protected void buildPdfDocument(Map<String, Object> model, com.lowagie.text.Document document,
			com.lowagie.text.pdf.PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PdfPTable table = new PdfPTable(2);
		Apple apple = (Apple)model;
		
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.getDefaultCell().setBackgroundColor(Color.lightGray);

        table.addCell("ID");
        table.addCell("NAME");

        table.addCell(apple.getName());
        table.addCell(apple.getColor());

        document.add(table);
		
	}

}
