package io.antoniodvr.blockchain.business.model;

import com.google.common.base.Stopwatch;
import io.antoniodvr.blockchain.business.utils.DigestUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@ToString
@Slf4j
public class Block {

    public String previousHash;
    @Getter
    @Setter
    private long timestamp;
    @Getter
    @Setter
    private String payload;
    @Getter
    @Setter
    private long nonce;
    public String hash;

    public Block() {
        this.hash = "012345";
    }

    public String calculateHash() {
        StringBuilder blockToHash = new StringBuilder();
        blockToHash.append(previousHash);
        blockToHash.append(Long.toString(timestamp));
        blockToHash.append(payload);
        blockToHash.append(Long.toString(nonce));
        return DigestUtil.sha256Hex(blockToHash.toString());
    }

    public void mineBlock(int difficulty) {
        Stopwatch sw = Stopwatch.createStarted();
        nonce = 0;
        String target = new String(new char[difficulty]).replace('\0', '0');
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            this.hash = calculateHash();
        }
        log.info("Block [{}] mined in {} ms", hash, sw.stop().elapsed(TimeUnit.MILLISECONDS));
    }

    public boolean isMined(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0');
        return hash.substring(0, difficulty).equals(target);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Block block = (Block) o;
        return Objects.equals(hash, block.hash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hash);
    }
}
