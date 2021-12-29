package org.my.block.modal;

import com.sun.javafx.scene.traversal.Algorithm;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.xml.crypto.AlgorithmMethod;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Data
@NoArgsConstructor
public class Block {

        private String hash;
        private  String previous_hash;
        private String data;
        private long timestamp;
        private int nonce;
        private int index;
        private List<Transactions> transactionslist;

        public Block(String data,int noonce,String previous_hash, int index){
                this.index =  index;
                this.previous_hash =previous_hash;
                this.data=data;
                this.timestamp= Instant.now().toEpochMilli();
                this.nonce = noonce;
        }

        public int getNonce() {
                return nonce;
        }

        public long getTimestamp() {
                return timestamp;
        }

        public String getData() {
                return data;
        }

        public String getHash() {
                return hash;
        }

        public String getPrevious_hash() {
                return previous_hash;
        }

        public void setData(String data) {
                this.data = data;
        }

        public void setHash(String hash) {
                this.hash = hash;
        }

        public void setNonce(int nonce) {
                this.nonce = nonce;
        }

        public void setPrevious_hash(String previous_hash) {
                this.previous_hash = previous_hash;
        }

        public void setTimestamp(long timestamp) {
                this.timestamp = timestamp;
        }

        public int getIndex() {
                return index;
        }

        public void setIndex(int index) {
                this.index = index;
        }

        public List<Transactions> getTransactionslist() {
                return transactionslist.stream().collect(Collectors.toList());
        }

        public void setTransactionslist(List<Transactions> transactionslist) {
                this.transactionslist = transactionslist.stream().collect(Collectors.toList());
        }
}
