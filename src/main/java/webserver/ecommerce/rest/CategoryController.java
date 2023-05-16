package webserver.ecommerce.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webserver.ecommerce.business.dto.CategoryDto;
import webserver.ecommerce.business.dto.ProductDto;
import webserver.ecommerce.business.services.CategoryServices;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:8080")
public class CategoryController {
    @Autowired
    private CategoryServices categoryServices;

    @GetMapping("/getAllCategories")
    public List<CategoryDto> getAllCategories() {
        return categoryServices.getAll();
    }

    @PostMapping("/createCategory")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto dto){
        return categoryServices.create(dto);
    }

    @DeleteMapping("/deleteCategory")
    public ResponseEntity<Map<String, Boolean>> deleteCategory(@RequestParam Long id){
        return categoryServices.delete(id);
    }

    @PutMapping("/updateCategory")
    public ResponseEntity<CategoryDto> updateCategory(@RequestParam Long id, @RequestBody CategoryDto dto){
        return categoryServices.updateById(id, dto);
    }

    @GetMapping("/getCategoryById")
    public ResponseEntity<CategoryDto> getCategoryById(@RequestParam Long id) {
        return categoryServices.getById(id);
    }
}