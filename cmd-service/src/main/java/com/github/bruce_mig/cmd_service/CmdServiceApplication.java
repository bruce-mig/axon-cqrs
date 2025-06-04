package com.github.bruce_mig.cmd_service;

import com.github.bruce_mig.cmd_service.config.AxonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ AxonConfig.class })
public class CmdServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmdServiceApplication.class, args);
	}

}
