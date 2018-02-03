import java.io.FileNotFoundException;


import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author Ben Horn
 * @since 1/2018
 *
 */
public class Main {

	public static XSSFWorkbook workbook;

	public static void main(String[] args) {

		String textToSearch = "הסערה הגדולה";
		String textToCompare = "ישראל";
		state stat= state.regular;  
		int numOfArticles = 5;


		boolean ynet = false;
		boolean WSJ = false;
		boolean TM = true;

		String startDate="1/1/2017";
		String endDate="1/1/2018";

		play(textToSearch, textToCompare, stat, numOfArticles, startDate,endDate, ynet, WSJ, TM);

		System.out.println();
		System.out.println("Done.");

	}





	private static void play(String tts, String ttc, state stat, int noa, String sd,String ed, 
			boolean ynet, boolean WSJ, boolean TM) {

		String fileName = "excelFile";

		Site ynetS = new Ynet(tts, ttc, noa, stat, sd,ed);
		Site WSJS = new WallStreetJournal(tts, ttc, noa, stat, sd,ed);
		Site TMS = new TheMarker(tts, ttc, noa, stat, sd,ed);

		startWriters();



		if(ynet){
			List<ArticlesRow> YnetReports=null;
			try{
				YnetReports = ynetS.Start();
			}
			catch(Exception e){System.err.println("Ynet Faild ");
			e.printStackTrace();}

			if(YnetReports!=null)
				ArticlesRow.WriteToFile(YnetReports);
		}


		if(WSJ){
			List<ArticlesRow> WSJReports=null;
			try{

				WSJReports = WSJS.Start();
			}
			catch(Exception e){System.err.println("Wall Street Journal Faild ");
			e.printStackTrace();}

			if(WSJReports!=null)
				ArticlesRow.WriteToFile(WSJReports);
		}

		if(TM){
			List<ArticlesRow> TMReports=null;
			try{
				
				 TMReports = TMS.Start();
			}
			catch(Exception e){System.err.println("The Marker Faild ");
			e.printStackTrace();}

			if(TMReports!=null)
				ArticlesRow.WriteToFile(TMReports);
		}



		closeWriters(fileName);

	}





	public static void startWriters(){

		workbook = new XSSFWorkbook();

		XSSFSheet ArticlesSheet = workbook.createSheet("Articles");
		XSSFSheet CommentsSheet = workbook.createSheet("comments");

		//		ArticlesSheet.getCTWorksheet().getSheetViews().getSheetViewArray(0).setRightToLeft(true);
		//		CommentsSheet.getCTWorksheet().getSheetViews().getSheetViewArray(0).setRightToLeft(true);

		String[] arhl={"num.","site","link","date","reporter","number of words","headline", "subtitle","body","","","comments"};
		Funcs.StringArrToLastRow(arhl, ArticlesSheet);

		String[] cmhl={"report num.","website","num.","is Original","name","date","title","comment"};
		Funcs.StringArrToLastRow(cmhl, CommentsSheet);

	}

	public static void closeWriters(String fileName){
		try {
			FileOutputStream outputStream = new FileOutputStream(fileName+".xlsx");
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
