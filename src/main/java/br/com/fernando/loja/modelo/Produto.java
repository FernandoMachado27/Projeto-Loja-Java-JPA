package br.com.fernando.loja.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQuery;

@Entity // essa classe é uma entidade, uma tabela no BD está mapeando, que é um espelho desta classe
@Table(name = "produtos") // nome da tabela
@NamedQuery(name = "Produto.produtosPorCategoria", query = "SELECT p FROM Produto p WHERE p.categoria.id.nome = :nome") // da um nome pra query
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // essa classe vai ser usada como herança, vai virar uma tabela só (SINLGE_TABLE)
public class Produto {
	
	@Id // indicando que é a chave primaria
	@GeneratedValue(strategy = GenerationType.IDENTITY) // indica que o valor vem do BD, é gerado no BD e tudo bem ser null na aplicação
	private Long id;
	private String nome;
	private String descricao;
	private BigDecimal preco;
	private LocalDate dataCadastro = LocalDate.now();
	
	@ManyToOne(fetch = FetchType.LAZY) // um produto tem uma categoria, uma categoria varios produtos
	private Categoria categoria; // relacionamento 2 entidades
	
	public Produto() {
		
	}
	
	public Produto(String nome, String descricao, BigDecimal preco, Categoria categoria) {
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
		this.categoria = categoria;
	}

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public BigDecimal getPreco() {
		return preco;
	}
	
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

}
