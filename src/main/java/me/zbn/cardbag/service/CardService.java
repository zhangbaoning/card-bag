package me.zbn.cardbag.service;

import me.zbn.cardbag.dao.CardDao;
import me.zbn.cardbag.utils.DESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: zhangbaoning
 * @date: 2019/2/18
 * @since: JDK 1.8
 * @description: TODO
 */
@Service
public class CardService {
    @Autowired private CardDao dao;
    public void save(String openid,byte[] fileByte){
        // 对文件进行加密
        DESUtil.encrypt("openid",fileByte,"decode");
    }
}
