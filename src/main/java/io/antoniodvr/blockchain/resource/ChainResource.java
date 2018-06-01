package io.antoniodvr.blockchain.resource;

import io.antoniodvr.blockchain.business.model.Block;
import io.antoniodvr.blockchain.business.model.ChainValidity;
import io.antoniodvr.blockchain.business.model.MoneyTransaction;
import io.antoniodvr.blockchain.business.service.ChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chain")
public class ChainResource {

    @Autowired
    private ChainService chainService;

    @GetMapping
    public ResponseEntity<List<Block>> getChain() {
        return ResponseEntity.ok(chainService.getBlockchain());
    }

    @GetMapping("/valid")
    public ResponseEntity<ChainValidity> isChainValid() {
        return ResponseEntity.ok(chainService.isChainValid());
    }

    @PostMapping
    public ResponseEntity<Block> mineBlock(@RequestBody MoneyTransaction transaction) {
        Block block = ChainService.createBlock(transaction, chainService.getLastBlock());
        chainService.addBlock(block);
        return ResponseEntity.ok(block);
    }

}
