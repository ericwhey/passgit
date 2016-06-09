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

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Eric Hey
 */
public class PathModel {

    private boolean file;
    private boolean added = false;
    private boolean changed = false;
    private boolean untracked = false;
    private boolean modified = false;
    private boolean deleted = false;

    private final List<PathModel> children = new ArrayList<PathModel>();

    private Path path;

    public PathModel(Path path) {
        this.path = path;

        File thisFile = path.toFile();

        if (thisFile.isDirectory()) {

            /*try {

                WatchKey key = path.register(watcher,
                        ENTRY_CREATE,
                        ENTRY_DELETE,
                        ENTRY_MODIFY);

            } catch (IOException ex) {
                Logger.getLogger(PathModel.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            for (File childFile : thisFile.listFiles()) {
                if (!childFile.isHidden()) {
                
                    children.add(new PathModel(childFile.toPath()));
                    
                }
            }

            file = false;
        } else {
            file = true;
        }
    }

   

    public PathModel getChild(int index) {
        return children.get(index);
    }

    public void addChild(PathModel childPathModel) {
        children.add(childPathModel);
    }

    public void addChildAt(PathModel childPathModel, int index) {
        children.add(index, childPathModel);
    }

    public int getChildCount() {
        return children.size();
    }

    public int getIndexOfChild(Object child) {
        return children.indexOf(child);
    }

    public void removeChild(int index) {
        children.remove(index);
    }

    public boolean isFile() {
        return file;
    }

    public boolean isDirectory() {
        return !file;
    }

    public boolean exists() {
        File thisFile = path.toFile();

        return thisFile.exists();
    }

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }
    
    
    
    public boolean isUntracked() {
        return untracked;
    }

    public void setUntracked(boolean untracked) {
        this.untracked = untracked;
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    
    @Override
    public String toString() {
        return path.toFile().getName();
    }

    public File getFile() {
        return path.toFile();
    }
    
    public Path getPath() {
        return path;
    }

    public boolean onPath(Path fullPath) {
        return fullPath.startsWith(path);
    }

    public List<PathModel> recursePath(Path fullPath) {
        if (onPath(fullPath)) {
            List<PathModel> pathList = new ArrayList<PathModel>();

            pathList.add(this);

            for (PathModel childPathModel : children) {
                childPathModel.recursePath(fullPath, pathList);
            }

            return pathList;
        } else {
            return null;
        }
    }

    public List<PathModel> recursePath(Path fullPath, List<PathModel> pathList) {
        if (onPath(fullPath)) {
            pathList.add(this);

            for (PathModel childPathModel : children) {
                childPathModel.recursePath(fullPath, pathList);
            }
        }

        return pathList;
    }
}
