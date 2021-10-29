package com.udemy.blockchain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.udemy.blockchain.model.BlockChain;
import com.udemy.blockchain.service.BlockChainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class BlockchainApplication {

    public static void main(String[] args) throws JsonProcessingException {
        SpringApplication.run(BlockchainApplication.class, args);
    }

}
