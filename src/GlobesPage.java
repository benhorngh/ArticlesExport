import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class GlobesPage extends Page{

	public GlobesPage(WindowState window){
		super();
		this.window = window;
		this.SiteName = "Globes";
	}

	@Override
	public String getTitle() {

		String str="";

		try{

			WebElement title = driver.findElement(By.cssSelector("#F_Title"));
			str = title.getText();
		}
		catch(Exception e){};
		return str;
	}

	@Override
	public String getSubTitle() {

		String str="";
		try{
			WebElement stitle = driver.findElement(By.cssSelector("#coteret_SubCoteretText"));
			str = stitle.getText();
		}
		catch(Exception e){};
		return str;
	}

	@Override
	public String getReporter() {

		String str="";
		try{
			WebElement reporter = driver.findElement(By.xpath("//*[@class='articleInfo']/a"));
			str = reporter.getText();
		}
		catch(Exception e){};
		return str;
	}

	@Override
	public String getDate() {
		String str="";
		try{
			WebElement date = driver.findElement(By.xpath("//*[@class='articleInfo']/span[@class='timestamp']"));
			str = date.getText();
			str = str.split(" ")[0];
			str = str.substring(0, str.length()-1);
			str = str.replaceAll("/", ".");
		}
		catch(Exception e){};
		return str;
	}

	@Override
	public String getBody() {

		boolean ok = false;
		String str="";
		try{
			ArrayList<WebElement> body = (ArrayList<WebElement>) driver.findElements(By.xpath("//*[@class='articleInner']/p"));
			for(int i=0; i<body.size(); i++){
				str += body.get(i).getText();
			}
			ok =true;
		}
		catch(Exception e){};

		if(!ok){
			try{
				WebElement body = driver.findElement(By.xpath("//*[@class='articleInner']"));
				str = body.getText();
				ok =true;
			}catch(Exception e){};
		}

		return str;
	}

	@Override
	public ArrayList<CommentRow> commentSecction() {
		WebElement frame = driver.findElement(By.xpath("//*[@class='sppre_frame-container']/iframe"));
		moveTo2(driver, frame);
		driver.switchTo().frame(frame);

		sleep(3000);
		boolean more = true;
		WebElement load ;
		while(more){
			try{
				load = driver.findElement(By.xpath("//*[contains(@class, 'load-more-messages')]"));
				moveTo2(driver, load);
				load = driver.findElement(By.xpath("//*[contains(@class, 'load-more-messages')]"));
				sleep(2000);
				clickInvisible(driver, load);
				sleep(2000);
				System.out.println("load more");
			}
			catch(Exception e){System.out.println("no more comments"); more = false;};
		}
		
		boolean rep = true;
		while(rep){
			try{
				load = driver.findElement(By.xpath("//*[@class='sppre_show-more-replies-button']"));
				moveTo2(driver, load);
				load = driver.findElement(By.xpath("//*[@class='sppre_show-more-replies-button']"));
				sleep(2000);
				clickInvisible(driver, load);
				sleep(2000);
				System.out.println("load more");
			}
			catch(Exception e){System.out.println("no more comments"); rep = false;};
		}
		

		System.out.println("open all?");
		sleep(3000);
		ArrayList<CommentRow> commentList = new ArrayList<CommentRow>();
		readComments(commentList);
		return commentList;
		
	}


	@Override
	public void readComments(ArrayList<CommentRow> commentList){
		ArrayList<WebElement> cmmts=null;
		try{
			cmmts= (ArrayList<WebElement>) 
				driver.findElements(By.xpath("//*[@class='sppre_messages-list']/li"));
		}catch(Exception e){e.printStackTrace(); return;}
		
		System.out.println("size::"+cmmts.size());
		
		ArrayList<WebElement> childs;
		int[] nums = {0,0};
		WebElement comment;
		
		for(WebElement cmmt : cmmts){
			try{
				comment =  cmmt.findElement(By.className("sppre_message-stack"));
				commentList.add(getComment(comment, nums,true));
			}catch(Exception e){e.printStackTrace();}


			try{
			childs = (ArrayList<WebElement>) 
					cmmt.findElement(By.className("sppre_children-list"))
					.findElements(By.tagName("li"));
			
			System.out.println("size::::"+childs.size());
			
			for(WebElement child:childs){
				nums[1]++;
				try{
					comment =  child.findElement(By.className("sppre_message-stack"));
					commentList.add(getComment(comment, nums,false));
				}catch(Exception e){e.printStackTrace();}


			}
			}catch(Exception e){}
			nums[1]=0;


		}
	}


	public CommentRow getComment(WebElement we, int[] nums, boolean org) {
		String tkbk="", date="",body="", sub="";
		try{
			WebElement comment = we.findElement(By.xpath(".//*[@data-spot-im-class='message-text']"));
			body = comment.getText();
		}catch(Exception e){}

		try{
			WebElement user = we.findElement(By.className("sppre_user-link"));
			WebElement name = user.findElement(By.className("sppre_username"));
			tkbk = name.getText();
		}catch(Exception e){}
		
		if(org){
			try{
				WebElement number = we.findElement(By.className("sppre_label"));
				nums[0] = Integer.parseInt(number.getText());
			}catch(Exception e){}
		}

		try{
			WebElement time = we.findElement(By.className("sppre_posted-at"));
			date = time.getText();
		}
		catch(Exception e){}
		

		if(!org){
			try{
//				WebElement repleyto = we.findElement(By.className("sppre_reply-to"));
//				WebElement name = repleyto.findElement(By.className("sppre_username"));
				WebElement name = we.findElement(By.xpath(".//*[@class='sppre_reply-to']//*[@class='sppre_username']"));
				
				sub = name.getText();
			}catch(Exception e){}
		}


		String number = nums[0]+"";
		if(!org)
			number+="."+nums[1];
		
		CommentRow cr = new CommentRow("Globes", tkbk, date, "", body,number, org);
		return cr;
	}

}


