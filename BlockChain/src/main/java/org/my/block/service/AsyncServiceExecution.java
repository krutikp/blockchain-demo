package org.my.block.service;

import com.sun.xml.internal.ws.util.CompletedFuture;
import org.my.block.modal.Block;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class AsyncServiceExecution {

//    @Async("asyncExecutor")
    public List<Block> getDistributedChain(String url)  {
        RestTemplate rest = new RestTemplate();
       Block[] objBlock = rest.getForObject(url+"/get-chain", Block[].class);

        return Arrays.asList(objBlock);
    }

}
