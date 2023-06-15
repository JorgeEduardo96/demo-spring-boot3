package pt.com.example.springboot3.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pt.com.example.springboot3.dtos.ProductRecordDTO;
import pt.com.example.springboot3.models.ProductModel;
import pt.com.example.springboot3.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository repository;

    @PostMapping
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDTO dto) {
        var model = new ProductModel();
        BeanUtils.copyProperties(dto, model);
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(model));
    }

    @GetMapping
    public List<ProductModel> getAllProducts() {
        List<ProductModel> products = repository.findAll();
        if (!products.isEmpty()) {
            products.forEach(product ->
                    product.add(linkTo(methodOn(ProductController.class).getOneProduct(product.getId())).withSelfRel()));
        }
        return products;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneProduct(@PathVariable UUID id) {
        Optional<ProductModel> productModelOptional = repository.findById(id);
        if (productModelOptional.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        ProductModel model = productModelOptional.get();
        model.add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("Products List"));
        return ResponseEntity.ok(model);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable UUID id, @RequestBody @Valid ProductRecordDTO dto) {
        Optional<ProductModel> productModelOptional = repository.findById(id);
        if (productModelOptional.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        var model = productModelOptional.get();
        BeanUtils.copyProperties(dto, model);

        return ResponseEntity.ok(repository.save(model));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        Optional<ProductModel> productModelOptional = repository.findById(id);
        if (productModelOptional.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        repository.delete(productModelOptional.get());

        return ResponseEntity.noContent().build();
    }
}
