package io.github.andersori.codenation.cipher.service;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.andersori.codenation.cipher.domain.FileInfo;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Slf4j
@Service
public class CodenationService {

	@Value("${codenation.api.url}")
	private String url;
	@Value("${codenation.token}")
	private String token;
	
	private final OkHttpClient client = new OkHttpClient();

	public FileInfo getFile() throws IOException {
		log.info("{} - {}", url, token);
		Request req = new Request.Builder()
				.url(HttpUrl
						.parse(url + "/generate-data")
						.newBuilder()
						.addQueryParameter("token", token)
						.build())
				.build();

		try (Response res = client.newCall(req).execute()) {
			log.info("Arquivo recebido");
			FileInfo fileInfo = new ObjectMapper()
					.readValue(
							res.body().string(), 
							FileInfo.class);
			
			res.close();
			log.info(fileInfo.toString());
			return fileInfo;
		}
	}

	public File sendFile(File file) throws IOException {
		RequestBody body = new MultipartBody.Builder()
				.setType(MultipartBody.FORM)
				.addFormDataPart("answer", "answer.json", RequestBody.create(
						MediaType.parse("application/octet-stream"), file))
				.build();
		
		Request req = new Request.Builder()
				.url(HttpUrl
						.parse(url + "/submit-solution")
						.newBuilder()
						.addQueryParameter("token", token)
						.build())
				.post(body)
				.build();
		
//		try(Response res = client.newCall(req).execute()){
//			log.info("Arquivo enviado -> {}", res.body().toString());
//			res.close();
//			return file;
//		}
		return file;
	}
}
