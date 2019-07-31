package me.zbn.cardbag.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.junit.Assert.*;

/**
 * @author: zhangbaoning
 * @date: 2019/2/28
 * @since: JDK 1.8
 * @description: TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CardServiceTest {
    @Autowired CardService service;
    @Test
    public void save() {
        File file = new File("C:\\Users\\lenovo\\Desktop\\index.jpg");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            service.upload("zhangbaoning",bos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Test
    public void getBankOCR(){
        //service.getBankOCR();
    }
}