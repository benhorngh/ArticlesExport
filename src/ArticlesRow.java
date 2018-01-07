import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import javax.sound.midi.Synthesizer;

import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 * @author Ben Horn and Sefi Erlich
 * @since 1/2018
 *
 */
public class ArticlesRow {
	public int num;
	public String headLine;
	public String date;
	public String reporter;
	public String site;
	public String body;
	public String comments;

	static int counter=1;

	public ArticlesRow(){
		this.num=counter;
		counter++;
		headLine="";
		date="";
		reporter="";
		site="";
		body="";
		comments="";
	}

	
	public void WriteToFile() {
		XSSFSheet sheet = Main.workbook.getSheet("Articles");
		String[] row = {num+"", site, headLine, date, reporter, body, comments};
		Funcs.StringArrToLastRow(row, sheet);
		
	}
	
	
	
	
	
	
	
	

	/**
	 * This functions use to write to csv file, instead of using apache POI for excel
	 */
	public void WriteToFile(FileWriter writer) {
		try {
			clean();

			writer.append(""+num+',');
			writer.append(site+',');
			writer.append(headLine+',');
			writer.append(date+',');
			writer.append(reporter+',');
			writer.append(body+',');

			Funcs.apeandComments(writer, comments);
			

			writer.append('\n');

			writer.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	public void clean(){
		headLine = Funcs.clean(headLine);
		reporter = Funcs.clean(reporter);
		body = Funcs.clean(body);
		comments = Funcs.clean(comments);
		date = Funcs.clean(date);
	}

	


}
