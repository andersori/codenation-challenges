package io.github.andersori.codenation.cipher;

import java.io.File;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.andersori.codenation.cipher.service.CodenationService;
import io.github.andersori.codenation.cipher.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class App implements CommandLineRunner {

	private final CodenationService codenation;
	private final FileService fileService;

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			File fileSent = codenation.sendFile(fileService.processFile(codenation.getFile()));

			if (fileSent != null && fileSent.exists()) {
				fileSent.delete();
				log.info("Arquivo deletado");
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

}
