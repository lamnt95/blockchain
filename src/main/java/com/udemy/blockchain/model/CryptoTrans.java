package com.udemy.blockchain.model;

import com.udemy.blockchain.util.UtilComponent;
import lombok.Data;

import java.util.Date;

@Data
public class CryptoTrans {
    private String id;
    private String from;
    private String to;
    private String token;
    private Double quantity;
    private Double fee;
    private Long status;
    private Date createdDate;

    public void init(){
        id = UtilComponent.getId();
        status = 1L;
        createdDate = new Date();
    }
}
