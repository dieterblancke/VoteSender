package com.dbsoftwares.votesender.utils;

/*
 * Created by DBSoftwares on 14 september 2017
 * Developer: Dieter Blancke
 * Project: VoteSender
 */

import net.md_5.bungee.api.ChatColor;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Utilities {

    public static String format(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static PublicKey getPublicKey(String key) {
        try {
            final byte[] base64Binary = Base64.getDecoder().decode(key);
            final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            final X509EncodedKeySpec keySpec = new X509EncodedKeySpec(base64Binary);

            return keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] encrypt(byte[] data, PublicKey key) throws Exception {
        final Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(1, key);
        return cipher.doFinal(data);
    }

    public static byte[] decrypt(byte[] data, PrivateKey key) throws Exception {
        final Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(2, key);
        return cipher.doFinal(data);
    }
}