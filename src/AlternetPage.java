import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;


public class AlternetPage extends Page{

	public AlternetPage(WindowState window, Site site){
		super(site);
		this.window = window;
		this.SiteName = "Alternet";


	}

	@Override
	public List<ArticlesRow> linksToList(List<String> urls){
		String url="";


		mainScreen.addToLog("Start open URLs");
		driver = startWebDriver("http://google.com");

		driver.manage().timeouts().pageLoadTimeout(29, TimeUnit.SECONDS);
		//		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);


		List<ArticlesRow> reports = new ArrayList<ArticlesRow>();
		for(int i=0; i<urls.size(); i++){
			url=urls.get(i);
			try{
				driver.get(url);
			}

			catch(TimeoutException e){
				System.out.println("TIMEOUT");	
			}
			//			catch(WebDriverException e){
			//				e.printStackTrace();
			//				sleep(60000);
			//				System.out.println("Invaild url "+url);
			//				continue;
			//			}		

			if(i==0){
				try{
					signIn();
					//					driver.navigate().refresh();
				}
				catch(Exception e){e.printStackTrace();System.err.println("can't login");}
			}
			ArticlesRow ar = urlHandler(url, false);

			if(ar != null){
				Date arD = stringToDate(ar.date);
				Date date = stringToDate(this.site.fromDate);

				if(date != null && arD != null && date.after(arD)){
					ArticlesRow.counter--;
				}
				else reports.add(ar);
			}

			System.out.println("finish URL");
			mainScreen.addToLog("finish url ."+(i+1));

		}
		driver.close();
		driver.quit();
		System.err.println("finish "+SiteName);

		mainScreen.addToLog("finish "+SiteName);

		return reports;
	}



	@Override
	public String getTitle() {
		String str = "";
		boolean ok = false;

		try{
			WebElement ttl = driver.findElement(By.xpath("//*[@class='headline']"));
			str = ttl.getText();
			if(!str.isEmpty())
				ok = true;

		}catch(Exception e){}


		return str;
	}

	@Override
	public String getSubTitle() {

		String str = "";
		boolean ok = false;

		try{
			WebElement sttl = driver.findElement(By.xpath("//*[@class='teaser']//*[@class='field-items']"));
			str = sttl.getText();
			if(!str.isEmpty())
				ok = true;

		}catch(Exception e){}


		return str;
	}

	@Override
	public String getReporter() {


		String str = "";
		boolean ok = false;

		try{
			WebElement rptr = driver.findElements(By.xpath("//*[@class='byline world']//a")).get(0);
			str = rptr.getText();
			if(!str.isEmpty())
				ok = true;

			rptr = driver.findElements(By.xpath("//*[@class='byline world']//a")).get(1);
			str += " "+ rptr.getText();
		}catch(Exception e){}

		if(!ok){
			try{
				WebElement rptr = driver.findElements(By.xpath("//*[@class='byline living']//a")).get(0);
				str = rptr.getText();
				if(!str.isEmpty())
					ok = true;

				rptr = driver.findElements(By.xpath("//*[@class='byline living']//a")).get(1);
				str += " "+ rptr.getText();
			}catch(Exception e){}
		}

		if(!ok){
			try{
				WebElement rptr = driver.findElements(By.xpath("//*[@class='byline media-culture']//a")).get(0);
				str = rptr.getText();
				if(!str.isEmpty())
					ok = true;

				rptr = driver.findElements(By.xpath("//*[@class='byline media-culture']//a")).get(1);
				str += " "+ rptr.getText();
			}catch(Exception e){}
		}
		if(!ok){
			try{
				WebElement rptr = driver.findElements(By.xpath("//*[@class='byline news-politics']//a")).get(0);
				str = rptr.getText();
				if(!str.isEmpty())
					ok = true;

				rptr = driver.findElements(By.xpath("//*[@class='byline news-politics']//a")).get(1);
				str += " "+ rptr.getText();
			}catch(Exception e){}
		}
		if(!ok){
			try{
				WebElement rptr = driver.findElements(By.xpath("//*[@class='byline']/a")).get(0);
				str = rptr.getText();
				if(!str.isEmpty())
					ok = true;

			}catch(Exception e){}
		}

		if(!ok){
			try{
				WebElement rptr = driver.findElements(By.xpath("//*[contains(@class,'byline')]//a")).get(0);
				str = rptr.getText();
				if(!str.isEmpty())
					ok = true;

				rptr = driver.findElements(By.xpath("//*[contains(@class,'byline')]//a")).get(1);
				str += " "+ rptr.getText();

			}catch(Exception e){}
		}
		if(!ok){
			try{
				WebElement rptr = driver.findElements(By.xpath("//*[@id='published']/a")).get(0);
				str = rptr.getText();
				if(!str.isEmpty())
					ok = true;

				rptr = driver.findElements(By.xpath("//*[@id='published']/a")).get(1);
				str += " "+ rptr.getText();

			}catch(Exception e){}
		}
		
		
		






		return str;
	}

