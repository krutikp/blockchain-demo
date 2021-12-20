package org.my.block.resource;

import org.my.block.modal.Block;
import org.my.block.service.BlockHandlingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BlockChinController {

    @Autowired
    BlockHandlingService block_service;

    @GetMapping("/mine-block")
    public Block mine_block(){
        Block prev_block = block_service.get_previous_block();
        int prev_nonce = prev_block.getNonce();
        int nonce = block_service.proof_of_work(prev_nonce);
        String previous_hash = block_service.getHashBlock(prev_block);
        Block block = block_service.createBlock("test", nonce,previous_hash);

        return block;

    }

    @GetMapping("/get-chain")
    public List<Block> getChain(){
        return  block_service.getChain_block();
    }
    @GetMapping("/chain/validate")
    public Boolean validateChain(){
        return block_service.is_chain_valid();
    }
}
