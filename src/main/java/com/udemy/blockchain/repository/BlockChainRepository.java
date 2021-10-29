package com.udemy.blockchain.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.udemy.blockchain.model.BlockChain;
import com.udemy.blockchain.model.CryptoTrans;
import com.udemy.blockchain.model.NodeValidator;

import java.util.List;

public interface BlockChainRepository {

    List<BlockChain> getChains();

    void setChains(List<BlockChain> req);

    List<NodeValidator> getNodes();

    void setNodes(NodeValidator req);

    Boolean start(Long level) throws JsonProcessingException, CloneNotSupportedException;

    BlockChain mine() throws JsonProcessingException, CloneNotSupportedException;

    void transfer(CryptoTrans req);

    BlockChain create(Long proof, String preHash) throws JsonProcessingException, CloneNotSupportedException;

    BlockChain getLast();

    Long getNewProof(Long preProof);

    Boolean isHashValid(Long proofA, Long proofB);

    String getHashBc(BlockChain req) throws CloneNotSupportedException, JsonProcessingException;

    Boolean isChainValid(List<BlockChain> req) throws JsonProcessingException, CloneNotSupportedException;


}
