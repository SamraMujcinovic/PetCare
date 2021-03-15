package ba.unsa.etf.nwt.pet_category_service.repository;

import ba.unsa.etf.nwt.pet_category_service.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
