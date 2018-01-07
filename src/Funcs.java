import java.io.FileWriter;

import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;


/**
 * @author Ben Horn and Sefi Erlich
 * @since 1/2018
 *
 */
public class Funcs {

	/**
	 * this finction write a String arr to the last row in sheet
	 */
	public static void StringArrToLastRow(String[] arr, XSSFSheet sheet) {
		Row row = sheet.getRow(sheet.getLastRowNum());
		if(row==null)
			row = sheet.createRow(sheet.getLastRowNum());
		else row = sheet.createRow(sheet.getLastRowNum()+1);
		
		
		Cell cell;
		for(int i=0; i<arr.length; i++){
			cell = row.createCell(i);
			cell.setCellValue(arr[i]);
		}
	}
	

	/**
	 * thread sleep for mil milliseconds
	 */
	public static void sleep(int mil){
		try {
			Thread.sleep(mil);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * apeand long text in file, seperate by ',' (next cell) 
	 */
	public static void apeandComments(FileWriter writer, String text){
		try {
			if(text.length()>32000){
				int size=0;
				int i=1;
				while(size+30000<text.length()){
					writer.append(text.substring(size, 30000*i)+',');
					i++;
					size+=30000;
				}
				writer.append(text.substring(size, text.length())+',');
			}
			else
				writer.append(text+',');
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}




	}

	
	/**
	 * remove ',' and '\n' from text, before insert to csv file
	 */
	public static String clean(String text){
		String str="";
		str=text.replaceAll("," , "/");
		str = str.replaceAll("\n", "	");
		str = str.replaceAll("\r", "	");
		str = str.replace('\n','	');
		str = str.replace(',', '/');
		str = str.replace('\r', '	');

		return str;
	}



}