	@Override
	public String getDate() {

		String str = "";
		boolean ok = false;

		try{//September 19, 2011, 11:00 AM GMT		
			WebElement dt = driver.findElement(By.xpath("//*[@class='story-date']"));
			str = dt.getText();

			String[] arr = str.trim().split(" ");
			arr[0] =""+ monthToInt(arr[0]);
			arr[1] = arr[1].substring(0, arr[1].length()-1);
			arr[2] =  arr[2].substring(0, arr[2].length()-1);
			
			if(arr[2].length()>4)
				arr[2] = arr[2].trim().substring(0, 4);

			str = arr[1] +"."+ arr[0] +"."+ arr[2];

			if(!str.isEmpty())
				ok = true;

		}catch(Exception e){}
		if(!ok){
			try{//August 26, 2010	
				WebElement dt = driver.findElement(By.xpath("//*[@datatype='xsd:dateTime']"));
				str = dt.getText();

				String[] arr = str.trim().split(" ");
				arr[0] =""+ monthToInt(arr[0]);
				arr[1] = arr[1].substring(0, arr[1].length()-1);
				
				if(arr[2].length()>4)
					arr[2] = arr[2].trim().substring(0, 4);

				str = arr[1] +"."+ arr[0] +"."+ arr[2];

				if(!str.isEmpty())
					ok = true;

			}catch(Exception e){}
		}



		return str;
	}

	@Override
	public String getBody() {

		String str = "";
		boolean ok = false;

		try{
			ArrayList<WebElement> bdy = (ArrayList<WebElement>)
					driver.findElements(By.xpath("//*[@itemprop='articleBody']//p")); 

			for(WebElement phr : bdy)
				str+=phr.getText();

			if(!str.isEmpty())
				ok =true;

		}catch(Exception e){}


		if(!ok){
			try{
				ArrayList<WebElement> bdy = (ArrayList<WebElement>)
						driver.findElements(By.xpath("//*[contains(@class,'the_body')]/p")); 

				for(WebElement phr : bdy)
					str+=phr.getText();

				if(!str.isEmpty())
					ok =true;

			}catch(Exception e){}
		}
		if(!ok){
			try{
				ArrayList<WebElement> bdy = (ArrayList<WebElement>)
						driver.findElements(By.xpath("//*[contains(@class,'the_body')]")); 

				for(WebElement phr : bdy)
					str+=phr.getText();

				if(!str.isEmpty())
					ok =true;

			}catch(Exception e){}
		}



		return str;
	}

