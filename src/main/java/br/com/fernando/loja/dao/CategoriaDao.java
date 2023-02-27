package br.com.fernando.loja.dao;

import javax.persistence.EntityManager;

import br.com.fernando.loja.modelo.Categoria;

public class CategoriaDao { // faz a ponte com BD
	
	private EntityManager em;

	public CategoriaDao(EntityManager em) {
		this.em = em;
	}
	
	public void cadastrar(Categoria categoria) {
		this.em.persist(categoria);
	}
	
	public void atualizar(Categoria categoria) {
		this.em.merge(categoria); // garantir que está no estado Managed invés de Detached
	}
	
	public void remover(Categoria categoria) {
		categoria = em.merge(categoria); // precisa guardar a entidade mergiada
		this.em.remove(categoria);
	}
}
