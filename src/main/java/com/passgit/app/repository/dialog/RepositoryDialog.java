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
public class RepositoryDialog extends JDialog {
    
    private RepositoryPanel passwordPanel;
    
    public RepositoryDialog(final Controller controller, Path rootPath, String keyFileFilename) {
        super();

        setTitle("PassGit Repository/Password");

        passwordPanel = new RepositoryPanel();

        passwordPanel.setRepository(rootPath);
        passwordPanel.setKeyFileFilename(keyFileFilename);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        JButton okayButton = new JButton("Okay");

        okayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                controller.openRepository(passwordPanel.getRepository(), passwordPanel.getPassword(), passwordPanel.getKeyFile());
            }

        });

        buttonPanel.add(okayButton);

        panel.add(passwordPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        passwordPanel.addListener(new RepositoryPanel.Listener() {
            @Override
            public void onOkay() {
                controller.openRepository(passwordPanel.getRepository(), passwordPanel.getPassword(), passwordPanel.getKeyFile());
            }
        });

        setContentPane(panel);
    }
    
    public void setFocus() {
        passwordPanel.setFocus();
    }
}
