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
package com.passgit.app.repository.digest;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import com.passgit.app.repository.Digest;

/**
 *
 * @author Eric Hey
 */
public class HmacSHA1MACDigester implements Digest {

    private Mac mac;

    @Override
    public void init(byte[] password) {
        try {
            //KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA1");
            SecretKey key = new SecretKeySpec(password, "HmacSHA1");
            //SecretKey key = keyGenerator.generateKey();
            
            mac = Mac.getInstance(key.getAlgorithm());
            
            mac.init(key);
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(HmacSHA1MACDigester.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(HmacSHA1MACDigester.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public byte[] getDigest(byte[] password) {
        mac.reset();
        byte [] digest = mac.doFinal(password);
        
        return digest;
    }
}
