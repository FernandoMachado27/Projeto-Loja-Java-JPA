package br.com.fernando.loja.dao;

import javax.persistence.EntityManager;

import br.com.fernando.loja.modelo.Cliente;

public class ClienteDao { // faz a ponte com BD
	
	private EntityManager em;

	public ClienteDao(EntityManager em) {
		this.em = em;
	}
	
	public void cadastrar(Cliente cliente) {
		this.em.persist(cliente);
	}

	public Cliente buscarPorId(Long id) {
		return em.find(Cliente.class, id); // passo a entidade e o que quero buscar
	}
	
}
