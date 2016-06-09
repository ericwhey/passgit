/* 
 * Copyright (C) 2016 Eric Hey
 *
 * This file is part of passGit.
 *
 * passGit is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * passGit is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with passGit.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.passgit.app.file.dialog;

import java.nio.CharBuffer;

/**
 *
 * @author Eric Hey
 */
public class PasswordGeneratorPanel extends javax.swing.JPanel {

    private boolean showPassword = false;
    private boolean showPassword1 = false;
    
    private char[] lowercaseLetters = { 'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    private char[] uppercaseLetters = { 'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    private char[] numbers = {'1','2','3','4','5','6','7','8','9','0'};
    private char[] symbols = {'~','!','@','#','$','%','^','&','*','(',')','+','=','[',']','{','}','|','\\',',','.',':','<','>','?'};
    private char[] underscores = {'_'};
    private char[] dashes = {'-'};
    private char[] whitespaces = {' '};
    
    private char[] lowercaseVowels = {'a','e','i','o','u'};
    private char[] uppercaseVowels = {'A','E','I','O','U'};
    private char[] lowercaseConsenants = {'b','c','d','f','g','h','j','k','l','m','n','p','r','s','t','v','w','z'};
    private char[] uppercaseConsenants = {'B','C','D','F','G','H','J','K','L','M','N','P','R','S','T','V','W','Z'};
    
    /**
     * Creates new form PasswordGeneratorPanel
     */
    public PasswordGeneratorPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedPane = new javax.swing.JTabbedPane();
        randomPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        numbersCheckbox = new javax.swing.JCheckBox();
        entropyCheckbox = new javax.swing.JCheckBox();
        uppercaseLettersCheckbox = new javax.swing.JCheckBox();
        strengthProgressBar = new javax.swing.JProgressBar();
        lengthSpinner = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lowercaseLettersCheckbox = new javax.swing.JCheckBox();
        underscoreCheckbox = new javax.swing.JCheckBox();
        whitespaceCheckbox = new javax.swing.JCheckBox();
        symbolsCheckbox = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        dashCheckbox = new javax.swing.JCheckBox();
        showButton = new javax.swing.JButton();
        generateButton = new javax.swing.JButton();
        passwordField = new javax.swing.JPasswordField();
        pronouncablePanel = new javax.swing.JPanel();
        uppercaseLettersCheckbox1 = new javax.swing.JCheckBox();
        lowercaseLettersCheckbox1 = new javax.swing.JCheckBox();
        numbersCheckbox1 = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lengthSpinner1 = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        strengthProgressBar1 = new javax.swing.JProgressBar();
        showButton1 = new javax.swing.JButton();
        generateButton1 = new javax.swing.JButton();
        passwordField1 = new javax.swing.JPasswordField();

        jLabel4.setText("Strength:");

        numbersCheckbox.setSelected(true);
        numbersCheckbox.setText("Numbers");

        entropyCheckbox.setText("Collect entropy");

        uppercaseLettersCheckbox.setSelected(true);
        uppercaseLettersCheckbox.setText("Uppercase Letters");
        uppercaseLettersCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uppercaseLettersCheckboxActionPerformed(evt);
            }
        });

        lengthSpinner.setValue(16);

        jLabel1.setText("Use the following characters:");

        jLabel3.setText("Password:");

        lowercaseLettersCheckbox.setSelected(true);
        lowercaseLettersCheckbox.setText("Lowercase Letters");

        underscoreCheckbox.setText("Underscore");

        whitespaceCheckbox.setText("Whitespace");

        symbolsCheckbox.setText("Symbols");

        jLabel2.setText("Length:");

        dashCheckbox.setText("Dash");

        showButton.setText("Show");
        showButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showButtonActionPerformed(evt);
            }
        });

        generateButton.setText("Generate");
        generateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout randomPanelLayout = new javax.swing.GroupLayout(randomPanel);
        randomPanel.setLayout(randomPanelLayout);
        randomPanelLayout.setHorizontalGroup(
            randomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(randomPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(randomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(randomPanelLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lengthSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(entropyCheckbox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(generateButton))
                    .addGroup(randomPanelLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(strengthProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(randomPanelLayout.createSequentialGroup()
                        .addGroup(randomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(randomPanelLayout.createSequentialGroup()
                                .addGroup(randomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lowercaseLettersCheckbox)
                                    .addComponent(numbersCheckbox))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(randomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dashCheckbox)
                                    .addComponent(underscoreCheckbox)))
                            .addGroup(randomPanelLayout.createSequentialGroup()
                                .addComponent(uppercaseLettersCheckbox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(symbolsCheckbox)
                                .addGap(32, 32, 32)
                                .addComponent(whitespaceCheckbox)))
                        .addGap(0, 89, Short.MAX_VALUE))
                    .addGroup(randomPanelLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(passwordField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(showButton, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        randomPanelLayout.setVerticalGroup(
            randomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(randomPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(randomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(uppercaseLettersCheckbox)
                    .addComponent(symbolsCheckbox)
                    .addComponent(whitespaceCheckbox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(randomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lowercaseLettersCheckbox)
                    .addComponent(underscoreCheckbox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(randomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numbersCheckbox)
                    .addComponent(dashCheckbox))
                .addGap(18, 18, 18)
                .addGroup(randomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lengthSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(entropyCheckbox)
                    .addComponent(generateButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(randomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(showButton)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(randomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(strengthProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        tabbedPane.addTab("Random", randomPanel);

        uppercaseLettersCheckbox1.setSelected(true);
        uppercaseLettersCheckbox1.setText("Uppercase Letters");
        uppercaseLettersCheckbox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uppercaseLettersCheckbox1ActionPerformed(evt);
            }
        });

        lowercaseLettersCheckbox1.setSelected(true);
        lowercaseLettersCheckbox1.setText("Lowercase Letters");

        numbersCheckbox1.setSelected(true);
        numbersCheckbox1.setText("Numbers");

        jLabel5.setText("Use the following characters:");

        jLabel6.setText("Length:");

        lengthSpinner1.setValue(16);

        jLabel7.setText("Password:");

        jLabel8.setText("Strength:");

        showButton1.setText("Show");
        showButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showButton1ActionPerformed(evt);
            }
        });

        generateButton1.setText("Generate");
        generateButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pronouncablePanelLayout = new javax.swing.GroupLayout(pronouncablePanel);
        pronouncablePanel.setLayout(pronouncablePanelLayout);
        pronouncablePanelLayout.setHorizontalGroup(
            pronouncablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pronouncablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pronouncablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pronouncablePanelLayout.createSequentialGroup()
                        .addGroup(pronouncablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pronouncablePanelLayout.createSequentialGroup()
                                .addGroup(pronouncablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(lowercaseLettersCheckbox1)
                                    .addComponent(numbersCheckbox1)
                                    .addComponent(uppercaseLettersCheckbox1))
                                .addGap(177, 177, 177))
                            .addGroup(pronouncablePanelLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(passwordField1)))
                        .addGroup(pronouncablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pronouncablePanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(generateButton1)
                                .addGap(6, 6, 6))
                            .addGroup(pronouncablePanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(showButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())))
                    .addGroup(pronouncablePanelLayout.createSequentialGroup()
                        .addGroup(pronouncablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pronouncablePanelLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lengthSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(pronouncablePanelLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(strengthProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        pronouncablePanelLayout.setVerticalGroup(
            pronouncablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pronouncablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(uppercaseLettersCheckbox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lowercaseLettersCheckbox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(numbersCheckbox1)
                .addGap(18, 18, 18)
                .addGroup(pronouncablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lengthSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(generateButton1))
                .addGap(7, 7, 7)
                .addGroup(pronouncablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(passwordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(showButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pronouncablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(strengthProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        tabbedPane.addTab("Pronouncable", pronouncablePanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabbedPane)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabbedPane)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void uppercaseLettersCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uppercaseLettersCheckboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_uppercaseLettersCheckboxActionPerformed

    private void uppercaseLettersCheckbox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uppercaseLettersCheckbox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_uppercaseLettersCheckbox1ActionPerformed

    private void generateButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateButton1ActionPerformed
        CharBuffer chars = CharBuffer.allocate(256);
        CharBuffer vowelsChars = CharBuffer.allocate(256);
        CharBuffer consenantsChars = CharBuffer.allocate(256);
        
        if (lowercaseLettersCheckbox1.isSelected()) {
            chars.put(lowercaseLetters);
            vowelsChars.put(lowercaseVowels);
            consenantsChars.put(lowercaseConsenants);
        }
        
        if (uppercaseLettersCheckbox1.isSelected()) {
            chars.put(uppercaseLetters);
            vowelsChars.put(uppercaseVowels);
            consenantsChars.put(uppercaseConsenants);
        }
        
        if (numbersCheckbox1.isSelected()) {
            chars.put(numbers);
        }
        
        int charsLength = chars.position();
        int vowelsCharsLength = vowelsChars.position();
        int consenantsCharsLength = consenantsChars.position();
        
        chars.rewind();
        vowelsChars.rewind();
        consenantsChars.rewind();
 
        int passwordLength = (int) lengthSpinner1.getValue();
        char[] password = new char[passwordLength];
        
                
        long combinations = 1;
        
        for (int i=0; i<passwordLength; ) {
            password[i] = consenantsChars.charAt((int) (Math.random() * consenantsCharsLength));
            combinations *= consenantsCharsLength;
            i++;
            if (i < (passwordLength)) {
                password[i] = vowelsChars.charAt((int) (Math.random() * vowelsCharsLength));
                combinations *= vowelsCharsLength;
                i++;
            }
            if (i < passwordLength) {
                password[i] = chars.charAt((int) (Math.random() * charsLength));
                combinations *= charsLength;
                i++;
            }

        }
 
        passwordField1.setText(new String(password));
        strengthProgressBar1.setMaximum((int)Math.pow(8, passwordLength));
        strengthProgressBar1.setValue((int)combinations);
        
    }//GEN-LAST:event_generateButton1ActionPerformed

    private void showButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showButtonActionPerformed
        if (showPassword) {
            passwordField.setEchoChar('*');
            showButton.setText("Show");
            showPassword = false;
        } else {
            passwordField.setEchoChar((char) 0);
            showButton.setText("Hide");
            showPassword = true;
        }
    }//GEN-LAST:event_showButtonActionPerformed

    private void showButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showButton1ActionPerformed
        if (showPassword1) {
            passwordField1.setEchoChar('*');
            showButton1.setText("Show");
            showPassword1 = false;
        } else {
            passwordField1.setEchoChar((char) 0);
            showButton1.setText("Hide");
            showPassword1 = true;
        }
    }//GEN-LAST:event_showButton1ActionPerformed

    private void generateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateButtonActionPerformed
        CharBuffer chars = CharBuffer.allocate(256);
        
        if (lowercaseLettersCheckbox.isSelected()) {
            chars.put(lowercaseLetters);
        }
        
        if (uppercaseLettersCheckbox.isSelected()) {
            chars.put(uppercaseLetters);
        }
        
        if (numbersCheckbox.isSelected()) {
            chars.put(numbers);
        }
        
        if (symbolsCheckbox.isSelected()) {
            chars.put(symbols);
        }
        
        if (underscoreCheckbox.isSelected()) {
            chars.put(underscores);
        }
        
        if (dashCheckbox.isSelected()) {
            chars.put(dashes);
        }
        
        if (whitespaceCheckbox.isSelected()) {
            chars.put(whitespaces);
        }
        
        int charsLength = chars.position();
        chars.rewind();
 
        int passwordLength = (int) lengthSpinner.getValue();
        char[] password = new char[passwordLength];
        for (int i=0; i<passwordLength; i++) {
            password[i] = chars.charAt((int) (Math.random() * charsLength));
        }
 
        passwordField.setText(new String(password));
    }//GEN-LAST:event_generateButtonActionPerformed

    public char[] getPassword() {
        if (tabbedPane.getSelectedIndex() == 0) {
            return passwordField.getPassword();
        } else if (tabbedPane.getSelectedIndex() == 1) {
            return passwordField1.getPassword();
        }
        
        return null;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox dashCheckbox;
    private javax.swing.JCheckBox entropyCheckbox;
    private javax.swing.JButton generateButton;
    private javax.swing.JButton generateButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JSpinner lengthSpinner;
    private javax.swing.JSpinner lengthSpinner1;
    private javax.swing.JCheckBox lowercaseLettersCheckbox;
    private javax.swing.JCheckBox lowercaseLettersCheckbox1;
    private javax.swing.JCheckBox numbersCheckbox;
    private javax.swing.JCheckBox numbersCheckbox1;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JPasswordField passwordField1;
    private javax.swing.JPanel pronouncablePanel;
    private javax.swing.JPanel randomPanel;
    private javax.swing.JButton showButton;
    private javax.swing.JButton showButton1;
    private javax.swing.JProgressBar strengthProgressBar;
    private javax.swing.JProgressBar strengthProgressBar1;
    private javax.swing.JCheckBox symbolsCheckbox;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JCheckBox underscoreCheckbox;
    private javax.swing.JCheckBox uppercaseLettersCheckbox;
    private javax.swing.JCheckBox uppercaseLettersCheckbox1;
    private javax.swing.JCheckBox whitespaceCheckbox;
    // End of variables declaration//GEN-END:variables
}
