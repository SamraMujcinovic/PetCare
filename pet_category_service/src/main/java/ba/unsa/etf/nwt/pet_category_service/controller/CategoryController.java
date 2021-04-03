package ba.unsa.etf.nwt.pet_category_service.controller;

import ba.unsa.etf.nwt.pet_category_service.model.Category;
import ba.unsa.etf.nwt.pet_category_service.response.Response;
import ba.unsa.etf.nwt.pet_category_service.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@RestController
public class CategoryController {

    private final CategoryService categoryService;


    @GetMapping("/categories")
    public List<Category> getCategories(){
        return categoryService.getCategories();
    }


    @GetMapping("/category/{id}")
    public Category getCategory(@NotNull @PathVariable Long id){
        return categoryService.getCategory(id);
    }

    @GetMapping("/category/byName")
    public Category getCategoryByName(@NotNull @RequestParam String name){
        return categoryService.getCategoryByName(name);
    }

    @PostMapping("/category")
    public Response addCategory(@Valid @RequestBody Category category){

        return categoryService.addCategory(category);
    }

    @DeleteMapping("/category")
    public Response deleteCategory(@NotNull @RequestParam Long id){
        return categoryService.deleteCategory(id);
    }

    @PutMapping("/category/update/{id}")
    public Category updateCategory(@NotNull @PathVariable Long id, @Valid @RequestBody Category category){
        return categoryService.updateCategory(id, category);
    }

}
