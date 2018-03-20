import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class FoxNewsPage extends Page{

	public FoxNewsPage(WindowState window, Site site){
		super(site);
		this.window = window;
		this.SiteName = "Fox News";
	}


	@Override
	public String getTitle() {
		String str ="";
		boolean ok = false;
		WebElement ttl ;

		try{
			ttl = driver.findElement(By.xpath("//h1[@class='headline']"));
			str = ttl.getText();

			if(!str.isEmpty())
				ok = true;
		}catch(Exception e){}

		if(!ok){
			try{
				ttl = driver.findElement(By.xpath("//*[@class='main']//h1"));
				str = ttl.getText();

				if(!str.isEmpty())
					ok = true;
			}catch(Exception e){}
		}

		if(!ok){
			try{
				ttl = driver.findElement(By.xpath("//h1[@class='head1']"));
				str = ttl.getText();

				if(!str.isEmpty())
					ok = true;
			}catch(Exception e){}
		}
		if(!ok){
			try{
				ttl = driver.findElement(By.xpath("//*[@class='article-header']/h1"));
				str = ttl.getText();

				if(!str.isEmpty())
					ok = true;
			}catch(Exception e){}
		}

		return str;
	}

	@Override
	public String getSubTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getReporter() {
		String str = "";
		boolean ok = false;
		WebElement rptr;

		try{
			rptr = driver.findElement(By.xpath("//*[@class='article-info']/div/div"));
			str = rptr.getText();

			if(!str.isEmpty())
				ok = true;
		}catch(Exception e){}

		if(!ok){
			try{
				rptr = driver.findElement(By.xpath("//*[@class='author-byline']/span"));
				str = rptr.getText();

				if(!str.isEmpty())
					ok = true;
			}catch(Exception e){}
		}

		if(!ok){
			try{
				rptr = driver.findElement(By.xpath("//*[@class='author']/a"));
				str = rptr.getText();

				if(!str.isEmpty())
					ok = true;
			}catch(Exception e){}
		}
		if(!ok){
			try{
				rptr = driver.findElement(By.xpath("//*[@class='source']"));
				str = rptr.getText();

				if(!str.isEmpty())
					ok = true;
			}catch(Exception e){}
		}

		if(!ok){
			try{
				rptr = driver.findElements(By.xpath("//*[contains(@class,'author-byline')]//a")).get(0);
				str = rptr.getText();

				if(!str.isEmpty())
					ok = true;

				rptr = driver.findElements(By.xpath("//*[contains(@class,'author-byline')]//a")).get(1);
				str += " "+rptr.getText();
			}catch(Exception e){}
		}

		return str;
	}

	@Override
	public String getDate() {

		String str = "";
		boolean ok = false;
		WebElement dt;

		try{
			dt = driver.findElement(By.xpath("//time[contains(@datatime,'')]"));
			str = dt.getText();

			String[] arr = str.trim().split(" ");

			if(str.contains("ago")){
				str = todayString();
			}

			else if(arr.length == 2){
				arr[0] = ""+ monthToInt(arr[0]);
				arr[1] = arr[1].substring(0, arr[1].length()-2);
				str = arr[1]+"."+arr[0]+"."+"2018";
			}

			else if(arr.length == 3){
				arr[0] = ""+ monthToInt(arr[0]);
				arr[1] = arr[1].substring(0, arr[1].length()-3);
				str = arr[1]+"."+arr[0]+"."+arr[2];
			}

			else if(arr.length == 4){
				arr[1] =""+ monthToInt(arr[1]);
				arr[2] = arr[2].substring(0, arr[2].length()-1);
				str = arr[2] + "." + arr[1] +"."+ arr[3];
			}

		}catch(Exception e){}

		return str;
	}

	@Override
	public String getBody() {
		String str = "";
		boolean ok = false;
		ArrayList<WebElement> bd;

		try{
			bd = (ArrayList<WebElement>)
					driver.findElements(By.xpath("//*[@class='article-text']/p"));

			for(WebElement p : bd)
				str += p.getText();

			if(!str.isEmpty())
				ok = true;
		}catch(Exception e){}

		if(!ok){
			try{
				bd = (ArrayList<WebElement>)
						driver.findElements(By.xpath("//*[@class='article-body']/p"));

				for(WebElement p : bd)
					str += p.getText();

				if(!str.isEmpty())
					ok = true;
			}catch(Exception e){}

		}

		return str;
	}

	@Override
	public ArrayList<CommentRow> commentSecction() {

		try{
			WebElement frame = driver.findElement(By.xpath("//*[@class='sppre_frame-container']/iframe"));
			driver.switchTo().frame(frame);
		}catch(Exception e){return null;}

		for(int i=0; i<200; i++){
			try{
				WebElement loadmore = driver.findElement(By.xpath("//*[@data-spmark='show-more']"));
				moveTo2(driver, loadmore);
				loadmore.click();
				sleep(1500);
				System.out.println("load");
			}catch(Exception e){break;}
		}


		ArrayList <WebElement> seemore = (ArrayList <WebElement>)
				driver.findElements(By.xpath("//*[@class='sppre_show-more-replies-button']"));

		for(WebElement s : seemore){
			try{
				moveTo2(driver, s);
				s.click();
				sleep(1500); 
				System.out.println("see");
			}catch(Exception e){}
		}

		ArrayList <WebElement> exp = (ArrayList <WebElement>)
				driver.findElements(By.xpath("//*[@class='sppre_see-more']"));

		for(WebElement s : exp){
			try{
				moveTo2(driver, s);
				s.click();
				sleep(1500);
				System.out.println("see");
			}catch(Exception e){}
		}



		ArrayList<CommentRow> cmmts = new ArrayList<CommentRow>();

		try{
			readComments(cmmts);
		}catch(Exception e){e.printStackTrace();}

		return cmmts;
	}

	@Override
	public void readComments(ArrayList<CommentRow> commentsList) {


		WebElement parent = driver.findElement(By.xpath("//*[contains(@class,'sppre_conversation-view')]"));
		readComments(commentsList, parent);
	}



	public void readComments(ArrayList<CommentRow> commentsList, WebElement parent) {

		if(parent == null)
			return;

		ArrayList<WebElement> cmmts=null;
		try{
			cmmts= (ArrayList<WebElement>) parent.findElements(By.xpath("./div/ul/li"));

		}catch(Exception e){return;}




		if(cmmts == null || cmmts.size() == 0)
			return;

		numbers.add(0);

		CommentRow cr = null ;
		for(WebElement cmmt: cmmts){

			WebElement cm = cmmt;
			count();
			try{
				cr = getComment(cmmt, listToArray(numbers));
			}catch(Exception e){}
			if(cr != null)
				commentsList.add(cr);

			readComments(commentsList, cm);

		}
		numbers.remove(numbers.size()-1);
	}


	private CommentRow getComment(WebElement comment, int[] num) {
		String name = "",dt="",bdy="", react = "";

		try{
			WebElement cmmt = null;
			try{
				cmmt = comment.findElement(By.xpath("./div/div[@class='sppre_message-stack']"));
			}catch(Exception e){ 
				cmmt = comment.findElement(By.xpath("./div[@class='sppre_message-stack']"));
			}


			try{
				bdy = cmmt.findElement(By.xpath(".//*[@data-spot-im-class='message-text']")).getText();
			}catch(Exception e){}

			try{
				react = cmmt.findElement(By.xpath(".//*[@class='sppre_reply-to']/span")).getText();
			}catch(Exception e){}

			try{//28 Nov, 2016
				dt = cmmt.findElement(By.xpath(".//*[@class='sppre_posted-at']")).getText();
				System.out.println("dt: "+dt);
				String[] arr = dt.split(" ");
				arr[1] = monthToInt(arr[1].substring(0, arr[1].length()-1))+"";

				if(arr.length == 3)
					dt = arr[0]+"."+arr[1]+"."+arr[2];
				else dt = arr[0]+"."+arr[1]+"."+"2018";
			}catch(Exception e){}
			try{
				name = cmmt.findElement(By.xpath(".//*[@data-spot-im-class='message-username']")).getText();
			}catch(Exception e){}

		}catch(Exception e){return null;}

		boolean org = false;
		String serial = ArrToStr(num);
		if(serial.indexOf(".") == -1)
			org = true;
		//		if(bdy.isEmpty()) return null;
		CommentRow cr = new CommentRow(this.SiteName, name, dt, react, bdy, serial, org);
		return cr;

	}



	private void count() {
		int x= numbers.get(numbers.size()-1);
		x++;
		numbers.remove(numbers.size()-1);
		numbers.add(x);
	}

	private int[] listToArray(ArrayList<Integer> IntArrList) {

		int[] arr = new int[IntArrList.size()];

		for(int i =0; i<arr.length; i++){
			arr[i]  = IntArrList.get(i);
		}
		arr[0]--;
		return arr;
	}

	private ArrayList<Integer> numbers = new ArrayList<Integer> ();


	private String ArrToStr(int[] num){
		String s="";
		s = num[0]+"";
		for(int i=1; i<num.length;i++){
			s+= "."+num[i];
		}
		return s;
	}



}
