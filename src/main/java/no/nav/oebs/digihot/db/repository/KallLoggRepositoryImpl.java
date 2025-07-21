package no.nav.oebs.digihot.db.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import no.nav.oebs.digihot.db.entity.KallLogg;
import org.springframework.stereotype.Repository;

@Repository
public class KallLoggRepositoryImpl implements KallLoggRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void pingKallLogg() {
		entityManager.createQuery("SELECT k FROM KallLogg k WHERE k.id = 0", KallLogg.class) //
				.getResultList();
	}
}
