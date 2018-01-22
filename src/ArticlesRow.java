import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sound.midi.Synthesizer;

import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 * @author Ben Horn
 * @since 1/2018
 *
 */
public class ArticlesRow {
	public int num;
	public String headLine;
	public String subHeadLine;
	public String date;
	public String reporter;
	public String site;
	public String body;
	public ArrayList<CommentRow> comments;
	public int numOfWords;

	static int counter=1;

	public String getCommentsString(){
		return CommentRow.wireAllComments(this.comments);
	}

	public ArticlesRow(){
		this.num=counter;
		counter++;
		headLine="";
		subHeadLine="";
		date="";
		reporter="";
		site="";
		body="";
		comments=new ArrayList<CommentRow>() ;
		numOfWords=0;
	}


	public void WriteToFile() {
		XSSFSheet sheet = Main.workbook.getSheet("Articles");
		numOfWords=body.split(" ").length;
		String[] row = {num+"", site, date, reporter, numOfWords+"", headLine, subHeadLine, body, getCommentsString()};
		Funcs.StringArrToLastRow(row, sheet);

	}
	
	public static void WriteToFile(List<ArticlesRow> Reports) {
		for(int i=0; i<Reports.size(); i++){
			Reports.get(i).WriteToFile();
			CommentRow.WriteToFile(Reports.get(i).comments);
		}

	}














	/**
	 * This functions use to write to csv file, instead of using apache POI for excel
	 * @param writer
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

			Funcs.apeandComments(writer, getCommentsString());


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
		//		comments = Funcs.clean(getCommentsString);
		date = Funcs.clean(date);
	}

	




}
