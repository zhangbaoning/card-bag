package me.zbn.cardbag.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author: zhangbaoning
 * @date: 2019/2/18
 * @since: JDK 1.8
 * @description: TODO
 */
public class AESUtilTest {

    @Test
    public void encode() {
        String name = "zhangbaoning";
       byte[] encodeStr = DESUtil.encrypt("12345678",name.getBytes(),"encode");
        System.out.println(new String(encodeStr));
        byte[] decodeStr = DESUtil.encrypt("12345678",encodeStr,"decode");
        System.out.println(new String(decodeStr));
        Assert.assertEquals(name,new String(decodeStr));

    }
}