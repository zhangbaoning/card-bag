package me.zbn.cardbag.service;

import me.zbn.cardbag.dao.RelationDao;
import me.zbn.cardbag.entity.Relation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Relation getByCardNo(String cardNo){
        Relation relation = new Relation();
        relation.setCardNo(cardNo);

        Example<Relation> example = Example.of(relation,ExampleMatcher.matching().withMatcher("cardNo", ExampleMatcher.GenericPropertyMatchers.contains()));
        return dao.findOne(example).get();
    }
    public List<Relation> getAll(){
       return dao.findAll();
    }
   public List getByOpenid(String openid){
        Relation relation = new Relation();
        relation.setOpenid(openid);
        Example<Relation> example = Example.of(relation,ExampleMatcher.matchingAll());
        return dao.findAll(example);
    }
}
