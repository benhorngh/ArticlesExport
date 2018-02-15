import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class TheMarkerPage extends Page {

	public TheMarkerPage(WindowState window){
		super();
		this.SiteName = "The Marker";
		this.window = window;
	}



	@Override
	public String getTitle() {
		String str="";

		try{
			WebElement art = driver.findElement(By.xpath("//*[@class='article  has-l-fixed-column']"));
			WebElement hl = art.findElement(By.className("h-mb--xxtight"));
			str=hl.getText();
		}
		catch(Exception e){
			try{
				WebElement hl = driver.findElement(By.xpath("//*[@class='art__head  mag-max']"));
				str=hl.getText();}
			catch(Exception e2){}

		}
		if(str.isEmpty()){
			try{
				WebElement hl = driver.findElement(By.xpath("//*[@class='h-mb--xxtight h-tac']"));
				str=hl.getText();}
			catch(Exception e){}
		}

		return str;
	}

	@Override
	public String getSubTitle() {
		String str="";
		try{
			WebElement shl = driver.findElement(By.xpath("//*[contains(@class, 't-delta h-mb--xxtight')]"));
			str = shl.getText();
		}
		catch(Exception e){
			try{
				WebElement shlBigA = driver.findElement(By.className("art__sub"));
				str = shlBigA.getText();}
			catch(Exception e2){}
		}
		if(str.isEmpty()){
			try{
				WebElement shl = driver.findElement(By.xpath("//*[@class='island--flush t-delta h-tac']"));
				str = shl.getText();}
			catch(Exception e2){}
		}


		return str;
	}

	@Override
	public String getReporter() {
		String str="";

		try{
			WebElement autor = driver.findElement(By.xpath("//*[@class='h-fr']/div[@class='t-byline']/address/a/span"));
			str=autor.getText();
		}
		catch(Exception e){
			try{
				WebElement byline = driver.findElement(By.xpath("//*[@class='t-byline']"));
				WebElement autor = byline.findElement(By.xpath("//*[@class='js-stat-util-info']/span"));
				str=autor.getText();
			}
			catch(Exception e1){}
		}
		if(str.isEmpty()){
			try{
				WebElement aut = driver.findElement(By.xpath("//*[@class='h-fr']/div[@class='t-byline']/address/span"));
				str = aut.getText();}
			catch(Exception e1){}

		}
		if(str.isEmpty()){
			try{
				WebElement aut = driver.findElement(By.xpath("//*[@class='t-byline h-tac']/address//span"));
				str = aut.getText();}
			catch(Exception e1){}

		}

		return str;
	}

	@Override
	public String getDate() {
		String str="";
		try{
			WebElement lilbox = driver.findElement(By.className("h-fr"));
			//			WebElement dt = lilbox.findElement(By.xpath("//*[@class='t-byline']/time"));
			WebElement dt = lilbox.findElement(By.xpath("//*[@class='h-fr']/div[@class='t-byline']/div/time"));

			str=dt.getText();
		}
		catch(Exception e){
			try{
				WebElement dt = driver.findElement(By.xpath("//*[@class='t-byline']/div[2]/time"));
				str=dt.getText();
			}
			catch(Exception e2){}
		}
		if(str.isEmpty()){
			WebElement dt = driver.findElement(By.xpath("//*[@class='t-byline h-tac']//time"));
			str=dt.getText();
			str = str.split(" ")[1];
		}
		return str;
	}


	@Override
	public String getBody() {
		String str="";
		//		WebElement bdy = driver.findElement(By.className("article__entry h-group"));
		List<WebElement> phgr = driver.findElements(By.className("t-body-text"));

		for (int i = 0; i < phgr.size(); i++) {
			str+=phgr.get(i).getText();
		}
		return str;
	}






	@Override
	public ArrayList<CommentRow> commentSecction() {
		String date = getDate();
		ArrayList <CommentRow> comments  = new ArrayList<CommentRow>();
		sleep(6000);



		//open all comments
		WebElement commentsSection = driver.findElement(By.className("comments"));

		moveTo2(driver, commentsSection);

		WebElement ctool = commentsSection.findElement(By.className("comments__tools"));

		WebElement openButton = ctool.findElement(By.xpath("//*[@class='comments__expand js-closed']"));

		sleep(5000);
		openButton = ctool.findElement(By.xpath("//*[@class='comments__expand js-closed']"));
		//		openButton = ctool.findElement(By.xpath("//*[@type='button']"));
		try{
			openButton.click();
		}
		catch(Exception e){	System.err.println("no comments");}


		try{
			WebElement lac = driver.findElement(By.cssSelector("#loadAllComments"));
			moveTo2(driver, lac);
			sleep(3000);
			lac.click();
			sleep(5000);

		}
		catch(Exception e){}


		readComments(comments);
		
		for(int i=0; i<comments.size(); i++)
			if(comments.get(i).date.indexOf(":")!=-1)
				comments.get(i).date = date;
		
		return comments;
	}

	@Override
	public void readComments(ArrayList<CommentRow> commentsList) {
		WebElement section = driver.findElement(By.cssSelector("#comments"));

		List<WebElement> cmmts = section.findElements(By.className("cmt"));

		int last = 0;
		boolean org = true;
		for(WebElement cmmt:cmmts){


			String num = cmmt.getAttribute("data-order");

			try{
				last = Integer.parseInt(num);
				org = true;
			}
			catch(Exception e){org = false;}
			CommentRow cr = oneComment(cmmt, last,org);
			commentsList.add(cr);


		}

	}

	/**
	 * Generate one CommentRow
	 * @param cmmt comment 
	 * @param num serial number of comment
	 * @param org true if original comment 
	 * @return CommentRow
	 */
	private CommentRow oneComment(WebElement cmmt , int num, boolean org){
		String gtb="", date="",  ghl="", body="" ;

		try{
			WebElement mc = cmmt.findElement(By.className("media__content"));

			WebElement title = mc.findElement(By.className("cmt__title"));
			ghl = title.getText();

			WebElement bdy = mc.findElement(By.className("cmt__text"));
			body = bdy.getText();

			String bottom = mc.getText();

			gtb = getAuthor(bottom);
			date = getDate(bottom);

		}
		catch(Exception e){e.printStackTrace();}
		CommentRow cr = new CommentRow("The Marker", gtb, date, ghl, body, num+"", org);
		return cr;
	}


	/**
	 * 
	 * @param text
	 * @return author name
	 */
	private String getAuthor(String text) {
		String au = "";
		if(text == null || text.isEmpty())
			return "";
		String[] arr = text.split('\n'+"");

		String dateandAut = arr[arr.length-5];

		arr = dateandAut.split(":");
		au = arr[0].substring(0, arr[0].length()-2);

		return au;
	}





	private String getDate(String text) {
		if(text.length()<11) return text;
		String dt = "";
		if(text == null || text.isEmpty())
			return "";


		String[] arr = text.split('\n'+"");
		String dateandAut = arr[arr.length-5];

		
//		dt = dateandAut.substring(dateandAut.length()-10);
//		return dt.split(" ")[0];
		
		dt = dateandAut;
		
		return dt.split(" ")[dt.split(" ").length-1];
	}

}
