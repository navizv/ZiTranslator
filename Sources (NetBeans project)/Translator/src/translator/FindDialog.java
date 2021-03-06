/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FindDialog.java
 *
 * Created on 01.04.2011, 10:26:30
 */
package translator;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ZaytsevID
 */
public class FindDialog extends javax.swing.JDialog {

    /** Creates new form FindDialog */
    public FindDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        findModeButtonGroup = new javax.swing.ButtonGroup();
        charFindTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        codeFindRadioButton = new javax.swing.JRadioButton();
        codeFindTextField = new javax.swing.JTextField();
        charFindRadioButton = new javax.swing.JRadioButton();
        replaceButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N

        charFindTextField.setName("charFindTextField"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(translator.TranslatorApp.class).getContext().getResourceMap(FindDialog.class);
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        findModeButtonGroup.add(codeFindRadioButton);
        codeFindRadioButton.setText(resourceMap.getString("codeFindRadioButton.text")); // NOI18N
        codeFindRadioButton.setName("codeFindRadioButton"); // NOI18N
        codeFindRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codeFindRadioButtonActionPerformed(evt);
            }
        });

        codeFindTextField.setName("codeFindTextField"); // NOI18N

        findModeButtonGroup.add(charFindRadioButton);
        charFindRadioButton.setSelected(true);
        charFindRadioButton.setText(resourceMap.getString("charFindRadioButton.text")); // NOI18N
        charFindRadioButton.setName("charFindRadioButton"); // NOI18N
        charFindRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                charFindRadioButtonActionPerformed(evt);
            }
        });

        replaceButton.setText(resourceMap.getString("replaceButton.text")); // NOI18N
        replaceButton.setName("replaceButton"); // NOI18N
        replaceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                replaceButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(codeFindTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(codeFindRadioButton))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(charFindTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(charFindRadioButton)
                                .addGap(18, 18, 18)
                                .addComponent(replaceButton)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(charFindTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(charFindRadioButton)
                    .addComponent(replaceButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(codeFindTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(codeFindRadioButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private String from;
    private byte[] fromB;
    private String enc;
    private Searchable mainView;

    void setEnc(String string) {
        enc = string;
    }
    private void codeFindRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codeFindRadioButtonActionPerformed
        // TODO add your handling code here:
        codeFindTextField.setEnabled(true);
        charFindTextField.setEnabled(false);
        from = charFindTextField.getText();
        try {
            fromB = from.getBytes(enc);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ReplaceDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        codeFindTextField.setText(stringFromBytes(fromB));
        codeFindTextField.requestFocusInWindow();
}//GEN-LAST:event_codeFindRadioButtonActionPerformed

    private void charFindRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_charFindRadioButtonActionPerformed
        // TODO add your handling code here:
        codeFindTextField.setEnabled(false);
        charFindTextField.setEnabled(true);
        fromB = parseBytes(codeFindTextField.getText());
        try {
            from = new String(fromB, enc);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ReplaceDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        charFindTextField.setText(from);
        charFindTextField.requestFocusInWindow();
}//GEN-LAST:event_charFindRadioButtonActionPerformed

    private void replaceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_replaceButtonActionPerformed
        // TODO add your handling code here:
        if (charFindRadioButton.isSelected()) {
            from = charFindTextField.getText();
        } else {
            fromB = parseBytes(codeFindTextField.getText());
            try {
                from = new String(fromB, enc);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(ReplaceDialog.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        mainView.search(from);
}//GEN-LAST:event_replaceButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                FindDialog dialog = new FindDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton charFindRadioButton;
    private javax.swing.JTextField charFindTextField;
    private javax.swing.JRadioButton codeFindRadioButton;
    private javax.swing.JTextField codeFindTextField;
    private javax.swing.ButtonGroup findModeButtonGroup;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton replaceButton;
    // End of variables declaration//GEN-END:variables

    private byte[] parseBytes(String text) {
        int len = text.length() / 2;
        byte[] res = new byte[len];
        for (int i = 0; i < len; i++) {
            String bt = text.substring(2 * i, 2 * i + 2);
            int tmp = Integer.parseInt(bt, 16);
            if (tmp < 128) {
                res[i] = (byte) tmp;
            } else {
                res[i] = (byte) (tmp - 256);
            }
        }
        return res;
    }

    private String stringFromBytes(byte[] b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String strq = Integer.toString((b[i] + 256) % 256, 16);
            if (strq.length() == 1) {
                strq = "0" + strq;
            }
            sb.append(strq);
        }
        return sb.toString();
    }

    void init(Searchable aThis) {
        mainView = aThis;
        charFindRadioButton.doClick();
        getRootPane().setDefaultButton(replaceButton);
    }
}
