package me.zbn.cardbag.dao;

import me.zbn.cardbag.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: zhangbaoning
 * @date: 2019/2/18
 * @since: JDK 1.8
 * @description: TODO
 */
public interface CardDao extends JpaRepository<Card,Integer> {
}
