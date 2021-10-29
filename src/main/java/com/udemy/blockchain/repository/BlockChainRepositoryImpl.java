package com.udemy.blockchain.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.udemy.blockchain.util.UtilComponent;
import com.udemy.blockchain.model.BlockChain;
import com.udemy.blockchain.model.CryptoTrans;
import com.udemy.blockchain.model.NodeValidator;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class BlockChainRepositoryImpl implements BlockChainRepository {

    private Long INIT_PROOF = 1L;
    private String INIT_PRE_HASH = "0";

    private List<BlockChain> chains = new ArrayList<>();
    private List<NodeValidator> nodes = new ArrayList<>();
    private List<CryptoTrans> trans = new ArrayList<>();
    private Long level;

    @Override
    public List<BlockChain> getChains() {
        return chains;
    }

    @Override
    public void setChains(List<BlockChain> req) {
        chains = req;
    }

    @Override
    public List<NodeValidator> getNodes() {
        return nodes;
    }

    @Override
    public void setNodes(NodeValidator req) {
        nodes.add(req);
    }

    @Override
    public Boolean start(Long level) throws JsonProcessingException, CloneNotSupportedException {
        this.level = level;
        this.create(INIT_PROOF, INIT_PRE_HASH);
        return true;
    }

    @Override
    public BlockChain mine() throws JsonProcessingException, CloneNotSupportedException {
        Boolean isValid = isChainValid(null);
        if (isValid.equals(true)) {
            BlockChain a = getLast();
            String h = a.getHash();
            Long pf = a.getProof();
            Long npf = getNewProof(pf);
            // chuyen tien cho miner
            create(npf, h);

        }
        return null;
    }

    @Override
    public void transfer(CryptoTrans req) {
        trans.add(req);
    }

    @Override
    public BlockChain create(Long proof, String preHash) throws JsonProcessingException, CloneNotSupportedException {
        BlockChain a = new BlockChain();
        int i = chains.size() + 1;
        Long il = UtilComponent.getLong(i);
        a.setIndex(il);
        a.setCreatedDate(new Date());
        a.setProof(proof);
        a.setPreHash(preHash);

        BlockChain b = a.clone();
        b.setHash("");

        ObjectMapper om = new ObjectMapper();
        String bcs = om.writeValueAsString(b);
        String hash = UtilComponent.getHash(bcs);

        if (trans != null && trans.size() > 0) {
            for (CryptoTrans it : trans) {
                it.init();
            }
            String dt = om.writeValueAsString(trans);
            a.setData(dt);
            trans = new ArrayList<>();
        }

        a.setHash(hash);
        chains.add(a);
        return a;
    }

    @Override
    public BlockChain getLast() {
        return chains.get(chains.size() - 1);
    }

    @Override
    public Long getNewProof(Long prePf) {
        Long newPf = INIT_PROOF;
        Boolean isPf = false;

        while (isPf.equals(false)) {
            Boolean isHashValid = isHashValid(newPf, prePf);
            if (isHashValid.equals(false)) {
                newPf += 1L;
            } else {
                isPf = true;
            }
        }

        return newPf;
    }

    @Override
    public Boolean isHashValid(Long proofA, Long proofB) {
        Long pf = proofA * proofA - proofB * proofB;
        String hs = UtilComponent.getHash(pf.toString());
        Boolean res = false;
        if (hs != null && hs.length() > 0) {
            List<String> hss = UtilComponent.toList(hs);
            if (level.equals(1L)) {
                res = hss != null && hss.get(0).equals("0") && hss.get(1).equals("0") && hss.get(1).equals("0") && hss.get(3).equals("0");
            } else if (level.equals(2L)) {
                res = hss != null && hss.get(0).equals("0") && hss.get(1).equals("0") && hss.get(1).equals("0") && hss.get(3).equals("0") && hss.get(4).equals("0");
            }
        }
        return res;
    }

    @Override
    public String getHashBc(BlockChain req) throws CloneNotSupportedException, JsonProcessingException {
        BlockChain a = req.clone();
        a.setHash("");
        ObjectMapper om = new ObjectMapper();
        String b = om.writeValueAsString(a);
        String hs = UtilComponent.getHash(b);
        return hs;
    }

    @Override
    public Boolean isChainValid(List<BlockChain> req) throws JsonProcessingException, CloneNotSupportedException {
        List<BlockChain> a = chains;
        if (req != null && req.size() > 0) {
            a = req;
        }
        BlockChain b = a.get(0);
        Long i = 1L;

        while (i < a.size()) {
            BlockChain c = a.get(i.intValue());
            String h1 = c.getPreHash();
            String h2 = getHashBc(b);

            if (h1 != null && !h1.equals(h2)) return false;

            Long pf1 = c.getProof();
            Long pf2 = b.getProof();
            Boolean isValid = isHashValid(pf1, pf2);
            if (isValid.equals(false)) return false;
            b = c;
            i += 1L;
        }
        return true;
    }

}
