package br.com.fernando.loja.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.fernando.loja.modelo.Produto;

public class ProdutoDao { // faz a ponte com BD
	
	private EntityManager em;

	public ProdutoDao(EntityManager em) {
		this.em = em;
	}
	
	public void cadastrar(Produto produto) {
		this.em.persist(produto);
	}
	
	public void atualizar(Produto produto) {
		this.em.merge(produto); // garantir que está no estado Managed invés de Detached
	}
	
	public void remover(Produto produto) {
		produto = em.merge(produto); // precisa guardar a entidade mergiada
		this.em.remove(produto);
	}
	
	public Produto buscarPorId(Long id) {
		return em.find(Produto.class, id); // passo a entidade e o que quero buscar
	}
	
	public List<Produto> buscarTodos(){
		String jpql = "SELECT p FROM Produto p"; // select com todos atributos pelo nome da entidade
		return em.createQuery(jpql, Produto.class).getResultList(); // falo que vai devolver list de Produto
	}
	
	public List<Produto> buscarPorNome(String nome){
		String jpql = "SELECT p FROM Produto p WHERE p.nome = :nome";
		return em.createQuery(jpql, Produto.class).setParameter("nome", nome).getResultList(); // vou substituir o parametro nome pelo nome da query
	}
	
	public List<Produto> buscarPorNomeDaCategoria(String nome){ // tabela que tem relacionamento, faz um JOIN, NamedQuery na Produto.java
		return em.createNamedQuery("Produto.produtosPorCategoria", Produto.class).setParameter("nome", nome).getResultList(); 
	}
	
	public BigDecimal buscarPrecoDoProdutoComNome(String nome){ // Retorno em BigDecimal
		String jpql = "SELECT p.preco FROM Produto p WHERE p.nome = :nome";
		return em.createQuery(jpql, BigDecimal.class).setParameter("nome", nome).getSingleResult(); // metodo que carrega 1 registro
	}
	
	public List<Produto> buscarPorParametros( String nome, BigDecimal preco, LocalDate dataCadastro){ // metodo parametros opcionais(pode passar qualquer um)
		String jpql = "SELECT P FROM Produto p WHERE 1=1";
		
		if (nome != null && !nome.trim().isEmpty()) {
			jpql = " AND p.nome = :nome";
		}
		if (preco != null) {
			jpql = " AND p.preco = :preco";
		}
		if (dataCadastro != null) {
			jpql = " AND p.dataCadastro = :dataCadastro";
		}
		
		TypedQuery<Produto> query = em.createQuery(jpql, Produto.class); // criar a query relacionado se o parametro veio ou não
		
		if (nome != null && !nome.trim().isEmpty()) {
			query.setParameter("nome", nome);
		}
		if (preco != null) {
			query.setParameter("preco", preco);
		}
		if (dataCadastro != null) {
			query.setParameter("dataCadastro", dataCadastro);
		}
		
		return query.getResultList();
	}
	
	public List<Produto> buscarPorParametrosComCriteria( String nome, BigDecimal preco, LocalDate dataCadastro){
		CriteriaBuilder builder = em.getCriteriaBuilder(); // builder cria obj Criteria
		CriteriaQuery<Produto> query = builder.createQuery(Produto.class); // criei a query
		Root<Produto> from = query.from(Produto.class); // vai fazer o FROM no produto.class
		
		Predicate filtros = builder.and(); // trabalhar ifs em cima dessa variavel, fazer os filtros
		if (nome != null && !nome.trim().isEmpty()) {
			filtros = builder.and(filtros, builder.equal(from.get("nome"), nome)); // cria novo AND usando o atual
		}
		if (preco != null) {
			filtros = builder.and(filtros, builder.equal(from.get("preco"), preco));
		}
		if (dataCadastro != null) {
			filtros = builder.and(filtros, builder.equal(from.get("dataCadastro"), dataCadastro));
		}
		query.where(filtros); // pega os filtros e joga no where
		
		return em.createQuery(query).getResultList();
	}
}
