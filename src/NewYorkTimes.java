import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class NewYorkTimes extends Site{


	public NewYorkTimes(String tts, String ttc, int noa, SearchState stat, String sd, String ed) {
		super(tts, ttc, noa, stat, sd,ed);
		this.url="https://www.nytimes.com/";
		this.window = WindowState.Invisible;
		this.DateRange = true;
		this.page = new NewYorkTimesPage(window, this);
	}


	@Override
	public boolean search() {
		driver = startWebDriver(url);
		driver.get(url);
		sleep(5000);

		try {
			WebElement button = driver.findElement(By.xpath("//button[@class='button search-button']"));
			button.click();

			WebElement field = driver.findElement(By.xpath("//input[@id='search-input']"));
			field.click();
			field.clear();
			field.sendKeys(this.textToSearch);
			sleep(1000);

			WebElement go = driver.findElement(By.xpath("//button[@type='submit']"));

			go.click();

		}catch(Exception e) {e.printStackTrace(); sleep(5000); return false;}


		return true;
	}



	/* 
	 * (non-Javadoc)
	 * @see Site#resultsPage(java.util.List)
	 * long result page.
	 */

	@Override
	public void resultsPage(List<String> urls) {
		if(!this.toDate.isEmpty())
			changeTime();

		ArrayList<WebElement> results  = (ArrayList<WebElement>) 
				driver.findElements(By.xpath("//ol//li[contains(class,SearchResults-item)]"));



		String link="", title="" , date="";
		int i=results.size()-10;
		int checks = 0;
		int res =0;
		boolean addLink=false;
		try{
			while(urls.size() < numOfArticles){
				link=""; title=""; date="";
				addLink=false;

				checks++;
				if(checks == maxSearch)
					return;

				if(i<results.size()){

					try{
						WebElement head = results.get(i)
								.findElement(By.xpath(".//h4[contains(class,Item-headline)]/.."));

						title = head.findElement(By.tagName("h4")).getText();
						link = head.getAttribute("href");

						WebElement dt = results.get(i)
								.findElement(By.xpath(".//time"));

						date = dt.getText();
					}catch(Exception e){System.err.println(e);i++; continue;}

					try{
						String[] arr = date.split(" ");
						//						May 29, 2010

						String year = "2018";
						if(arr.length == 3){
							arr[0] = ""+monthToInt(arr[0]);
							year = arr[2];
							arr[1] = arr[1].substring(0, arr[1].length()-1);
						}
						else arr[0] = ""+monthToInt(arr[0].substring(0,arr[0].length()-1));

						date = arr[1]+"."+arr[0]+"."+year;
					}catch(Exception e){e.printStackTrace(); }

					System.out.println(link);
					System.out.println(title);



					if(!link.contains("video")) {
						try{
							String s = this.toDate;
							addLink = stateHandle(link, title, date);
							if(!s.equals(this.toDate)){
								changeTime();
								i= results.size() %10 ;
							}

						}catch(Exception e){e.printStackTrace();addLink=false;}
					}
					else addLink=false;

					if(addLink){
						urls.add(link);
						removeDuplicate(urls);
						mainScreen.addToLog(urls.size()+"/"+this.numOfArticles);
					}
					i++;
				}

				if(i>=results.size()){
					int size= results.size();
					clickLoadMore();
					results  = (ArrayList<WebElement>) 
							driver.findElements(By.xpath("//ol//li[contains(class,SearchResults-item)]"));		

					if(size == results.size()) {
						sleep(2000);
						res++;
					}
					else res = 0;
					if(res >= 10){
						String s = this.toDate;
						updateToDate(true);
						if(s.equals(this.toDate))
							break;
						res = 0;
						i=0;
					}
				}
			}
		}catch(Exception e){ e.printStackTrace();return;}
	}


	private void changeTime(){
		for(int i=0; i<5; i++){
			try{

				try{
					WebElement bu = driver.findElement(By.xpath("//button[contains(@class,'SearchForm-dateRangeLabel')]"));
					moveTo2(driver, bu);
					sleep(2000);
					bu.click();
				}catch(Exception e){}


				WebElement d1 = driver.findElements(By.xpath("//*[@class='DateInput DateInput_1']/input")).get(0);
				WebElement d2 = driver.findElements(By.xpath("//*[@class='DateInput DateInput_1']/input")).get(1);

				String[] from = this.fromDate.split("\\.");
				String[] to = this.toDate.split("\\.");

				if(from[0].length() == 1)
					from[0] = "0"+from[0];
				if(from[1].length() == 1)
					from[1] = "0"+from[1];

				if(to[0].length() == 1)
					to[0] = "0"+to[0];
				if(to[1].length() == 1)
					to[1] = "0"+to[1];

				d1.click();
				d1.clear();
				d1.sendKeys(from[1]+"/"+from[0]+"/"+from[2]);

				d2.click();
				d2.clear();
				d2.sendKeys(to[1]+"/"+to[0]+"/"+to[2]);
				sleep(3000);
				break;
			}catch(Exception e){e.printStackTrace();System.out.println("TRY AGAIN");}
		}
	}


	private void clickLoadMore() {
		try{
			WebElement button = driver.findElement(By.xpath("//div[contains(@class,'Search-showMore')]/button"));
			moveTo2(driver,  button);
			sleep(1400);
			button.click();
			sleep(2000);
		}catch(Exception e){e.printStackTrace();}

	}

}
