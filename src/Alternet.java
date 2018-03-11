import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 
 * @author benho
 * https://www.alternet.org/
 */
public class Alternet extends Site{

	public Alternet(String tts, String ttc, int noa, SearchState stat, String sd, String ed) {
		super(tts, ttc, noa, stat, sd,ed);
		this.url="https://www.alternet.org";
		this.window = WindowState.Invisible;
		this.DateRange = false;
		this.page = new AlternetPage(window, this);
	}

	@Override
	public boolean search() {
		driver = startWebDriver(url + "/search/site");

		try{
			WebElement field = driver.findElement(By.xpath("//input[@id='edit-keys']"));
			field.clear();
			field.click();
			field.sendKeys(this.textToSearch);

			WebElement go = driver.findElement(By.xpath("//input[@id='edit-submit']"));
			go.click();

		}catch(Exception e){e.printStackTrace();return false;}

		sleep(2000);


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

					WebElement ttl = results.get(i).findElement(By.xpath(".//*[@class='title']/a"));
					link = ttl.getAttribute("href");

					title = ttl.getText();

				}catch(Exception e){e.printStackTrace();}

				System.out.println(link);
				System.out.println(title);

				try{
					WebElement dt = results.get(i).findElement(By.xpath(".//*[@class='info-date']"));
					date = dt.getText();

					//			          April 26, 2013, 8:10pm - 

					date = date.trim();
					String[] arr = date.split(" ");
					arr[0] = ""+monthToInt(arr[0]);
					arr[1] = arr[1].substring(0, arr[1].length()-1);
					arr[2] = arr[2].substring(0, arr[2].length()-1);

					date = arr[1]+"."+arr[0]+"."+arr[2];
				}catch(Exception e){
					date = todayString();}

				//c
				if(!link.contains("comics") && !link.contains("rss") && !link.contains("progressive-wire")){
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
				}
				else addLink = false;
				if(addLink){
					urls.add(link);
					removeDuplicate(urls);
					mainScreen.addToLog(urls.size()+"/"+this.numOfArticles);
				}

				i++;

			}
			if(i >=results.size()){
				i=0;
				try{
					WebElement nextButton = driver.findElement(By.xpath("//*[@class='pager-next']"));
					moveTo2(driver, nextButton);
					sleep(1000);
					nextButton.click();
					sleep(1000);
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
