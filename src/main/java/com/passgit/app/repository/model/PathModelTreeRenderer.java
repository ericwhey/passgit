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
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.border.LineBorder;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

/**
 *
 * @author Eric Hey
 */
public class PathModelTreeRenderer extends DefaultTreeCellRenderer {

    private final Icon gitModifiedIcon = new ImageIcon(getClass().getResource("/com/passgit/app/git-modified.png"));
    private final Icon gitUntrackedIcon = new ImageIcon(getClass().getResource("/com/passgit/app/git-untracked.png"));
    private final Icon gitDeletedIcon = new ImageIcon(getClass().getResource("/com/passgit/app/git-deleted.png"));
    private final Icon gitAddedIcon = new ImageIcon(getClass().getResource("/com/passgit/app/git-added.png"));
    private final Icon gitChangedIcon = new ImageIcon(getClass().getResource("/com/passgit/app/git-changed.png"));
    
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        Component c = super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
        
        if (value instanceof PathModel) {
            PathModel pathModel = (PathModel)value;
 
            if (pathModel.isModified()) {
                this.setIcon(gitModifiedIcon);
            } else if (pathModel.isUntracked()) {
                this.setIcon(gitUntrackedIcon);
            } else if (pathModel.isDeleted()) {
                this.setIcon(gitDeletedIcon);
            } else if (pathModel.isAdded()) {
                this.setIcon(gitAddedIcon);
            } else if (pathModel.isChanged()) {
                this.setIcon(gitChangedIcon);
            } else {
                this.setIcon(null);
            }

        }
        
        return c;
    }
    
}
