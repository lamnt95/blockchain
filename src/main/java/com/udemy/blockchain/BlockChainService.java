package com.udemy.blockchain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.util.URLEncoder;
import org.springframework.cglib.core.Block;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Service
public class BlockChainService {

    // Chuỗi khối Blockchain
    private List<BlockChain> chains = new ArrayList<>();

    // Constant
    // Bằng chứng đầu tiên
    private Long INIT_PROOF = 1L;
    // PreHash đầu tiên
    private String INIT_PRE_HASH = "0";

    //  Khởi tạo network
    //  Khởi tạo block đầu tiên
    public void startNetwork() throws JsonProcessingException {
        this.chains = new ArrayList<>();
        this.createBlock(this.INIT_PROOF, this.INIT_PRE_HASH);
    }

    // tạo block
    public void createBlock(Long pf, String ph) throws JsonProcessingException {
        BlockChain bc = new BlockChain();
        int i = chains.size() + 1;
        Long il = Long.parseLong(Integer.toString(i));
        bc.setIndex(il);
        bc.setCreatedDate(new Date());
        bc.setProof(pf);
        bc.setPreHash(ph);
        ObjectMapper om = new ObjectMapper();
        String bcs = om.writeValueAsString(bc);
        String hash = toHash(bcs);
        bc.setHash(hash);
        chains.add(bc);
    }

    // lấy block phía trước liền kề
    public BlockChain getLastBlock() {
        return this.chains.get(this.chains.size() - 1);
    }

    // lấy bằng chứng xác thực mới
    public Long getNewProof(Long prePf) {
        Long newPf = this.INIT_PROOF;
        Boolean isPf = false;

        while (isPf.equals(false)) {
            log.info("---------------");
            Boolean isHashValid = isCheckHashValid(newPf, prePf);
            if (isHashValid.equals(true)) {
                isPf = true;
            } else {
                newPf += 1L;
                log.info(newPf.toString());
            }
        }
        return newPf;
    }

    // toHash
    public String toHash(String in){
        return Hashing.sha256()
                .hashString(in, StandardCharsets.UTF_8)
                .toString();
    }

    // check hash có 4 phần tử đầu tiên = 0 khay không
    public Boolean isCheckHashValid(Long pfa, Long pfb) {
        Long pf1 = pfa * pfa - pfb * pfb;
        String pf2 = pf1.toString();
        String hash = toHash(pf2);
        if (hash != null && hash.length() > 0) {
            log.info(hash);
            List<String> hs = Arrays.asList(hash.split("").clone());
            Boolean ie = hs != null && hs.get(0).equals("0") && hs.get(1).equals("0") && hs.get(2).equals("0") && hs.get(3).equals("0");
            if (ie.equals(true)) {
                return true;
            }
        }
        return false;
    }

    // hash 1 block
    public String getHashBc(BlockChain bc) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        String bcs = om.writeValueAsString(bc);
        String hash = toHash(bcs);
        return hash;
    }

    // check chain là valid
    public Boolean isChainValid() throws JsonProcessingException {
        BlockChain pbc = chains.get(0);
        Long bci = 1L;

        while (bci < chains.size()) {
            BlockChain bc = chains.get(bci.intValue());
            String ph = bc.getPreHash();
            String hpbc = getHashBc(pbc);
            if (ph != null && !ph.equals(hpbc)) {
                return false;
            }
            Long ppf = pbc.getProof();
            Long pf = bc.getProof();
            Boolean isHashValid = isCheckHashValid(pf, ppf);
            if (isHashValid.equals(false)) {
                return false;
            }
            pbc = bc;
            bci += 1L;
        }

        return true;
    }

}
