import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;

public class Frame extends JFrame {

    private JPanel contentPane;
    private static final String[] bezeichner = { "a", "b", "d", "l", "s", "e", "f" };
    private ArrayList<JLabel> labels;
    private ArrayList<JTextField> textFields;
    private JLabel lbl_preview_e, lbl_preview_f;
    private JButton btn_reset, btn_copy, btn_calc;
    private JTextPane tp_result;
    private static final Font tp_font = new Font(Font.SANS_SERIF, Font.PLAIN, 18);


    Frame() {

        this.contentPane = new JPanel();
        this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.contentPane.setLayout(null);

        this.labels = new ArrayList<>();
        this.textFields = new ArrayList<>();

        for(int i = 0; i < this.bezeichner.length; i++) {
            this.labels.add(new JLabel(this.bezeichner[i]));
            this.textFields.add(new JTextField());
        }
        this.setUpLabelsAndTextfields();

        this.lbl_preview_e = new JLabel();
        this.lbl_preview_e.setBounds(127, 242, 46, 14);
        this.contentPane.add(this.lbl_preview_e);
        this.lbl_preview_f = new JLabel();
        this.lbl_preview_f.setBounds(127, 282, 46, 14);
        this.contentPane.add(this.lbl_preview_f);


        this.btn_reset = new JButton("Reset");
        this.btn_reset.setBounds(20, 350, 106, 20);
        this.btn_reset.addActionListener(actionEvent -> {
            this.tp_result.setFocusable(false);
            this.tp_result.setText("");
            for (int i = 0; i < Frame.bezeichner.length; i++) {
                this.textFields.get(i).setText("");
            }
            this.lbl_preview_e.setText("");
            this.lbl_preview_f.setText("");
        });
        this.contentPane.add(this.btn_reset);

        this.btn_copy = new JButton("Kopieren");
        this.btn_copy.setBounds(20, 380, 106, 20);
        this.btn_copy.addActionListener(actionEvent -> {
            String clipBoard = "";
            clipBoard = "Eingegebene Werte: \n";
            for (int i = 0; i < Frame.bezeichner.length; i++) {
                clipBoard += Frame.bezeichner[i] + " = " + this.textFields.get(i).getText();
            }
            this.tp_result.setFont(new Font(Font.SANS_SERIF, 3,25));
            clipBoard += "\n \n" + this.tp_result.getText();
            this.tp_result.setFont(this.tp_font);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                    new StringSelection(clipBoard), null);
        });
        this.contentPane.add(this.btn_copy);

        this.btn_calc = new JButton("Berechnen");
        this.btn_calc.setBounds(20, 320, 106, 20);
        this.btn_calc.addActionListener(actionEvent -> {
            double a = Double.valueOf(this.textFields.get(0).getText());
            double b = Double.valueOf(this.textFields.get(1).getText());
            double d = Double.valueOf(this.textFields.get(2).getText());
            double l = Double.valueOf(this.textFields.get(3).getText());
            double s = Double.valueOf(this.textFields.get(4).getText());
            double e = Double.valueOf(this.textFields.get(5).getText());
            double f = Double.valueOf(this.textFields.get(6).getText());
            Kanal kanal = new Kanal(a, b, d, l, s, e, f);
            this.tp_result.setText(kanal.calculateAll());
        });
        this.contentPane.add(this.btn_calc);


        this.tp_result = new JTextPane();
        this.tp_result.setFont(this.tp_font);
        tp_result.setBounds(165, 40, 560, 360);
        tp_result.setFocusable(false);
        tp_result.setEditable(false);
        contentPane.add(this.tp_result);

        this.setContentPane(this.contentPane);
        this.setTitle("Kanal 2018");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setUpLabelsAndTextfields() {
        int yStartPoint = 40;
        for (int i = 0; i < this.bezeichner.length && yStartPoint<=280; i++) {
            this.labels.get(i).setBounds(20, yStartPoint, 46, 14);
            this.contentPane.add(this.labels.get(i));
            this.textFields.get(i).setBounds(40, yStartPoint, 86,20);
            this.contentPane.add(this.textFields.get(i));
            yStartPoint += 40;
        }
    }
}
