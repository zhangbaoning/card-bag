package me.zbn.cardbag.service;

import com.google.gson.Gson;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.ocr.v20181119.OcrClient;
import com.tencentcloudapi.ocr.v20181119.models.BankCardOCRRequest;
import com.tencentcloudapi.ocr.v20181119.models.BankCardOCRResponse;
import me.zbn.cardbag.dao.BankCardDao;
import me.zbn.cardbag.dao.CardDao;
import me.zbn.cardbag.entity.BankCard;
import me.zbn.cardbag.entity.Card;
import me.zbn.cardbag.entity.Relation;
import me.zbn.cardbag.utils.OCSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author: zhangbaoning
 * @date: 2019/2/18
 * @since: JDK 1.8
 * @description: TODO
 */
@Service
public class CardService {
    @Autowired
    private CardDao dao;
    @Autowired
    private BankCardDao bankCardDao;
    @Autowired
    private OCSUtil ocsUtil;

    public String upload(String openid, byte[] fileByte) {
        // 对openid进行MD5
        String openidMd5 = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            openidMd5 = new String(messageDigest.digest(openid.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 对文件进行加密
        /*byte[] encrypted = DESUtil.encrypt(openidMd5, fileByte, "decode");
        ocsUtil.upload(encrypted, openid);*/
        return  ocsUtil.upload(fileByte, openid);

    }
    public BankCard getBankOCR(byte[] imgByte){
        BankCard bankCard = null;
        try {


            byte[] base64Image =  Base64Utils.encode(imgByte);
            Credential cred = new Credential("AKIDQY1AlQo2SSkHZtL1OK2N4d2TAAwwpFJO", "3omLqRVM7Hj85PrgHhmSZZgnrNVkfnsS");

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("ocr.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            OcrClient client = new OcrClient(cred, "ap-beijing", clientProfile);
            Gson gson =new Gson();
            Map<String,String> paramMap = new HashMap<>(1);
            paramMap.put("ImageBase64",new String(base64Image));

            String params = gson.toJson(paramMap);
            BankCardOCRRequest req = BankCardOCRRequest.fromJsonString(params, BankCardOCRRequest.class);

            BankCardOCRResponse resp = client.BankCardOCR(req);

            System.out.println(BankCardOCRRequest.toJsonString(resp));
             bankCard =gson.fromJson(BankCardOCRRequest.toJsonString(resp), BankCard.class);
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
        return bankCard;

    }
    public boolean existsByCardNo(String cardNo){
       return bankCardDao.existsById(cardNo);
    }
    public String getUrlByFileName(String fileName){
        return ocsUtil.getUrl(fileName);
    }
    public void save(BankCard bankCard){
        bankCardDao.save(bankCard);

    }
    public BankCard getByCardNo(String cardNo) {
        Optional<BankCard> bankCardOptional = bankCardDao.findById(cardNo);
      return bankCardOptional.get();

    }
    public void del(BankCard bankCard){
        bankCardDao.delete(bankCard);
    }

}
