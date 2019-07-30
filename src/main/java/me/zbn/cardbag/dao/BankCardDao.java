package me.zbn.cardbag.dao;

import me.zbn.cardbag.entity.BankCard;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: zhangbaoning
 * @date: 2019/7/30
 * @since: JDK 1.8
 * @description: TODO
 */
public interface BankCardDao extends JpaRepository<BankCard, Integer> {
}
