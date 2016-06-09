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
package com.passgit.app.file.value;

import com.passgit.app.PassGit;
import com.passgit.app.file.Value;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Eric Hey
 */
public class EncryptedByteArrayValue implements Value {
    
    private PassGit app;
    private byte[] value;
    
    public EncryptedByteArrayValue(PassGit app, byte[] value) {
        this.app = app;
        this.value = value;
    }

    public byte[] getDecryptedByteArray() {
        try {
            return app.getCryptographer().decrypt(value);
        } catch (Exception ex) {
            Logger.getLogger(EncryptedStringValue.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         
        return new byte[0];
    }
    
    public byte[] getEncryptedByteArray() {
        return value;
    }

    @Override
    public String getString() {
        try {
            return new String(app.getCryptographer().decrypt(value));
        } catch (Exception ex) {
            Logger.getLogger(EncryptedStringValue.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    @Override
    public void setString(String value) {
        try {
            this.value = app.getCryptographer().encrypt(value.getBytes());
        } catch (Exception ex) {
            Logger.getLogger(EncryptedByteArrayValue.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setCharArray(char[] value) {
        try {
            this.value = app.getCryptographer().encrypt(PassGit.toBytes(value));
        } catch (Exception ex) {
            Logger.getLogger(EncryptedByteArrayValue.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
