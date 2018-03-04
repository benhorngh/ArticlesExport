import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * https://edition.cnn.com/
 * @author benho
 */

public class CNN extends Site{

	public CNN(String tts, String ttc, int noa, SearchState stat, String sd, String ed) {
		super(tts, ttc, noa, stat, sd,ed);
		this.url="http://edition.cnn.com/";
		this.window = WindowState.Invisible;
		this.DateRange = false;
		this.page = new CNNPage(window , this);
	}

	@Override
	public boolean search() {

		if(this.state == SearchState.comment)
			return false;

		driver = startWebDriver(url); 
		//		driver.get(url);

		try{
			WebElement opens = driver.findElement(By.xpath("//*[@id='search-button']"));
			moveTo(driver,opens );
			sleep(1000);
			clickInvisible(driver, opens);
			sleep(1000);
			WebElement ssection = driver.findElement(By.xpath("//*[@class='search__form-fields']"));
			WebElement field = ssection.findElement(By.xpath(".//input"));
			//			moveTo(driver, field);
			//			field.click();
			clickInvisible(driver, field);
			//			clickInvisible(driver, field);
			sleep(1000);
			//			field.clear();
			//			field.sendKeys(this.textToSearch);
			sendKeysInvisible(driver,field , this.textToSearch);
			sleep(1000);

			WebElement button = ssection.findElement(By.xpath(".//button"));
			//			button.click();
			clickInvisible(driver, button);
			sleep(2000);

		}catch(Exception e){e.printStackTrace();sleep(4000);return false;}

		driver.get(driver.getCurrentUrl());

		try{
			WebElement stories = driver.findElement(By.xpath("//*[@class='facet_list']//*[@for='collection_article']"));
			stories.click();
			sleep(3000);
		}catch(Exception e){e.printStackTrace();return false;}
		//		driver.get(driver.getCurrentUrl());

		return true;
	}


	/* 
	 * (non-Javadoc)
	 * @see Site#resultsPage(java.util.List)
	 * results in separated pages.
	 */

	@Override
	public void resultsPage(List<String> urls) {

		List<WebElement> results = driver.findElements(By.xpath("//*[@class='cnn-search__results-list']/div"));

		this.firstPage = driver.getCurrentUrl();

		System.out.println(results.size());
		if(results == null || results.size() == 0){
			System.err.println("no results");
			return;
		}


		String link="", title="", date="";
		int i=0;
		int checks = 0;
		int next = 0;
		int res= 0;
		boolean addLink=false;
		try{
			while(urls.size() < numOfArticles){
				addLink=false;
				link=""; title=""; date="";
				checks++;
				if(checks == maxSearch)
					return;


				try{
					WebElement ttl = results.get(i).findElement(By.xpath(".//*[@class='cnn-search__result-headline']/*"));
					link = ttl.getAttribute("href");

					title = ttl.getText();

					System.out.println(link);
					System.out.println(title);

					WebElement dt = results.get(i).findElement(By.className("cnn-search__result-publish-date"));
					date = dt.getText();
					String arr[] = date.split(" ");
					date = arr[1].substring(0, arr[1].length()-1)+"."+monthToInt(arr[0])+"."+arr[2];

				}catch(Exception e){e.printStackTrace();}

				try{
					String s = toDate;
					addLink = stateHandle(link, title, date);
					if(!s.equals(toDate)){
						driver.get(firstPage);
						results = driver.findElements(By.xpath("//*[@class='cnn-search__results-list']/div"));
						i=0;

					}

				}catch(Exception e){e.printStackTrace();addLink=false;}

				if(addLink){
					urls.add(link);
					removeDuplicate(urls);
					mainScreen.addToLog(urls.size()+"/"+this.numOfArticles);
				}
				addLink=false;

				i++;

				if(i>=results.size()){


					try {

						WebElement pagination = driver.findElement(By.xpath("//*[@class='pagination-arrow pagination-arrow-right cnnSearchPageLink text-active']"));
						moveTo(driver, pagination);
						sleep(1000);

						pagination.click();

						sleep(1000);

						next  =0;
					}catch(Exception e) {
						e.printStackTrace();sleep(3000);

						next++;
						sleep(2000);

						if(next >= 10){
							String s = this.toDate;
							updateToDate(true);
							if(s.equals(this.toDate))
								break;
							next = 0;
							driver.get(firstPage);
						}}
					results = driver.findElements(By.xpath("//*[@class='cnn-search__results-list']/div"));
					i=0;

					if(results.size() == 0){
						res++;
						sleep(2000);
					}else res=0;
					if(res >= 10){
						String s = this.toDate;
						updateToDate(true);
						if(s.equals(this.toDate))
							break;
						res = 0;
						driver.get(firstPage);
					}
				}
			}
		}
		catch(Exception e){e.printStackTrace();}
	}


	//	@Override
	//	public void firstPage(){
	//
	//		String curl= driver.getCurrentUrl();
	//		String[] arr =curl.split("&");
	//		if(arr.length == 5){
	//
	//			arr[2] = arr[2].substring(0, arr[2].indexOf("=")+1)
	//					+"1";
	//
	//			curl = arr[0]+arr[1] + arr[2] + arr[4];
	//		}
	//		driver.get(curl);
	//	}



}
