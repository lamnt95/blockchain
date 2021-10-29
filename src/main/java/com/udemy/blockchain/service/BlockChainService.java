package com.udemy.blockchain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.udemy.blockchain.model.BlockChain;
import com.udemy.blockchain.model.CryptoTrans;
import com.udemy.blockchain.model.NodeValidator;

import java.io.IOException;
import java.util.List;

public interface BlockChainService {

    List<BlockChain> getChains();

    Boolean start() throws JsonProcessingException, CloneNotSupportedException;

    BlockChain mine() throws JsonProcessingException, CloneNotSupportedException;

    void transfer(CryptoTrans tran);

    void addNode(NodeValidator req);

    Boolean replaceChains(List<BlockChain> req) throws IOException, CloneNotSupportedException;

}
