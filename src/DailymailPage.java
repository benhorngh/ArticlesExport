import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class DailymailPage extends Page{

	public DailymailPage(WindowState window, Site site){
		super(site);
		this.window = window;
		this.SiteName = "Dailymail";
	}


	@Override
	public String getTitle() {

		WebElement ttl;
		String str="";
		boolean ok =false;

		try{
			ttl = driver.findElement(By.xpath("//*[@id='js-article-text']/h1"));
			str = ttl.getText();

			if(!str.isEmpty())
				ok = true;
		}catch(Exception e){}

		if(!ok){
			try{
				ttl = driver.findElement(By.xpath("//*[@id='js-article-text']//h1"));
				str = ttl.getText();

				if(!str.isEmpty())
					ok = true;
			}catch(Exception e){}
		}
		

		//h1[contains(@id,'ext-gen')]
		//*[@id='js-article-text']/h1

		return str;
	}

	@Override
	public String getSubTitle() {

		String str = "";
		ArrayList <WebElement> sttl;
		boolean ok = false;

		try{
			sttl = (ArrayList <WebElement>)
					driver.findElements(By.xpath("//*[@id='js-article-text']/ul/li"));
			for(WebElement s : sttl)
				str += s.getText();

			if(!str.trim().isEmpty())
				ok = true;
		}catch(Exception e){}


		return str;
	}

	@Override
	public String getReporter() {
		String str = "";
		boolean ok = false;
		WebElement rptr;

		try{
			rptr = driver.findElements(By.xpath("//*[@class='author-section byline-plain']/a")).get(0);
			str = rptr.getText();

			if(!str.isEmpty())
				ok = true;

			rptr = driver.findElements(By.xpath("//*[@class='author-section byline-plain']/a")).get(1);
			str += rptr.getText();

		}catch(Exception e){}


		return str;
	}

	@Override
	public String getDate() {
		String str = "";
		boolean ok = false;
		WebElement dt ;

		try{
			dt = driver.findElement(By.xpath("//*[@class='article-timestamp article-timestamp-published']"));
			str = dt.getText();
			String[] arr = str.trim().split(" ");

			arr[arr.length-2] = ""+monthToInt(arr[arr.length-2]);

			str = arr[arr.length-3]+"."+arr[arr.length-2]+"."+arr[arr.length-1];

		}catch(Exception e){}
		return str;
	}

	@Override
	public String getBody() {
		String str ="";
		boolean ok = false;
		ArrayList<WebElement> bdy;

		try{
			bdy = (ArrayList<WebElement>) driver.findElements(By.xpath("//*[@itemprop='articleBody']/p"));
			for(WebElement phr : bdy)
				str += phr.getText()+" ";

			if(!str.trim().isEmpty())
				ok = true;
		}catch(Exception e){}
		
		if(!ok){
			try{
				bdy = (ArrayList<WebElement>) driver.findElements(By.xpath("//*[@itemprop='articleBody']//p"));
				for(WebElement phr : bdy)
					str += phr.getText()+" ";

				if(!str.trim().isEmpty())
					ok = true;
			}catch(Exception e){}
		}

		return str;
	}

	@Override
	public ArrayList<CommentRow> commentSecction() {

		try{
		WebElement vm = driver.findElement(By.xpath("//*[@id='js-view-all-link']"));
		moveTo2(driver, vm);
		sleep(2000);
		vm.click();
		sleep(2000);
		}catch(Exception e){}
		ArrayList <CommentRow> comments = new ArrayList <CommentRow>();

		try{
			readComments(comments);
		}catch(Exception e){}

		return comments;
	}

	@Override
	public void readComments(ArrayList<CommentRow> commentsList) {

		ArrayList<WebElement> cmmts = (ArrayList<WebElement>)
				driver.findElements(By.xpath("//*[@id='js-comments']/div[contains(@class,'comment-post')]"));

		if(cmmts.size() == 0) return;
		ArrayList<WebElement> rep;

		CommentRow cr = null;
		for(int i=0; i<cmmts.size(); i++){
			try{
				cr = comment(cmmts.get(i), ""+(i+1));
			}catch(Exception e){}
			if(cr != null)
				commentsList.add(cr);

			try{
				rep =  (ArrayList<WebElement>)
						cmmts.get(i).findElements(By.xpath("//*[@id='js-comments']/div[contains(@class,'comment-post')]"));

				for(int j=0; j<rep.size(); j++){
					try{
						cr = comment(rep.get(j), (i+1)+"."+(j+1));
					}catch(Exception e){}
					if(cr != null)
						commentsList.add(cr);
				}
			}catch(Exception e){}
		}
	}

	public CommentRow comment(WebElement cmmt, String num){
		String name = "",dt="",bdy="", react = "";
	
			try{
				name = cmmt.findElement(By.xpath(".//*[@class='user-info']/a")).getText();
			}catch(Exception e){}

//			try{
//				dt = cmmt.findElement(By.xpath(".//*[@class='post-meta']")).getText();
//			}catch(Exception e){}
			
			try{
				bdy = cmmt.findElement(By.xpath(".//*[contains(@class,'comment-body')]")).getText();
			}catch(Exception e){}

	

		boolean org = false;
		if(num.indexOf(".") == -1)
			org = true;
		if(bdy.isEmpty()) return null;
		CommentRow cr = new CommentRow(this.SiteName, name, dt, react, bdy, num, org);
		return cr;

	}

}
