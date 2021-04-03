package ba.unsa.etf.nwt.pet_category_service.repository;

import ba.unsa.etf.nwt.pet_category_service.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    //Optional<Pet> findByName(String name);

    List<Pet> findByRase_Id(Long id);

    List<Pet> findByRase_Category_Id(Long id);

    List<Pet> findByNameContains(String substring);

    List<Pet> findByRase_NameContains(String substring);

    List<Pet> findPetsByName(String substring);

}
