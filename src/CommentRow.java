import java.io.FileWriter;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 * @author Ben Horn and Sefi Erlich
 * @since 1/2018
 *
 */
public class CommentRow {
	
	public int ArticleNum;
	public String site;
	public String talkbakist;
	public String headline;
	public String comment;
	public int convNum;
	
	
	
	public CommentRow(){
		ArticleNum=0;
		site="";
		talkbakist="";
		headline="";
		comment="";
		convNum=0;
	}


	public void WriteToFile() {
		XSSFSheet sheet = Main.workbook.getSheet("comments");
		String[] row = {ArticleNum+"", site, convNum+"" ,talkbakist, headline, comment};
		Funcs.StringArrToLastRow(row, sheet);
	}
	
	
	
	
	
	
	/**
	 * This functions use to write to csv file, instead of using apache POI for excel
	 */
	public void WriteToFile(FileWriter writer) {
		try {
			clean();
			writer.append(""+ArticleNum+',');
			writer.append(site+',');
			writer.append(""+convNum+',');
			writer.append(talkbakist+',');
			writer.append(headline+',');
			writer.append(comment+',');
			writer.append('\n');
			writer.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void clean(){
		headline = Funcs.clean(headline);
		talkbakist = Funcs.clean(talkbakist);
		comment = Funcs.clean(comment);
	}
	
	



	
}
