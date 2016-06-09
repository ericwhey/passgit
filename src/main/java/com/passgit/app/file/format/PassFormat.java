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
package com.passgit.app.file.format;

import com.passgit.app.file.value.EncryptedByteArrayValue;
import com.passgit.app.PassGit;
import com.passgit.app.repository.model.PathModel;
import com.passgit.app.file.Value;
import com.passgit.app.repository.cryptography.CAST5Cryptography;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import com.passgit.app.file.Format;
import com.passgit.app.repository.Cryptography;

/**
 *
 * @author Eric Hey
 */
public class PassFormat implements Format {

    @Override
    public Map<String, Value> loadValues(PassGit app, PathModel pathModel) {
        Map<String, Value> values = new HashMap<String, Value>();

        try (BufferedReader reader = new BufferedReader(new FileReader(pathModel.getFile()))) {
            boolean firstLine = true;
            String line;
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    values.put("password", new EncryptedByteArrayValue(app, new BASE64Decoder().decodeBuffer(line)));
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(PassMultilineFormat.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return values;
    }

    @Override
    public void saveValues(PassGit app, PathModel pathModel, Map<String, Value> values) {
        try (FileWriter writer = new FileWriter(pathModel.getFile())) {
            if (values.containsKey("password")) {
                writer.write(new BASE64Encoder().encode(((EncryptedByteArrayValue) values.get("password")).getEncryptedByteArray()));
                writer.write('\n');
            } 
        } catch (IOException ex) {
            Logger.getLogger(PassMultilineFormat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        
    @Override
    public String toString() {
        return "Pass File Format";
    }

    
    @Override
    public Cryptography getSuggestedCryptographer() {
        return new CAST5Cryptography();
    }
    
}
