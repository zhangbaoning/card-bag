package me.zbn.cardbag.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author: zhangbaoning
 * @date: 2019/2/28
 * @since: JDK 1.8
 * @description: TODO
 */
public class DESUtilTest {

    @Test
    public void encrypt() {
        try {
            File file = new File("C:\\Users\\lenovo\\Desktop\\index.jpg");
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            byte[] source = bos.toByteArray();
            List sourceList = new ArrayList<>(32);
            List targetList = new ArrayList<>();
            byte[] bytes =  DESUtil.encrypt("12345678", source,"encode");
            System.out.println(new String(bytes));
//            for (int i = 1; i <= source.length; i++) {
//                sourceList.add(source[i-1]);
//                if ( i % 5 == 0) {
//                    Byte[] objects = (Byte[]) sourceList.toArray(new Byte[sourceList.size()]);
//                    byte[] bytes =  DESUtil.encrypt("zhangbaoning", ArrayUtils.toPrimitive(objects),"encode");
//                    targetList.addAll(Arrays.asList(bytes));
//                    sourceList.clear();
//                }
//            }
            System.out.println(targetList.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}