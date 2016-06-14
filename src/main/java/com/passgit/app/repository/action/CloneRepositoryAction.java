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
package com.passgit.app.repository.action;

import com.passgit.app.Controller;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author Eric Hey
 */
public class CloneRepositoryAction extends AbstractAction {

    private final Controller controller;
    
    public CloneRepositoryAction(Controller controller) {
        super("Clone");
        
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        controller.cloneRepository();
    }
    
}
