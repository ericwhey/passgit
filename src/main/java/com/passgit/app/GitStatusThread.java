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

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Eric Hey
 */
public class GitStatusThread extends Thread {

    private final PassGit app;
    
    public GitStatusThread(PassGit app) {
        this.app = app;
    }
    
    @Override
    public void run() {
       
        boolean stop = false;
        
        while (!stop) {
            try {
                Thread.sleep(5000);
                app.updateStatus();
            } catch (InterruptedException ex) {
                stop = true;
                Logger.getLogger(GitStatusThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
