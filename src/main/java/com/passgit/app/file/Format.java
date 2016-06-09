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
package com.passgit.app.file;

import com.passgit.app.PassGit;
import com.passgit.app.repository.model.PathModel;
import java.util.Map;
import com.passgit.app.repository.Cryptography;

/**
 *
 * @author Eric Hey
 */
public interface Format {
    public Map<String,Value> loadValues(PassGit app, PathModel pathModel);
    public void saveValues(PassGit app, PathModel pathModel, Map<String,Value> properties);
    public Cryptography getSuggestedCryptographer();
}
