package com.peliculas.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import org.primefaces.util.Base64;

/**
 * Clase donde se encuentran los métodos para encriptar y desencriptar
 *
 *
 * @author csacanam
 */
public class CipherAlgorithms
{

    private static final char[] PASSWORD = "enfldsgbnlsngdlksdsgm".toCharArray();
    private static final byte[] SALT =
    {
        (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12,
        (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12,
    };

    private CipherAlgorithms()
    {

    }

    /**
     * Permite encriptar una cadena
     * @param property Cadena que se desea encriptar
     * @return Cadena encriptada
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException 
     */
    public static String encrypt(String property) throws GeneralSecurityException, UnsupportedEncodingException
    {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
        pbeCipher.init(Cipher.ENCRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
        return Base64.encodeToString(pbeCipher.doFinal(property.getBytes("UTF-8")),false);
    }


    /**
     * Permite desencriptar una cadena
     * @param property Texto encriptado
     * @return Texto desencriptado
     * @throws GeneralSecurityException
     * @throws IOException 
     */
    public static String decrypt(String property) throws GeneralSecurityException, IOException
    {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
        pbeCipher.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
        return new String(pbeCipher.doFinal(Base64.decode(property)), "UTF-8");
    }

  

}
