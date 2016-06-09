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
package com.passgit.app.repository.listener;

import com.passgit.app.Controller;
import com.passgit.app.PassGit;
import com.passgit.app.file.action.AddDirectoryAction;
import com.passgit.app.file.action.AddFileAction;
import com.passgit.app.file.action.CopyPasswordToClipboardAction;
import com.passgit.app.file.action.DeleteAction;
import com.passgit.app.file.action.EditFileAction;
import com.passgit.app.file.action.GitAddAction;
import com.passgit.app.file.action.GitRemoveAction;
import com.passgit.app.repository.model.PathModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;

/**
 *
 * @author Eric Hey
 */
public class PathModelTreeMouseListener extends MouseAdapter {

    private PassGit app;
    private JTree tree;
    
    private final AddFileAction addFileAction;
    private final AddDirectoryAction addDirectoryAction;
    private final EditFileAction editFileAction;
    private final DeleteAction deleteAction;
    private final CopyPasswordToClipboardAction copyPasswordAction;
    private final GitAddAction gitAddAction;
    private final GitRemoveAction gitRemoveAction;
    
    public PathModelTreeMouseListener(PassGit app, JTree tree) {
        this.app = app;
        this.tree = tree;
        
        Controller controller = app.getController();
        
        addFileAction = controller.getAddFileAction();
        addDirectoryAction = controller.getAddDirectoryAction();
        editFileAction = controller.getEditFileAction();
        deleteAction = controller.getDeleteAction();
        copyPasswordAction = controller.getCopyPasswordToClipboardAction();
        gitAddAction = controller.getGitAddAction();
        gitRemoveAction = controller.getGitRemoveAction();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            TreePath selectedPath = tree.getPathForLocation(e.getX(), e.getY());
            
            final PathModel pathModel = (PathModel) selectedPath.getLastPathComponent();

            JPopupMenu popupMenu = new JPopupMenu();
            
            if (pathModel.isDirectory()) {
                addFileAction.setPathModel(pathModel);
           
                popupMenu.add(new JMenuItem(addFileAction));
            
                addDirectoryAction.setPathModel(pathModel);

                popupMenu.add(new JMenuItem(addDirectoryAction));
            }
            
            if (pathModel.isFile()) {
                editFileAction.setPathModel(pathModel);

                popupMenu.add(new JMenuItem(editFileAction));
            }
            
            deleteAction.setPathModel(pathModel);

            popupMenu.add(new JMenuItem(deleteAction));
            
            if (pathModel.isFile()) {
            
                copyPasswordAction.setPathModel(pathModel);

                popupMenu.add(new JMenuItem(copyPasswordAction));
            }
            
            if (app.isGitRepository()) {
                gitAddAction.setPathModel(pathModel);
            
                popupMenu.add(new JMenuItem(gitAddAction));
                
                gitRemoveAction.setPathModel(pathModel);
                popupMenu.add(new JMenuItem(gitRemoveAction));
            }

            popupMenu.show(e.getComponent(), e.getX(), e.getY());
        } else if (e.getClickCount() == 2) {
            TreePath selectedPath = tree.getPathForLocation(e.getX(), e.getY());
            final PathModel pathModel = (PathModel) selectedPath.getLastPathComponent();

            app.openEditDialog(pathModel);
        }
    }
}
