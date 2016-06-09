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
package com.passgit.app;

import com.passgit.app.repository.listener.PathModelTreeMouseListener;
import com.passgit.app.repository.model.PathModelTreeRenderer;
import com.passgit.app.repository.model.PathModelTreeModel;
import java.awt.BorderLayout;
import java.awt.event.MouseListener;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author Eric Hey
 */
public class MainFrame extends javax.swing.JFrame {

    private final PassGit app;

    /**
     * Creates new form PassFrame
     *
     * @param app
     */
    public MainFrame(PassGit app) {
        this.app = app;
        
        this.setTitle("PassGit");
        
        initComponents();
        
        Controller controller = app.getController();
        
        newButton.setAction(controller.getNewRepositoryAction());
        openButton.setAction(controller.getOpenRepositoryAction());
        closeButton.setAction(controller.getCloseRepositoryAction());

        commitButton.setAction(controller.getCommitRepositoryAction());
        pushButton.setAction(controller.getPushRepositoryAction());
        pullButton.setAction(controller.getPullRepositoryAction());
        
        convertButton.setAction(controller.getConvertRepositoryAction());
    }
    
    public void openRepository(PathModelTreeModel treeModel) {
        this.setTitle("PassGit - " + app.getRootPath());
        
        JTree tree = new JTree();

        tree.setModel(treeModel);
        
        tree.setCellRenderer(new PathModelTreeRenderer());

        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        MouseListener mouseListener = new PathModelTreeMouseListener(app, tree);
            
        tree.addMouseListener(mouseListener);
        
        jPanel1.removeAll();
        
        jPanel1.add(new JScrollPane(tree), BorderLayout.CENTER);

        jPanel1.doLayout();
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                jPanel1.repaint();
                jPanel1.revalidate();
            }
            
        });
   
    }
    
    public void closeRepository() {
        this.setTitle("PassGit");
        
        jPanel1.removeAll();
        jPanel1.add(jLabel1, BorderLayout.CENTER);
        

        jPanel1.doLayout();
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                jPanel1.repaint();
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

        jToolBar1 = new javax.swing.JToolBar();
        newButton = new javax.swing.JButton();
        openButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        commitButton = new javax.swing.JButton();
        pushButton = new javax.swing.JButton();
        pullButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        convertButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jToolBar1.setRollover(true);

        newButton.setText("New");
        newButton.setFocusable(false);
        newButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        newButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(newButton);

        openButton.setText("Open");
        openButton.setFocusable(false);
        openButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        openButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(openButton);

        closeButton.setText("Close");
        closeButton.setFocusable(false);
        closeButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        closeButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(closeButton);
        jToolBar1.add(jSeparator2);

        commitButton.setText("Commit");
        commitButton.setFocusable(false);
        commitButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        commitButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(commitButton);

        pushButton.setText("Push");
        pushButton.setFocusable(false);
        pushButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pushButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(pushButton);

        pullButton.setText("Pull");
        pullButton.setFocusable(false);
        pullButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pullButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(pullButton);
        jToolBar1.add(jSeparator1);

        convertButton.setText("Convert");
        convertButton.setFocusable(false);
        convertButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        convertButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        convertButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                convertButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(convertButton);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/passgit/app/passgit.png"))); // NOI18N
        jPanel1.add(jLabel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void convertButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_convertButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_convertButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JButton commitButton;
    private javax.swing.JButton convertButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton newButton;
    private javax.swing.JButton openButton;
    private javax.swing.JButton pullButton;
    private javax.swing.JButton pushButton;
    // End of variables declaration//GEN-END:variables

    
}