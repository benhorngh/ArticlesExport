import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class mainScreen {

	private JFrame frame;
	private JTextField textField_2;
	private JTextField textField_4;
	private Choice choice ;
	private JSpinner spinner;
	private JCheckBox chckbxYnet ;
	private JCheckBox chckbxTheMarker;
	private JCheckBox chckbxBloomberg;
	private JCheckBox chckbxReuters;
	private JCheckBox chckbxGlobes;
	private JTextPane txtpnStartDate;
	private JTextPane txtpnEndDate;
	private JTextField txtstart;
	private JTextField txtend;

	private static JButton btnStart;

	private static JTextArea log;
	private JCheckBox chckbxCnn;
	private JCheckBox chckbxToTxt;
	private JTextPane txtpnState;
	private JScrollPane scrollPane;
	private JCheckBox chckbxBbc;
	private JCheckBox chckbxUsatoday;
	private JPanel panel_2;



	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainScreen window = new mainScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public mainScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {


		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 840, 465);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(200dlu;min):grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("min(70dlu;default):grow"),},
				new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		frame.getContentPane().add(panel_1, "4, 2, 2, 1, fill, fill");
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(60dlu;min):grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(50dlu;default):grow"),},
				new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));

		JTextPane txtpnTextToSearch_1 = new JTextPane();
		txtpnTextToSearch_1.setDisabledTextColor(Color.BLACK);
		txtpnTextToSearch_1.setCaretColor(Color.BLACK);
		txtpnTextToSearch_1.setBackground(new Color(255, 255, 255));
		txtpnTextToSearch_1.setForeground(new Color(0, 0, 0));
		txtpnTextToSearch_1.setFont(new Font("Arial", Font.PLAIN, 11));
		txtpnTextToSearch_1.setEditable(false);
		txtpnTextToSearch_1.setEnabled(false);
		txtpnTextToSearch_1.setText("Text to search");


		panel_1.add(txtpnTextToSearch_1, "2, 2, center, center");

		textField_4 = new JTextField();
		panel_1.add(textField_4, "3, 2, 2, 1, fill, default");
		textField_4.setColumns(10);


		textField_4.setToolTipText("Text for the search field in the site");



		JTextPane txtpnTextToCompare_1 = new JTextPane();
		txtpnTextToCompare_1.setDisabledTextColor(Color.BLACK);
		txtpnTextToCompare_1.setCaretColor(Color.BLACK);
		txtpnTextToCompare_1.setBackground(new Color(255, 255, 255));
		txtpnTextToCompare_1.setForeground(Color.BLACK);
		txtpnTextToCompare_1.setFont(new Font("Arial", Font.PLAIN, 11));
		txtpnTextToCompare_1.setEditable(false);
		txtpnTextToCompare_1.setEnabled(false);
		txtpnTextToCompare_1.setText("Keywords");
		panel_1.add(txtpnTextToCompare_1, "2, 4, center, center");

		textField_2 = new JTextField();
		panel_1.add(textField_2, "3, 4, 2, 1, fill, default");
		textField_2.setColumns(10);
		textField_2.setEditable(false);

		textField_2.setToolTipText("Keywords to compare with, inside the article.");

		JTextPane txtpnNumberOfReports = new JTextPane();
		txtpnNumberOfReports.setDisabledTextColor(Color.BLACK);
		txtpnNumberOfReports.setCaretColor(Color.BLACK);
		txtpnNumberOfReports.setBackground(new Color(255, 255, 255));
		txtpnNumberOfReports.setForeground(new Color(0, 0, 0));
		txtpnNumberOfReports.setFont(new Font("Arial", Font.PLAIN, 11));
		txtpnNumberOfReports.setEditable(false);
		txtpnNumberOfReports.setEnabled(false);
		txtpnNumberOfReports.setText("Number of reports");
		panel_1.add(txtpnNumberOfReports, "2, 6, center, center");

		spinner = new JSpinner();
		panel_1.add(spinner, "3, 6, 2, 1");

		txtpnStartDate = new JTextPane();
		txtpnStartDate.setDisabledTextColor(Color.BLACK);
		txtpnStartDate.setBackground(new Color(255, 255, 255));
		txtpnStartDate.setFont(new Font("Arial", Font.PLAIN, 11));
		txtpnStartDate.setForeground(Color.WHITE);
		txtpnStartDate.setEditable(false);
		txtpnStartDate.setEnabled(false);
		txtpnStartDate.setText("start date (dd.mm.yyyy)");
		panel_1.add(txtpnStartDate, "2, 10, center, center");

		txtstart = new JTextField();
		txtstart.setText("");
		panel_1.add(txtstart, "3, 10, 2, 1, fill, default");
		txtstart.setColumns(10);

		txtpnEndDate = new JTextPane();
		txtpnEndDate.setDisabledTextColor(Color.BLACK);
		txtpnEndDate.setBackground(new Color(255, 255, 255));
		txtpnEndDate.setFont(new Font("Arial", Font.PLAIN, 11));
		txtpnEndDate.setForeground(Color.WHITE);
		txtpnEndDate.setEditable(false);
		txtpnEndDate.setEnabled(false);
		txtpnEndDate.setText("end date (dd.mm.yyyy)");
		panel_1.add(txtpnEndDate, "2, 12, center, center");

		txtend = new JTextField();
		txtend.setText("");
		panel_1.add(txtend, "3, 12, 2, 1, fill, default");
		txtend.setColumns(10);


		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		frame.getContentPane().add(panel, "6, 2, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(90px;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.GROWING_BUTTON_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
				new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));

		chckbxYnet = new JCheckBox("Ynet");
		chckbxYnet.setFocusable(false);
		chckbxYnet.setBackground(Color.WHITE);
		panel.add(chckbxYnet, "2, 2");

		chckbxTheMarker = new JCheckBox("The Marker");
		chckbxTheMarker.setFocusable(false);
		chckbxTheMarker.setBackground(Color.WHITE);
		panel.add(chckbxTheMarker, "2, 4");

		chckbxGlobes = new JCheckBox("Globes");
		chckbxGlobes.setFocusable(false);
		chckbxGlobes.setBackground(Color.WHITE);
		panel.add(chckbxGlobes, "2, 6");

		chckbxReuters = new JCheckBox("Reuters");
		chckbxReuters.setFocusable(false);
		chckbxReuters.setBackground(Color.WHITE);
		panel.add(chckbxReuters, "2, 8");




		chckbxBloomberg = new JCheckBox("Bloomberg");
		chckbxBloomberg.setFocusable(false);
		chckbxBloomberg.setBackground(Color.WHITE);
		panel.add(chckbxBloomberg, "2, 10");

		chckbxCnn = new JCheckBox("CNN");
		chckbxCnn.setBackground(Color.WHITE);
		chckbxCnn.setFocusable(false);
		panel.add(chckbxCnn, "2, 12");

		chckbxBbc = new JCheckBox("BBC");
		chckbxBbc.setBackground(Color.WHITE);
		chckbxBbc.setFocusable(false);
		panel.add(chckbxBbc, "2, 14");

		chckbxUsatoday = new JCheckBox("USAtoday");
		chckbxUsatoday.setBackground(Color.WHITE);
		panel.add(chckbxUsatoday, "2, 16");


		JTextPane jtp = new JTextPane();
		frame.getContentPane().add(jtp, "2, 4");
		jtp.setDisabledTextColor(Color.BLACK);
		jtp.setCaretColor(Color.BLACK);
		jtp.setBackground(new Color(255, 255, 255));
		jtp.setForeground(new Color(0, 0, 0));
		jtp.setFont(new Font("Arial", Font.PLAIN, 11));
		jtp.setEditable(false);
		jtp.setEnabled(false);
		jtp.setText("log:");

		scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, "4, 4, 1, 5, fill, fill");

		log = new JTextArea(8, 40);
		scrollPane.setViewportView(log);
		log.setAlignmentY(Component.TOP_ALIGNMENT);
		log.setAlignmentX(Component.LEFT_ALIGNMENT);
		log.setDisabledTextColor(Color.BLACK);
		log.setFont(new Font("Arial", Font.PLAIN, 12));
		log.setWrapStyleWord(true);
		log.setFocusable(false);
		log.setEnabled(false);
		log.setEditable(false);
		//		log.setDisabledTextColor(SystemColor.activeCaption);
		log.setText("");

		panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		frame.getContentPane().add(panel_2, "6, 4, 1, 5, fill, fill");
		panel_2.setLayout(null);



		choice = new Choice();
		choice.setBounds(100, 10, 158, 20);
		panel_2.add(choice);
		choice.setFocusable(false);
		choice.add("Regular");
		choice.add("Title");
		choice.add("Body");
		choice.add("Comments");
		choice.add("Everywhere");
		// Add item listener
		choice.addItemListener(new ItemListener(){

			@SuppressWarnings("deprecation")
			public void itemStateChanged(ItemEvent arg0) {
				if(choice.getSelectedItem().equals("Regular")){
					textField_2.setEditable(false);
				}
				else textField_2.setEditable(true);
			}
		});

		txtpnState = new JTextPane();
		txtpnState.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		txtpnState.setAlignmentX(Component.RIGHT_ALIGNMENT);
		txtpnState.setBounds(10, 10, 121, 20);
		panel_2.add(txtpnState);
		txtpnState.setText("state");

		txtpnState.setToolTipText("type of search. if set to body, the program ill search the keywords in the body of the article."+'\n'+"regular will ignore the keywords and take all articles in result page.");

		chckbxToTxt = new JCheckBox("to txt");
		chckbxToTxt.setBounds(100, 36, 121, 23);
		panel_2.add(chckbxToTxt);
		chckbxToTxt.setFocusable(false);
		chckbxToTxt.setBackground(Color.WHITE);

		btnStart = new JButton("Start");
		btnStart.setBounds(144, 92, 121, 23);
		panel_2.add(btnStart);
		btnStart.setFocusable(false);


		btnStart.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)  {

				btnStart.setEnabled(false);


				Thread thread = new Thread() {
					public void run(){

						log.setText("");

						String tts = textField_4.getText();
						//						String ttse = textField_3.getText();
						String ttc = textField_2.getText();
						//						String ttce = textField_1.getText();
						String sdt = txtstart.getText();
						String edt = txtend.getText();


						String selected = choice.getSelectedItem();

						int noa = (Integer) spinner.getValue();
						SearchState state = SearchState.regular;
						if(selected.equals("Regular"))
							state = SearchState.regular;
						if(selected.equals("Title"))
							state = SearchState.headline;
						if(selected.equals("Body"))
							state = SearchState.body;
						if(selected.equals("Comments"))
							state = SearchState.comment;
						if(selected.equals("Everywhere"))
							state = SearchState.everywhere;

						boolean totxt = chckbxToTxt.isSelected();

						boolean[] sites = {chckbxYnet.isSelected()
								,chckbxTheMarker.isSelected()
								,chckbxBloomberg.isSelected()
								,chckbxReuters.isSelected()
								,chckbxGlobes.isSelected()
								,chckbxCnn.isSelected()
								,chckbxBbc.isSelected()
								,chckbxUsatoday.isSelected()
						};


						System.out.println("tts: "+tts);
						//						System.out.println("ttse: "+ttse);
						System.out.println("ttc: "+ttc);
						//						System.out.println("ttce: "+ttce);
						System.out.println("stat: "+selected);
						System.out.println("sdt: "+sdt);
						System.out.println("edt: "+edt);
						System.out.println(Arrays.toString(sites));


						//						Main.starter(tts, ttse, ttc, ttce, state, sdt, edt, noa, sites);
						Main.starter(tts, tts, ttc, ttc, state, sdt, edt, noa, sites, totxt);
					}
				};
				thread.start();
			}
		});

	}

	public static void addToLog(String message){
		if(log != null){

			String past = log.getText();
			String nm="";
			if(past == null || past.isEmpty())
				nm = message;
			else nm = message + '\n' + past;

			log.setText(nm);

			if(message.contains("Done"))
				btnStart.setEnabled(true);	

			if(message.contains("error"))
				btnStart.setEnabled(true);	
		}
	}



	private static void addPopup(Component component, final JPopupMenu popup) {
	}
}
