package io.github.andersori.codenation.cipher.service;

import java.io.File;
import java.io.IOException;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.andersori.codenation.cipher.domain.FileInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileService {

	public File processFile(FileInfo fileInfo) throws IOException {
		log.info("Processando arquivo");

		fileInfo.setDecifrado(decipher(fileInfo.getCifrado(), fileInfo.getNumeroCasas()));
		fileInfo.setResumoCriptografico(sha1(fileInfo.getDecifrado()));

		File file = File.createTempFile("answer", ".json");

		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(file, fileInfo);

		log.info("Arquivo processado -> {}", mapper.readValue(file, FileInfo.class));
		return file;
	}

	private String decipher(String msg, int shift) {
		msg = msg.toLowerCase();
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < msg.length(); i++) {
			char asciiCode = msg.charAt(i);

			if (asciiCode >= 97 && asciiCode <= 122) {

				if ((asciiCode - shift) < 97) {
					asciiCode = (char) ((asciiCode - shift) + 26);
				} else {
					asciiCode = (char) (asciiCode - shift);
				}
				
				result.append(Character.toString(asciiCode));
			} else {
				result.append(asciiCode);
			}
		}

		return result.toString();
	}

	private String sha1(String msg) {
		return DigestUtils.sha1Hex(msg);
	}
}
