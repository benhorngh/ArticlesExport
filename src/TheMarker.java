import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.WebElement;

public class TheMarker extends Site{

	static final String url= "http://www.themarker.com";


	public TheMarker(){
		super();
		this.window = WindowState.Invisible;
		this.page = new TheMarkerPage(window);
	}




	@Override
	public List<String> findLinks(String textToSearch, String textToCompare, int numOfArticles, state state) {

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

		catch (NullPointerException e) {e.printStackTrace(); return null;}
		catch (NoSuchFrameException e) {e.printStackTrace(); return null;}
		catch (NoSuchElementException e) {e.printStackTrace(); return null;}


		List<String> urls = new ArrayList<String>();


		WebElement box = driver.findElement(By.xpath("//*[contains(@class, 'border-box no-border-box list')]"));

		List<WebElement> resutls = driver.findElements(By.xpath("//*[contains(@class, 'tmTeaser generalTeaser')]"));

//		System.out.println(resutls.size());

		int found = 0;
		String link="", title="";
		int i=0;
		boolean addLink=false;
		while(found < numOfArticles){


			WebElement ttl = resutls.get(i).findElement(By.className("caption"));
			link = ttl.getAttribute("href");
			System.out.println(link);

//			WebElement teas = ttl.findElement(By.className("teaserTitleText"));
			title = ttl.getText();
			System.out.println(title);


			if(state==state.regular){
				addLink=true;
			}
			if(state==state.headline){
				if(contain(title, textToCompare))
					addLink=true;
			}
			if(state==state.body){
				addLink = bodyState(link, textToCompare);
			}
			if(state==state.comment){
				addLink = commentState(link, textToCompare);
			}
			
			if(addLink){
				urls.add(link);
				found++;
			}
			addLink=false;

			i++;

			if(i==6){
				i=0;
				WebElement pagination = driver.findElement(By.className("pagination"));
				moveTo(driver, pagination);

				WebElement nextButton = pagination.findElement(By.className("next"));
				nextButton.click();
				sleep(1000);

				resutls = driver.findElements(By.xpath("//*[contains(@class, 'tmTeaser generalTeaser')]"));
			}
		}
		return urls;
	}
	
	

}
