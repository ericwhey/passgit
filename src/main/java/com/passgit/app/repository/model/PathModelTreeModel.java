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

import java.util.ArrayList;
import java.util.List;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author Eric Hey
 */
public class PathModelTreeModel implements TreeModel {
    
    private PathModel root;

    public PathModelTreeModel(PathModel root) {
        this.root = root;
    }

    @Override
    public Object getRoot() {
        return root;
    }

    @Override
    public Object getChild(Object parent, int index) {
        PathModel pathModel = (PathModel)parent;
        
        return pathModel.getChild(index);
    }

    @Override
    public int getChildCount(Object parent) {
        PathModel pathModel = (PathModel)parent;
        
        return pathModel.getChildCount();
    }

    @Override
    public boolean isLeaf(Object node) {
        PathModel pathModel = (PathModel)node;
        
        return pathModel.isFile();
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
        
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        PathModel pathModel = (PathModel)parent;
        
        return pathModel.getIndexOfChild(child);
    }
    
    private final List<TreeModelListener> listeners = new ArrayList<TreeModelListener>();

    @Override
    public void addTreeModelListener(TreeModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {
        listeners.remove(l);
    }
    
    public void fireChangedEvent(List<PathModel> path) {
        for (TreeModelListener l : listeners) {
            TreeModelEvent event = new TreeModelEvent(this, path.toArray());
            l.treeNodesChanged(event);
        }
    }
    
    public void fireAddEvent(List<PathModel> path, int index, PathModel object) {
        for (TreeModelListener l : listeners) {
            TreeModelEvent event = new TreeModelEvent(this, path.toArray(), new int[] { index }, new Object[] { object });
            l.treeNodesInserted(event);
        }
    }
    
    public void fireRemoveEvent(List<PathModel> path, int index) {
        for (TreeModelListener l : listeners) {
            TreeModelEvent event = new TreeModelEvent(this, path.toArray(), new int[] { index }, new Object[] {});
            l.treeNodesRemoved(event);
        }
    }
    
}
