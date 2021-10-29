package com.udemy.blockchain;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
@Data
public class BlockChain {
    private Long index;             //      Số Block
    private Date createdDate;       //      Ngày tạo Block
    private String data;            //      Dữ liệu trong Block
    private String hash;            //      Hash của Block
    private String preHash;         //      PreHash của Block
    private Long proof;           //      Bằng chứng xác thực Block
}
