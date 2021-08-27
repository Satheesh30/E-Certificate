package pdfcertificate;

import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDType0Font;


public class PDF_ForntText {
	
	PDPage page;	
	public PDF_ForntText(PDPage page) {
		this.page = page;
	}
	public ArrayList<String> paragraph(String text,PDType0Font font,float fontSize,float width) throws Exception{
	    
	    ArrayList<String> lines = new ArrayList<String>();
	    int lastSpace = -1;
	    while (text.length() > 0)
	    {
	        int spaceIndex = text.indexOf(' ', lastSpace + 1);
	        if (spaceIndex < 0)
	            spaceIndex = text.length();
	        String subString = text.substring(0, spaceIndex);
	        float size = fontSize * font.getStringWidth(subString) / 1000;
	       // System.out.printf("'%s' - %f of %f\n", subString, size, width);
	        if (size > width)
	        {
	            if (lastSpace < 0)
	                lastSpace = spaceIndex;
	            subString = text.substring(0, lastSpace);
	            lines.add(subString);
	            text = text.substring(lastSpace).trim();
	           // System.out.printf("'%s' is line\n", subString);
	            lastSpace = -1;
	        }
	        else if (spaceIndex == text.length())
	        {
	            lines.add(text);
	            //System.out.printf("'%s' is line\n", text);
	            text = "";
	        }
	        else
	        {
	            lastSpace = spaceIndex;
	        }
	    }	
	    return lines;
		
	}
}