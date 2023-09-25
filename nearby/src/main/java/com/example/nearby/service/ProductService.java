package com.example.nearby.service;

import com.example.nearby.datamodel.domain.Category;
import com.example.nearby.datamodel.domain.PriceHistory;
import com.example.nearby.datamodel.domain.Product;
import com.example.nearby.datamodel.dto.CreateProductRequest;
import com.example.nearby.datamodel.dto.UpdateProductRequest;
import com.example.nearby.repository.CategoryRepository;
import com.example.nearby.repository.PriceHistoryRepository;
import com.example.nearby.repository.ProductRepository;
import com.example.nearby.service.handler.DistanceHandler;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService extends AbstractBaseService<Product, ProductRepository> {

    @Autowired
    private DistanceHandler distanceHandler;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PriceHistoryService priceHistoryService;

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

    public List<Product> getProductsNearestToCoordinate(Double lat, Double lon, String productName) {
        List<Product> products = productName.isEmpty() ? getRepository().findAll() :
                getProductsByNameStartingWith(productName);
        return distanceHandler.findNearestProducts(lat, lon, products);
    }

    public Product createProduct(CreateProductRequest productRequest) {
        ProductRepository repository = getRepository();
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setGpsCoordinates(productRequest.getGpsCoordinates());
        categoryService.getById(productRequest.getCategoryId()).ifPresent(product::setCategory);
        Product persistedProduct = repository.save(product);
        createPriceHistoryRecord(persistedProduct);
        return persistedProduct;
    }

    private void createPriceHistoryRecord(Product persistedProduct) {
        PriceHistory priceHistory = new PriceHistory(persistedProduct, java.time.LocalDateTime.now());
        priceHistoryService.create(priceHistory);
    }

    public boolean updateProduct(UpdateProductRequest updateProductRequest, Long productId) {
        ProductRepository repository = getRepository();
        Optional<Product> productOptional = repository.findById(productId);
        if (productOptional.isPresent()) {
            Product persistedProduct = productOptional.get();
            boolean isNewPriceHistoryRecordNeeded = !persistedProduct.getPrice().equals(updateProductRequest.getPrice());
            persistedProduct.setName(updateProductRequest.getName());
            persistedProduct.setDescription(updateProductRequest.getDescription());
            persistedProduct.setImage(updateProductRequest.getImage());
            persistedProduct.setPrice(updateProductRequest.getPrice());
            categoryService.getById(updateProductRequest.getCategoryId()).ifPresent(persistedProduct::setCategory);
            if (isNewPriceHistoryRecordNeeded) {
                createPriceHistoryRecord(repository.save(persistedProduct));
            } else {
                repository.save(persistedProduct);
            }
            return true;
        } else {
            return false;
        }
    }

    public List<Product> getProductsByNameStartingWith(String productName) {
        return getRepository().getProductsByNameStartingWith(productName);
    }
}
