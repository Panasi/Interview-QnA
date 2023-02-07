package com.panasi.interview_questions.payload;

import java.io.FileOutputStream;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.panasi.interview_questions.repository.dto.FullQuestionDto;

public class PdfEditor {
	
	private static Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.BOLD);
	private static Font questionFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.NORMAL);
	private static Font answerFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL);
	
	private static void addTitle(String titleName, Document document) throws DocumentException {
		Paragraph title = new Paragraph(titleName, titleFont);
	    title.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
	    addEmptyLine(title, 1);
	    document.add(title);
	}
	
	private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
	
	public static void editPDF(List<FullQuestionDto> questions, String titleName, FileOutputStream fileOutputStream) throws DocumentException {
		
	    Document document = new Document(PageSize.A4);
	    PdfWriter.getInstance(document, fileOutputStream);
	    document.open();
	    
	    addTitle(titleName, document);
	    
	    questions.forEach(question -> {
	    	
	    	Paragraph questionParagraph = new Paragraph(question.getId() + ". " + question.getName(), questionFont);
	    	questionParagraph.setSpacingBefore(8);
	    	try {
				document.add(questionParagraph);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
	    	question.getAnswers().forEach(answer -> {
	    		Paragraph answerParagraph = new Paragraph("-  " + answer.getName(), answerFont);
	    		answerParagraph.setIndentationLeft(20);
	    		try {
					document.add(answerParagraph);
				} catch (DocumentException e) {
					e.printStackTrace();
				}
	    	});
	    	
	    });
	    
	    document.close();
	    
	 }
	
}
