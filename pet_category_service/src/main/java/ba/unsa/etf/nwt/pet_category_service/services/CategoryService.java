package ba.unsa.etf.nwt.pet_category_service.services;

import ba.unsa.etf.nwt.pet_category_service.models.Category;
import ba.unsa.etf.nwt.pet_category_service.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }
}
