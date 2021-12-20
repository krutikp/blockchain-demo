package org.my.block;

import org.my.block.service.BlockHandlingService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class BlockChainApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlockChainApplication.class, args);
	}

}
