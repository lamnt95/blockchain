package com.udemy.blockchain.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.udemy.blockchain.model.BlockChain;
import com.udemy.blockchain.model.CryptoTrans;
import com.udemy.blockchain.model.NodeValidator;
import com.udemy.blockchain.service.BlockChainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("blockchain")
public class BlockChainController {

    @Autowired
    private BlockChainService bcSv;

    @GetMapping("chains")
    public List<BlockChain> getChains() {
        return bcSv.getChains();
    }

    @PostMapping("start")
    public Boolean start() throws JsonProcessingException, CloneNotSupportedException {
        return bcSv.start();
    }

    @PostMapping("mine")
    public BlockChain mine() throws JsonProcessingException, CloneNotSupportedException {
        return bcSv.mine();
    }

    @PostMapping("transfer")
    public void transfer(
            @RequestBody() CryptoTrans tran
    ) {
        bcSv.transfer(tran);
    }

    @PostMapping("node")
    public void addNode(
            @RequestBody() NodeValidator req
    ) {
        bcSv.addNode(req);
    }
}
