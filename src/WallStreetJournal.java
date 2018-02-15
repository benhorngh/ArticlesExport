import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.WebElement;

public class WallStreetJournal extends Site {

	/*
	 * *****************************
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!]]]-------
	 * *****************************
	 *
	 * 
	 * this website canceld for now.
	 * 
	 * 
	 * *****************************
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!]]]-------
	 * *****************************
	 */

	static final String url="http://www.wsj.com";


	

	public WallStreetJournal(String tts, String ttc, int noa, SearchState stat, String sd, String ed) {
		super(tts, ttc, noa, stat, sd,ed);
	}


	@Override
	public List<String> findLinks() {


		driver = startWebDriver(url);


		try {
			sleep(5000);

			//open search field
			WebElement search = driver.findElement(By.className("style__search-wrapper_1bYLpXJNEXNvgt7PcFMhQt"));
			search.click();
			sleep(1000);

			//set keys to the search field
			WebElement textField = driver.findElement(By.className("style__wsj-search-input_R7oQjW8fXqah81OIFgvIt"));
			textField.click();
			textField.sendKeys(textToSearch);
			sleep(1000);

			//click search button
			WebElement searchbutton = driver.findElement(By.className("style__search-submit_36AD7RDgkLzFtv3AeWjhgR"));
			searchbutton.click();


		}catch (NullPointerException e) {e.printStackTrace(); return null;}
		catch (NoSuchFrameException e) {e.printStackTrace(); return null;}
		catch (NoSuchElementException e) {e.printStackTrace(); return null;}

		try{
//			WebElement ad = driver.findElement(By.cssSelector("#cx-scrim-wrapper"));
			WebElement close = driver.findElement(By.className("close-btn"));
			close.click();
		}

		catch (NoSuchElementException e) {e.printStackTrace(); }



		WebElement box =driver.findElement(By.xpath("//*[contains(@class, 'items hedSumm')]"));
//		WebElement box = driver.findElement(By.cssSelector("items hedSumm"));
		List<WebElement> results = box.findElements(By.xpath("//*[contains(@class, 'item-container headline-item')]"));



		System.out.println(results.size());

		int found = 0;
		int i=0;
		String link="";
		String title="";
		while(found<numOfArticles){


			WebElement headline = results.get(i).findElement(By.className("headline"));
			
			System.out.println("hl: " +headline.getText());
			
			title = headline.getText();
			System.out.println(title);
			WebElement h3 = results.get(i).findElement(By.xpath("//*[@class='headline']/a"));
			
			link=h3.getAttribute("href");
			System.out.println("l "+link);
			i++;





			if(i==20){
				//TODO click next page, update results List.
			}
		}


		sleep(30000);
		return null;
	}


	@Override
	public void resultsPage(List<String> urls) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean search() {
		// TODO Auto-generated method stub
		return false;
	}




}
