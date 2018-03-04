import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CNNPage extends Page{
	
	public CNNPage(WindowState window, Site site){
		super(site);
		this.window = window;
		this.SiteName = "CNN";
		
	}
	

	@Override
	public String getTitle() {
		String title="";
		boolean ok = false;
		WebElement ttl = null;

		if(!ok){
			try{
				ttl =  driver.findElement(By.xpath("//*[contains(@class,'pg-headline')]"));
				if(!ttl.getText().isEmpty())
					ok= true;
				title = ttl.getText();
			}
			catch(Exception e){
			}
		}

		if(!ok){
			try{
				ttl =  driver.findElement(By.xpath("//*[contains(@class,'article-title')]"));
				if(!ttl.getText().isEmpty())
					ok= true;
				title = ttl.getText();
			}
			catch(Exception e){
			}
		}
		
		if(!ok){
			try{
				ttl =  driver.findElement(By.xpath("//*[contains(@class,'Article__title')]"));
				if(!ttl.getText().isEmpty())
					ok= true;
				title = ttl.getText();
			}
			catch(Exception e){
			}
		}
		
		if(!ok){
			try{
				ttl =  driver.findElement(By.xpath("//*[@class='PageHead__title']"));
				if(!ttl.getText().isEmpty())
					ok= true;
				title = ttl.getText();
			}
			catch(Exception e){
			}
		}
		return title;
		
		
		
	}



	@Override
	public String getSubTitle() {
		
		String subtitle="";
		boolean ok = false;
		WebElement sub = null;

		if(!ok){
			try{
				sub =  driver.findElement(By.xpath("//*[contains(@class,'Article__subtitle')]"));
				if(!sub.getText().isEmpty())
					ok= true;
				subtitle = sub.getText();
			}
			catch(Exception e){
			}
		}
		return subtitle;
	}

	@Override
	public String getReporter() {
		//*[@class='metadata ']//*//*[contains(@class,'author')]

		String reporter="";
		boolean ok = false;
		WebElement rptr = null;

		if(!ok){
			try{
				rptr =  driver.findElement(By.xpath("//*[@class='metadata ']//*[contains(@class,'author')]/a"));
				if(!rptr.getText().isEmpty() || rptr.getText().length()<3)
					ok= true;
				reporter = rptr.getText();
			}
			catch(Exception e){
			}
		}

		if(!ok){
			try{
				rptr =  driver.findElement(By.xpath("//*[contains(@class,metadata)]//*[contains(@class,'author')]"));
				if(!rptr.getText().isEmpty())
					ok= true;
				reporter = rptr.getText();
				reporter = reporter.substring(2, reporter.length()-4);
			}
			catch(Exception e){
			}
		}
		if(!ok){
			try{
				rptr =  driver.findElement(By.xpath("//*[@class='byline']"));
				if(!rptr.getText().isEmpty())
					ok= true;
				reporter = rptr.getText();
			}
			catch(Exception e){
			}
		}
		if(!ok){
			try{
				rptr =  driver.findElement(By.xpath("//*[@class='Article__subtitle']"));
				if(!rptr.getText().isEmpty())
					ok= true;
				reporter = rptr.getText();
				reporter = reporter.split(" ")[0]+reporter.split(" ")[1];
			}
			catch(Exception e){
			}
		}
		
		

		return reporter;
	}

	@Override
	public String getDate() {
		//update-time


		String date="";
		boolean ok = false;

		WebElement dt = null;

		if(!ok){
			try{
				dt =  driver.findElement(By.xpath("//*[@class='update-time']"));
				if(!dt.getText().isEmpty())
					ok= true;
				date = dt.getText();
				String[] arr= date.split(" ");
				String day = arr[arr.length-2].substring(0, arr[arr.length-2].length()-1);
				date = day +"." + monthToInt(arr[arr.length-3])+"."+arr[arr.length-1];
			}
			catch(Exception e){
			}
		}

		if(!ok){
			try{
				dt =  driver.findElement(By.xpath("//*[@class='cnnDateStamp']"));
				if(!dt.getText().isEmpty())
					ok= true;
				date = dt.getText();
				date = dt.getText();
				String[] arr= date.split(" ");
				String day = arr[2].substring(0, arr[1].length()-1);
				date = day +"." + monthToInt(arr[0])+"."+arr[2];
				date = date.substring(0, date.length()-1);
			}
			catch(Exception e){
			}
		}
		if(!ok){
			try{
				dt =  driver.findElement(By.xpath("//*[@class='PageHead__published']"));
				if(!dt.getText().isEmpty())
					ok= true;
				date = dt.getText();
				String[] arr= date.split(" ");
				
				String day = arr[arr.length-3];
				day = day.substring(0, day.length()-3);
				date = day+"."+monthToInt(arr[arr.length-2])+"."+arr[arr.length-1];
			}
			catch(Exception e){
			}
		}
		if(!ok){
			try{
				dt =  driver.findElement(By.xpath("//*[@class='Article__subtitle']"));
				if(!dt.getText().isEmpty())
					ok= true;
				date = dt.getText();
				
				String[] arr = date.split(" ");
				String day = arr[arr.length-3];
				day = day.substring(0, day.length()-2);
				String month = ""+monthToInt(arr[arr.length-2]);
				date = day+"."+month+"."+arr[arr.length-1];
			}
			
			catch(Exception e){
			}
		}
		
		
		
		return date;

	}

	@Override
	public String getBody() {

		String body="";
		boolean ok = false;
		List<WebElement> bd = null;

		if(!ok){
			try{
				bd =  driver.findElements(By.xpath("//*[@id='storytext']/p"));

				for(int i=0; i<bd.size(); i++){
					body += bd.get(i).getText();
				}

				if(!body.isEmpty())
					ok= true;

			}
			catch(Exception e){
			}
		}

		if(!ok){
			try{
				bd =  driver.findElements(By.xpath("//*[@class='zn-body__read-all']"));
				// size suppose to be 0.
				for(int i=0; i<bd.size(); i++){
					body += bd.get(i).getText();
				}

				if(!body.isEmpty())
					ok= true;
			}
			catch(Exception e){
			}
		}
		if(!ok){
			try{
				bd =  driver.findElements(By.xpath("//*[@itemprop='articleBody']//*[@class='l-container']//div[contains(@class,body__paragraph)]"));
				// size suppose to be 0.
				for(int i=0; i<bd.size(); i++){
					body += bd.get(i).getText();
				}

				if(!body.isEmpty())
					ok= true;
			}
			catch(Exception e){
			}
		}
		
		if(!ok){
			try{
				bd =  driver.findElements(By.xpath("//*[@class='Article__body']/div"));
				// size suppose to be 0.
				for(int i=0; i<bd.size(); i++){
					body += bd.get(i).getText();
				}

				if(!body.isEmpty())
					ok= true;
			}
			catch(Exception e){
			}
		}
		if(!ok){
			try{
				bd =  driver.findElements(By.xpath("//*[@class='BasicArticle__main']//*[starts-with(@class,'BasicArticle__paragraph')]"));
				// size suppose to be 0.
				for(int i=0; i<bd.size(); i++){
					body += bd.get(i).getText();
				}

				if(!body.isEmpty())
					ok= true;
			}
			catch(Exception e){
			}
		}
		
		
		
		
		return body;
	}

	@Override
	public ArrayList<CommentRow> commentSecction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void readComments(ArrayList<CommentRow> commentsList) {
		// TODO Auto-generated method stub

	}

}
