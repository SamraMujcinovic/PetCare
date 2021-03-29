package ba.unsa.etf.nwt.pet_category_service.repository;

import ba.unsa.etf.nwt.pet_category_service.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    Optional<Pet> findByName(String name);

    List<Pet> findByRase_Id(Long id);

    List<Pet> findByRase_Category_Id(Long id);

    List<Pet> findByNameContains(String substring);

    List<Pet> findByRase_NameContains(String substring);

}
