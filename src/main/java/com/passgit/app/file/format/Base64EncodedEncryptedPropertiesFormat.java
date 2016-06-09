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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import com.passgit.app.repository.cryptography.AES128Cryptography;
import com.passgit.app.file.Format;
import com.passgit.app.repository.Cryptography;

/**
 *
 * @author Eric Hey
 */
public class Base64EncodedEncryptedPropertiesFormat implements Format {

    @Override
    public Map<String,Value> loadValues(PassGit app, PathModel pathModel) {
        Map<String,Value> values = new HashMap<String,Value>();
                
        Properties properties = new Properties();

        File thisFile = pathModel.getFile();

        if (thisFile.exists()) {
            try {

                BufferedReader reader = new BufferedReader(new FileReader(thisFile));

                properties.load(reader);
                
                if (properties.containsKey("title")) {
                    values.put("title", new EncryptedByteArrayValue(app, new BASE64Decoder().decodeBuffer(properties.getProperty("title"))));
                } 
                
                if (properties.containsKey("url")) {
                    values.put("url", new EncryptedByteArrayValue(app, new BASE64Decoder().decodeBuffer(properties.getProperty("url"))));
                }
                
                if (properties.containsKey("username")) {
                    values.put("username", new EncryptedByteArrayValue(app, new BASE64Decoder().decodeBuffer(properties.getProperty("username"))));
                }
                
                if (properties.containsKey("password")) {
                    values.put("password", new EncryptedByteArrayValue(app, new BASE64Decoder().decodeBuffer(properties.getProperty("password"))));
                }

            } catch (IOException ex) {
                Logger.getLogger(Base64EncodedEncryptedPropertiesFormat.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return values;
    }

    @Override
    public void saveValues(PassGit app, PathModel pathModel, Map<String,Value> values) {
        try {
            FileWriter writer = new FileWriter(pathModel.getFile());
            
            Properties properties = new Properties();
            if (values.containsKey("title")) {
                Value value = values.get("title");
                if (value instanceof EncryptedByteArrayValue) {
                    EncryptedByteArrayValue encryptedByteArrayValue = (EncryptedByteArrayValue)value;
                    properties.setProperty("title", new BASE64Encoder().encode(encryptedByteArrayValue.getEncryptedByteArray()));
                } else {
                    try {
                        properties.setProperty("title", new BASE64Encoder().encode(app.getCryptographer().encrypt(value.getString().getBytes())));
                    } catch (Exception ex) {
                        Logger.getLogger(Base64EncodedEncryptedPropertiesFormat.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
            if (values.containsKey("url")) {
                Value value = values.get("url");
                if (value instanceof EncryptedByteArrayValue) {
                    EncryptedByteArrayValue encryptedByteArrayValue = (EncryptedByteArrayValue)value;
                    properties.setProperty("url", new BASE64Encoder().encode(encryptedByteArrayValue.getEncryptedByteArray()));
                } else {
                    try {
                        properties.setProperty("url", new BASE64Encoder().encode(app.getCryptographer().encrypt(value.getString().getBytes())));
                    } catch (Exception ex) {
                        Logger.getLogger(Base64EncodedEncryptedPropertiesFormat.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
            if (values.containsKey("username")) {
                Value value = values.get("username");
                if (value instanceof EncryptedByteArrayValue) {
                    EncryptedByteArrayValue encryptedByteArrayValue = (EncryptedByteArrayValue)value;
                
                    properties.setProperty("username", new BASE64Encoder().encode(encryptedByteArrayValue.getEncryptedByteArray()));
                } else {
                    try {
                        properties.setProperty("username", new BASE64Encoder().encode(app.getCryptographer().encrypt(value.getString().getBytes())));
                    } catch (Exception ex) {
                        Logger.getLogger(Base64EncodedEncryptedPropertiesFormat.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            if (values.containsKey("password")) { 
                properties.setProperty("password", new BASE64Encoder().encode(((EncryptedByteArrayValue)values.get("password")).getEncryptedByteArray()));
            }
            
            properties.store(writer, new Date().toString());
        } catch (IOException ex) {
            Logger.getLogger(Base64EncodedEncryptedPropertiesFormat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public String toString() {
        return "Encrypted Properties File Format";
    }

    @Override
    public Cryptography getSuggestedCryptographer() {
        return new AES128Cryptography();
    }
}
