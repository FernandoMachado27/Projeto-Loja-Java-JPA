package br.com.fernando.loja.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil { // isolar criação EntityManager (faz ligação com BD)
	
	private static final EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("loja"); // nome que passo no XML
	
	public static EntityManager getEntityManager() {
		return FACTORY.createEntityManager();
	}

}
