/*
 * Copyright (C) 2016 Eric Hey
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.passgit.app.repository.dialog;

import com.passgit.app.Controller;
import com.passgit.app.PassGit;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 *
 * @author Eric Hey
 */
public class NewRepositoryDialog extends JDialog {
    
    private NewRepositoryPanel newRepositoryPanel;
    
    private Controller controller;
    
    public NewRepositoryDialog(PassGit app) {
        super();
        
        this.controller = app.getController();

        setTitle("New passGit Repository");

        newRepositoryPanel = new NewRepositoryPanel(app);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        JButton okayButton = new JButton("Okay");

        okayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                controller.newRepository(newRepositoryPanel.getRepository(), newRepositoryPanel.getPassword(), newRepositoryPanel.getKeyFile(), newRepositoryPanel.getFileFormat(), newRepositoryPanel.getCryptographer(), newRepositoryPanel.getInit());
            }

        });

        buttonPanel.add(okayButton);
        
        JButton cancelButton = new JButton("Cancel");
        
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewRepositoryDialog.this.setVisible(false);
            }
            
        });
        
        buttonPanel.add(cancelButton);

        panel.add(newRepositoryPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(panel);
    }
    
    public interface Listener {
        public void onOkay();
        public void onCancel();
    }
    
    private Listener listener;
    
    public void setListener(Listener listener) {
        this.listener = listener;
    }
    
}
