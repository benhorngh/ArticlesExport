import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 
 * @author benho
 *http://www.dailymail.co.uk
 */
public class Dailymail extends Site{

	public Dailymail(String tts, String ttc, int noa, SearchState stat, String sd, String ed) {
		super(tts, ttc, noa, stat, sd,ed);
		this.url="http://www.dailymail.co.uk";
		this.window = WindowState.Invisible;
		this.DateRange = false;
		this.page = new DailymailPage(window, this);
	}

	
	@Override
	public boolean search() {

		driver = startWebDriver(url);
		
		try{
			WebElement field = driver.findElement(By.xpath("//input[@class='text-input']"));
			moveTo(driver, field);
			field.click();
			field.clear();
			field.sendKeys(this.textToSearch);
			
			WebElement go = driver.findElement(By.xpath("//button[@type='submit']"));
			go.click();
			
		}catch(Exception e){e.printStackTrace();  return false;}
		
		sleep(2000);
		try{
			String s = "//a[@onclick="+'"'+"searchSetPageSize('50');"+'"'+"]";
			WebElement ffty = driver.findElements(By.xpath(s)).get(0);
			
			ffty.click();
			sleep(30000);
		}catch(Exception e){e.printStackTrace(); }
		return true;
	}

	
	/* 
	 * (non-Javadoc)
	 * @see Site#resultsPage(java.util.List)
	 * results in separated pages.
	 */
	
	@Override
	public void resultsPage(List<String> urls) {

		List<WebElement> results = driver.findElements(By.xpath("//*[contains(@class,'sch-result ')]"));

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

					WebElement ttl = results.get(i).findElement(By.xpath(".//*[@class='sch-res-title']/a"));
					link = ttl.getAttribute("href");

					title = ttl.getText();

				}catch(Exception e){e.printStackTrace();}

				System.out.println(link);
				System.out.println(title);

				try{
					WebElement dt = results.get(i).findElement(By.xpath(".//*[@class='sch-res-info']"));
					date = dt.getText();

//					By Reuters - March 15th 2018, 8:07:06 am
					
					date = date.trim();
					String[] arr = date.split(" ");
					
					String day = arr[arr.length-4];
					day = day.substring(0, day.length()-2);
					String month = arr[arr.length-5];
					month = ""+monthToInt(month);
					String year = arr[arr.length-3];
					year = year.substring(0, year.length()-1);
					
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
							results = driver.findElements(By.xpath("//*[contains(@class,'sch-result ')]"));
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
					WebElement nextButton = driver.findElements(By.xpath("//a[@class='paginationNext']")).get(0);
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
				results = driver.findElements(By.xpath("//*[contains(@class,'sch-result ')]"));

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
