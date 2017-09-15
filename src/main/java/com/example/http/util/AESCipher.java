package com.example.http.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import net.sf.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
public class AESCipher {
     
//    private static final String IV_STRING = "Da08_Dbay23_Bhan";
    private static final String charset = "UTF-8";
      
    public static String aesEncryptString(String content, String key,String aesIv) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        byte[] contentBytes = content.getBytes(charset);
        byte[] keyBytes = key.getBytes(charset);
        byte[] encryptedBytes = aesEncryptBytes(contentBytes, keyBytes,aesIv);
        String encryptResultStr = Hex.encodeHexString(encryptedBytes); 
//        String result = new String(encryptedBytes,"UTF-8"); 
//        BASE64Encoder base64 = new   BASE64Encoder();	 
//	    return  base64.encode(encryptedBytes);
	    return encryptResultStr;
//        Encoder encoder = Base64.getEncoder();
//        return encoder.encodeToString(encryptedBytes);
    }
    public static String aesDecryptString(String content, String key,String aesIv) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException, DecoderException {
    	byte[] hexContent = Hex.decodeHex(content.toCharArray()); 
    	byte[] keyBytes = key.getBytes(charset);
        byte[] decryptedBytes = aesDecryptBytes(hexContent, keyBytes,aesIv);
//        String result = new String(decryptedBytes, "UTF-8");
        return new String(decryptedBytes, charset);    
    }
     
    public static byte[] aesEncryptBytes(byte[] contentBytes, byte[] keyBytes,String aesIv) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        return cipherOperation(contentBytes, keyBytes, Cipher.ENCRYPT_MODE,aesIv);
    }
     
    public static byte[] aesDecryptBytes(byte[] contentBytes, byte[] keyBytes,String aesIv) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        return cipherOperation(contentBytes, keyBytes, Cipher.DECRYPT_MODE,aesIv);
    }
     
    private static byte[] cipherOperation(byte[] contentBytes, byte[] keyBytes, int mode,String aesIv) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES"); 
        byte[] initParam = aesIv.getBytes(charset);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(mode, secretKey, ivParameterSpec);
        return cipher.doFinal(contentBytes);
    }
    
    public static String aesEncryptObject(Object obj,String aesKey,String aesIv) throws Exception{
    	JSONObject encrjson = JSONObject.fromObject(obj);
	    String cSrc = encrjson.toString();
	    String result = AESCipher.aesEncryptString(cSrc, aesKey,aesIv);
		return result;
    }
    public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException, DecoderException {
    	 String s= "{\"device_type\":\"2\",\"app_version\":\"1.0\",\"access_token\":\"4647b4f7-1d23-4fbe-907e-af68bd4b6732\"}";
    	 String s1= AESCipher.aesEncryptString(s,"Hu08an_gDSha_n23","Da08_Dbay23_Bhan");
    	 //String s1="2ad6a6a35224b27495f36d65ce433a3d626270a45327772fe1a1724ff6e6f7dd462d6a95a3305b35fbf2bb7df069ec12c95c6e180f84c51e0baa64205b896a2ddfd4bfea5ca86e27b877e2ec70274613";
         System.out.println(s1);
    	 String s2 = AESCipher.aesDecryptString(s1,"Hu08an_gDSha_n23","Da08_Dbay23_Bhan");
    	 System.out.println(s2);
    	
	}
}