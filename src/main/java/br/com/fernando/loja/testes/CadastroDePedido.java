package br.com.fernando.loja.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.fernando.loja.dao.CategoriaDao;
import br.com.fernando.loja.dao.ClienteDao;
import br.com.fernando.loja.dao.PedidoDao;
import br.com.fernando.loja.dao.ProdutoDao;
import br.com.fernando.loja.modelo.Categoria;
import br.com.fernando.loja.modelo.Cliente;
import br.com.fernando.loja.modelo.ItemPedido;
import br.com.fernando.loja.modelo.Pedido;
import br.com.fernando.loja.modelo.Produto;
import br.com.fernando.loja.util.JPAUtil;
import br.com.fernando.loja.vo.RelatorioDeVendasVo;

public class CadastroDePedido {

	public static void main(String[] args) {
		popularBancoDeDados(); // salvei produto no BD
		
		EntityManager em = JPAUtil.getEntityManager();
		
		ProdutoDao produtoDao = new ProdutoDao(em);
		ClienteDao clienteDao = new ClienteDao(em);
		
		Produto produto = produtoDao.buscarPorId(1l); // recuperei produto recém salvo no BD
		Produto produto2 = produtoDao.buscarPorId(2l);
		Produto produto3 = produtoDao.buscarPorId(3l);
		Cliente cliente = clienteDao.buscarPorId(1l);
		
		
		em.getTransaction().begin(); // dentro de uma transação
		
		Pedido pedido = new Pedido(cliente); // vinculando cliente com pedido 
		pedido.adicionarItem(new ItemPedido(10, pedido, produto));
		pedido.adicionarItem(new ItemPedido(40, pedido, produto2));
		
		Pedido pedido2 = new Pedido(cliente);
		pedido.adicionarItem(new ItemPedido(2, pedido, produto3));
		
		PedidoDao pedidoDao = new PedidoDao(em);
		pedidoDao.cadastrar(pedido); // salvei pedido no BD
		pedidoDao.cadastrar(pedido2);
		
		em.getTransaction().commit();
		
		BigDecimal totalVendido = pedidoDao.valorTotalVendido();
		System.out.println("VALOR TOTAL: " + totalVendido);
		
		List<RelatorioDeVendasVo> relatorio = pedidoDao.relatorioDeVendas();
		relatorio.forEach(System.out::println); // percorre cada um dos elementos e imprime
	}
	
	private static void popularBancoDeDados() {
		Categoria celulares = new Categoria("CELULARES");
		Categoria videogames = new Categoria("VIDEOGAMES");
		Categoria informatica = new Categoria("INFORMATICA");
		
		Produto celular = new Produto("Xiaomi Redmi", "Muito legal", new BigDecimal("800"), celulares);
		Produto videogame = new Produto("PS5", "Playstation 5", new BigDecimal("800"), videogames);
		Produto macbook = new Produto("Macbook", "Macbook pro", new BigDecimal("800"), informatica);
		
		Cliente cliente = new Cliente("Rodrigo", "123456");

		EntityManager em = JPAUtil.getEntityManager();
		
		ProdutoDao produtoDao = new ProdutoDao(em);
		CategoriaDao categoriaDao = new CategoriaDao(em);
		ClienteDao clienteDao = new ClienteDao(em);
		
		em.getTransaction().begin(); // JPA pega a transação e inicie
		
		categoriaDao.cadastrar(celulares); // salvei 1° a categoria
		categoriaDao.cadastrar(videogames);
		categoriaDao.cadastrar(informatica);
		
		produtoDao.cadastrar(celular); // inserir registro no BD
		produtoDao.cadastrar(videogame);
		produtoDao.cadastrar(macbook);
		
		clienteDao.cadastrar(cliente);
		
		em.getTransaction().commit();
		em.close();
	}

}