	@Override
	public ArrayList<CommentRow> commentSecction() {


		ArrayList<CommentRow> cmmts = new ArrayList<CommentRow>();
		WebElement openComm = null;

		try{
			openComm = driver.findElement(By.xpath("//*[@class='commentlink']"));
		}catch(Exception e){

			try{	
				openComm = driver.findElement(By.xpath("//*[@class=' comments_link']"));

			}catch(Exception e2){return cmmts;}

		}

		moveTo2(driver, openComm);
		sleep(1000);
		openComm.click();
		sleep(8000);


		//		id="disqus_thread"
		WebElement frame = driver.findElement(By.xpath("//*[@id='disqus_thread']/iframe"));
		//		String frameName = frame.getAttribute("name");

		driver.switchTo().frame(frame);


		for(int i=0; i<20; i++){
			try{
				WebElement loadMore = driver.findElement(By.xpath("//*[@class='load-more']"));

				if(loadMore.getAttribute("style").equals("display: none;"))
					break;

				moveTo2(driver, loadMore);
				sleep(1000);
				loadMore.click();
				System.out.println("more");
			}catch(Exception e){break;}
		}

		ArrayList <WebElement> seemore = (ArrayList <WebElement>)
				driver.findElements(By.xpath("//a[@class='see-more']"));

		for(WebElement more :  seemore){
			moveTo(driver, more);
			sleep(1000);
			more.click();
		}


		sleep(1000);
		try{
			readComments(cmmts);
		}catch(Exception e){}

		return cmmts;
	}

	@Override
	public void readComments(ArrayList<CommentRow> commentsList) {
		if(path.size()==0)
			path.add("//*[@id='posts']");
		ArrayList<WebElement> cmmts=null;
		try{
			cmmts= (ArrayList<WebElement>) driver.findElements(By.xpath(pts()+"/ul/li"));
		}catch(Exception e){return;}

		numbers.add(0);
		for(WebElement cmmt: cmmts){
			count();
			CommentRow cr = getComment(cmmt, listToArray(numbers));
			if(cr != null)
				commentsList.add(cr);
			path.add(cmmt.getAttribute("id"));
			readComments(commentsList);
		}
		numbers.remove(numbers.size()-1);
		path.remove(path.size()-1);
	}


	private CommentRow getComment(WebElement cmmt, int[] num) {
		String name = "",dt="",bdy="", react = "";

		try{
			WebElement content = cmmt.findElement(By.xpath("./div[@class='post-content']"));

			try{
				name = content.findElement(By.xpath(".//*[@class='post-byline']/span[1]")).getText();
			}catch(Exception e){}

			try{
				react = content.findElement(By.xpath(".//*[@class='post-byline']/span[2]")).getText();
			}catch(Exception e){}

			try{
				dt = content.findElement(By.xpath(".//*[@class='post-meta']")).getText();
			}catch(Exception e){}
			try{
				bdy = content.findElement(By.xpath(".//*[@class='post-body-inner']")).getText();
			}catch(Exception e){}

		}catch(Exception e){};

		boolean org = false;
		String serial = ArrToStr(num);
		if(serial.indexOf(".") == -1)
			org = true;
		if(bdy.isEmpty()) return null;
		CommentRow cr = new CommentRow(this.SiteName, name, dt, react, bdy, serial, org);
		return cr;

	}



	private void count() {
		int x= numbers.get(numbers.size()-1);
		x++;
		numbers.remove(numbers.size()-1);
		numbers.add(x);
	}

	private int[] listToArray(ArrayList<Integer> IntArrList) {

		int[] arr = new int[IntArrList.size()];

		for(int i =0; i<arr.length; i++){
			arr[i]  = IntArrList.get(i);
		}
		return arr;
	}

	private ArrayList<Integer> numbers = new ArrayList<Integer> ();
	private ArrayList<String> path = new ArrayList<String>();

	private String pts(){
		String str = path.get(0);
		for(int i=1; i<path.size(); i++){
			if(path.get(i).isEmpty())
				break;
			str += "//*[@id='"+path.get(i)+"']";
		}
		return str;
	}
	private String ArrToStr(int[] num){
		String s="";
		s = num[0]+"";
		for(int i=1; i<num.length;i++){
			s+= "."+num[i];
		}
		return s;
	}

}
