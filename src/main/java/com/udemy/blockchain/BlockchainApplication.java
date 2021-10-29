package com.udemy.blockchain;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class BlockchainApplication {

    public static void main(String[] args) throws JsonProcessingException {
        SpringApplication.run(BlockchainApplication.class, args);
        BlockChainService bcs = new BlockChainService();
        bcs.startNetwork();
        Boolean iv1 = bcs.isChainValid();
        if (iv1.equals(true)) {
            BlockChain bc = bcs.getLastBlock();
            String ph = bcs.getHashBc(bc);
            Long pf = bc.getProof();
            Long npf = bcs.getNewProof(pf);
            bcs.createBlock(npf, ph);
        }
        Boolean iv2 = bcs.isChainValid();
        if (iv2.equals(true)) {
            BlockChain bc = bcs.getLastBlock();
            String ph = bcs.getHashBc(bc);
            Long pf = bc.getProof();
            Long npf = bcs.getNewProof(pf);
            bcs.createBlock(npf, ph);
        }
        Boolean iv3 = bcs.isChainValid();
        log.info("done");
    }

}
