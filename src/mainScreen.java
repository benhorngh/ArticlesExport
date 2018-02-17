import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Component;
import javax.swing.Box;
import java.awt.BorderLayout;
import java.awt.TextArea;
import javax.swing.JList;
import java.awt.Color;
import javax.swing.JSpinner;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import javax.swing.JFormattedTextField;
import javax.swing.JMenuItem;
import javax.swing.AbstractListModel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSlider;
import javax.swing.JPopupMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Choice;

public class mainScreen {

	private JFrame frame;
//	private JTextField txtField_1;
//	private JTextField txtField_2;
//	private JTextField txtField_3;
//	private JTextField txtField_4;

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


		 JTextField txtField_1;
		 JTextField txtField_2;
		 JTextField txtField_3;
		 JTextField txtField_4;

		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("308px"),
				FormSpecs.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("112px"),},
			new RowSpec[] {
				RowSpec.decode("194px"),}));
		
		JPanel panel_6 = new JPanel();
		frame.getContentPane().add(panel_6, "3, 1, fill, fill");
		panel_6.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,},
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
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		JCheckBox chckbxYnet = new JCheckBox("Ynet");
		panel_6.add(chckbxYnet, "2, 2");
		
		JCheckBox chckbxTheMarker = new JCheckBox("The Marker");
		panel_6.add(chckbxTheMarker, "2, 4");
		
		JCheckBox chckbxBloomberg = new JCheckBox("Bloomberg");
		panel_6.add(chckbxBloomberg, "2, 6");
		
		JCheckBox chckbxReuters = new JCheckBox("Reuters");
		panel_6.add(chckbxReuters, "2, 8");
		
		JCheckBox chckbxGlobes = new JCheckBox("Globes");
		panel_6.add(chckbxGlobes, "2, 10");
		
		JPanel panel_7 = new JPanel();
		frame.getContentPane().add(panel_7, "1, 1, fill, fill");
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(panel_7, popupMenu);
		panel_7.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
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
				RowSpec.decode("default:grow"),}));
		
		JTextPane txtpnTextToSearch_1 = new JTextPane();
		txtpnTextToSearch_1.setEditable(false);
		txtpnTextToSearch_1.setText("text to search");
		panel_7.add(txtpnTextToSearch_1, "2, 2, fill, fill");
		
		txtField_1 = new JTextField();
		txtField_1.setText("field1");
		panel_7.add(txtField_1, "4, 2, fill, default");
		txtField_1.setColumns(10);
		
		JTextPane txtpnTextToSearch = new JTextPane();
		txtpnTextToSearch.setEditable(false);
		txtpnTextToSearch.setText("text to search- english");
		panel_7.add(txtpnTextToSearch, "2, 4, fill, fill");
		
		txtField_2 = new JTextField();
		txtField_2.setText("field2");
		panel_7.add(txtField_2, "4, 4, fill, default");
		txtField_2.setColumns(10);
		
		JTextPane txtpnTextToCompare_1 = new JTextPane();
		txtpnTextToCompare_1.setEditable(false);
		txtpnTextToCompare_1.setText("text to compare");
		panel_7.add(txtpnTextToCompare_1, "2, 6, fill, fill");
		
		txtField_3 = new JTextField();
		txtField_3.setText("field3");
		panel_7.add(txtField_3, "4, 6, fill, default");
		txtField_3.setColumns(10);
		
		JTextPane txtpnTextToCompare = new JTextPane();
		txtpnTextToCompare.setEditable(false);
		txtpnTextToCompare.setText("text to compare- english");
		panel_7.add(txtpnTextToCompare, "2, 8, fill, fill");
		
		txtField_4 = new JTextField();
		txtField_4.setText("field4");
		panel_7.add(txtField_4, "4, 8, fill, default");
		txtField_4.setColumns(10);
		
		JTextPane txtpnNomberOfReports = new JTextPane();
		txtpnNomberOfReports.setEditable(false);
		txtpnNomberOfReports.setText("nomber of reports");
		panel_7.add(txtpnNomberOfReports, "2, 10, fill, fill");
		
		JSpinner spinner_1 = new JSpinner();
		panel_7.add(spinner_1, "4, 10");
	}
	
	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
