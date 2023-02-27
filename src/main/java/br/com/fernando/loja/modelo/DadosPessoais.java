package br.com.fernando.loja.modelo;

import javax.persistence.Embeddable;

@Embeddable // classe pode ser imbutida dentro de uma entidade
public class DadosPessoais {
	
	private String nome;
	private String cpf;
	
	public DadosPessoais(String nome, String cpf) {
		this.nome = nome;
		this.cpf = cpf;
	}
	
	public DadosPessoais() {
		
	}

	public String getNome() {
		return nome;
	}

	public String getCpf() {
		return cpf;
	}

}
