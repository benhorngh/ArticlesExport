import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class BBCPage extends Page{

	public BBCPage(WindowState window){
		super();
		this.window = window;
		this.SiteName = "BBC";
	}

	@Override
	public String getTitle() {
		String str="";
		boolean ok = false;
		WebElement ttl ;
		if(!ok) {
			try {
				ttl = driver.findElement(By.xpath("//*[@class='story-body__h1']"));
				str = ttl.getText();
				
				if(!str.isEmpty())
					ok = true;
			}
			catch(Exception e) {}
		}

		return str;
	}

	@Override
	public String getSubTitle() {
		String str="";
		boolean ok = false;
		WebElement shl ;
		
		if(!ok) {
			try {
				shl = driver.findElement(By.xpath("//*[@class='story-body__introduction']"));
				str = shl.getText();
				
				if(!str.isEmpty())
					ok = true;
			}
			catch(Exception e) {}
		}

		return str;
	}

	@Override
	public String getReporter() {
		
		String str="";
		boolean ok = false;
		WebElement rptr ;
		
		if(!ok) {
			try {
				rptr = driver.findElement(By.xpath("//*[@class='byline']/span[1]"));
				str = rptr.getText();
				
				if(!str.isEmpty())
					ok = true;
			}
			catch(Exception e) {}
		}

		return str;
		
	}

	@Override
	public String getDate() {
		
		String str="";
		boolean ok = false;
		WebElement dt ;
		
		if(!ok) {
			try {
				dt = driver.findElement(By.xpath("//*[@class='date date--v2']"));
				str = dt.getText();
				
				if(!str.isEmpty())
					ok = true;
				
				String[] arr = str.split(" ");
				String month =""+ monthToInt(arr[1]);
				str = arr[0]+"."+month+"."+arr[2];
			}
			catch(Exception e) {}
		}

		return str;
	}

	@Override
	public String getBody() {
		// TODO Auto-generated method stub
		
		String str="";
		boolean ok = false;
		ArrayList <WebElement> bdy ;
		
		if(!ok) {
			try {
				bdy = (ArrayList <WebElement>)
						driver.findElements(By.xpath("//*[@class='story-body__inner']/p"));
				

				for(int i=1; i<bdy.size(); i++) {
					str += bdy.get(i).getText();
				}
				
				if(!str.isEmpty())
					ok = true;
			}
			catch(Exception e) {}
		}
		
		if(!ok) {
			try {
				bdy = (ArrayList <WebElement>)
						driver.findElements(By.xpath("//*[@itemprop='articleBody']/p"));
				

				for(int i=1; i<bdy.size(); i++) {
					str += bdy.get(i).getText();
				}
				
				if(!str.isEmpty())
					ok = true;
			}
			catch(Exception e) {}
		}
		
		
		
		
		return str;
	}

	@Override
	public ArrayList<CommentRow> commentSecction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void readComments(ArrayList<CommentRow> commentsList) {
		// TODO Auto-generated method stub

	}


}
