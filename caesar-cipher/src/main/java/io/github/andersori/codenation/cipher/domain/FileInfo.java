package io.github.andersori.codenation.cipher.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo {

	public static class Builder {
		private Integer numeroCasas;
		private String token;
		private String cifrado;
		private String decifrado;
		private String resumoCriptografico;

		public Builder setNumeroCasas(Integer numeroCasas) {
			this.numeroCasas = numeroCasas;
			return this;
		}

		public Builder setToken(String token) {
			this.token = token;
			return this;
		}

		public Builder setCifrado(String cifrado) {
			this.cifrado = cifrado;
			return this;
		}

		public Builder setDecifrado(String decifrado) {
			this.decifrado = decifrado;
			return this;

		}

		public Builder setResumoCriptografico(String resumoCriptografico) {
			this.resumoCriptografico = resumoCriptografico;
			return this;
		}

		public FileInfo build() {
			return new FileInfo(numeroCasas, token, cifrado, decifrado, resumoCriptografico);
		}
	}

	@JsonProperty("numero_casas")
	private Integer numeroCasas;
	@JsonProperty("token")
	private String token;
	@JsonProperty("cifrado")
	private String cifrado;
	
	@Setter
	@JsonProperty("decifrado")
	private String decifrado;
	@Setter
	@JsonProperty("resumo_criptografico")
	private String resumoCriptografico;
}
