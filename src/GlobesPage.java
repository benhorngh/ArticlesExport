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

		System.out.println("open all?");
		sleep(10000);
		return null;
	}

	@Override
	public void readComments(ArrayList<CommentRow> commentsList) {
		// TODO Auto-generated method stub

	}

}
