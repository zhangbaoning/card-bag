package me.zbn.cardbag.service;

import me.zbn.cardbag.dao.RelationDao;
import me.zbn.cardbag.entity.Relation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: zhangbaoning
 * @date: 2019/7/31
 * @since: JDK 1.8
 * @description: TODO
 */
@Service
public class RelationService {
    @Autowired
    private RelationDao dao;
    public void save(String openid,String uuid,String bankNo,String type){
        Relation relation = new Relation();
        relation.setOpenid(openid);
        relation.setUuid(uuid);
        dao.save(relation);
    }
}
