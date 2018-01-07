import java.io.FileWriter;
import java.io.IOError;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

/**
 * @author Ben Horn and Sefi Erlich
 * @since 1/2018
 *
 */
public class YnetPage {

	static WebDriver driver;

	public static void main(String[] args) {

		List<String> urls=new ArrayList<String>();
		//		urls.add("https://www.ynet.co.il/articles/0,7340,L-5066878,00.html");
		urls.add("https://www.ynet.co.il/articles/0,7340,L-5054045,00.html");
		//		urls.add("https://www.ynet.co.il/articles/0,7340,L-5054255,00.html");

		linksToCsv(urls);
	}

	
	
	public void starthasthread(String url,String output){
		collectingThread th=new collectingThread(url,output);
		th.start();
	}

	public static void linksToCsv(List<String> urls){

		String url="";
		driver = new ChromeDriver();
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		driver.get("http://google.com");

		for(int i=0; i<urls.size(); i++){

			
			url=urls.get(i);
			driver.navigate().to(url);

			urlToFile(url);
		}
		driver.quit();
		System.out.println("finish Ynet");
	}


	private static void urlToFile(String url) {

		try{
			
			Funcs.sleep(2000);

			//headline
			WebElement hl= driver.findElements(By.className("art_header_title")).get(0);
			String headline=hl.getText();


			//publish date
			WebElement pt= driver.findElements(By.className("art_header_footer")).get(0);
			String[] dt =pt.getText().split(":")[1].split(",");
			String publishDate=dt[0].trim();

			//reporter
			WebElement rpr= driver.findElements(By.className("art_header_footer_author")).get(0);
			String reporter=rpr.getText();

			//body
			WebElement body= driver.findElements(By.className("text14")).get(0);
			String article=body.getText();

			ArticlesRow ar = new ArticlesRow();

			ar.body = article;
			ar.site = "Ynet";
			ar.date =publishDate;
			ar.reporter = reporter;
			ar.headLine = headline;
			int an=ar.num;
			ar.comments = getComments(url, an);

			ar.WriteToFile();

			//		System.out.println(headline);
			//		System.out.println(reporter);
			//		System.out.println(publishDate);
			//		System.out.println(article);
			//		System.out.println(ar.comments);
		}catch (Exception e){
			System.err.println(e);
		}
	}


	private static String getComments(String url, int articleNum) {
		ArrayList<String>  cmmt =  commentSecction(url, articleNum );
		String allComments="";
		for(int i=0; i<cmmt.size(); i++){
			allComments+=cmmt.get(i)+'\n';
		}
		return allComments;
	}



	public  static  ArrayList<String>  commentSecction(String url, int articleNum)  {
		ArrayList<String> linesToWire=null;

		boolean IsComments=true;
		//CLICK ON SHOW ALL COMMENTS
		try {
			Funcs.sleep(2000);

			List<WebElement> showAllCommentsButton = driver.findElements(By.cssSelector("#stlkbcopenalldiv"));

			WebElement we=showAllCommentsButton.get(0);  

			JavascriptExecutor jse2 = (JavascriptExecutor)driver;
			jse2.executeScript("arguments[0].scrollIntoView()", we); 
			we.click();


		}
		catch (Exception e){
			System.err.println("no comments"+e);
			IsComments=false;
		}



		linesToWire=new ArrayList<String>();
		while(IsComments) {
			readComments(linesToWire , articleNum);
			try {
				//search for "next" button
				WebElement NextButton = driver.findElement(By.xpath("//*[@class='tkb_arrow sprite_article_tkb_arrow_next']"));
				JavascriptExecutor jse2 = (JavascriptExecutor)driver;
				jse2.executeScript("arguments[0].scrollIntoView()", NextButton); 
				Funcs.sleep(1000);

				NextButton.click();

				Funcs.sleep(2000);


			}
			catch (Exception e){
				System.out.println("finish url");
				break;
			}
		}
		return linesToWire;

	}



	public static int readComments(ArrayList<String> linesToWire, int articleNum){
		int last=1;
		Funcs.sleep(1000);
		WebElement Commentdiv= driver.findElements(By.className("art_tkb")).get(0);
		List<WebElement> blocks=Commentdiv.findElements(By.xpath("//*[contains(@id, 'tcontentdiv')]"));
		for (WebElement we:blocks){ //for every comment, do:


			WebElement headline=we.findElement(By.className("art_tkb_talkback_details_inner"));

			WebElement number=we.findElement(By.className("art_tkb_talkback_index"));

			WebElement text=we.findElement(By.xpath("./div[contains(@id, 'ttextcont')]"));

			linesToWire.add(new String(getHeadline(headline.getText())+" : "+text.getText()));

			CommentRow cr = new CommentRow();
			cr.site = "Ynet";
			cr.ArticleNum = articleNum;
			cr.comment = text.getText();
			cr.headline = getHeadline(headline.getText());
			cr.talkbakist = getTalkbackist(headline.getText());

			if(!number.getText().isEmpty()){
				last = setConvNum(number.getText());
			}

			cr.convNum = last;
			cr.WriteToFile();

			//			System.err.println(headline.getText());
			//			System.out.println(text.getText());
			//			System.out.println(number.getText());
			//			System.out.println();

		}
		return last;
	}

	private static int setConvNum(String text) {
		try{
			return Integer.parseInt(text.substring(0, text.length()-1));
		}
		catch(NullPointerException e){return 0;}
		catch(StringIndexOutOfBoundsException e){
			return -1;
		}
	}

	public static String getHeadline(String text) {
		String[] arr = text.split("\n");
		String str="";

		try{
			str = arr[0];
		}
		finally{return str;}

	}
	public static String getTalkbackist(String text) {
		String[] arr = text.split("\n");
		String str="";


		try{
			str = arr[1];
		}
		finally{return str;}
	}

}
