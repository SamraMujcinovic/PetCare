package ba.unsa.etf.nwt.adopt_service.repository;

import ba.unsa.etf.nwt.adopt_service.model.AdoptionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdoptionRequestRepository extends JpaRepository<AdoptionRequest, Long> {
}
