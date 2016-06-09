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
package com.passgit.app.file.action;

import com.passgit.app.Controller;
import com.passgit.app.repository.model.PathModel;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author Eric Hey
 */
public class GitAddAction extends AbstractAction {

    private PathModel pathModel;
    private final Controller controller;
    
    public GitAddAction(Controller controller) {
        super("Git Add");
        
        this.controller = controller;
    }

    public void setPathModel(PathModel pathModel) {
        this.pathModel = pathModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        controller.gitAdd(pathModel);
    }
}
