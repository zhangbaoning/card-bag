package me.zbn.cardbag.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.zbn.cardbag.entity.BankCard;
import me.zbn.cardbag.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
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
    @Autowired private CardService service;
    @PostMapping("upload")
    @ResponseBody
    public Map<String,String> uploadCard(String openid, MultipartFile file){
        Gson gson = new Gson();
            Map<String,String> returnMap = new HashMap<>(1);
        try {
            System.out.println(file.getOriginalFilename());
            String[] fileNameArray = file.getOriginalFilename().split("\\.");
            String formart = "."+fileNameArray[fileNameArray.length-1];
            String uuid = UUID.randomUUID().toString().replace("-","");
            String imgUrl =   service.save(uuid+formart,file.getBytes());
            returnMap.put("imgUrl",imgUrl);
            BankCard bankCard = service.getBankOCR(file.getBytes());
            String bankCardJson = gson.toJson(bankCard);
            Map bankCardMap = gson.fromJson(bankCardJson,Map.class);
            bankCardMap.remove("id");
            returnMap.putAll(bankCardMap);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnMap;
    }
}
