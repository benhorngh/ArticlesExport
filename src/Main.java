import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

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
		String textToSearchEnglish = "glass";
		String textToCompare = "טראמפ";
		String textToCompareEnglish = "the";
		SearchState stat= SearchState.body;  
		int numOfArticles = 15;
		boolean ynet = false;
		boolean TM = false;
		boolean blmbrg = false;
		boolean rtrs = false;
		boolean glbs = false;
		boolean CNN = true;
		boolean[] players={
				ynet
				,TM
				,blmbrg
				,rtrs
				,glbs
				,CNN
		};

		String startDate="1.1.2018"; 
		String endDate="1.2.2018";
		
		boolean toFile = true;

		starter(textToSearch,textToSearchEnglish,textToCompare,textToCompareEnglish
				,stat,startDate,endDate,numOfArticles, players, toFile);
	}

	public static void starter(String textToSearch
			,String textToSearchEnglish
			,String textToCompare
			,String textToCompareEnglish
			,SearchState stat
			,String startDate
			,String endDate
			,int numOfArticles
			,boolean[] players
			,boolean toFile
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


		Site[] sites = init(textToSearch,textToSearchEnglish, textToCompare, textToCompareEnglish,
				stat, numOfArticles, startDate,endDate);
		//		boolean[] players = {ynet, TM, blmbrg, rtrs, glbs};
		//Ynet , TheMarker, Bloomberg, Reuters, Globes .

		play(sites, players, toFile);

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
		
		try {
			outputStream = new FileOutputStream(folderName+"/"+fileName+".xlsx");
		} catch (FileNotFoundException e1) {
			
			boolean ok = false;
			int i=1;
			while(ok){
				try {
					outputStream = new FileOutputStream(folderName+"/"+fileName+"-"+i+".xlsx");
					ok =true;
				} catch (FileNotFoundException e) {
					i++;
				}
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
