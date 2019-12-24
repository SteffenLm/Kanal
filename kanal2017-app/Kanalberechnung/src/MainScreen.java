import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class MainScreen extends JFrame {

	/**
	 * Do not know, which Java Program needs a serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainScreen frame = new MainScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainScreen() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 760, 470);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Kanalberechnung");
		
		//Create Labels a
		JLabel lbl_a = new JLabel("a");
		JLabel lbl_b = new JLabel("b");		
		JLabel lbl_d = new JLabel("d");		
		JLabel lbl_l = new JLabel("l");
		JLabel lbl_s = new JLabel("s");
		JLabel lbl_e = new JLabel("e");		
		JLabel lbl_f = new JLabel("f");
		
		//Create Textfields and set text for user input
		JTextField tf_a = new JTextField("");
		JTextField tf_b = new JTextField("");
		JTextField tf_d = new JTextField("");
		JTextField tf_l = new JTextField("");
		JTextField tf_s = new JTextField("");
		JTextField tf_e = new JTextField("");
		JTextField tf_f = new JTextField("");
		
		//Create Preview Labels and set Text, which show precalculated values of e and f
		JLabel lbl_e_preview = new JLabel("");		
		JLabel lbl_f_preview = new JLabel("");
				
		//Create Buttons and set Text.
		JButton btn_reset = new JButton("Reset");
		JButton btn_copy = new JButton("Kopieren");
		JButton btn_calc = new JButton("Berechnen");
		
		//Create Textpane
		JTextPane tp_result = new JTextPane();
		
		//Set Position of Labels (First Column)
		lbl_a.setBounds(20, 40, 46, 14);
		lbl_b.setBounds(20, 80, 46, 14);
		lbl_d.setBounds(20, 120, 46, 14);
		lbl_l.setBounds(20, 160, 46, 14);		
		lbl_s.setBounds(20, 200, 46, 14);		
		lbl_e.setBounds(20, 240, 46, 14);		
		lbl_f.setBounds(20, 280, 46, 14);		
		
		//Set Postions of TextFields (Second Column)
		tf_a.setBounds(40, 40, 86, 20);
		tf_b.setBounds(40, 80, 86, 20);
		tf_d.setBounds(40, 120, 86, 20);
		tf_l.setBounds(40, 160, 86, 20);
		tf_s.setBounds(40, 200, 86, 20);
		tf_e.setBounds(40, 240, 86, 20);
		tf_f.setBounds(40, 280, 86, 20);
		
		//Set Postions of Preview Labels
		lbl_e_preview.setBounds(127, 242, 46, 14);
		lbl_f_preview.setBounds(127, 282, 46, 14);
		
		//Set Positions of Buttons
		btn_calc.setBounds(20, 320, 106, 20);
		btn_reset.setBounds(20, 350, 106, 20);
		btn_copy.setBounds(20, 380, 106, 20);
		
		//Create font for TextPane
		Font f = new Font(Font.SANS_SERIF, 3,18);
		
		//Set Position and Properties for TextPane
		tp_result.setBounds(165, 40, 560, 360);
		tp_result.setFocusable(false);
		tp_result.setEditable(false);
		tp_result.setFont(f);
		
		//Add Labels to Content Pane
		contentPane.add(lbl_a);
		contentPane.add(lbl_b);
		contentPane.add(lbl_d);
		contentPane.add(lbl_l);
		contentPane.add(lbl_s);
		contentPane.add(lbl_e);
		contentPane.add(lbl_f);
		
		//Add Textfields to Content Pane
		contentPane.add(tf_a);
		contentPane.add(tf_b);
		contentPane.add(tf_d);
		contentPane.add(tf_l);
		contentPane.add(tf_s);
		contentPane.add(tf_e);
		contentPane.add(tf_f);
		
		//Add Preview Labels to Content Pane
		contentPane.add(lbl_e_preview);
		contentPane.add(lbl_f_preview);
		
		//Add Buttons to Content Pane
		contentPane.add(btn_calc);
		contentPane.add(btn_reset);
		contentPane.add(btn_copy);
		
		//TextPane to Content Pane
		contentPane.add(tp_result);
		
		//Listeners
		//Listener for Textfields
		//Triggers if Textfield looses the focus.
		tf_b.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if(tf_d.getText() != "" && tf_b.getText() != "")
				{
					//Define and Initialize variables
					double d, b, e;
					d = 0;
					b = 0;
					e = 0;
					//Set Values of Variables from Textfields
					b = Double.parseDouble(tf_b.getText());
					d = Double.parseDouble(tf_d.getText());
					//Calculate the preview Value
					e = (d-b)/2;
					//Set calculated Value to Preview Label
					lbl_e_preview.setText(String.valueOf(e));
				}
			}
		});
		tf_d.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (tf_a.getText() != "" && tf_b.getText() != "") {
					// e = (d-b)/2
					// f = (d-a)/2

					double d, b, a, temp;
					String t_d;
					d = 0;
					b = 0;
					a = 0;
					temp = 0;

					t_d = tf_d.getText();

					d = Double.parseDouble(t_d);
					b = Double.parseDouble(tf_b.getText());
					a = Double.parseDouble(tf_a.getText());
					temp = (d - b) / 2;
					lbl_e_preview.setText(String.valueOf(temp));
					temp = (d - a) / 2;
					lbl_f_preview.setText(String.valueOf(temp));
				}
			}
		});
		
		//Listener for Buttons
		btn_reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tp_result.setFocusable(false);
				tp_result.setText("");
				tf_a.setText("");
				tf_b.setText("");
				tf_d.setText("");
				tf_l.setText("");
				tf_s.setText("");
				tf_e.setText("");
				tf_f.setText("");
				lbl_e_preview.setText("");
				lbl_f_preview.setText("");

			}
		});
		btn_copy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Font f = new Font(Font.SANS_SERIF, 3,25);
				String print = new String();
				tp_result.setFont(f);
				print = "Eingegebene Werte: \n";
				print += "a = " + tf_a.getText() + "  b = " + tf_b.getText() + "  d = " + tf_d.getText() + "  l = " + tf_l.getText() + "  s = " + tf_s.getText() + "  e = " + tf_e.getText() + "  f = " + tf_f.getText() + "\n\n";
				print += tp_result.getText();
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                        new StringSelection(print), null);
				Font f2 = new Font(Font.SANS_SERIF, 3,18);
				tp_result.setFont(f2);
			}
		});
		btn_calc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				tp_result.setEditable(true);
				berechnungen calculator = new berechnungen(Double.parseDouble(tf_a.getText()),
						Double.parseDouble(tf_b.getText()), Double.parseDouble(tf_d.getText()),
						Double.parseDouble(tf_l.getText()), Double.parseDouble(tf_s.getText()),
						Double.parseDouble(tf_e.getText()), Double.parseDouble(tf_f.getText()));
				tp_result.setText(calculator.output());
				tp_result.setEditable(false);
				tp_result.setFocusable(true);
			}
		});
	}
}
