import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author Ben Horn and Sefi Erlich
 * @since 1/2018
 *
 */
public class Main {

	public static XSSFWorkbook workbook;
	
	public static void main(String[] args) {


		String textToSearch = "ביבי";
		int state= 1;  //1 for all articles, 0 for only headlines
		int numOfArticles = 3;

		boolean ynet = true;

		startWriters();
		if(ynet)
			Ynet.YnetSearcher(textToSearch, numOfArticles, state);

		String name = "excelFile";
		closeWriters(name);

	}

	public static void startWriters(){

		
		 workbook = new XSSFWorkbook();

		XSSFSheet ArticlesSheet = workbook.createSheet("Articles");
		XSSFSheet CommentsSheet = workbook.createSheet("comments");

		ArticlesSheet.getCTWorksheet().getSheetViews().getSheetViewArray(0).setRightToLeft(true);
		CommentsSheet.getCTWorksheet().getSheetViews().getSheetViewArray(0).setRightToLeft(true);

		String[] arhl={"מס' כתבה","אתר","כותרת","תאריך","כתב","הכתבה","תגובות"};
		Funcs.StringArrToLastRow(arhl, ArticlesSheet);

		String[] cmhl={"מס' כתבה","אתר","מס' תגובה","שם","כותרת","תגובה"};
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
