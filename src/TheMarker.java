import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.WebElement;

/**
 * http://www.themarker.com
 * @author benho
 *
 */
public class TheMarker extends Site {


	public TheMarker(String tts, String ttc, int noa, SearchState stat, String sd, String ed) {
		super(tts, ttc, noa, stat, sd,ed);
		this.url= "http://www.themarker.com";
		this.window = WindowState.Invisible;
		this.page = new TheMarkerPage(window ,this);
		this.DateRange = false;
	}




	@Override
	public boolean search() {
 
		driver = startWebDriver(url);
		sleep(10000);
		//search
		try{
			WebElement search = driver.findElement(By.id("search-widget"));

			sleep(5000);
			WebElement searchButton = search.findElement(By.cssSelector("#search-button"));
			moveTo(driver, searchButton);
			searchButton.click();
			sleep(5000);

			WebElement opensearch = search.findElement(By.cssSelector("#mh__search-form"));


			WebElement field = opensearch.findElement(By.className("search-field"));

			field.click();
			field.clear();
			field.sendKeys(textToSearch);

			WebElement button = opensearch.findElement(By.xpath("//*[contains(@class, 'js-mh__search-action mh__search-action')]"));
			button.click();
		}

		catch (NullPointerException e) {e.printStackTrace(); return false;}
		catch (NoSuchFrameException e) {e.printStackTrace(); return false;}
		catch (NoSuchElementException e) {e.printStackTrace(); return false;}


		return true; 
	}





	/*
	 * (non-Javadoc)
	 * @see Site#resultsPage(java.util.List)
	 * results in separated pages.
	 */



	@Override
	public void resultsPage(List<String> urls) {
	
		List<WebElement> results = driver.findElements(By.xpath("//*[contains(@class, 'tmTeaser generalTeaser')]"));

		this.firstPage = driver.getCurrentUrl();
		
		String link="", title="", date="";
		int i=0;
		int checks = 0;
		boolean addLink=false;
		int res=0;
		while(urls.size() < numOfArticles){

			link=""; title=""; date="";
			addLink=false;
			checks++;
			if(checks == maxSearch)
				return;

			if(i<results.size()){
				try{
					WebElement ttl = results.get(i).findElement(By.className("caption"));
					link = ttl.getAttribute("href");

					title = ttl.getText();

				}catch(Exception e){e.printStackTrace();}

				System.out.println(link);
				System.out.println(title);

				try{
					WebElement dt = results.get(i).findElement(By.className("authorBarElement"));
					date = dt.getText();
				}catch(Exception e){e.printStackTrace();}

				try{
					String s = toDate;
					addLink = stateHandle(link, title, date);
					if(!s.equals(toDate)){
						driver.get(firstPage);
						results = driver.findElements(By.xpath("//*[contains(@class, 'tmTeaser generalTeaser')]"));
						i=0;
					}
				}catch(Exception e){e.printStackTrace();addLink=false;}

				if(!link.contains("LIVE")){

					if(addLink){
						urls.add(link);
						removeDuplicate(urls);
						mainScreen.addToLog(urls.size()+"/"+this.numOfArticles);
					}
				}

				i++;

			}
			if(i >=results.size()){
				i=0;
				try{
					WebElement pagination = driver.findElement(By.className("pagination"));
					moveTo(driver, pagination);

					WebElement nextButton = pagination.findElement(By.className("next"));
					nextButton.click();
					sleep(1000);

				}catch(Exception e){e.printStackTrace();}
				
				results = driver.findElements(By.xpath("//*[contains(@class, 'tmTeaser generalTeaser')]"));
				
				if(results.size() ==0){
					sleep(2000);
					res++;
				}else res =0;
				
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

	

}
