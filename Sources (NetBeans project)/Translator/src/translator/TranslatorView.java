/*
 * TranslatorView.java
 */
package translator;

import java.awt.FileDialog;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.CaretEvent;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.CaretListener;

/**
 * The application's main frame.
 */
public class TranslatorView extends FrameView implements Searchable {

    public TranslatorView(SingleFrameApplication app) {
        super(app);

        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String) (evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer) (evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
        initControls();
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = TranslatorApp.getApplication().getMainFrame();
            aboutBox = new TranslatorAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        TranslatorApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        fileNameTextField = new javax.swing.JTextField();
        readEncLabel = new javax.swing.JLabel();
        writeEncLabel = new javax.swing.JLabel();
        readEncComboBox = new javax.swing.JComboBox();
        writeEncComboBox = new javax.swing.JComboBox();
        translateButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        codeTextField = new javax.swing.JTextField();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        openFileMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        findMenuItem = new javax.swing.JMenuItem();
        replaceMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N

        fileNameTextField.setEditable(false);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(translator.TranslatorApp.class).getContext().getResourceMap(TranslatorView.class);
        fileNameTextField.setText(resourceMap.getString("fileNameTextField.text")); // NOI18N
        fileNameTextField.setName("fileNameTextField"); // NOI18N

        readEncLabel.setText(resourceMap.getString("readEncLabel.text")); // NOI18N
        readEncLabel.setName("readEncLabel"); // NOI18N

        writeEncLabel.setText(resourceMap.getString("writeEncLabel.text")); // NOI18N
        writeEncLabel.setName("writeEncLabel"); // NOI18N

        readEncComboBox.setName("readEncComboBox"); // NOI18N

        writeEncComboBox.setName("writeEncComboBox"); // NOI18N

        translateButton.setText(resourceMap.getString("translateButton.text")); // NOI18N
        translateButton.setName("translateButton"); // NOI18N
        translateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                translateButtonActionPerformed(evt);
            }
        });

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        textArea.setColumns(20);
        textArea.setFont(resourceMap.getFont("textArea.font")); // NOI18N
        textArea.setRows(5);
        textArea.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        textArea.setName("textArea"); // NOI18N
        jScrollPane1.setViewportView(textArea);

        codeTextField.setEditable(false);
        codeTextField.setText(resourceMap.getString("codeTextField.text")); // NOI18N
        codeTextField.setName("codeTextField"); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fileNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(readEncComboBox, 0, 135, Short.MAX_VALUE)
                    .addComponent(readEncLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(writeEncComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(writeEncLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(translateButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(codeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(fileNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(readEncLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(readEncComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(writeEncLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(writeEncComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(translateButton)
                            .addComponent(codeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                .addContainerGap())
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        openFileMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openFileMenuItem.setText(resourceMap.getString("openFileMenuItem.text")); // NOI18N
        openFileMenuItem.setName("openFileMenuItem"); // NOI18N
        openFileMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openFileMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(openFileMenuItem);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(translator.TranslatorApp.class).getContext().getActionMap(TranslatorView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        editMenu.setText(resourceMap.getString("editMenu.text")); // NOI18N
        editMenu.setName("editMenu"); // NOI18N

        findMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        findMenuItem.setText(resourceMap.getString("findMenuItem.text")); // NOI18N
        findMenuItem.setName("findMenuItem"); // NOI18N
        findMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(findMenuItem);

        replaceMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        replaceMenuItem.setText(resourceMap.getString("replaceMenuItem.text")); // NOI18N
        replaceMenuItem.setName("replaceMenuItem"); // NOI18N
        replaceMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                replaceMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(replaceMenuItem);

        menuBar.add(editMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 301, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents
    protected byte[] data = null;
    protected String str = null;
    protected String fileName = null;
    private JFrame self = this.getFrame();

    private void readFile() throws IOException {
        str = new String(data, (String) this.readEncComboBox.getSelectedItem());
        textArea.setText(str);
        textArea.setCaretPosition(0);
        //ObjectInputStream ois = new ObjectInputStream(is);
    }

    private void openFileMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openFileMenuItemActionPerformed
        // TODO add your handling code here:
        FileDialog fd = new FileDialog(this.getFrame(), "Открыть файл", FileDialog.LOAD);
        fd.setVisible(true);
        if (fd.getDirectory() != null) {
            fileName = fd.getDirectory() + fd.getFile();
            openFile();
        }
    }//GEN-LAST:event_openFileMenuItemActionPerformed

    private void translateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_translateButtonActionPerformed
        try {
            // TODO add your handling code here:
            OutputStream os = new FileOutputStream(fileName);
            str = textArea.getText();
            os.write(str.getBytes((String) writeEncComboBox.getSelectedItem()));
            os.close();
            readEncComboBox.setSelectedItem(writeEncComboBox.getSelectedItem());
            openFile();
        } catch (Exception ex) {
            Logger.getLogger(TranslatorView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_translateButtonActionPerformed

    private void replaceMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_replaceMenuItemActionPerformed
        // TODO add your handling code here:
        ReplaceDialog rf = new ReplaceDialog(self, true);
        rf.setEnc((String) readEncComboBox.getSelectedItem());
        rf.init();
        rf.setLocationRelativeTo(self);
        rf.setVisible(true);
        if (rf.getReturnStatus() == ReplaceDialog.RET_OK) {
            str = textArea.getText();
            str = str.replace(rf.from, rf.to);
            textArea.setText(str);
        }
    }//GEN-LAST:event_replaceMenuItemActionPerformed

    private void findMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findMenuItemActionPerformed
        // TODO add your handling code here:
        cur = 0;//textArea.getCaretPosition();
        //!!Сделать поиск не сначала!!
        textArea.requestFocusInWindow();
        FindDialog fd = new FindDialog(self, false);
        fd.setEnc((String) readEncComboBox.getSelectedItem());
        fd.init(this);
        fd.setLocationRelativeTo(self);
        fd.setVisible(true);
    }//GEN-LAST:event_findMenuItemActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField codeTextField;
    private javax.swing.JMenu editMenu;
    private javax.swing.JTextField fileNameTextField;
    private javax.swing.JMenuItem findMenuItem;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openFileMenuItem;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JComboBox readEncComboBox;
    private javax.swing.JLabel readEncLabel;
    private javax.swing.JMenuItem replaceMenuItem;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JTextArea textArea;
    private javax.swing.JButton translateButton;
    private javax.swing.JComboBox writeEncComboBox;
    private javax.swing.JLabel writeEncLabel;
    // End of variables declaration//GEN-END:variables
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;

    private void initControls() {
        textArea.getActionMap();
        Object[] chsts = Charset.availableCharsets().keySet().toArray();
        for (int i = 0; i < chsts.length; i++) {
            String tmp = (String) chsts[i];
            //!!!!Сделать выбор опциональным!!
            if (tmp.equals("UTF-8") || tmp.equals("UTF-16") || tmp.contains("Unicode") || tmp.contains("KOI") || tmp.contains("1251") || tmp.contains("866") || tmp.contains("8859-5")) {
                readEncComboBox.insertItemAt(chsts[i], 0);
                writeEncComboBox.insertItemAt(chsts[i], 0);
                continue;
            }
            readEncComboBox.addItem(chsts[i]);
            writeEncComboBox.addItem(chsts[i]);
        }
        readEncComboBox.setSelectedIndex(0);
        writeEncComboBox.setSelectedIndex(0);
        self.setTitle("Translator 1.0");
        textArea.addCaretListener(new CaretListener() {

            public void caretUpdate(CaretEvent e) {
                if (str == null) {
                    return;

                }
                try {
                    int y = e.getDot();

                    Character tmp = textArea.getText().charAt(y);
                    byte[] chr = tmp.toString().getBytes((String) readEncComboBox.getSelectedItem());
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < chr.length; i++) {
                        String tmpp = Integer.toString((chr[i] + 256) % 256, 16);
                        if (tmpp.length() == 1) {
                            tmpp = "0" + tmpp;
                        }
                        tmpp = tmpp.toUpperCase();
                        sb.append(" 0x" + tmpp);
                    }
                    codeTextField.setText(sb.toString());
                } catch (StringIndexOutOfBoundsException ex) {
                    codeTextField.setText("");
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(TranslatorView.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        readEncComboBox.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (data != null) {
                    try {
                        int tmp = textArea.getCaretPosition();
                        readFile();
                        if (tmp > str.length()) {
                            tmp = 0;
                        }
                        textArea.setCaretPosition(tmp);
                        textArea.requestFocus();
                    } catch (IOException ex) {
                        Logger.getLogger(TranslatorView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    private void openFile() {
        try {

            fileNameTextField.setText(fileName);
            FileInputStream is = new FileInputStream(fileName);
            //int len = is.
            File f = new File(fileName);
            data = new byte[(int) f.length()];
            is.read(data);
            is.close();
            readFile();
        } catch (Exception ex) {
            Logger.getLogger(TranslatorView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    int cur = 0;

    public void search(String f) {
        cur = str.indexOf(f, cur + 1);
        if (cur < 0) {
            JOptionPane.showMessageDialog(self, "Nothing found");
        } else {
            textArea.setCaretPosition(cur);
            textArea.select(cur, cur + f.length());
        }
    }
}
