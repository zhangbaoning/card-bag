package me.zbn.cardbag.controller;

import me.zbn.cardbag.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author: zhangbaoning
 * @date: 2019/2/18
 * @since: JDK 1.8
 * @description: TODO
 */
public class CardController {
    @Autowired private CardService service;
    public void uploadCard(String openid,MultipartFile file){
        try {
            service.save(openid,file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
