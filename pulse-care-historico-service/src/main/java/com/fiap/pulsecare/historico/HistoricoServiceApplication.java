package com.fiap.pulsecare.historico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.fiap.pulsecare")
public class HistoricoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HistoricoServiceApplication.class, args);
	}

}
