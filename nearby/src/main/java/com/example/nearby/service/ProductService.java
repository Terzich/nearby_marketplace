package com.example.nearby.service;

import com.example.nearby.datamodel.domain.Category;
import com.example.nearby.datamodel.domain.Product;
import com.example.nearby.datamodel.dto.UpdateProductRequest;
import com.example.nearby.repository.CategoryRepository;
import com.example.nearby.repository.ProductRepository;
import com.example.nearby.service.handler.DistanceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService extends AbstractBaseService<Product, ProductRepository> {

    @Autowired
    private DistanceHandler distanceHandler;

    @Autowired
    private CategoryService categoryService;

    public ProductService(ProductRepository repository) {
        super(repository);
    }

    @Override
    public Optional<Product> getById(Long id) {
        Optional<Product> product = super.getById(id);
        product.ifPresent(value -> value.setViews(value.getViews() + 1));
        product.ifPresent(value -> getRepository().save(value));
        return product;
    }

    public List<Product> getProductsNearestToCoordinate(Double lat, Double lon) {
        List<Product> products = getRepository().findAll();
        return distanceHandler.findNearestProducts(lat, lon, products);
    }

    public boolean updateProduct(UpdateProductRequest updateProductRequest, Long productId) {
        ProductRepository repository = getRepository();
        Optional<Product> productOptional = repository.findById(productId);
        if(productOptional.isPresent()) {
            Product persistedProduct = productOptional.get();
            persistedProduct.setName(updateProductRequest.getName());
            persistedProduct.setDescription(updateProductRequest.getDescription());
            persistedProduct.setImage(updateProductRequest.getImage());
            categoryService.getById(updateProductRequest.getCategoryId()).ifPresent(persistedProduct::setCategory);
            repository.save(persistedProduct);
            return true;
        } else {
            return false;
        }
    }

}
