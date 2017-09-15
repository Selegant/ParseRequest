package com.example.http.test;

import com.example.http.service.Analysis;
import com.example.http.util.Config;
import com.example.http.util.ConfigQz;
import org.apache.commons.codec.DecoderException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
@RunWith(SpringJUnit4ClassRunner.class)
public class MainTest {
    @Autowired
    Analysis analysis;

    @Test
    public void test() throws NoSuchPaddingException, InvalidKeyException, DecoderException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, IOException {
        String s= "{\"device_type\":\"2\",\"app_version\":\"1.0\",\"access_token\":\"4647b4f7-1d23-4fbe-907e-af68bd4b6732\"}";
        Config config=new ConfigQz();
        analysis.enCode(s,config);

    }
}
