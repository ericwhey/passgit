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
package com.passgit.app.repository.model;

import java.awt.Color;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.border.LineBorder;
import javax.swing.tree.TreeCellRenderer;

/**
 *
 * @author Eric Hey
 */
public class PathModelTreeRenderer extends JLabel implements TreeCellRenderer {

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        if (value instanceof PathModel) {
            PathModel pathModel = (PathModel)value;
            
            this.setText(pathModel.toString());
            
            if (pathModel.isModified()) {
                this.setIcon(new ImageIcon(getClass().getResource("/com/passgit/app/git-modified.png")));
            } else if (pathModel.isUntracked()) {
                this.setIcon(new ImageIcon(getClass().getResource("/com/passgit/app/git-untracked.png")));
            } else if (pathModel.isDeleted()) {
                this.setIcon(new ImageIcon(getClass().getResource("/com/passgit/app/git-deleted.png")));
            } else if (pathModel.isAdded()) {
                this.setIcon(new ImageIcon(getClass().getResource("/com/passgit/app/git-added.png")));
            } else if (pathModel.isChanged()) {
                this.setIcon(new ImageIcon(getClass().getResource("/com/passgit/app/git-changed.png")));
            } else {
                this.setIcon(null);
            }
            
            if (hasFocus) {
                this.setBorder(new LineBorder(Color.blue));
            } else {
                this.setBorder(null);
            }
            
            if (selected) {
                this.setBackground(Color.yellow);
            } else {
                this.setBackground(null);
            }
        } else {
            this.setText("N/A");
        }
        
        return this;
    }
    
}
