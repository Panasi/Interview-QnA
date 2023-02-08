package com.panasi.interview_questions.payload;

import java.io.IOException;
import java.util.List;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.panasi.interview_questions.repository.dto.FullAnswerDto;
import com.panasi.interview_questions.repository.dto.FullQuestionDto;


public final class PdfEditor {
	
	private static final String ARIAL = "./src/main/resources/fonts/Arial.ttf";
	
	private static PdfFont createFont() throws IOException {
		return PdfFontFactory.createFont(ARIAL);
	}
	
	private static void addTitle(Document document, String title) throws IOException {
		Paragraph titleParagraph = new Paragraph(title);
		titleParagraph.setFont(createFont());
		titleParagraph.setFontSize(16);
		titleParagraph.setBold();
		titleParagraph.setTextAlignment(TextAlignment.CENTER);
		titleParagraph.setMarginBottom(5);
		document.add(titleParagraph);
	}
	
	private static void addList(Document document, List<FullQuestionDto> questions) throws IOException {
		
		for (int i = 0; i < questions.size(); i++) {
			
			FullQuestionDto question = questions.get(i);
			Paragraph questionParagraph = new Paragraph(i + 1 + ". " + question.getName());
			questionParagraph.setFont(createFont());
			questionParagraph.setFontSize(14);
			questionParagraph.setMarginTop(10);
			questionParagraph.setMarginBottom(5);
			document.add(questionParagraph);
			
			List<FullAnswerDto> answers = question.getAnswers();
			com.itextpdf.layout.element.List list = new com.itextpdf.layout.element.List();
			list.setFont(createFont());
			list.setFontSize(11);
			list.setItalic();
			list.setTextAlignment(TextAlignment.JUSTIFIED);
			
			answers.forEach(answer -> {
				ListItem listItem = new ListItem(answer.getName());
				listItem.setMarginBottom(5);
				list.add(listItem);
			});
			document.add(list);
			
		}
		
	}
	
	public static void createPDF(String filePath, String title, List<FullQuestionDto> questions) throws IOException {
		
		PdfWriter writer = new PdfWriter(filePath);
		PdfDocument pdfDoc = new PdfDocument(writer);
		pdfDoc.addNewPage();
		Document document = new Document(pdfDoc);
		
		addTitle(document, title);
		addList(document, questions);
		
	    document.close();
	    
	 }
	
}
