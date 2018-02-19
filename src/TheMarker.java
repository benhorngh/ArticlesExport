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
		this.page = new TheMarkerPage(window);
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

		chooseTime();

		return true;
	}








	@Override
	public void resultsPage(List<String> urls) {
		//		WebElement box = driver.findElement(By.xpath("//*[contains(@class, 'border-box no-border-box list')]"));

		List<WebElement> resutls = driver.findElements(By.xpath("//*[contains(@class, 'tmTeaser generalTeaser')]"));

		int found = 0;
		String link="", title="", date="";
		int i=0;
		int  checks = 0;
		boolean addLink=false;
		while(found < numOfArticles){

			link=""; title=""; date="";


			WebElement ttl = resutls.get(i).findElement(By.className("caption"));
			link = ttl.getAttribute("href");
			
			System.out.println(link);

			title = ttl.getText();
			System.out.println(title);

			try{
				WebElement dt = resutls.get(i).findElement(By.className("authorBarElement"));
				date = dt.getText();
			}catch(Exception e){e.printStackTrace();}

			addLink = stateHandle(link, title, date);

			if(link.contains("LIVE")){addLink=false;}
			
			if(addLink){
				urls.add(link);
				found++;
			}
			addLink=false;

			i++;
			checks++;

			if(i==6){
				i=0;
				WebElement pagination = driver.findElement(By.className("pagination"));
				moveTo(driver, pagination);

				WebElement nextButton = pagination.findElement(By.className("next"));
				nextButton.click();
				sleep(1000);

				resutls = driver.findElements(By.xpath("//*[contains(@class, 'tmTeaser generalTeaser')]"));
			}
			if(checks == maxSearch)
				return;
		}


	}




	public void chooseTime() {

		if(this.fromDate.isEmpty())
			return;

		WebElement tof = driver.findElement(By.className("textOnlySearchForm"));
		WebElement bttn = tof.findElement(By.cssSelector("#advancedFormOpenBtn"));
		moveTo2(driver, bttn);
		sleep(1000);
		bttn.click();

		//		WebElement st = driver.findElement(By.xpath("//*[@name='startDate']"));
		//startDate


	}



}
