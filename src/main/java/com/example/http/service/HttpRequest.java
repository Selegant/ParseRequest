package com.example.http.service;

import org.apache.commons.codec.DecoderException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by WT on 2017/9/15.
 */
public interface HttpRequest {
    public String sendGet(String url,String coding,String arguements,String type);
    public String sendPost(String url,String coding,String arguements,String type);
    public  String switchType(String type,String result) throws NoSuchPaddingException, DecoderException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, IOException;
}
