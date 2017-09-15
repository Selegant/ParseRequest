package com.example.http.service.impl;

import com.example.http.service.Analysis;
import com.example.http.util.AESCipher;
import com.example.http.util.Config;
import org.apache.commons.codec.DecoderException;
import org.springframework.stereotype.Service;

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
@Service
public class AnalysisImpl implements Analysis {

    @Override
    public String enCode(String param, Config config) throws NoSuchPaddingException, DecoderException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, IOException {
        return AESCipher.aesEncryptString(param,config.aesKey,config.aesIv);
    }

    @Override
    public String deCode(String param, Config config) throws NoSuchPaddingException, DecoderException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, IOException {
        return AESCipher.aesDecryptString(param,config.aesKey,config.aesIv);
    }
}
