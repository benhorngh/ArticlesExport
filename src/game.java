
import java.util.ArrayList;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class game {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WebDriver driver=  Funcs.startWebDriver("http://www.google.com");
		
		
		((JavascriptExecutor)driver).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
	    driver.switchTo().window(tabs.get(tabs.size()-1));
	    driver.get("http://bing.com");
		YnetPage.driver=driver;
		
		tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(0));
		driver.switchTo().window(tabs.get(1));
		driver.close();
		driver.switchTo().window(tabs.get(0));
		

	}

}
