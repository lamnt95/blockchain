package com.udemy.blockchain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.udemy.blockchain.util.HttpService;
import com.udemy.blockchain.util.UtilComponent;
import com.udemy.blockchain.model.BlockChain;
import com.udemy.blockchain.model.CryptoTrans;
import com.udemy.blockchain.model.NodeValidator;
import com.udemy.blockchain.repository.BlockChainRepository;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlockChainServiceImpl implements BlockChainService {

    @Autowired
    private BlockChainRepository bcRepo;

    @Autowired
    private HttpService http;

    @Override
    public List<BlockChain> getChains() {
        return bcRepo.getChains();
    }

    @Override
    public Boolean start() throws JsonProcessingException, CloneNotSupportedException {
        return bcRepo.start(1L);
    }

    @Override
    public BlockChain mine() throws JsonProcessingException, CloneNotSupportedException {
        return bcRepo.mine();
    }

    @Override
    public void transfer(CryptoTrans tran) {
        bcRepo.transfer(tran);
    }

    @Override
    public void addNode(NodeValidator req) {
        req.setId(UtilComponent.getId());
        bcRepo.setNodes(req);
    }

    @Override
    public Boolean replaceChains(List<BlockChain> req) throws IOException, CloneNotSupportedException {
        List<BlockChain> maxChain = new ArrayList<>();
        Long maxLen = UtilComponent.getLong(bcRepo.getChains().size());
        List<NodeValidator> nodes = bcRepo.getNodes();
        for (NodeValidator node : nodes) {
            String url = node.getUrl() + "/chain";
            Response res = http.get(url, null);
            int stt = res.code();
            if (stt >= 200 && stt <= 300) {
                String rb = res.body().string();
                ObjectMapper om = new ObjectMapper();
                List<BlockChain> a = om.readValue(rb, new TypeReference<List<BlockChain>>() {
                });
                Long len = UtilComponent.getLong(a.size());
                if (len > maxLen) {
                    maxLen = len;
                    maxChain = a;
                }
            }
        }
        Boolean isValid = bcRepo.isChainValid(maxChain);
        if (maxChain != null && maxChain.size() > 0 && isValid) {
            bcRepo.setChains(maxChain);
            return true;
        }
        return false;
    }
}
