package com.dbsoftwares.votesender.utils;

/*
 * Created by DBSoftwares on 14 september 2017
 * Developer: Dieter Blancke
 * Project: VoteSender
 */

import net.md_5.bungee.api.ChatColor;

import javax.xml.bind.DatatypeConverter;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class Utilities {

    public static String format(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static PublicKey getPublicKey(String key) {
        try {
            byte[] base64Binary = DatatypeConverter.parseBase64Binary(key);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(base64Binary);
            return keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            return null;
        }
    }
}