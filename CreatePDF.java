package pdfcertificate;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;


import sendMail.SendMail;

public class CreatePDF {


public void getCertificate(String name,String description,String signDate,String mail) throws Exception{	
		
		//Document and Page Creation
		PDDocument doc = new PDDocument();
		PDRectangle pageSize = new PDRectangle(842, 595);
		PDPage docpage = new PDPage(pageSize);
		doc.addPage(docpage);
		PDPage page = doc.getPage(0);
		
		//Page Content Stream Object Creation of the PDF Document.
		PDPageContentStream content = new PDPageContentStream(doc, page);
		
		//Load the Background in Page
		PDImageXObject background = PDImageXObject.createFromFile("src\\main\\resources\\Certificate1.jpg", doc);
		content.drawImage(background, 0, 0, 842, 595);
		
		//Load Signature in the Certificate
		PDImageXObject sign = PDImageXObject.createFromFile("src\\main\\resources\\sign1.jpg", doc);
		content.drawImage(sign, 560, 120, 150, 40);
		
		//Color Object Creation
		Color nameColor =  Color.DARK_GRAY;
		Color blackColor = Color.BLACK;
		
		//Load Name on the Certificate 
		
		//Selecting the Font using file input stream
		FileInputStream fontFileAllura = new FileInputStream("src\\main\\resources\\Allura-Regular.ttf");
		PDType0Font nameFont = PDType0Font.load(doc, fontFileAllura, true);
		FileInputStream fontFileLato = new FileInputStream("src\\main\\resources\\Allura-Regular.ttf");
		PDType0Font latoFont = PDType0Font.load(doc, fontFileLato, true);
		
		//Calculate the Name String Height and Width for Center the Text		
		int nameFontSize = 40; // Or whatever font size you want.
		float titleWidth = nameFont.getStringWidth(name) / 1000 * nameFontSize; // get your text width 
		float titleHeight = nameFont.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * nameFontSize; // get your text height
				
		//Text Name append		
		content.beginText();	
		content.newLineAtOffset((page.getMediaBox().getWidth() - titleWidth) / 2, page.getMediaBox().getHeight() - 257 - titleHeight); // this is make your text center
		content.setFont(nameFont, nameFontSize);		
		content.setNonStrokingColor(nameColor);			
		content.showText(name);		
		content.endText();	
		
		//Load Sign Date on the certificate 
		
		//Calculate the Sign Date String Height and Width for Center the Text		
		int signDateFontSize = 14; // Or whatever font size you want.
		float signDateWidth = latoFont.getStringWidth(signDate) / 1000 * signDateFontSize; // get your text width 
		float signDateHeight = latoFont.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * signDateFontSize; // get your text height
		
		//Text Sign Text Append 
		content.beginText();
	
		content.newLineAtOffset((page.getMediaBox().getWidth() - signDateWidth) / 2 + 205 , page.getMediaBox().getHeight() - 475 - signDateHeight); 
		content.setLeading(14.5f);
		content.setFont(latoFont, signDateFontSize);
		content.setNonStrokingColor(blackColor);
		content.showText(signDate);		
		content.endText();		
		
					
				
	    
		//Certificate Content 
		
		//Paragraph font size and margin 
		float contentFontSize = 12;
		float margin = 200;
		PDRectangle mediabox = page.getMediaBox(); 
		float width = mediabox.getWidth() - 2*margin; 
		float leading = 1.5f * contentFontSize;
		
		//passing the font size and margin to the paragraph method in the text class. 
		//That is return the lines as a Arraylist.		
		PDF_ForntText text = new PDF_ForntText(page);		
		ArrayList<String> lines = text.paragraph(description, latoFont, contentFontSize, width);
		content.beginText();
		content.setFont(latoFont, contentFontSize);	 
		float contentWidth = latoFont.getStringWidth(lines.get(0)) / 1000 * contentFontSize; // get your text width 
		float contentHeight = latoFont.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * contentFontSize; // get your text height
		content.newLineAtOffset((page.getMediaBox().getWidth() - contentWidth) / 2.5f , page.getMediaBox().getHeight() - 330 - contentHeight); 
		
			
		//Printing the array list with text align center logic.
		int i = 0;		
		for (String line: lines){
		    if(i>=0) {
		    	float contentWidth2 = latoFont.getStringWidth(line) / 1000 * contentFontSize; // get your text width
			    content.newLineAtOffset((width - contentWidth2) / 2 , -leading);
		    }	        
		    content.showText(line) ;	 
		    i++;
		}
		content.endText(); 
			
	   		
		content.close();//Should close the Content Stream in final stage.
		
		
		
		//mail attach Excel file setContent
		
		Multipart multipart=new MimeMultipart();
		BodyPart messagetext=new MimeBodyPart();	
		MimeBodyPart messageBodyPart=new MimeBodyPart();
		
		
		
		 File file= new File(name+".pdf");
			FileOutputStream outfile=new FileOutputStream(file);
			doc.save(outfile);
	
		DataSource source=new FileDataSource(file);
		messageBodyPart.setDataHandler(new DataHandler(source));
	
		messageBodyPart.attachFile(file);
		
		
	           
		messagetext.setText(" Dear "+name+"\n\n Greething To You !\n\n Your JAVA Course is complete succussfully and Certiticate Genarated\n \n\nThank & Regards,\nMr. Satheesh kumar P\nFullStack Development Trainee,\nHaaris Infotech Pvt.Ltd,\nE.mail:psk30198@gmail.com "  );
		multipart.addBodyPart(messagetext);
		multipart.addBodyPart(messageBodyPart);
		
	
	//	doc.save("C:\\Users\\smile\\Desktop\\"+name+".pdf");//Save the Document Path as where you want.
		doc.close();//Should close the doc final stage
		
	

	
		System.out.println(name+" E-Certificate Genarated succussfully");
		SendMail.sendme(mail, name,multipart);
		
		
	}

}
