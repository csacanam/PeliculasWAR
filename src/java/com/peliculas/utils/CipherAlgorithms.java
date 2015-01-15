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
 * Se tenía un problema que decía que no se podía encriptar o desencriptar si la
 * entrada no era múltiplo de 16 bytes. La solución se encontró acá:
 * http://stackoverflow.com/questions/17567996/illegal-block-size-exception-input-length-must-be-multiple-of-16-when-decrypting
 *
 * Se usó la librería de Codes de Apache para codificar y de codificar cadenas
 * con base a 64 bytes. Esta última se encuentra disponible en:
 * http://commons.apache.org/proper/commons-codec/download_codec.cgi
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

    public static String encrypt(String property) throws GeneralSecurityException, UnsupportedEncodingException
    {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
        pbeCipher.init(Cipher.ENCRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
        return Base64.encodeToString(pbeCipher.doFinal(property.getBytes("UTF-8")),false);
    }


    public static String decrypt(String property) throws GeneralSecurityException, IOException
    {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
        pbeCipher.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
        return new String(pbeCipher.doFinal(Base64.decode(property)), "UTF-8");
    }

  

}
