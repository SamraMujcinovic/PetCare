package ba.unsa.etf.nwt.pet_category_service.repository;

import ba.unsa.etf.nwt.pet_category_service.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

}
