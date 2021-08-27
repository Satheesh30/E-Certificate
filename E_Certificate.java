package pdfcertificate;
/*
 * 1. create the certificate 
 * 	 i) PDFBox jar file use to create PDF format
 * 	 ii)Sending to mail 
 * 2. Method 
 * 	i) CreatePDF.java file to making PDF format
 * 	ii)SendMail.java file  use to send  through Email ID
 */

import java.io.File;
import java.util.Scanner;

public class E_Certificate {
	
	public static void main(String[] args)throws Exception {
		
		CreatePDF pdf=new CreatePDF();
		
	
		File names=new File("src/main/resources/Name");
		Scanner readName=new Scanner(names);//Scanner object is created
		int lastIndex;
		String address;
		while(readName.hasNext()) {//Reads all name and email id form the text file and store it .
			address=readName.nextLine();
			lastIndex=address.lastIndexOf("-");
			String mail=address.substring(0,lastIndex);
			String name=address.substring(lastIndex+1,address.length());
		
		//content of E-certificate in the subject
		String description="Has satisfactorily completed 5 months of “Java Full Stack, MEAN Stack, and MERN Stack” training from 1/5/2021 to 28/10/2021.";
		// sign date of the certificate create
		String signDate="29/10/2021 ";
		// passing content of Certificate to Genarated		
		pdf.getCertificate(name, description, signDate,mail);
		System.out.println("**********************************************************************");
		}
		readName.close();
		
			
		

}

}