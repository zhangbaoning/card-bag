package me.zbn.cardbag.controller;

import me.zbn.cardbag.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

            Map<String,String> returnMap = new HashMap<>(1);
        try {
            System.out.println(file.getOriginalFilename());
            String[] fileNameArray = file.getOriginalFilename().split("\\.");
            String formart = "."+fileNameArray[fileNameArray.length-1];
            String imgUrl =   service.save(openid+formart,file.getBytes());
            returnMap.put("imgUrl",imgUrl);
            service.getBankOCR(file.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnMap;
    }
}
