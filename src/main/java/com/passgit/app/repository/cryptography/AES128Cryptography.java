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
package com.passgit.app.repository.cryptography;

import java.security.Key;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import com.passgit.app.repository.Cryptography;

/**
 *
 * @author Eric Hey
 */
public class AES128Cryptography implements Cryptography {

    Key key;

    @Override
    public void init(byte[] password) {

        int length = password.length;

        byte[] keyValue = Arrays.copyOf(password, 16);

        for (int i = length; i < 16; i++) {
            keyValue[i] = (byte) (16 - length);
        }

        key = new SecretKeySpec(keyValue, "AES");

    }

    @Override
    public byte[] encrypt(byte[] byteArray) throws Exception {

        Cipher chiper = Cipher.getInstance("AES");
        chiper.init(Cipher.ENCRYPT_MODE, key);

        byte[] encryptedValue = chiper.doFinal(byteArray);
        
        return encryptedValue;

    }

    @Override
    public byte[] decrypt(byte[] byteArray) throws Exception {

        Cipher chiper = Cipher.getInstance("AES");
        chiper.init(Cipher.DECRYPT_MODE, key);

        //byte[] decodedValue = new BASE64Decoder().decodeBuffer(encryptedText);
        byte[] decryptedValue = chiper.doFinal(byteArray);

        //String decryptedText = new String(decryptedValue);
        return decryptedValue;

    }
    
    @Override
    public String toString() {
        return "AES-128";
    }
}
