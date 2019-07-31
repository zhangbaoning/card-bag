package me.zbn.cardbag.dao;

import me.zbn.cardbag.entity.Relation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: zhangbaoning
 * @date: 2019/7/31
 * @since: JDK 1.8
 * @description: TODO
 */
public interface RelationDao extends JpaRepository<Relation,Integer> {
}
