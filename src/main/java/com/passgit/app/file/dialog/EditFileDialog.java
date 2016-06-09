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

import com.passgit.app.MainFrame;
import com.passgit.app.PassGit;
import com.passgit.app.file.Value;
import com.passgit.app.repository.model.PathModel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 *
 * @author Eric Hey
 */
public class EditFileDialog extends JDialog {

    public EditFileDialog(PassGit app, MainFrame mainFrame, PathModel pathModel, Map<String,Value> values) {
        super(mainFrame);

        final EditFilePanel editPassPanel = new EditFilePanel(app, pathModel, values);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        panel.add(editPassPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        final JButton saveButton = new JButton("Save");
        saveButton.setEnabled(false);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editPassPanel.save();
                EditFileDialog.this.setVisible(false);
            }

        });

        buttonPanel.add(saveButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditFileDialog.this.setVisible(false);
            }

        });

        buttonPanel.add(cancelButton);

        editPassPanel.addListener(new EditFilePanel.Listener() {
            @Override
            public void onValidated(boolean valid) {
                saveButton.setEnabled(valid);
            }
        });

        panel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(panel);
        setTitle(pathModel.toString());
        setModal(true);
    }
}
