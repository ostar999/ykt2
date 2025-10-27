package demo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Icon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import net.sourceforge.pinyin4j.PinyinHelper;

/* loaded from: classes8.dex */
public class Pinyin4jAppletDemo extends JApplet {
    private static final Dimension APP_SIZE = new Dimension(600, 400);
    private static String appName = "pinyin4j-2.0.0 applet demo";
    private static final long serialVersionUID = -1934962385592030162L;
    private JPanel jContentPane = null;
    private JTabbedPane jTabbedPane = null;
    private JPanel formattedCharPanel = null;
    private JPanel optionPanel = null;
    private JButton convertButton = null;
    private JPanel buttonPanel = null;
    private JTextArea formattedOutputField = null;
    private JComboBox toneTypesComboBox = null;
    private JComboBox vCharTypesComboBox = null;
    private JComboBox caseTypesComboBox = null;
    String[] caseTypes = {"LOWERCASE", "UPPERCASE"};
    String[] vCharTypes = {"WITH_U_AND_COLON", "WITH_V", "WITH_U_UNICODE"};
    String[] toneTypes = {"WITH_TONE_NUMBER", "WITHOUT_TONE", "WITH_TONE_MARK"};
    private JLabel toneLabel = null;
    private JLabel charLabel = null;
    private JTextField charTextField = null;
    private JPanel unformattedCharPanel = null;
    private JLabel unformattedHanyuPinyinLabel = null;
    private JTextArea unformattedHanyuPinyinTextArea = null;
    private JPanel unformattedHanyuPinyinPanel = null;
    private JPanel unformattedTongyongPinyinPanel = null;
    private JLabel unformattedTongyongPinyinLabel = null;
    private JTextArea unformattedTongyongPinyinTextArea = null;
    private JPanel unformattedWadePinyinPanel = null;
    private JLabel unformattedWadePinyinLabel = null;
    private JTextArea unformattedWadePinyinTextArea = null;
    private JPanel unformattedMPS2PinyinPanel = null;
    private JLabel unformattedMPS2PinyinLabel = null;
    private JTextArea unformattedMPS2PinyinTextArea = null;
    private JPanel unformattedYalePinyinPanel = null;
    private JLabel unformattedYalePinyinLabel = null;
    private JTextArea unformattedYalePinyinTextArea = null;
    private JPanel unformattedGwoyeuRomatzyhPanel = null;
    private JLabel unformattedGwoyeuRomatzyhLabel = null;
    private JTextArea unformattedGwoyeuRomatzyhTextArea = null;
    private JScrollPane unformattedMPS2PinyinScrollPane = null;
    private JScrollPane unformattedHanyuPinyinScrollPane = null;
    private JScrollPane unformattedTongyongPinyinScrollPane = null;
    private JScrollPane unformattedWadePinyinScrollPane = null;
    private JScrollPane unformattedYalePinyinScrollPane = null;
    private JScrollPane unformattedGwoyeuRomatzyhScrollPane = null;

    public Pinyin4jAppletDemo() {
        init();
    }

    private JPanel getButtonPanel() {
        if (this.buttonPanel == null) {
            JPanel jPanel = new JPanel();
            this.buttonPanel = jPanel;
            jPanel.add(getConvertButton(), (Object) null);
        }
        return this.buttonPanel;
    }

    private JComboBox getCaseTypesComboBox() {
        if (this.caseTypesComboBox == null) {
            this.caseTypesComboBox = new JComboBox(this.caseTypes);
        }
        return this.caseTypesComboBox;
    }

