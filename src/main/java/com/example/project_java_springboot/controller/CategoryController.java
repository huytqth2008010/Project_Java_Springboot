package com.example.project_java_springboot.controller;

import com.example.project_java_springboot.entity.Category;
import com.example.project_java_springboot.entity.enums.EnumStatus;
import com.example.project_java_springboot.entity.enums.ProductStatus;
import com.example.project_java_springboot.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Category>> findAll() {
        List<Category> categoryList = new ArrayList<>();

        List<Category> categories = categoryService.findAll();
        for (Category obj: categories) {
            if (obj.getStatus() == ProductStatus.ACTIVE){
                categoryList.add(obj);
            }
        }
        return ResponseEntity.ok(categoryList);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Category> save(@RequestBody Category category) {
        category.setStatus(ProductStatus.ACTIVE);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.save(category));
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public ResponseEntity<Category> update(@PathVariable Integer id, @RequestBody Category category) {
        Optional<Category> optionalCategory = categoryService.findById(id);

        if (!optionalCategory.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        if (category.getStatus() == null) {
            category.setStatus(ProductStatus.ACTIVE);
        }
        Category existCategory = optionalCategory.get();
        existCategory.setName(category.getName());
        existCategory.setThumbnail(category.getThumbnail());
        existCategory.setStatus(category.getStatus());

        return ResponseEntity.ok(categoryService.save(existCategory));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<Category> findById(@PathVariable Integer id) {
        Optional<Category> optionalCategory = categoryService.findById(id);

        if (!optionalCategory.isPresent()) {
            ResponseEntity.notFound();
        }

        return ResponseEntity.ok(optionalCategory.get());
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        Optional<Category> optionalCategory = categoryService.findById(id);

        if (!optionalCategory.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
        Category existCategory = optionalCategory.get();
        existCategory.setStatus(ProductStatus.DELETED);
        categoryService.save(existCategory);
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }
}
