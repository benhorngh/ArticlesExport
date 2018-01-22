import java.io.FileWriter;

import java.io.IOException;
import java.util.Arrays;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;


/**
 * @author Ben Horn
 * @since 1/2018
 *
 */
public abstract class Funcs {
	

	
	/**
	 * this function open and start new WebDriver
	 * @param url -first url
	 * @return new webDriver open with param url
	 */
	public static WebDriver startWebDriver(String url){
		WebDriver driver;
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		driver = new ChromeDriver(options);
		driver.get(url);
		return driver;
	}
	
	


	/**
	 * move to element in page. 
	 * @param driver
	 * @param web element
	 */
	public static boolean moveTo(WebDriver driver, WebElement we){
		Actions actions = new Actions(driver);
		try{
			actions.moveToElement(we).perform();
		}
		catch (IndexOutOfBoundsException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * move to element in page. 
	 * @param driver
	 * @param web element
	 */
	public static boolean moveTo2(WebDriver driver, WebElement we){
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("arguments[0].scrollIntoView()", we); 
		return true;
	}


//	/**
//	 * check if the headline contains the keys.
//	 * if there ar words surrounded by " " the finction will relate them like one word.
//	 * @param headLine
//	 * @param text
//	 * @return true if contains, false otherwise
//	 */
//	public static boolean contain(String headLine, String text) {
//		text= text.trim();
//		int index =0;
//		int count = 0;
//		String gr = '"'+"";
//		while(index!= -1){
//			count ++;
//			index = text.indexOf(gr ,index+1);
//		}
//		count = count /2;
//		String[] grs = new String[count];
//
//		for(int i=0; i<count; i++){
//			int start = text.indexOf(gr);
//			int end = text.indexOf(gr, start+1);
//			grs[i]= text.substring(start, end+1);
//			grs[i]= grs[i].replaceAll(gr, "");
//			text= text.substring(0, start)+text.substring(end+1);
//		}
//		String[] words = text.split(" ");
//
//		//		System.out.println(Arrays.toString(words));
//		//		System.out.println(Arrays.toString(grs));
//
//		String[] keys = new String[words.length+ grs.length];
//
//		for(int i=0; i<grs.length; i++){
//			keys[i]=grs[i].trim();
//		}
//		for(int i=0; i<words.length; i++){
//			keys[i+grs.length]=words[i].trim();
//		}
//
//		for(int i=0; i<keys.length; i++){
//			if(!headLine.contains(keys[i]))
//				return false;
//		}
//
//		return true;
//	}

	/**
	 * this finction write a String arr to the last row in sheet
	 * @param arr
	 * @param sheet
	 */
	public static void StringArrToLastRow(String[] arr, XSSFSheet sheet) {
		Row row = sheet.getRow(sheet.getLastRowNum());
		if(row==null)
			row = sheet.createRow(sheet.getLastRowNum());
		else row = sheet.createRow(sheet.getLastRowNum()+1);
		String cmt="";
		if(arr[arr.length-1].length()>30000){
			cmt=arr[arr.length-1].substring(30000, arr[arr.length-1].length());
			arr[arr.length-1]=arr[arr.length-1].substring(0, 30000);
		}

		int i=0;
		Cell cell;
		for(i=0; i<arr.length; i++){
			cell = row.createCell(i);
			cell.setCellValue(arr[i]);
		}
		cell = row.createCell(i);
		cell.setCellValue(cmt);

	}


	/**
	 * thread sleep for mil milliseconds
	 * @param mil
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
	 * @param writer
	 * @param text
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
	 * @param text
	 * @return
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

	/**
	 * replase " with ' ' (space)
	 * @param text
	 * @return
	 */
	public static String SearchField(String text) {
		String newStr = text;
		if(text.indexOf('"'+"")!=-1){
			newStr=text.replace('"', ' ');
		}
		return newStr;
	}



}
