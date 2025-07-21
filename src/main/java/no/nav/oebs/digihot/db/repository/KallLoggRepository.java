package no.nav.oebs.digihot.db.repository;

import no.nav.oebs.digihot.db.entity.KallLogg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.REQUIRES_NEW)
public interface KallLoggRepository extends JpaRepository<KallLogg, Integer>, KallLoggRepositoryCustom {

}
