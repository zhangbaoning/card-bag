package me.zbn.cardbag.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

/**
 * @author: zhangbaoning
 * @date: 2019/7/31
 * @since: JDK 1.8
 * @description: TODO
 */
@Entity
public class Relation {
    private int id;
    private String openid;
    private String uuid;
    private String type;
    private String cardNo;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "openid", nullable = true, length = 255)
    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    @Basic
    @Column(name = "uuid", nullable = true, length = 255)
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Basic
    @Column(name = "type", nullable = true, length = 255)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Relation relation = (Relation) o;
        return id == relation.id &&
                Objects.equals(openid, relation.openid) &&
                Objects.equals(uuid, relation.uuid) &&
                Objects.equals(type, relation.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, openid, uuid, type);
    }

    @Basic
    @Column(name = "card_no", nullable = true, length = 255)
    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}
