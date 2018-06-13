import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 
 * @author benho
 * http://www.foxnews.com/
 */
public class FoxNews extends Site{

	
	public FoxNews(String tts, String ttc, int noa, SearchState stat, String sd, String ed) {
		super(tts, ttc, noa, stat, sd,ed);
		this.url="http://www.foxnews.com/";
		this.window = WindowState.Invisible;
		this.DateRange = false;
		this.page = new FoxNewsPage(window, this);
	}
	
	@Override
	public boolean search() {
		driver = startWebDriver(url);
		
		try{
			WebElement srch = driver.findElement(By.xpath("//*[contains(@class,'search-toggle')]"));
			srch.click();
			
			WebElement field = driver.findElement(By.xpath("//fieldset/input[@type='text']"));
			field.click();
			field.clear();
			field.sendKeys(this.textToSearch);
			
			WebElement smbt = driver.findElement(By.xpath("//fieldset/input[@type='submit']"));
			smbt.click();
			
		}catch(Exception e){e.printStackTrace(); return false;}
		
		return true;
	}

	/* 
	 * (non-Javadoc)
	 * @see Site#resultsPage(java.util.List)
	 * results in separated pages.
	 */
	
	
	@Override
	public void resultsPage(List<String> urls) {
//		if(!this.toDate.isEmpty())
//			changeTime();
		


		List<WebElement> results = driver.findElements(By.xpath("//*[@class='search-directive ng-scope']"));

		this.firstPage = driver.getCurrentUrl();

		String link="", title="", date="";
		int i=0;
		int checks = 0;
		int startover = 1;
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

					WebElement ttl = results.get(i).findElement(By.xpath(".//*[@ng-bind='article.title']"));
					link = ttl.getAttribute("href");

					title = ttl.getText();

				}catch(Exception e){e.printStackTrace();}

				System.out.println(link);
				System.out.println(title);

				try{
					WebElement dt = results.get(i).findElement(By.xpath(".//*[contains(@class,'search-date')]"));
					date = dt.getText();

					//Nov 28, 2016

					date = date.trim();
					String[] arr = date.split(" ");
					arr[0] = ""+monthToInt(arr[0]);
					arr[1] = arr[1].substring(0, arr[1].length()-1);

					date = arr[1]+"."+arr[0]+"."+arr[2];
				}catch(Exception e){
					}

				//c
				if((!link.contains("video.fox"))&&(!link.contains("slideshow"))){
					try{
						String s = toDate;
						addLink = stateHandle(link, title, date);
						if(!s.equals(toDate)){
							driver.get(firstPage);
							sleep(1000);
							results = driver.findElements(By.xpath(".//*[contains(@class,'search-date')]"));
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
					WebElement nextButton = driver.findElements(By.xpath("//li[contains(@class,'arrow')]/a")).get(1);
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

				results = driver.findElements(By.xpath("//*[@class='search-directive ng-scope']"));

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

				
				startover++;
				if(startover%10 == 0){
					try{
						driver = startOver(driver);
					}catch(Exception e){e.printStackTrace();}
				}
			}

		}
	}

	
//	private void changeTime() {
//		WebElement adv = driver.findElement(By.xpath("//*[@class='advanced-search ng-scope']"));
//		adv.click();
//		
//		WebElement from = driver.findElements(By.xpath("//*[contains(@class,'filter-date')]/div/input")).get(0);
//		WebElement to = driver.findElements(By.xpath("//*[contains(@class,'filter-date')]/div/input")).get(1);
//		
//				
//	}

}
