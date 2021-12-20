package org.my.block.service;

import org.my.block.modal.Block;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class BlockHandlingService {

        List<Block> chain_block = null;

        private BlockHandlingService(){
            chain_block = new ArrayList<>();
           createBlock("test1", 1, "0");
        }

        public List<Block>  getChain_block(){
            return new ArrayList<>(chain_block);
        }

        public  Block createBlock(String data,int nonce, String previous_hash){
            Block objBlock= new Block(data, nonce, previous_hash);
            chain_block.add(objBlock);
            return objBlock;
        }

        public Block get_previous_block(){
            return chain_block.get(chain_block.size()-1);
        }

        public String  getHashBlock(Block block){

            return getSha256(block.toString());
        }

        public boolean is_chain_valid(){
            Block previous_block = chain_block.get(0);
            int i=1;
            while(i < chain_block.size()){
                Block block = chain_block.get(i);
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
