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

import com.passgit.app.repository.model.PathModel;
import java.io.IOException;
import java.nio.file.Path;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Eric Hey
 */
public class FilesystemWatcherThread extends Thread {

    private PassGit app;
    private WatchService watcher;
    
    public void setApp(PassGit app) {
        this.app = app;
    }
    
    public void setWatcher(WatchService watcher) {
        this.watcher = watcher;
    }

    @Override
    public void run() {
        WatchKey key;

        for (;;) {

            // wait for key to be signaled
            try {
                key = watcher.take();
            } catch (InterruptedException x) {
                return;
            }

            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();

                // This key is registered only
                // for ENTRY_CREATE events,
                // but an OVERFLOW event can
                // occur regardless if events
                // are lost or discarded.
                if (kind == OVERFLOW) {
                    continue;
                }

                // The filename is the
                // context of the event.
                WatchEvent<Path> ev = (WatchEvent<Path>) event;
                Path filename = ev.context();

                System.out.println("Detected some event related to " + filename.toAbsolutePath().toString());

                if (kind == ENTRY_CREATE) {

                    Path path = app.getKeys().get(key);

                    Path addedPath = path.resolve(filename);
                    System.out.format("Detected added path %s%n", addedPath);

                    if (!addedPath.toFile().isHidden()) {

                        if (addedPath.toFile().isDirectory()) {

                            try {
                                app.register(addedPath);
                            } catch (IOException ex) {
                                Logger.getLogger(PassGit.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }

                        List<PathModel> recursePath = app.getRootPathModel().recursePath(addedPath);
                        
                        PathModel childPathModel = new PathModel(addedPath);

                        PathModel existingPathModel = recursePath.get(recursePath.size()-1);
                        
                        boolean found = false;
                        
                        if (existingPathModel.equals(childPathModel)) {
                            found = true;
                        }
                        
                        for (int childIndex=0; !found && childIndex<existingPathModel.getChildCount(); childIndex++) {
                            PathModel siblingPathModel = existingPathModel.getChild(childIndex);
                            System.out.println("Comparing " + siblingPathModel.toString() + " with " + childPathModel.toString());

                            if (siblingPathModel.compareTo(childPathModel) > 0) {
                                existingPathModel.addChildAt(childPathModel, childIndex);
                                
                                app.getPathModelTreeModel().fireAddEvent(recursePath, childIndex, childPathModel);
                                
                                found = true;
                            }
                        }
                        
                        if (!found) {
                            
                            existingPathModel.addChild(childPathModel);
                            
                            int childIndex = existingPathModel.getChildCount() - 1;

                            app.getPathModelTreeModel().fireAddEvent(recursePath, childIndex, childPathModel);
                        }

                    }

                } else if (kind == ENTRY_DELETE) {
                    Path path = app.getKeys().get(key);

                    Path deletedPath = path.resolve(filename);
                    System.out.format("Detected deleted path %s%n", deletedPath);

                    List<PathModel> pathModelList = app.getRootPathModel().recursePath(deletedPath);
                    
                    PathModel deletedPathModel = pathModelList.get(pathModelList.size()-1);
                    
                    if (deletedPathModel.isUntracked()) {
                        app.deleteFromPathModelTreeModel(deletedPathModel);
                    } else {
                        deletedPathModel.setDeleted(true);
                        app.getPathModelTreeModel().fireChangedEvent(pathModelList);
                    }

                } else if (kind == ENTRY_MODIFY) {
                    Path path = app.getKeys().get(key);

                    Path modifiedPath = path.resolve(filename);
                    System.out.format("Detected modified path %s%n", modifiedPath);
                }
            }

            // Reset the key -- this step is critical if you want to
            // receive further watch events.  If the key is no longer valid,
            // the directory is inaccessible so exit the loop.
            boolean valid = key.reset();
            if (!valid) {
                break;
            }
        }
    }

}
