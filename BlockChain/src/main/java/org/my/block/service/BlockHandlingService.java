package org.my.block.service;

import org.my.block.modal.Block;
import org.my.block.modal.Transactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class BlockHandlingService {

        final List<Block> chain_block =   new ArrayList<>();
        final List<Transactions> transaction_pool = new ArrayList<>();
        final Set<String>  nodes = new HashSet<>();

        @Autowired
        AsyncServiceExecution objAsyncService;

        private BlockHandlingService(){

           createBlock("test1", 1, "0");
        }

        public List<Block>  getChain_block(){
            return new ArrayList<>(chain_block);
        }

        public  Block createBlock(String data,int nonce, String previous_hash){
            Block objBlock= new Block(data, nonce, previous_hash,chain_block.size()+1);
            objBlock.setTransactionslist(transaction_pool);
            chain_block.add(objBlock);
            transaction_pool.clear();
            return objBlock;
        }

        public Block get_previous_block(){
            return chain_block.get(chain_block.size()-1);
        }

        public String  getHashBlock(Block block){

            return getSha256(block.toString());
        }

        public boolean is_chain_valid(List<Block> chain){
            Block previous_block = chain.get(0);
            int i=1;
            while(i < chain.size()){
                Block block = chain.get(i);
                if(!block.getPrevious_hash ().equals(getHashBlock(previous_block))){
                    return  Boolean.FALSE;
                }
                int prev_nonce = previous_block.getNonce();
                int nonce = block.getNonce();
                String hashString = get_hash_work(nonce,prev_nonce);

                if(!hashString.startsWith("0000")){
                    return  Boolean.FALSE;
                }
                previous_block = block;
                i++;
            }
            return Boolean.TRUE;
        }

        public int proof_of_work(int previous_nonce){
            int new_nonce=1;
            boolean check_nonce=false;

            while(!check_nonce){
                String hash_work = get_hash_work(new_nonce, previous_nonce);
                if(hash_work.startsWith("0000")){
                    check_nonce = true;
                }else{
                    new_nonce++;
                }
            }
            return new_nonce;
        }
        public int  add_transactions(String sender, String receiver, double amount){
            Transactions objTransaction = new Transactions();

            objTransaction.setSender(sender);
            objTransaction.setReceiver(receiver);
            objTransaction.setAmount(amount);

            this.transaction_pool.add(objTransaction);
            Block previous_block = get_previous_block();

            return previous_block.getIndex() + 1;
        }
        public void add_node(String address) {
            nodes.add(address);
        }
        public Boolean replace_chain() {
            Set<String> network = this.nodes;
            List<Block> longest_chain = null;
            int max_len = this.chain_block.size();
            List<Block> responseChain= null;
                for (String uri: network) {
                    responseChain = objAsyncService.getDistributedChain(uri);
                    if(null != responseChain){
                        int len = responseChain.size();
                        if(len > max_len && is_chain_valid(responseChain)){
                            max_len = len;
                            longest_chain =responseChain;
                        }
                    }
                }
                if(null != longest_chain){
                    this.chain_block.clear();
                    this.chain_block.addAll(longest_chain);
                    return Boolean.TRUE;
                }

                return Boolean.FALSE;
        }
    private String get_hash_work(int new_nonce, int previous_nonce){
        String dataToHash = String.valueOf((new_nonce * new_nonce ) - (previous_nonce * previous_nonce));

        return getSha256(dataToHash);
    }

    private  String getSha256(String value) {
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(value.getBytes());
            return bytesToHex(md.digest());
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    private  String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
           String hex = Integer.toHexString(b);
            if (hex.length() == 1)
                result.append('0');
            result.append(hex);
        }

        return result.toString();
    }

}
