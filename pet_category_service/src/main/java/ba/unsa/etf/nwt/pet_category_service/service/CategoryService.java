package ba.unsa.etf.nwt.pet_category_service.service;

import ba.unsa.etf.nwt.pet_category_service.exception.ResourceNotFoundException;
import ba.unsa.etf.nwt.pet_category_service.exception.WrongInputException;
import ba.unsa.etf.nwt.pet_category_service.model.Category;
import ba.unsa.etf.nwt.pet_category_service.repository.CategoryRepository;
import ba.unsa.etf.nwt.pet_category_service.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Response addCategory(Category category) {
        try {
            Category c = findCategoryByName(category.getName());
            throw new WrongInputException("Category with name " + c.getName() + " already exists!");
        }catch (ResourceNotFoundException e) {
            categoryRepository.save(category);
            return new Response(true, "Category added successfully!", HttpStatus.OK);
        }
    }

    public void saveCategory(Category c) {
        categoryRepository.save(c);
    }


    public Category getCategory(Long categoryID) {

        Category category = getCategoryById(categoryID);
        return category;

    }

    public Category getCategoryById(Long id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No category with ID " + id));
    }


    public Response deleteCategory(Long id) {

        Category c = getCategoryById(id);
        categoryRepository.deleteById(id);
        return new Response(true, "Category successfully deleted!", HttpStatus.OK);
    }

    public Category findCategoryByName(String name) {
        return categoryRepository
                .findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("No category with name " + name));
    }

    public Category getCategoryByName(String name) {
        //if(name == null) return new CategoryResponse(null, "Add a name for search!", "BAD_REQUEST", false);
        Category c = findCategoryByName(name);
        return c;
    }

    public Category updateCategory(Long id, Category newCategory) {
        Category c = getCategory(id);
        try {
            Category cr1 = getCategoryByName(newCategory.getName());
            //ako vec postoji kateogrija sa tim imenom vracamo response da postoji vec ta kategorija
            throw new WrongInputException("Category with that name already exists!");
        }catch(ResourceNotFoundException e){
            //ako nije nadjena kategorija sa tim imenom
            c.setDescription(newCategory.getDescription());
            c.setName(newCategory.getName());
            categoryRepository.save(c);
            return c;
        }
    }
}
