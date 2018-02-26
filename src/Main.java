import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author benho
 * @since 1/2018
 *
 */
public class Main {

	/*TODO
	 * rename file
	 * time but not content
	 */

	static final String fileName = "excelFile";
	static final String folderName ="output";

	public static void main(String[] args) {
		// replaced with GUI


		String textToSearch = "משטרה";
		String textToSearchEnglish = "stock";
		String textToCompare = "שוטר";
		String textToCompareEnglish = "money";
		SearchState stat= SearchState.everywhere; 
		int numOfArticles = 200;
		boolean ynet = false;
		boolean TM = false;
		boolean blmbrg = false;
		boolean rtrs = false;
		boolean glbs = false;
		boolean CNN = false;
		boolean BBC = true;
		boolean[] players={
				ynet
				,TM
				,blmbrg
				,rtrs
				,glbs
				,CNN
				,BBC
		};

		String startDate=""; 
		String endDate="";

		boolean toFile = true;

		starter(textToSearch,textToSearchEnglish,textToCompare,textToCompareEnglish
				,stat,startDate,endDate,numOfArticles, players, toFile);
	}

	/**
	 * @param textToSearch -text to the search field
	 * @param textToSearchEnglish -text to the search field in english (for websites in english.)
	 * @param textToCompare -text to search inside the article
	 * @param textToCompareEnglish -text to search inside the article in english (for websites in english.)
	 * @param stat -state of search. regular, search in title, body or in the comments. 
	 * @param startDate -starting date
	 * @param endDate -ending date
	 * @param numOfArticles -number of needed reports
	 * @param players -true/false for each site.
	 * @param toFile -true if include txt file of each article in output folder.
	 */
	public static void starter(String textToSearch
			,String textToSearchEnglish
			,String textToCompare
			,String textToCompareEnglish
			,SearchState stat
			,String startDate
			,String endDate
			,int numOfArticles
			,boolean[] players
			,boolean toTxt
			){
		
		


		//		if(!endDate.isEmpty()){
		//			endDate= endDate.replace(endDate.charAt(endDate.length()-5)+"", '.'+"");
		//		}
		//		if(!startDate.isEmpty()){
		//			startDate =startDate.replaceAll(startDate.charAt(startDate.length()-5)+"", '.'+"");
		//		}

		File directory = new File(folderName);
		if (! directory.exists())
			directory.mkdir();



		if(textToSearch.isEmpty() && textToSearchEnglish.isEmpty()){
			mainScreen.addToLog("error: 'text to search' is empty");
			return;
		}
		if(numOfArticles<=0){
			mainScreen.addToLog("error: Invaild 'Number of reports' ");
			return;
		}
		if(numOfArticles<=0){
			mainScreen.addToLog("error: Invaild 'Number of reports' ");
			return;
		}
		if(!startDate.isEmpty()){
			Date sd;
			sd = Funcs.stringToDate(startDate);
			if(sd == null){
				mainScreen.addToLog("error: Invaild date");
				return;
			}
			
			String[] arr = startDate.split("\\.");
			try{
				if(Integer.parseInt(arr[0])>32 || Integer.parseInt(arr[1])>12){
					mainScreen.addToLog("error: Invaild start date");
					return;
				}
			}catch(Exception e){mainScreen.addToLog("error: Invaild start date");
			return;}
		}
		
		
		
		if(!endDate.isEmpty()){
			Date ed;
			ed = Funcs.stringToDate(endDate);
			if(ed == null){
				mainScreen.addToLog("error: Invaild date");
				return;
			}
			
			String[] arr = endDate.split("\\.");
			try{
				if(Integer.parseInt(arr[0])>32 || Integer.parseInt(arr[1])>12){
					mainScreen.addToLog("error: Invaild end date");
					return;
				}
			}catch(Exception e){mainScreen.addToLog("error: Invaild end date");
			return;}
		}






		if(endDate.isEmpty() &&  (!startDate.isEmpty())){
			endDate = Funcs.todayString();
		}

		if(startDate.isEmpty() && (!endDate.isEmpty())){
			startDate = "1.1.1980";
		}

		if(Funcs.stringToDate(startDate)==null)
			startDate ="";

		if(Funcs.stringToDate(endDate)==null)
			endDate ="";


		//		System.out.println("s "+startDate);
		//		System.out.println("e "+endDate);
		//		


		mainScreen.addToLog("starting..");

		System.out.println();
		System.out.println("tts: "+textToSearch +" || "+textToSearchEnglish);
		System.out.println("ttc: "+textToCompare+" || " + textToCompareEnglish);
		System.out.println("stat: "+stat);
		System.out.println("start d: "+startDate);
		System.out.println("end d: "+endDate);
		System.out.println("noa: "+numOfArticles);
		System.out.println("totxt: "+toTxt);
		System.out.print("players: ");
		if(players[0]) System.out.print("Ynet ");
		if(players[1]) System.out.print("TheMarker ");
		if(players[2]) System.out.print("Bloomberg ");
		if(players[3]) System.out.print("Reuters ");
		if(players[4]) System.out.print("Globes ");
		if(players[5]) System.out.print("CNN ");
//		if(players[6]) System.out.print("BBC ");
		System.out.println();
		System.out.println();

		Site[] sites = init(textToSearch,textToSearchEnglish, textToCompare, textToCompareEnglish,
				stat, numOfArticles, startDate,endDate);
		//		boolean[] players = {ynet, TM, blmbrg, rtrs, glbs};
		//Ynet , TheMarker, Bloomberg, Reuters, Globes .

		play(sites, players, toTxt);

		System.out.println();
		System.out.println("Done.");
		mainScreen.addToLog("Done."+'\n');
	}