    private JTextField getCharTextField() {
        if (this.charTextField == null) {
            JTextField jTextField = new JTextField();
            this.charTextField = jTextField;
            jTextField.setFont(new Font("Dialog", 0, 12));
            this.charTextField.setText("和");
            this.charTextField.setPreferredSize(new Dimension(26, 20));
        }
        return this.charTextField;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getChineseCharText() {
        return this.charTextField.getText();
    }

    private JButton getConvertButton() {
        if (this.convertButton == null) {
            JButton jButton = new JButton();
            this.convertButton = jButton;
            jButton.setText("Convert to Pinyin");
            this.convertButton.addActionListener(new ActionListener() { // from class: demo.Pinyin4jAppletDemo.2
                private String concatPinyinStringArray(String[] strArr) {
                    StringBuffer stringBuffer = new StringBuffer();
                    if (strArr != null && strArr.length > 0) {
                        for (String str : strArr) {
                            stringBuffer.append(str);
                            stringBuffer.append(System.getProperty("line.separator"));
                        }
                    }
                    return stringBuffer.toString();
                }

                /* JADX WARN: Can't wrap try/catch for region: R(14:0|2|(1:4)(2:6|(1:8)(12:9|(1:11)|12|(1:14)(2:16|(1:18)(9:19|(1:21)|22|(1:24)(6:26|(1:28)|35|29|33|34)|25|35|29|33|34))|15|22|(0)(0)|25|35|29|33|34))|5|12|(0)(0)|15|22|(0)(0)|25|35|29|33|34) */
                /* JADX WARN: Code restructure failed: missing block: B:31:0x005a, code lost:
                
                    r7 = move-exception;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:32:0x005b, code lost:
                
                    r7.printStackTrace();
                    r7 = null;
                 */
                /* JADX WARN: Removed duplicated region for block: B:14:0x002c  */
                /* JADX WARN: Removed duplicated region for block: B:16:0x0032  */
                /* JADX WARN: Removed duplicated region for block: B:24:0x0048  */
                /* JADX WARN: Removed duplicated region for block: B:26:0x004e  */
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                private void updateFormattedTextField(char r7, java.lang.String r8, java.lang.String r9, java.lang.String r10) {
                    /*
                        r6 = this;
                        net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat r0 = new net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat
                        r0.<init>()
                        demo.Pinyin4jAppletDemo r1 = demo.Pinyin4jAppletDemo.this
                        java.lang.String[] r1 = r1.toneTypes
                        r2 = 0
                        r3 = r1[r2]
                        r4 = 2
                        r5 = 1
                        if (r3 != r8) goto L16
                        net.sourceforge.pinyin4j.format.HanyuPinyinToneType r8 = net.sourceforge.pinyin4j.format.HanyuPinyinToneType.WITH_TONE_NUMBER
                    L12:
                        r0.setToneType(r8)
                        goto L24
                    L16:
                        r3 = r1[r5]
                        if (r3 != r8) goto L1d
                        net.sourceforge.pinyin4j.format.HanyuPinyinToneType r8 = net.sourceforge.pinyin4j.format.HanyuPinyinToneType.WITHOUT_TONE
                        goto L12
                    L1d:
                        r1 = r1[r4]
                        if (r1 != r8) goto L24
                        net.sourceforge.pinyin4j.format.HanyuPinyinToneType r8 = net.sourceforge.pinyin4j.format.HanyuPinyinToneType.WITH_TONE_MARK
                        goto L12
                    L24:
                        demo.Pinyin4jAppletDemo r8 = demo.Pinyin4jAppletDemo.this
                        java.lang.String[] r8 = r8.vCharTypes
                        r1 = r8[r2]
                        if (r1 != r9) goto L32
                        net.sourceforge.pinyin4j.format.HanyuPinyinVCharType r8 = net.sourceforge.pinyin4j.format.HanyuPinyinVCharType.WITH_U_AND_COLON
                    L2e:
                        r0.setVCharType(r8)
                        goto L40
                    L32:
                        r1 = r8[r5]
                        if (r1 != r9) goto L39
                        net.sourceforge.pinyin4j.format.HanyuPinyinVCharType r8 = net.sourceforge.pinyin4j.format.HanyuPinyinVCharType.WITH_V
                        goto L2e
                    L39:
                        r8 = r8[r4]
                        if (r8 != r9) goto L40
                        net.sourceforge.pinyin4j.format.HanyuPinyinVCharType r8 = net.sourceforge.pinyin4j.format.HanyuPinyinVCharType.WITH_U_UNICODE
                        goto L2e
                    L40:
                        demo.Pinyin4jAppletDemo r8 = demo.Pinyin4jAppletDemo.this
                        java.lang.String[] r8 = r8.caseTypes
                        r9 = r8[r2]
                        if (r9 != r10) goto L4e
                        net.sourceforge.pinyin4j.format.HanyuPinyinCaseType r8 = net.sourceforge.pinyin4j.format.HanyuPinyinCaseType.LOWERCASE
                    L4a:
                        r0.setCaseType(r8)
                        goto L55
                    L4e:
                        r8 = r8[r5]
                        if (r8 != r10) goto L55
                        net.sourceforge.pinyin4j.format.HanyuPinyinCaseType r8 = net.sourceforge.pinyin4j.format.HanyuPinyinCaseType.UPPERCASE
                        goto L4a
                    L55:
                        java.lang.String[] r7 = net.sourceforge.pinyin4j.PinyinHelper.toHanyuPinyinStringArray(r7, r0)     // Catch: net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination -> L5a
                        goto L5f
                    L5a:
                        r7 = move-exception
                        r7.printStackTrace()
                        r7 = 0
                    L5f:
                        java.lang.String r7 = r6.concatPinyinStringArray(r7)
                        demo.Pinyin4jAppletDemo r8 = demo.Pinyin4jAppletDemo.this
                        javax.swing.JTextArea r8 = demo.Pinyin4jAppletDemo.access$1000(r8)
                        r8.setText(r7)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: demo.Pinyin4jAppletDemo.AnonymousClass2.updateFormattedTextField(char, java.lang.String, java.lang.String, java.lang.String):void");
                }

                private void updateUnformattedTextField(char c3) {
                    Pinyin4jAppletDemo.this.unformattedHanyuPinyinTextArea.setText(concatPinyinStringArray(PinyinHelper.toHanyuPinyinStringArray(c3)));
                    Pinyin4jAppletDemo.this.unformattedTongyongPinyinTextArea.setText(concatPinyinStringArray(PinyinHelper.toTongyongPinyinStringArray(c3)));
                    Pinyin4jAppletDemo.this.unformattedWadePinyinTextArea.setText(concatPinyinStringArray(PinyinHelper.toWadeGilesPinyinStringArray(c3)));
                    Pinyin4jAppletDemo.this.unformattedMPS2PinyinTextArea.setText(concatPinyinStringArray(PinyinHelper.toMPS2PinyinStringArray(c3)));
                    Pinyin4jAppletDemo.this.unformattedYalePinyinTextArea.setText(concatPinyinStringArray(PinyinHelper.toYalePinyinStringArray(c3)));
                    Pinyin4jAppletDemo.this.unformattedGwoyeuRomatzyhTextArea.setText(concatPinyinStringArray(PinyinHelper.toGwoyeuRomatzyhStringArray(c3)));
                }

                public void actionPerformed(ActionEvent actionEvent) {
                    char cCharAt = Pinyin4jAppletDemo.this.getChineseCharText().charAt(0);
                    updateUnformattedTextField(cCharAt);
                    updateFormattedTextField(cCharAt, (String) Pinyin4jAppletDemo.this.toneTypesComboBox.getSelectedItem(), (String) Pinyin4jAppletDemo.this.vCharTypesComboBox.getSelectedItem(), (String) Pinyin4jAppletDemo.this.caseTypesComboBox.getSelectedItem());
                }
            });
        }
        return this.convertButton;
    }

    private JPanel getFormattedCharPanel() {
        if (this.formattedCharPanel == null) {
            JPanel jPanel = new JPanel();
            this.formattedCharPanel = jPanel;
            jPanel.setLayout(new BorderLayout());
            this.formattedCharPanel.add(getFormattedOutputField(), "Center");
        }
        return this.formattedCharPanel;
    }

    private JTextArea getFormattedOutputField() {
        if (this.formattedOutputField == null) {
            JTextArea jTextArea = new JTextArea();
            this.formattedOutputField = jTextArea;
            jTextArea.setEditable(false);
        }
        return this.formattedOutputField;
    }

    private JPanel getJContentPane() {
        if (this.jContentPane == null) {
            JPanel jPanel = new JPanel();
            this.jContentPane = jPanel;
            jPanel.setLayout(new BorderLayout());
            this.jContentPane.add(getJTabbedPane(), "Center");
            this.jContentPane.add(getOptionPanel(), "North");
            this.jContentPane.add(getButtonPanel(), "South");
        }
        return this.jContentPane;
    }

    private JTabbedPane getJTabbedPane() {
        if (this.jTabbedPane == null) {
            JTabbedPane jTabbedPane = new JTabbedPane();
            this.jTabbedPane = jTabbedPane;
            jTabbedPane.addTab("Unformatted Chinese Romanization Systems", (Icon) null, getUnformattedCharPanel(), (String) null);
            this.jTabbedPane.addTab("Formatted Hanyu Pinyin", (Icon) null, getFormattedCharPanel(), (String) null);
        }
        return this.jTabbedPane;
    }

    private JPanel getOptionPanel() {
        if (this.optionPanel == null) {
            JLabel jLabel = new JLabel();
            this.charLabel = jLabel;
            jLabel.setText("Input Chinese:");
            JLabel jLabel2 = new JLabel();
            this.toneLabel = jLabel2;
            jLabel2.setText(" Format:");
            JPanel jPanel = new JPanel();
            this.optionPanel = jPanel;
            jPanel.setPreferredSize(new Dimension(640, 34));
            this.optionPanel.add(this.charLabel, (Object) null);
            this.optionPanel.add(getCharTextField(), (Object) null);
            this.optionPanel.add(this.toneLabel, (Object) null);
            this.optionPanel.add(getToneTypesComboBox(), (Object) null);
            this.optionPanel.add(getVCharTypesComboBox(), (Object) null);
            this.optionPanel.add(getCaseTypesComboBox(), (Object) null);
        }
        return this.optionPanel;
    }

    private JComboBox getToneTypesComboBox() {
        if (this.toneTypesComboBox == null) {
            JComboBox jComboBox = new JComboBox(this.toneTypes);
            this.toneTypesComboBox = jComboBox;
            jComboBox.addActionListener(new ActionListener() { // from class: demo.Pinyin4jAppletDemo.3
                public void actionPerformed(ActionEvent actionEvent) {
                    JComboBox jComboBox2;
                    boolean z2;
                    Pinyin4jAppletDemo pinyin4jAppletDemo = Pinyin4jAppletDemo.this;
                    if (pinyin4jAppletDemo.toneTypes[2] == ((String) pinyin4jAppletDemo.toneTypesComboBox.getSelectedItem())) {
                        Pinyin4jAppletDemo.this.vCharTypesComboBox.setSelectedIndex(2);
                        jComboBox2 = Pinyin4jAppletDemo.this.vCharTypesComboBox;
                        z2 = false;
                    } else {
                        jComboBox2 = Pinyin4jAppletDemo.this.vCharTypesComboBox;
                        z2 = true;
                    }
                    jComboBox2.setEnabled(z2);
                }
            });
        }
        return this.toneTypesComboBox;
    }

    private JPanel getUnformattedCharPanel() {
        if (this.unformattedCharPanel == null) {
            JLabel jLabel = new JLabel();
            this.unformattedHanyuPinyinLabel = jLabel;
            jLabel.setText("Hanyu Pinyin");
            GridLayout gridLayout = new GridLayout();
            gridLayout.setRows(2);
            gridLayout.setHgap(1);
            gridLayout.setVgap(1);
            gridLayout.setColumns(3);
            JPanel jPanel = new JPanel();
            this.unformattedCharPanel = jPanel;
            jPanel.setLayout(gridLayout);
            this.unformattedCharPanel.add(getUnformattedHanyuPinyinPanel(), (Object) null);
            this.unformattedCharPanel.add(getUnformattedTongyongPinyinPanel(), (Object) null);
            this.unformattedCharPanel.add(getUnformattedWadePinyinPanel(), (Object) null);
            this.unformattedCharPanel.add(getUnformattedMPS2PinyinPanel(), (Object) null);
            this.unformattedCharPanel.add(getUnformattedYalePinyinPanel(), (Object) null);
            this.unformattedCharPanel.add(getUnformattedGwoyeuRomatzyhPanel(), (Object) null);
        }
        return this.unformattedCharPanel;
    }

    private JPanel getUnformattedGwoyeuRomatzyhPanel() {
        if (this.unformattedGwoyeuRomatzyhPanel == null) {
            JLabel jLabel = new JLabel();
            this.unformattedGwoyeuRomatzyhLabel = jLabel;
            jLabel.setText("Gwoyeu Romatzyh");
            JPanel jPanel = new JPanel();
            this.unformattedGwoyeuRomatzyhPanel = jPanel;
            jPanel.setLayout(new BorderLayout());
            this.unformattedGwoyeuRomatzyhPanel.add(this.unformattedGwoyeuRomatzyhLabel, "North");
            this.unformattedGwoyeuRomatzyhPanel.add(getUnformattedGwoyeuRomatzyhScrollPane(), "Center");
        }
        return this.unformattedGwoyeuRomatzyhPanel;
    }

    private JScrollPane getUnformattedGwoyeuRomatzyhScrollPane() {
        if (this.unformattedGwoyeuRomatzyhScrollPane == null) {
            JScrollPane jScrollPane = new JScrollPane();
            this.unformattedGwoyeuRomatzyhScrollPane = jScrollPane;
            jScrollPane.setViewportView(getUnformattedGwoyeuRomatzyhTextArea());
        }
        return this.unformattedGwoyeuRomatzyhScrollPane;
    }

    private JTextArea getUnformattedGwoyeuRomatzyhTextArea() {
        if (this.unformattedGwoyeuRomatzyhTextArea == null) {
            JTextArea jTextArea = new JTextArea();
            this.unformattedGwoyeuRomatzyhTextArea = jTextArea;
            jTextArea.setEditable(false);
            this.unformattedGwoyeuRomatzyhTextArea.setLineWrap(true);
        }
        return this.unformattedGwoyeuRomatzyhTextArea;
    }

    private JPanel getUnformattedHanyuPinyinPanel() {
        if (this.unformattedHanyuPinyinPanel == null) {
            JPanel jPanel = new JPanel();
            this.unformattedHanyuPinyinPanel = jPanel;
            jPanel.setLayout(new BorderLayout());
            this.unformattedHanyuPinyinPanel.add(this.unformattedHanyuPinyinLabel, "North");
            this.unformattedHanyuPinyinPanel.add(getUnformattedHanyuPinyinScrollPane(), "Center");
        }
        return this.unformattedHanyuPinyinPanel;
    }

    private JScrollPane getUnformattedHanyuPinyinScrollPane() {
        if (this.unformattedHanyuPinyinScrollPane == null) {
            JScrollPane jScrollPane = new JScrollPane();
            this.unformattedHanyuPinyinScrollPane = jScrollPane;
            jScrollPane.setViewportView(getUnformattedHanyuPinyinTextArea());
        }
        return this.unformattedHanyuPinyinScrollPane;
    }

    private JTextArea getUnformattedHanyuPinyinTextArea() {
        if (this.unformattedHanyuPinyinTextArea == null) {
            JTextArea jTextArea = new JTextArea();
            this.unformattedHanyuPinyinTextArea = jTextArea;
            jTextArea.setEditable(false);
            this.unformattedHanyuPinyinTextArea.setLineWrap(true);
        }
        return this.unformattedHanyuPinyinTextArea;
    }

    private JPanel getUnformattedMPS2PinyinPanel() {
        if (this.unformattedMPS2PinyinPanel == null) {
            JLabel jLabel = new JLabel();
            this.unformattedMPS2PinyinLabel = jLabel;
            jLabel.setText("MPSII Pinyin");
            JPanel jPanel = new JPanel();
            this.unformattedMPS2PinyinPanel = jPanel;
            jPanel.setLayout(new BorderLayout());
            this.unformattedMPS2PinyinPanel.add(this.unformattedMPS2PinyinLabel, "North");
            this.unformattedMPS2PinyinPanel.add(getUnformattedMPS2PinyinScrollPane(), "Center");
        }
        return this.unformattedMPS2PinyinPanel;
    }

    private JScrollPane getUnformattedMPS2PinyinScrollPane() {
        if (this.unformattedMPS2PinyinScrollPane == null) {
            JScrollPane jScrollPane = new JScrollPane();
            this.unformattedMPS2PinyinScrollPane = jScrollPane;
            jScrollPane.setViewportView(getUnformattedMPS2PinyinTextArea());
        }
        return this.unformattedMPS2PinyinScrollPane;
    }

    private JTextArea getUnformattedMPS2PinyinTextArea() {
        if (this.unformattedMPS2PinyinTextArea == null) {
            JTextArea jTextArea = new JTextArea();
            this.unformattedMPS2PinyinTextArea = jTextArea;
            jTextArea.setEditable(false);
            this.unformattedMPS2PinyinTextArea.setLineWrap(true);
        }
        return this.unformattedMPS2PinyinTextArea;
    }

    private JPanel getUnformattedTongyongPinyinPanel() {
        if (this.unformattedTongyongPinyinPanel == null) {
            JLabel jLabel = new JLabel();
            this.unformattedTongyongPinyinLabel = jLabel;
            jLabel.setText("Tongyong Pinyin");
            JPanel jPanel = new JPanel();
            this.unformattedTongyongPinyinPanel = jPanel;
            jPanel.setLayout(new BorderLayout());
            this.unformattedTongyongPinyinPanel.add(this.unformattedTongyongPinyinLabel, "North");
            this.unformattedTongyongPinyinPanel.add(getUnformattedTongyongPinyinScrollPane(), "Center");
        }
        return this.unformattedTongyongPinyinPanel;
    }

    private JScrollPane getUnformattedTongyongPinyinScrollPane() {
        if (this.unformattedTongyongPinyinScrollPane == null) {
            JScrollPane jScrollPane = new JScrollPane();
            this.unformattedTongyongPinyinScrollPane = jScrollPane;
            jScrollPane.setViewportView(getUnformattedTongyongPinyinTextArea());
        }
        return this.unformattedTongyongPinyinScrollPane;
    }

    private JTextArea getUnformattedTongyongPinyinTextArea() {
        if (this.unformattedTongyongPinyinTextArea == null) {
            JTextArea jTextArea = new JTextArea();
            this.unformattedTongyongPinyinTextArea = jTextArea;
            jTextArea.setEditable(false);
            this.unformattedTongyongPinyinTextArea.setLineWrap(true);
        }
        return this.unformattedTongyongPinyinTextArea;
    }

    private JPanel getUnformattedWadePinyinPanel() {
        if (this.unformattedWadePinyinPanel == null) {
            JLabel jLabel = new JLabel();
            this.unformattedWadePinyinLabel = jLabel;
            jLabel.setText("Wade-Giles  Pinyin");
            JPanel jPanel = new JPanel();
            this.unformattedWadePinyinPanel = jPanel;
            jPanel.setLayout(new BorderLayout());
            this.unformattedWadePinyinPanel.add(this.unformattedWadePinyinLabel, "North");
            this.unformattedWadePinyinPanel.add(getUnformattedWadePinyinScrollPane(), "Center");
        }
        return this.unformattedWadePinyinPanel;
    }

    private JScrollPane getUnformattedWadePinyinScrollPane() {
        if (this.unformattedWadePinyinScrollPane == null) {
            JScrollPane jScrollPane = new JScrollPane();
            this.unformattedWadePinyinScrollPane = jScrollPane;
            jScrollPane.setViewportView(getUnformattedWadePinyinTextArea());
        }
        return this.unformattedWadePinyinScrollPane;
    }

    private JTextArea getUnformattedWadePinyinTextArea() {
        if (this.unformattedWadePinyinTextArea == null) {
            JTextArea jTextArea = new JTextArea();
            this.unformattedWadePinyinTextArea = jTextArea;
            jTextArea.setEditable(false);
            this.unformattedWadePinyinTextArea.setLineWrap(true);
        }
        return this.unformattedWadePinyinTextArea;
    }

    private JPanel getUnformattedYalePinyinPanel() {
        if (this.unformattedYalePinyinPanel == null) {
            JLabel jLabel = new JLabel();
            this.unformattedYalePinyinLabel = jLabel;
            jLabel.setText("Yale Pinyin");
            JPanel jPanel = new JPanel();
            this.unformattedYalePinyinPanel = jPanel;
            jPanel.setLayout(new BorderLayout());
            this.unformattedYalePinyinPanel.add(this.unformattedYalePinyinLabel, "North");
            this.unformattedYalePinyinPanel.add(getUnformattedYalePinyinScrollPane(), "Center");
        }
        return this.unformattedYalePinyinPanel;
    }

    private JScrollPane getUnformattedYalePinyinScrollPane() {
        if (this.unformattedYalePinyinScrollPane == null) {
            JScrollPane jScrollPane = new JScrollPane();
            this.unformattedYalePinyinScrollPane = jScrollPane;
            jScrollPane.setViewportView(getUnformattedYalePinyinTextArea());
        }
        return this.unformattedYalePinyinScrollPane;
    }

    private JTextArea getUnformattedYalePinyinTextArea() {
        if (this.unformattedYalePinyinTextArea == null) {
            JTextArea jTextArea = new JTextArea();
            this.unformattedYalePinyinTextArea = jTextArea;
            jTextArea.setEditable(false);
            this.unformattedYalePinyinTextArea.setLineWrap(true);
        }
        return this.unformattedYalePinyinTextArea;
    }

    private JComboBox getVCharTypesComboBox() {
        if (this.vCharTypesComboBox == null) {
            this.vCharTypesComboBox = new JComboBox(this.vCharTypes);
        }
        return this.vCharTypesComboBox;
    }

    public static void main(String[] strArr) {
        Pinyin4jAppletDemo pinyin4jAppletDemo = new Pinyin4jAppletDemo();
        System.runFinalizersOnExit(true);
        JFrame jFrame = new JFrame(appName);
        jFrame.addWindowListener(new WindowAdapter() { // from class: demo.Pinyin4jAppletDemo.1
            public void windowClosing(WindowEvent windowEvent) {
                Pinyin4jAppletDemo.this.stop();
                Pinyin4jAppletDemo.this.destroy();
                System.exit(0);
            }
        });
        jFrame.add("Center", pinyin4jAppletDemo);
        pinyin4jAppletDemo.init();
        pinyin4jAppletDemo.start();
        jFrame.setSize(APP_SIZE);
        jFrame.pack();
        jFrame.setVisible(true);
    }

    public void init() {
        setSize(APP_SIZE);
        setContentPane(getJContentPane());
        setName(appName);
    }
}
