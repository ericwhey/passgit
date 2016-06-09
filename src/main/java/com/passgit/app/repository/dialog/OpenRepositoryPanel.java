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
package com.passgit.app.repository.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;

/**
 *
 * @author Eric Hey
 */
public class OpenRepositoryPanel extends javax.swing.JPanel {

    /**
     * Creates new form CipherPanel
     */
    public OpenRepositoryPanel() {
        initComponents();
        
        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireDoneEvent();
            }
            
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        keyFileField = new javax.swing.JTextField();
        chooseKeyFileButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        repositoryField = new javax.swing.JTextField();
        chooseRepositoryButton = new javax.swing.JButton();

        jLabel1.setText("Password:");

        jLabel2.setText("Key File:");

        chooseKeyFileButton.setText("Choose...");
        chooseKeyFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseKeyFileButtonActionPerformed(evt);
            }
        });

        jLabel3.setText("Repository:");

        chooseRepositoryButton.setText("Choose...");
        chooseRepositoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseRepositoryButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(keyFileField, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(chooseKeyFileButton))
                            .addComponent(passwordField)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(repositoryField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chooseRepositoryButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(repositoryField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chooseRepositoryButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(keyFileField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chooseKeyFileButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void chooseKeyFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseKeyFileButtonActionPerformed
        final JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setCurrentDirectory(new File(repositoryField.getText()));

        //In response to a button click:
        int returnVal = fc.showOpenDialog(this);
        
        File currentFile = fc.getSelectedFile();
        
        keyFileField.setText(currentFile.toPath().toString());
    }//GEN-LAST:event_chooseKeyFileButtonActionPerformed

    private void chooseRepositoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseRepositoryButtonActionPerformed
        //Create a file chooser
        final JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setCurrentDirectory(new File(repositoryField.getText()));

        //In response to a button click:
        int returnVal = fc.showOpenDialog(this);
        
        File currentDirectory = fc.getCurrentDirectory();
        
        repositoryField.setText(currentDirectory.toPath().toString());
    }//GEN-LAST:event_chooseRepositoryButtonActionPerformed

    public void setRepository(Path path) {
        repositoryField.setText(path.toString());
    }
    
    public Path getRepository() {
        return new File(repositoryField.getText()).toPath();
    }
    
    public char[] getPassword() {
        return passwordField.getPassword();
    }
    
    public void setKeyFileFilename(String filename) {
        keyFileField.setText(filename);
    }
    
    public File getKeyFile() {
        if (!keyFileField.getText().equals("")) {
            return new File(keyFileField.getText());
        } 
            
        return null;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton chooseKeyFileButton;
    private javax.swing.JButton chooseRepositoryButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField keyFileField;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JTextField repositoryField;
    // End of variables declaration//GEN-END:variables

    public interface Listener {
        public void onOkay();
    }
    
    private List<Listener> listeners = new ArrayList<Listener>();
    
    public void addListener(Listener l) {
        listeners.add(l);
    }
    
    public void fireDoneEvent() {
        for (Listener l : listeners) {
            l.onOkay();
        }
    }
    
    public void setFocus() {
        passwordField.requestFocus();
    }
}