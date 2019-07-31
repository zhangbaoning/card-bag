package me.zbn.cardbag.entity;

import com.google.gson.annotations.SerializedName;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author: zhangbaoning
 * @date: 2019/7/31
 * @since: JDK 1.8
 * @description: TODO
 */
@Entity
@Table(name = "bank_card", schema = "card_bag", catalog = "")
public class BankCard implements Serializable {
    @SerializedName("CardNo")
    private String cardNo;
    @SerializedName("BankInfo")
    private String bankInfo;
    @SerializedName("ValidDate")
    private String validDate;

    @Id
    @Column(name = "card_no", nullable = false, length = 255)
    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    @Basic
    @Column(name = "bank_info", nullable = true, length = 255)
    public String getBankInfo() {
        return bankInfo;
    }

    public void setBankInfo(String bankInfo) {
        this.bankInfo = bankInfo;
    }

    @Basic
    @Column(name = "valid_date", nullable = true, length = 255)
    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankCard bankCard = (BankCard) o;
        return Objects.equals(cardNo, bankCard.cardNo) &&
                Objects.equals(bankInfo, bankCard.bankInfo) &&
                Objects.equals(validDate, bankCard.validDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNo, bankInfo, validDate);
    }
}
