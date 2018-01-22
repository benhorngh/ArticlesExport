import java.io.FileWriter;
import java.io.IOError;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Ben Horn and Sefi Erlich
 * @since 1/2018
 *
 */
public class YnetPage extends Page  {


	public  ArticlesRow urlHandler(String url, boolean bodyOnly) {

		ArticlesRow ar = new ArticlesRow();
		try{

			sleep(2000);

			//headline
			WebElement hl= driver.findElements(By.className("art_header_title")).get(0);
			String headline=hl.getText();

			moveTo(driver, hl);


			//subheadline
			String subheadline="";
			try{
				WebElement shl= driver.findElement(By.className("art_header_sub_title"));
				subheadline=shl.getText();
			}
			catch(NoSuchElementException e){System.out.println("no subtitle");

			}

			//publish date
			WebElement pt= driver.findElements(By.className("art_header_footer")).get(0);
			String[] dt =pt.getText().split(":")[1].split(",");
			String publishDate=dt[0].trim();

			//reporter
			WebElement rpr= driver.findElements(By.className("art_header_footer_author")).get(0);
			String reporter=rpr.getText();

			//body
			List<WebElement> body =  driver.findElements(By.xpath("//*[@class='text14']/span/p"));
			ArrayList<WebElement> allPh = new ArrayList<WebElement>(body);
			String article="";
			for(int l=0; l <allPh.size(); l++){
				article+=allPh.get(l).getText();
			}

			if(bodyOnly){
				ar.body = article;
				return ar;
			}



			ArrayList<CommentRow> cmmts = getComments(url, ar.num);

			ar.body = article;
			ar.site = "Ynet";
			ar.date =publishDate;
			ar.reporter = reporter;
			ar.headLine = headline;
			ar.subHeadLine = subheadline;
			ar.comments = cmmts;
			//			ar.WriteToFile();
			//			CommentRow.WriteToFile(cmmts);


		}catch (Exception e){
			e.printStackTrace();
		}
		return ar;
	}

	/**
	 * this function get all the comments
	 * @param url
	 * @param articleNum
	 * @return all the comments connected
	 */
	public  ArrayList<CommentRow> getComments(String url, int articleNum) {
		ArrayList<CommentRow>  cmmt =  commentSecction(url, articleNum);
		return cmmt;

	}


	public  ArrayList<CommentRow>  commentSecction(String url, int articleNum)  {
		ArrayList<CommentRow> linesToWire=null;

		boolean IsComments=true;
		//CLICK ON SHOW ALL COMMENTS
		try {
			sleep(2000);

			List<WebElement> showAllCommentsButton = driver.findElements(By.cssSelector("#stlkbcopenalldiv"));

			WebElement we=showAllCommentsButton.get(0);  

			moveTo2(driver, we);
			we.click();
			sleep(3000);
			driver.navigate().refresh();
			sleep(3000);

		}
		catch (Exception e){
			System.err.println("no comments");
			IsComments=false;
		}

		linesToWire=new ArrayList<CommentRow>();
		while(IsComments) {

			readComments(linesToWire , articleNum);

			try {
				//search for "next" button
				WebElement NextButton = driver.findElement(By.xpath("//*[@class='tkb_arrow sprite_article_tkb_arrow_next']"));

				moveTo2(driver, NextButton);
				sleep(1000);

				NextButton.click();

				sleep(2000);


			}
			catch (Exception e){
				System.out.println("finish url");
				break;
			}
		}
		return linesToWire;
	}

	public void readComments(ArrayList<CommentRow> commentsArr, int articleNum){
		int last=1;

		WebElement Commentdiv= driver.findElements(By.className("art_tkb")).get(0);


		WebElement talkbacks= Commentdiv.findElements(By.className("art_tkb_talkbacks")).get(0);
		//		List<WebElement> blocks=talkbacks.findElements(By.xpath("//div[@class='art_tkb_talkback art_tkb_talkback_open ']"));
		List<WebElement> blocks=talkbacks.findElements(By.xpath("//*[contains(@id, 'tcontentdiv')]"));


		//		List<WebElement> blocks=Commentdiv.findElements(By.xpath("//*[contains(@id, 'tcontentdiv')]"));
		moveTo(driver, talkbacks);


		for (WebElement we:blocks){ //for every comment, do:

			try {
				//				 moveTo(driver,we);
				//				 sleep(3000);
				we.findElement(By.className("art_tkb_talkback_details_inner"));
			} catch(StaleElementReferenceException e) {
				System.out.println("pass1");
				continue;
			}
			catch(NoSuchElementException e) {
				System.out.println("pass2");
				continue;
			}
			catch(ElementNotVisibleException e) {
				System.out.println("pass3");
				continue;
			}


			WebElement headline=we.findElement(By.className("art_tkb_talkback_details_inner"));

			WebElement number=we.findElement(By.className("art_tkb_talkback_index"));

			WebElement text=we.findElement(By.xpath("./div[contains(@id, 'ttextcont')]"));



			String ghl = getHeadline(headline.getText());
			String gtb = getTalkbackist(headline.getText());
			if(!number.getText().isEmpty()){
				last = setConvNum(number.getText());
			}

			String date=getDate(gtb);
			String body= text.getText();
			ghl= fixTitle( ghl, body );
			gtb= fixName(gtb);




			CommentRow cr = new CommentRow("Ynet", articleNum, gtb, date, ghl, body, last);



			commentsArr.add(cr);




		}
	}

	private static String fixName(String gtb) {
		return gtb.substring(0,gtb.indexOf('('));
	}
	private static String fixTitle(String ttl, String body) {
		if(body.isEmpty()||body.equals(""))
			ttl=ttl.substring(0,ttl.length()-4);
		return ttl;
	}
	private static String getDate(String gtb) {
		return gtb.substring(gtb.indexOf('(')+1, gtb.indexOf('(')+9);
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
	private static String getHeadline(String text) {
		String[] arr = text.split("\n");
		String str="";

		try{
			str = arr[0];
		}
		finally{return str;}

	}
	private static String getTalkbackist(String text) {
		String[] arr = text.split("\n");
		String str="";


		try{
			str = arr[1];
		}
		finally{return str;}
	}




}