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
package com.passgit.app.repository.password;

import static com.passgit.app.PassGit.toBytes;
import com.passgit.app.repository.Password;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.BASE64Decoder;

/**
 *
 * @author Eric Hey
 */
public class DefaultPassword implements Password {

    @Override
    public byte[] getKey(char[] password, File keyFile) {

        byte[] key;
        

        try {

            byte[] keyFilePassword;
            
            if (keyFile != null) {
                BufferedReader reader = new BufferedReader(new FileReader(keyFile));

                String line = reader.readLine();

                keyFilePassword = new BASE64Decoder().decodeBuffer(line);
            } else {
                keyFilePassword = new byte[0];
            }

            if (password.length > 0 && keyFilePassword.length > 0) {
                MessageDigest md = MessageDigest.getInstance("SHA-256");

                ByteBuffer bytes = ByteBuffer.allocate(password.length + keyFilePassword.length);
                bytes.put(toBytes(password));
                bytes.put(keyFilePassword);
                key = md.digest(bytes.array());
            } else if (keyFilePassword.length > 0) {
                key = keyFilePassword;
            } else {
                key = toBytes(password);
            }

            return key;
        } catch (IOException ex) {
            Logger.getLogger(DefaultPassword.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(DefaultPassword.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

}
