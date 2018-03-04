import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Yahoo extends Site{
	
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
	

	public Yahoo(String tts, String ttc, int noa, SearchState stat, String sd, String ed) {
		super(tts, ttc, noa, stat, sd,ed);
		this.url="https://www.yahoo.com";
		this.window = WindowState.visible;
		this.DateRange = false;
//		this.page = new GlobesPage(window);
	}
	
	@Override
	public boolean search() {
		
		startWebDriver(url);
		
		try{
			WebElement search = driver.findElement(By.xpath("//*[@id='search-assist-input']//input"));
		
			search.click();
			search.clear();
			search.sendKeys(this.textToSearch);
			
			WebElement button = driver.findElement(By.xpath("//*[@id='search-buttons']//button"));
			button.click();
				}
		catch(Exception e){return false;}
		
		
		return false;
	}

	@Override
	public void resultsPage(List<String> urls) {
		// TODO Auto-generated method stub
		
	}

}
