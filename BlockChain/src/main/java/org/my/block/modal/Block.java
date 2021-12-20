package org.my.block.modal;

import com.sun.javafx.scene.traversal.Algorithm;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.xml.crypto.AlgorithmMethod;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;


@Data
public class Block {

        public String hash;
        public  String previous_hash;
        public String data;
        public long timestamp;
        public int nonce;

        public Block(String data,int noonce,String previous_hash){
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
}
