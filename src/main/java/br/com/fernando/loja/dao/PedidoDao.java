package br.com.fernando.loja.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.fernando.loja.modelo.Pedido;
import br.com.fernando.loja.vo.RelatorioDeVendasVo;

public class PedidoDao { // faz a ponte com BD
	
	private EntityManager em;

	public PedidoDao(EntityManager em) {
		this.em = em;
	}
	
	public void cadastrar(Pedido pedido) {
		this.em.persist(pedido);
	}
	
	public BigDecimal valorTotalVendido() {
		String jpql = "SELECT SUM(p.valorTotal) FROM Pedido p"; // SUM = somatório, faz o select da tabela de pedidos e soma o valor total
		return em.createQuery(jpql, BigDecimal.class).getSingleResult();
	}
	
	public List<RelatorioDeVendasVo> relatorioDeVendas() {
		String jpql = "SELECT new br.com.fernando.loja.vo.RelatorioDeVendasVo(" // SELECT new para criar uma instancia dessa classe que não é entidade
				+ "produto.nome, "
				+ "SUM(item.quantidade), "
				+ "MAX(pedido.data)) "
				+ "FROM Pedido pedido "
				+ "JOIN pedido.itens item "
				+ "JOIN item.produto produto "
				+ "GROUP BY produto.nome "
				+ "ORDER BY item.quantidade DESC";
		return em.createQuery(jpql, RelatorioDeVendasVo.class).getResultList();
	}
	
	public Pedido buscarPedidoComCliente(Long id) { // JPA já busque alem do Pedido, o cliente também
		return em.createQuery("SELECT p FROM Pedido p JOIN FETCH p.cliente WHERE p.id = :id", Pedido.class).setParameter("id", id).getSingleResult();
	}
	
}
