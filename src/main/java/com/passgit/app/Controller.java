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

import com.passgit.app.file.Format;
import com.passgit.app.file.action.AddDirectoryAction;
import com.passgit.app.file.action.AddFileAction;
import com.passgit.app.file.action.CopyPasswordToClipboardAction;
import com.passgit.app.file.action.DeleteAction;
import com.passgit.app.file.action.EditFileAction;
import com.passgit.app.file.action.GitAddAction;
import com.passgit.app.file.action.GitRemoveAction;
import com.passgit.app.repository.Cryptography;
import com.passgit.app.repository.action.CloseRepositoryAction;
import com.passgit.app.repository.action.CommitRepositoryAction;
import com.passgit.app.repository.action.ConvertRepositoryAction;
import com.passgit.app.repository.action.NewRepositoryAction;
import com.passgit.app.repository.action.OpenRepositoryAction;
import com.passgit.app.repository.action.PullRepositoryAction;
import com.passgit.app.repository.action.PushRepositoryAction;
import com.passgit.app.repository.model.PathModel;
import java.io.File;
import java.nio.file.Path;

/**
 *
 * @author Eric Hey
 */
public class Controller {
    private final PassGit app;
  
    private final NewRepositoryAction newRepositoryAction;
    private final OpenRepositoryAction openRepositoryAction;
    private final CloseRepositoryAction closeRepositoryAction;
    private final CommitRepositoryAction commitRepositoryAction;
    private final PushRepositoryAction pushRepositoryAction;
    private final PullRepositoryAction pullRepositoryAction;
    private final ConvertRepositoryAction convertRepositoryAction;
    
    private final AddDirectoryAction addDirectoryAction;
    private final AddFileAction addFileAction;
    private final CopyPasswordToClipboardAction copyPasswordToClipboardAction;
    private final DeleteAction deleteAction;
    private final EditFileAction editFileAction;
    
    private final GitAddAction gitAddAction;
    private final GitRemoveAction gitRemoveAction;
    
    public NewRepositoryAction getNewRepositoryAction() {
        return newRepositoryAction;
    }
    
    public OpenRepositoryAction getOpenRepositoryAction() {
        return openRepositoryAction;
    }
    
    public CloseRepositoryAction getCloseRepositoryAction() {
        return closeRepositoryAction;
    }
    
    public CommitRepositoryAction getCommitRepositoryAction() {
        return commitRepositoryAction;
    }
    
    public PushRepositoryAction getPushRepositoryAction() {
        return pushRepositoryAction;
    }
    
    public PullRepositoryAction getPullRepositoryAction() {
        return pullRepositoryAction;
    }
    
    public ConvertRepositoryAction getConvertRepositoryAction() {
        return convertRepositoryAction;
    }
    
    public AddDirectoryAction getAddDirectoryAction() {
        return addDirectoryAction;
    }
    
    public AddFileAction getAddFileAction() {
        return addFileAction;
    }
    
    public CopyPasswordToClipboardAction getCopyPasswordToClipboardAction() {
        return copyPasswordToClipboardAction;
    }
    
    public DeleteAction getDeleteAction() {
        return deleteAction;
    }
    
    public EditFileAction getEditFileAction() {
        return editFileAction;
    }
    
    public GitAddAction getGitAddAction() {
        return gitAddAction;
    }
    
    public GitRemoveAction getGitRemoveAction() {
        return gitRemoveAction;
    }
    
    public Controller(PassGit app) {
        this.app = app;
        
        newRepositoryAction = new NewRepositoryAction(this);
        closeRepositoryAction = new CloseRepositoryAction(this);
        openRepositoryAction = new OpenRepositoryAction(this);
        commitRepositoryAction = new CommitRepositoryAction(this);
        pushRepositoryAction = new PushRepositoryAction(this);
        pullRepositoryAction = new PullRepositoryAction(this);
        convertRepositoryAction = new ConvertRepositoryAction(this);
        
        addDirectoryAction = new AddDirectoryAction(this);
        addFileAction = new AddFileAction(this);
        copyPasswordToClipboardAction = new CopyPasswordToClipboardAction(this);
        deleteAction = new DeleteAction(this);
        editFileAction = new EditFileAction(this);
        
        gitAddAction = new GitAddAction(this);
        gitRemoveAction = new GitRemoveAction(this);
    }
    
    public void openNewRepository() {
        app.openNewRepositoryDialog();
    }
    
    public void newRepository(Path rootPath, char[] password, File keyFile, Format fileFormat, Cryptography cryptography, boolean init) {
        app.setRootPath(rootPath);
        
        if (app.repositoryExists()) {
            if (app.newRepository(password, keyFile, fileFormat, cryptography, init)) {
                showRepository();
            }
        } else {
            if (app.openCreateRepositoryDirectoryDialog()) {
                if (app.createRepositoryDirectory(rootPath)) {
                    if (app.newRepository(password, keyFile, fileFormat, cryptography, init)) {
                        showRepository();
                    }
                }
            }
        }
    }
    
    public void openRepository() {
        app.openRepositoryDialog();   
    }
    
    public void openRepository(Path rootPath, char[] password, File keyFile) {
        app.setRootPath(rootPath);
        
        if (app.repositoryExists()) {
            if (app.isRepositoryIntialized()) {
                if (app.openRepository(password, keyFile)) {
                    showRepository();
                }
            } else {
                if (app.initializeRepository(password, keyFile)) {
                    showRepository();
                }
            }
        } else {
            if (app.openCreateRepositoryDirectoryDialog()) {
                if (app.createRepositoryDirectory(rootPath)) {
                    if (app.initializeRepository(password, keyFile)) {
                        showRepository();
                    }
                }
            }
        }
    }
    
    private void showRepository() {
        closeRepositoryAction.setEnabled(true);
        commitRepositoryAction.setEnabled(app.isGitRepository());            
        pushRepositoryAction.setEnabled(app.isGitRepository());
        pullRepositoryAction.setEnabled(app.isGitRepository());
        convertRepositoryAction.setEnabled(false);
        app.showRepository();
    }
    
    public void closeRepository() {
        closeRepositoryAction.setEnabled(false);
        commitRepositoryAction.setEnabled(false);            
        pushRepositoryAction.setEnabled(false);
        pullRepositoryAction.setEnabled(false);
        convertRepositoryAction.setEnabled(false);
        app.closeRepository();
    }
    
    public void commitRepository() {
        app.commitRepository();
    }
    
    public void pushRepository() {
        
    }
    
    public void pullRepository() {
        
    }
    
    public void convertRepository() {
        
    }
    
    public void addDirectory(PathModel pathModel) {
        app.addDirectory(pathModel);
    }
    
    public void addFile(PathModel pathModel) {
        app.addFile(pathModel);
    }
    
    public void delete(PathModel pathModel) {
        app.delete(pathModel);
    }
    
    public void editFile(PathModel pathModel) {
        app.openEditDialog(pathModel);
    }
    
    public void copyPasswordToClipboard(PathModel pathModel) {
        app.copyPasswordToClipboard(pathModel);
    }
    
    public void gitAdd(PathModel pathModel) {
        app.gitAdd(pathModel);
    }
    
    public void gitRemove(PathModel pathModel) {
        app.gitRemove(pathModel);
    }
}
