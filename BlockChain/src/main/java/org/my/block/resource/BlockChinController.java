package org.my.block.resource;

import org.my.block.modal.Block;
import org.my.block.modal.Transactions;
import org.my.block.service.BlockHandlingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class BlockChinController {

    final String NODE_ADDRESS = UUID.randomUUID().toString().replace("-","");
    final String RECEIVER = "K"+UUID.randomUUID().toString();
    @Autowired
    BlockHandlingService block_service;


    @GetMapping("/mine-block")
    public Block mine_block(){
        Block prev_block = block_service.get_previous_block();
        int prev_nonce = prev_block.getNonce();
        int nonce = block_service.proof_of_work(prev_nonce);
        String previous_hash = block_service.getHashBlock(prev_block);
        block_service.add_transactions(NODE_ADDRESS, RECEIVER, 10);
        Block block = block_service.createBlock("test", nonce,previous_hash);

        return block;

    }

    @GetMapping("/get-chain")
    public List<Block> get_cain(){
        return  block_service.getChain_block();
    }
    @GetMapping("/chain/validate")
    public Boolean validate_chain(){
        return block_service.is_chain_valid(block_service.getChain_block());
    }

    @PostMapping("/add-transaction")
    @Validated
    public String add_transaction(@RequestBody Transactions trans){

        int index = block_service.add_transactions(trans.getSender(),trans.getReceiver(),trans.getAmount());
        return  "Transaction will be added to the block "+index;
    }
    @PostMapping("/connect-nodes")
    public String set_connecting_nodes(@RequestBody Map<String,List<String>> nodes){

        List<String> lstnode = nodes.get("NODES");
        for(String node: lstnode){
            block_service.add_node(node);
        }
        return  "Block chain nodes added.";
    }
    @GetMapping("/chain-replace")
    public String is_chain_replaced(){
        Boolean is_chain_replaced = block_service.replace_chain();
        if(is_chain_replaced)
            return "Node has differed the chain. Hence, It replaced with the longest chain";
        else
            return "Chain is identical with other nodes";
    }
}
