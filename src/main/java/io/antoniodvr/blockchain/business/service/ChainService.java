package io.antoniodvr.blockchain.business.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.antoniodvr.blockchain.business.model.Block;
import io.antoniodvr.blockchain.business.model.ChainValidity;
import io.antoniodvr.blockchain.business.model.Transaction;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ChainService {

    public final static int CHAIN_DIFFICULTY = 3;

    @Getter
    private List<Block> blockchain;

    public ChainService() {
        this.blockchain = new ArrayList<>();
    }

    public boolean addBlock(Block block) {
        return blockchain.add(block);
    }

    public Block getLastBlock() {
        Block block = null;
        if (blockchain.size() > 0) {
            block = blockchain.get(blockchain.size() - 1);
        }
        return block;
    }

    public static Block createBlock(Transaction t, Block lastBlock) {
        Block block = new Block();
        block.setTimestamp(System.currentTimeMillis());

        Gson gson = new GsonBuilder().create();
        String payload = gson.toJson(t);
        block.setPayload(payload);

        if (lastBlock != null) {
            block.previousHash = lastBlock.hash;
        } else {
            block.previousHash = "";
        }

        block.mineBlock(CHAIN_DIFFICULTY);
        return block;
    }

    public ChainValidity isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[CHAIN_DIFFICULTY]).replace('\0', '0');

        for(int i=1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i-1);
            if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
                log.warn("Current Hashes not equal");
                return ChainValidity.notValid();
            }
            if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
                log.warn("Previous Hashes not equal");
                return ChainValidity.notValid();
            }
            if(!currentBlock.hash.substring(0, CHAIN_DIFFICULTY).equals(hashTarget)) {
                log.warn("This block hasn't been mined");
                return ChainValidity.notValid();
            }
        }
        return ChainValidity.valid();
    }

}
