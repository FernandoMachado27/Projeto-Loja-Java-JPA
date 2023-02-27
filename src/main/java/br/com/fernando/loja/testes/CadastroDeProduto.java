package br.com.fernando.loja.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.fernando.loja.dao.CategoriaDao;
import br.com.fernando.loja.dao.ProdutoDao;
import br.com.fernando.loja.modelo.Categoria;
import br.com.fernando.loja.modelo.CategoriaId;
import br.com.fernando.loja.modelo.Produto;
import br.com.fernando.loja.util.JPAUtil;

public class CadastroDeProduto {

	public static void main(String[] args) {
		cadastrarProduto();
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao produtoDao = new ProdutoDao(em);
		
		Produto p = produtoDao.buscarPorId(1l); // (1 + L) pra entender que é Long
		System.out.println(p.getPreco());
		
		List<Produto> todos = produtoDao.buscarPorNomeDaCategoria("CELULARES");
		todos.forEach(p2 -> System.out.println(p.getNome())); // dado o produto quero fazer um sysout no p.getNome
		
		BigDecimal precoDoProduto = produtoDao.buscarPrecoDoProdutoComNome("Xiaomi Redmi");
		System.out.println("Preço do produto: " + precoDoProduto);
	}

	private static void cadastrarProduto() {
		Categoria celulares = new Categoria("CELULARES");
		
		Produto celular = new Produto("Xiaomi Redmi", "Muito legal", new BigDecimal("800"), celulares);

		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao produtoDao = new ProdutoDao(em);
		CategoriaDao categoriaDao = new CategoriaDao(em);
		
		em.getTransaction().begin(); // JPA pega a transação e inicie
		
		categoriaDao.cadastrar(celulares); // salvei 1° a categoria
		produtoDao.cadastrar(celular); // inserir registro no BD
		
		em.getTransaction().commit();
		
		em.find(Categoria.class, new CategoriaId("CELULARES", "xpto"));
		
		em.close();
	}

}
