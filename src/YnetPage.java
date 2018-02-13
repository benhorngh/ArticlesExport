import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

/**
 * @author benho
 * @since 1/2018
 *
 */
public class YnetPage extends Page  {
	
	
	
	public YnetPage(WindowState window){
		super();
		this.SiteName = "Ynet";
		this.window = window;
	}
	
	
	
	
	
	
	@Override
	public String getTitle(){
		WebElement hl= driver.findElements(By.className("art_header_title")).get(0);
		moveTo(driver, hl);
		return hl.getText();
	}
	@Override
	public String getSubTitle(){
		String str="";
		try{
			WebElement shl= driver.findElement(By.className("art_header_sub_title"));
			str=shl.getText();
		}
		catch(NoSuchElementException e){System.out.println("no subtitle");

		}
		return str;
	}
	
	@Override
	public String getReporter(){
		String str="";
		WebElement rpr= driver.findElements(By.className("art_header_footer_author")).get(0);
		str=rpr.getText();
		return str;

	}
	@Override
	public String getDate(){
		String str="";
		try{
			WebElement pt= driver.findElements(By.className("art_header_footer")).get(0);
			String[] dt =pt.getText().split(":")[1].split(",");
			str=dt[0].trim();
		}
		catch(Exception e){}
		return str;
	}
	@Override
	public String getBody(){
		String str="";
		List<WebElement> body =  driver.findElements(By.xpath("//*[@class='text14']/span/p"));
		ArrayList<WebElement> allPh = new ArrayList<WebElement>(body);
		for(int l=0; l <allPh.size(); l++){
			str+=allPh.get(l).getText();
		}
		return str;
	}

	


	@Override
	public  ArrayList<CommentRow>  commentSecction()  {
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
//			driver.navigate().refresh();
//			sleep(3000);

		}
		catch (Exception e){
			IsComments=false;
		}

		linesToWire=new ArrayList<CommentRow>();
		while(IsComments) {

			readComments(linesToWire);

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

	@Override
	public void readComments(ArrayList<CommentRow> commentsArr){
		int last=1;

		WebElement Commentdiv= driver.findElements(By.className("art_tkb")).get(0);


		WebElement talkbacks= Commentdiv.findElements(By.className("art_tkb_talkbacks")).get(0);
		//		List<WebElement> blocks=talkbacks.findElements(By.xpath("//div[@class='art_tkb_talkback art_tkb_talkback_open ']"));
		List<WebElement> blocks=talkbacks.findElements(By.xpath("//*[contains(@id, 'tcontentdiv')]"));


		//		List<WebElement> blocks=Commentdiv.findElements(By.xpath("//*[contains(@id, 'tcontentdiv')]"));
		moveTo(driver, talkbacks);

	
		int prev = 0;
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


			boolean org = true;
			String ghl = getHeadline(headline.getText());
			String gtb = getTalkbackist(headline.getText());
			if(!number.getText().isEmpty()){
				last = setConvNum(number.getText());
			}
			if(prev == last){
				org = false;
			}
			else{
				prev = last;
				org = true;
			}
			

			String date=getDate(gtb);
			String body= text.getText();
			ghl= fixTitle( ghl, body );
			gtb= fixName(gtb);

			CommentRow cr = new CommentRow("Ynet", gtb, date, ghl, body, last, org);

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
	@SuppressWarnings("finally")
	private static String getHeadline(String text) {
		String[] arr = text.split("\n");
		String str="";

		try{
			str = arr[0];
		}
		catch(Exception e){}
		finally{return str;}

	}
	@SuppressWarnings("finally")
	private static String getTalkbackist(String text) {
		String[] arr = text.split("\n");
		String str="";

		try{
			str = arr[1];
		}
		catch(Exception e){}
		finally{return str;}
	}




}