	/**
	 * 
	 * @param tts -text to the search field
	 * @param etts -text to the search field in english (for websites in english.)
	 * @param ttc  -text to search inside the article
	 * @param ettc -text to search inside the article in english (for websites in english.)
	 * @param stat -state of search. regular, search in title, body or in the comments. 
	 * @param noa -number of needed reports
	 * @param sd starting date
	 * @param ed ending date
	 * @return initialized Sites array.
	 */
	private static Site[] init(String tts, String etts, String ttc, String ettc, 
			SearchState stat, int noa, String sd,String ed){
		Site[] sites = new Site[6];
		sites[0]=new Ynet     (tts, ttc, noa, stat, sd,ed);
		sites[1]=new TheMarker(tts, ttc, noa, stat, sd,ed);
		sites[2]=new Bloomberg(etts, ettc, noa, stat, sd,ed);
		sites[3]=new Reuters(etts, ettc, noa, stat, sd,ed);
		sites[4]=new Globes(tts, ttc, noa, stat, sd,ed);
		sites[5]=new CNN(etts, ettc, noa, stat, sd,ed);
//		sites[6]=new BBC(etts, ettc, noa, stat, sd,ed);

		return sites;
	}

	private static void play(Site[] sites, boolean[] players, boolean totxt) {




		/*
		 * !!warnning!!
		 * use only for strong comuter. uncheded deeply.
		 * !!warnning!!
		 */
		boolean useThreads = false;


		startWriters();


		File directory;
		directory = new File(folderName+"/"+fileName+".xlsx");
		if (!directory.exists()){
			try {
				outputStream = new FileOutputStream(folderName+"/"+fileName+".xlsx");
			} catch (FileNotFoundException e) {}
		}

		else{
			boolean ok = false;
			int j=1;
			while(!ok){
				directory = new File(folderName+"/"+fileName+"-"+j+".xlsx");
				if (!directory.exists()){
					try {
						outputStream = new FileOutputStream(folderName+"/"+fileName+"-"+j+".xlsx");
						ok =true;
					} catch (FileNotFoundException e) {}
				}
				j++;
			}
		}


		Thread[] threads = new  Thread[sites.length];
		for(int i=0; i<threads.length; i++){
			threads[i] = new Thread(sites[i]);
		}


		for(int i=0; i<sites.length; i++){
			if(players[i]){
				try{
					if(useThreads)
						threads[i].start();
					else {
						sites[i].run();
					}
				}
				catch(Exception e){System.err.println("failed!");
				e.printStackTrace();}
			}
		}

		if(useThreads){
			try {
				Thread.currentThread();
				Thread.sleep(100000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.err.println("the end has come. join!");
			try{
				for(int i=0; i<threads.length; i++){
					threads[i].join();
				}
			}catch(Exception e){};
		}

		for(int i=0; i<sites.length; i++){
			if(players[i]==true)
				if(sites[i].articles!=null && sites[i].articles.size()!=0)
					ArticlesRow.WriteToFile(sites[i].articles,  totxt);
		}

		closeWriters();
		
		
		closeAllChrome();


	}
	
	private static void closeAllChrome() {
		try {
			Runtime.
			   getRuntime().
//			   exec("cmd /c start \"\" closeOpenChromeD.bat");
			   exec("taskkill /im chromedriver.exe /f");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(Exception e) {e.printStackTrace();}
	}


	static XSSFWorkbook workbook;
	static FileOutputStream outputStream;

	public static void startWriters(){
		workbook = new XSSFWorkbook();

		XSSFSheet ArticlesSheet = workbook.createSheet("Articles");
		XSSFSheet CommentsSheet = workbook.createSheet("Comments");

		//		ArticlesSheet.getCTWorksheet().getSheetViews().getSheetViewArray(0).setRightToLeft(true);
		//		CommentsSheet.getCTWorksheet().getSheetViews().getSheetViewArray(0).setRightToLeft(true);

		String[] arhl={"num.","site","link","date","reporter","number of words","headline", "subtitle","body","","","comments"};
		Funcs.StringArrToLastRow(arhl, ArticlesSheet);

		String[] cmhl={"report num.","website","num.","is Original","name","date","title","comment"};
		Funcs.StringArrToLastRow(cmhl, CommentsSheet);
	}



	public static void closeWriters(){
		try {
			workbook.write(outputStream);
			outputStream.close();
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
