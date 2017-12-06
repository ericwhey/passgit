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

import com.passgit.app.file.dialog.EditFilePanel;
import com.passgit.app.MainFrame;
import com.passgit.app.PassGit;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import com.passgit.app.repository.Cryptography;

/**
 *
 * @author Eric Hey
 */
public class AES256Cryptography implements Cryptography {

    private Cipher cipher;
    private SecretKeySpec secretKey;
    //private PBEParameterSpec pbeParamSpec;

    public Cipher getCipher() {
        return cipher;
    }

    public SecretKeySpec getKey() {
        return secretKey;
    }

    @Override
    public void init(byte[] password) {
        byte[] salt = {
            (byte) 0xc7, (byte) 0x73, (byte) 0x21, (byte) 0x8c,
            (byte) 0x7e, (byte) 0xc8, (byte) 0xee, (byte) 0x99
        };

        char[] keyValue = new char[password.length + (password.length % 8)];

        for (int i = 0; i < password.length; i++) {
            keyValue[i] = (char) password[i];
        }

        if (password.length % 8 == 7) {
            for (int i = 0; i < 1; i++) {
                keyValue[password.length + i] = (char) 0x01;
            }
        }
        if (password.length % 8 == 6) {
            for (int i = 0; i < 2; i++) {
                keyValue[password.length + i] = (char) 0x02;
            }
        }
        if (password.length % 8 == 5) {
            for (int i = 0; i < 3; i++) {
                keyValue[password.length + i] = (char) 0x03;
            }
        }
        if (password.length % 8 == 4) {
            for (int i = 0; i < 4; i++) {
                keyValue[password.length + i] = (char) 0x04;
            }
        }
        if (password.length % 8 == 3) {
            for (int i = 0; i < 5; i++) {
                keyValue[password.length + i] = (char) 0x05;
            }
        }
        if (password.length % 8 == 2) {
            for (int i = 0; i < 6; i++) {
                keyValue[password.length + i] = (char) 0x06;
            }
        }
        if (password.length % 8 == 1) {
            for (int i = 0; i < 7; i++) {
                keyValue[password.length + i] = (char) 0x07;
            }
        }

        //int count = 20;

        PBEKeySpec keySpec = new PBEKeySpec(keyValue, salt, 50, 256);

        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithSHA256And256BitAES-CBC-BC");
            //key = keyFactory.generateSecret(keySpec);

            secretKey = new SecretKeySpec(keyFactory.generateSecret(keySpec).getEncoded(), "AES");
            //.(pbeKeySpec, "AES");

            //pbeParamSpec = new PBEParameterSpec(salt, count);
            cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");

            //cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(PassGit.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(PassGit.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(PassGit.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(PassGit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public byte[] encrypt(byte[] valueToEnc) {

        Cipher cipher = this.getCipher();
        try {

            cipher.init(Cipher.ENCRYPT_MODE, this.getKey());//, this.getParamSpec());
            byte[] encValue = cipher.doFinal(valueToEnc);
            //String encryptedValue = new BASE64Encoder().encode(encValue);

            return encValue;
        } catch (InvalidKeyException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);

        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);

            //} catch (InvalidAlgorithmParameterException ex) {
            //   Logger.getLogger(PassFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public byte[] decrypt(byte[] valueToDec) {
        Cipher cipher = this.getCipher();
        try {
            //byte[] passwordCipher = new BASE64Decoder().decodeBuffer(valueToDec);

            cipher.init(Cipher.DECRYPT_MODE, this.getKey()); //, this.getParamSpec());

            int ctLength = valueToDec.length;

            byte[] plainText = new byte[cipher.getOutputSize(ctLength)];
            int ptLength = cipher.update(valueToDec, 0, ctLength, plainText, 0);
            ptLength += cipher.doFinal(plainText, ptLength);

            return plainText;
        } catch (InvalidKeyException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);

        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);

            //} catch (InvalidAlgorithmParameterException ex) {
            //    Logger.getLogger(PassFrame.class.getName()).log(Level.SEVERE, null, ex);
        
        } catch (ShortBufferException ex) {
            Logger.getLogger(EditFilePanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    
    @Override
    public String toString() {
        return "AES-256";
    }

}
