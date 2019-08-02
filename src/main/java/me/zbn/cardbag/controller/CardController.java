package me.zbn.cardbag.controller;

import com.google.gson.Gson;
import me.zbn.cardbag.entity.BankCard;
import me.zbn.cardbag.entity.Relation;
import me.zbn.cardbag.service.CardService;
import me.zbn.cardbag.service.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

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
    Gson gson = new Gson();

    @PostMapping("upload")
    @ResponseBody
    public Map<String, String> uploadCard(String openid, MultipartFile file) {
        Map<String, String> returnMap = new HashMap<>(1);
        try {
            // 获取格式
            String[] fileNameArray = file.getOriginalFilename().split("\\.");
            String formart = "." + fileNameArray[fileNameArray.length - 1];
            // 生成文件名
            String uuid = UUID.randomUUID().toString().replace("-", "");

            //  OCR获取卡号 查询卡号 是否保存
            BankCard bankCard = service.getBankOCR(file.getBytes());
            // TODO 是否都存在
            if (!service.existsByCardNo(bankCard.getCardNo())) {


                String imgUrl = service.upload(uuid + formart, file.getBytes());
                service.save(bankCard);
                returnMap.put("imgUrl", imgUrl);

                relationService.save(openid, uuid + formart, bankCard.getCardNo(), "bankcard");
            } else {
                //通过卡号返回UUID
                uuid = relationService.getByCardNo(bankCard.getCardNo()).getUuid();
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

    @GetMapping("getAllCard/{openid}")
    @ResponseBody
    public List getCardList(@PathVariable("openid") String openid) {
        // 通过openid获取uuid
        List<Relation> relationList = relationService.getByOpenid(openid);
        //List<Relation> relationList = relationService.getAll();
        List returnList = new ArrayList();
        for (Relation relation : relationList) {
            Map relationMap = new HashMap(2);
            String url = service.getUrlByFileName(relation.getUuid());
            int id = relation.getId();
            relationMap.put("id", id);
            relationMap.put("url", url);
            relationMap.put("type", "image");
            returnList.add(relationMap);
        }
        return returnList;
    }

    @ResponseBody
    @GetMapping("onLogin")
    public Map<String,Object> onLogin(String code) {
        RestTemplate template = new RestTemplate();
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid={appid}&secret={secret}&js_code={js_code}&grant_type={grant_type}";
        Map<String,String> paramMap = new HashMap<>(4);
        paramMap.put("js_code", code);
        paramMap.put("appid", "wx6e7ba566cdb6dcf8");
        paramMap.put("secret", "8262a066f8d262c25099aab6106f2ff7");
        paramMap.put("grant_type", "authorization_code");
        ResponseEntity<String> returnStr = template.getForEntity(url, String.class, paramMap);

        Map returnMap = gson.fromJson(returnStr.getBody(), Map.class);
        returnMap.remove("session_key");
        return returnMap;

    }


}
