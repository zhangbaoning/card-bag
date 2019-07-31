package me.zbn.cardbag.controller;

import com.google.gson.Gson;
import me.zbn.cardbag.entity.BankCard;
import me.zbn.cardbag.service.CardService;
import me.zbn.cardbag.service.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author: zhangbaoning
 * @date: 2019/2/18
 * @since: JDK 1.8
 * @description: TODO
 */
@Controller
public class CardController {
    @Autowired
    private CardService service;
    @Autowired
    private RelationService relationService;

    @PostMapping("upload")
    @ResponseBody
    public Map<String, String> uploadCard(String openid, MultipartFile file) {
        Gson gson = new Gson();
        Map<String, String> returnMap = new HashMap<>(1);
        try {
            // 获取格式
            String[] fileNameArray = file.getOriginalFilename().split("\\.");
            String formart = "." + fileNameArray[fileNameArray.length - 1];
            // 生成文件名
            String uuid = UUID.randomUUID().toString().replace("-", "");

            //  OCR获取卡号 查询卡号 是否保存
            BankCard bankCard = service.getBankOCR(file.getBytes());

            if (!service.existsByCardNo(bankCard.getCardNo())) {


                String imgUrl = service.upload(uuid + formart, file.getBytes());
                service.save(bankCard);
                returnMap.put("imgUrl", imgUrl);

                relationService.save(openid, uuid, "bankcard");
            } else {
                //通过卡号返回UUID
                relationService.save();
                // 存在的话获取UUID返回路径
                String imgUrl = service.getUrlByFileName(uuid + formart);
                returnMap.put("imgUrl", imgUrl);

            }
            String bankCardJson = gson.toJson(bankCard);
            Map bankCardMap = gson.fromJson(bankCardJson, Map.class);
            returnMap.putAll(bankCardMap);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnMap;
    }
}
