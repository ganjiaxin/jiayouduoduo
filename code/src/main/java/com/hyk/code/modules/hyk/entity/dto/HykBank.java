package com.hyk.code.modules.hyk.entity.dto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Auther: 霍中曦
 * @Date: 2019/1/28 21:20
 * @Description:
 */
@Component
public class HykBank {

    /*银行id*/
    @Value("123")
    private String bankId;

    /*银行名*/
    @Value("zhong")
    private String bankName;


    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
