package me.zbn.cardbag.utils;

import me.zbn.cardbag.CardBagApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author: zhangbaoning
 * @date: 2019/2/28
 * @since: JDK 1.8
 * @description: TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CardBagApplication.class)


@TestPropertySource(locations="classpath:application.yml")

public class OCSUtilTest {
    @Autowired
    private OCSUtil ocsUtil;
    @Test
    public void upload() {

        try {
            File file = new File("C:\\Users\\lenovo\\Desktop\\index.jpg");
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            ocsUtil.upload(bos.toByteArray(),"zhang");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}