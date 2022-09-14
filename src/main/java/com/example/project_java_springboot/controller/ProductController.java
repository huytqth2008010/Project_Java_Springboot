package com.example.project_java_springboot.controller;

import com.example.project_java_springboot.entity.Category;
import com.example.project_java_springboot.entity.Product;
import com.example.project_java_springboot.entity.enums.ProductStatus;
import com.example.project_java_springboot.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Product>> findAll() {
        List<Product> productList = new ArrayList<>();

        List<Product> products = productService.findAll();
        for (Product obj: products) {
            if (obj.getStatus() == ProductStatus.ACTIVE){
                productList.add(obj);
            }
        }
        return ResponseEntity.ok(productList);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/get-page")
    public ResponseEntity<Page<Product>> getPage(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") Integer categoryId,
            @RequestParam(defaultValue = "") String sort,
            @RequestParam(defaultValue = "1") int pageIndex,
            @RequestParam(defaultValue = "10") int pageSize) {

        return ResponseEntity.ok(productService.getPage(keyword, categoryId, sort, pageIndex, pageSize));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Product> save(@RequestBody Product product) {
        product.setStatus(ProductStatus.ACTIVE);
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public ResponseEntity<Product> update(@PathVariable String id, @RequestBody Product product) {
        Optional<Product> optionalProduct = productService.findById(id);

        if (!optionalProduct.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Product existProduct = optionalProduct.get();


        if (product.getStatus() == null) {
            product.setStatus(ProductStatus.ACTIVE);
        }

        existProduct.setName(product.getName());
        existProduct.setThumbnail(product.getThumbnail());
        existProduct.setDescription(product.getDescription());
        existProduct.setUnit_price(product.getUnit_price());
        existProduct.setPromotion_price(product.getPromotion_price());
        existProduct.setQty(product.getQty());
        existProduct.setSlug(product.getSlug());
        existProduct.setStatus(product.getStatus());
        existProduct.setCategoryId(product.getCategoryId());

        return ResponseEntity.ok(productService.save(existProduct));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<Product> findById(@PathVariable String id) {
        Optional<Product> optionalProduct = productService.findById(id);

        if (!optionalProduct.isPresent()) {
            ResponseEntity.notFound();
        }

        return ResponseEntity.ok(optionalProduct.get());
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable String id) {
        Optional<Product> optionalProduct = productService.findById(id);

        if (!optionalProduct.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }

        Product existProduct = optionalProduct.get();
        existProduct.setStatus(ProductStatus.DELETED);
        productService.save(existProduct);

        return ResponseEntity.status(HttpStatus.OK).body(true);
    }
}
