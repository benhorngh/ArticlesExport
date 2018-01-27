import java.io.FileWriter;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
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
	 *  the visibility of the webdriver window. by WindowState enum.
	 */
	WindowState window = WindowState.Regular;

	
	public WebDriver startWebDriver(String url){
		if(this.window == WindowState.Regular){
			return startRegularWebDriver(url);
		}
		if(this.window == WindowState.Background){
			return startDistanceWebDriver(url);
		}
		if(this.window == WindowState.Invisible){
			return startHeadlessWebDriver(url);
		}
		return null;
	}
	
	
	/**
	 * this function open and start new visable WebDriver
	 * @param url -first url
	 * @return new webDriver open with param url
	 */
	public WebDriver startRegularWebDriver(String url){
		WebDriver driver;
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
//		options.addArguments("--headless");
		options.addArguments("--start-maximized");
		driver = new ChromeDriver(options);
		try{
//			driver.manage().window().setPosition(new Point(-2000, 0));
		}
		catch(Exception e){System.err.println(e);}


		if(url.isEmpty() || url.equals(""))
			return driver;
		driver.get(url);
		return driver;
	}
	
	
	
	/**
	 * this function open and start new invisible WebDriver
	 * @param url -first url
	 * @return new webDriver open with param url
	 */
	public WebDriver startHeadlessWebDriver(String url){
		WebDriver driver;
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		options.addArguments("--start-maximized");
		driver = new ChromeDriver(options);
		


		if(url.isEmpty() || url.equals(""))
			return driver;
		driver.get(url);
		return driver;
	}
	
	
	
	
	/**
	 * this function open and start new WebDriver in background
	 * @param url -first url
	 * @return new webDriver open with param url
	 */
	public WebDriver startDistanceWebDriver(String url){
		WebDriver driver;
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
//		options.addArguments("--headless");
		options.addArguments("--start-maximized");
		driver = new ChromeDriver(options);
		try{
			driver.manage().window().setPosition(new Point(-2000, 0));
		}
		catch(Exception e){System.err.println(e);}


		if(url.isEmpty() || url.equals(""))
			return driver;
		driver.get(url);
		return driver;
	}


	/**
	 * replase " with ' ' (space)
	 * @param text
	 * @return
	 */
	public String SearchField(String text) {
		String newStr = text;
		if(text.indexOf('"'+"")!=-1){
			newStr=text.replace('"', ' ');
		}
		return newStr;
	}


	/**
	 * move to element in page. 
	 * @param driver
	 * @param web element
	 */
	public boolean moveTo(WebDriver driver, WebElement we){
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
	public boolean moveTo2(WebDriver driver, WebElement we){
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("arguments[0].scrollIntoView()", we); 
		return true;
	}



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
	public void sleep(int mil){
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

	



}
