package ba.unsa.etf.nwt.adopt_service.repository;

import ba.unsa.etf.nwt.adopt_service.models.AddPetRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddPetRequestRepository extends JpaRepository<AddPetRequest, Long> {
}
