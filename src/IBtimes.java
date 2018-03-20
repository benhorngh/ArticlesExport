import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

/**
 * 
 * @author benho
 *http://www.ibtimes.com/
 */
public class IBtimes extends Site{

	public IBtimes(String tts, String ttc, int noa, SearchState stat, String sd, String ed) {
		super(tts, ttc, noa, stat, sd,ed);
		this.url="http://www.ibtimes.com/";
		this.window = WindowState.Invisible;
		this.DateRange = false;
		this.page = new IBtimesPage(window, this);
	}


	@Override
	public boolean search() {
		driver = startWebDriver("http://www.ibtimes.com/search/site");

		try{

			

			WebElement field = driver.findElements(By.xpath("//input[@id='edit-keys']")).get(0); 
			field.click();
			field.clear();
			field.sendKeys(this.textToSearch);
			sleep(1000);

			WebElement sch = driver.findElement(By.xpath("//*[@id='edit-basic']//input[@id='edit-submit']"));
			clickInvisible(driver, sch);
			
		}catch(Exception e){e.printStackTrace(); return false;}

		sleep(1000);
		return true;
	}


	/* 
	 * (non-Javadoc)
	 * @see Site#resultsPage(java.util.List)
	 * results in separated pages.
	 */


	@Override
	public void resultsPage(List<String> urls) {



		List<WebElement> results = driver.findElements(By.xpath("//li[@class='search-result']"));

		this.firstPage = driver.getCurrentUrl();

		String link="", title="", date="";
		int i=0;
		int checks = 0;
		int count = 0;
		boolean addLink=false;
		int tries=0;
		while(urls.size() < numOfArticles){

			link=""; title=""; date="";
			addLink=false;
			checks++;
			if(checks == maxSearch)
				return;

			if(i<results.size()){
				try{

					WebElement ttl = results.get(i).findElement(By.xpath(".//h3/a"));
					link = ttl.getAttribute("href");

					title = ttl.getText();

				}catch(Exception e){e.printStackTrace();}

				System.out.println(link);
				System.out.println(title);

				try{
					WebElement dt = results.get(i).findElement(By.xpath(".//*[@class='byline']"));
					date = dt.getText();

					//					By someone on 12/29/18 8:07:06 am

					date = date.trim();
					String[] arr = date.split("/");

					String day = arr[1];
					String month = arr[0].split(" ")[arr[0].split(" ").length-1];
					String year = arr[2].split(" ")[0];
					
					year = "20"+year;
					date = day+"."+month+"."+year;

					System.out.println(date);
				}catch(Exception e){
					link ="";}

				//c

				if(!link.contains("/video/")){

					try{
						String s = toDate;
						addLink = stateHandle(link, title, date);
						if(!s.equals(toDate)){
							driver.get(firstPage);
							sleep(1000);
							results = driver.findElements(By.xpath("//li[@class='search-result']"));
							i=0;
						}
					}catch(Exception e){e.printStackTrace();addLink=false;}

				}else addLink = false;

				if(addLink){
					urls.add(link);
					removeDuplicate(urls);
					mainScreen.addToLog(urls.size()+"/"+this.numOfArticles);
				}

				i++;

			}
			if(i >=results.size()){

				try{
					WebElement nextButton = driver.findElement(By.xpath("//*[@class='pager-next next last']"));
					moveTo(driver, nextButton);
					sleep(1000);
					nextButton.click();
					sleep(2000);
					count =0;
				}
				catch(Exception e){
					e.printStackTrace();

					count++;

					if(count  >= 10){
						String s = this.toDate;
						updateToDate(true);
						if(s.equals(this.toDate))
							break;
						count = 0;
						driver.get(firstPage);
					}
				}
				i=0;
				results = driver.findElements(By.xpath("//li[@class='search-result']"));

				if(results.size() ==0){
					sleep(2000);
					tries++;
				}else tries =0;

				if(tries >= 10){
					String s = this.toDate;
					updateToDate(true);
					if(s.equals(this.toDate))
						break;
					tries = 0;
					driver.get(firstPage);
				}

			}

		}


	}

}
