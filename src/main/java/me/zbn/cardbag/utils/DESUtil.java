package me.zbn.cardbag.utils;

import me.zbn.cardbag.EncryptEnum;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;

/**
 * @author: zhangbaoning
 * @date: 2019/2/18
 * @since: JDK 1.8
 * @description: TODO
 */
public class DESUtil {
    public static byte[] encrypt(String key, byte[] fileByte,String method) {
        try {
            byte[] keyBytes = key.getBytes();
            SecretKey secretKey = new SecretKeySpec(keyBytes, "DES");
            Cipher cipher = Cipher.getInstance("DES");
            if (EncryptEnum.encode.equals(method)){
                // 加密
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            }else if (EncryptEnum.decode.equals(method)){
                // 解密
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
            }
            return cipher.doFinal(fileByte);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
