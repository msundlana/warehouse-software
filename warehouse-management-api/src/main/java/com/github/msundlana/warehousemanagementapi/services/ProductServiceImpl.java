package com.github.msundlana.warehousemanagementapi.services;

import com.github.msundlana.warehousemanagementapi.exception.ProductNotFoundException;
import com.github.msundlana.warehousemanagementapi.models.Product;
import com.github.msundlana.warehousemanagementapi.models.ProductDTO;
import com.github.msundlana.warehousemanagementapi.repositories.ProductRepository;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl extends BaseService<Product,ProductDTO> implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    public ProductServiceImpl() {
        super(Product.class, ProductDTO.class);
    }

    private static final ModelMapper mapper = new ModelMapper();

    @Override
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        logger.info("Retrieving all products with pagination: {}", pageable);
        var products = productRepository.findAll(pageable);
        logger.info("Retrieved {} products", products.getTotalElements());
        return products.map(this::convertToDto);
    }

    @Override
    public ProductDTO getProductById(Long id) {
        logger.info("Retrieving product by ID: {}", id);
        var product = findProductById(id);
        logger.info("product found: {}", product);
        return this.convertToDto(product);
    }

    @Override
    public List<ProductDTO> addProducts(List<ProductDTO> productDTOLists){
        logger.info("Adding {} products", productDTOLists.size());
        var products = this.convertToEntity(productDTOLists);
        var savedProducts = productRepository.saveAll(products);
        logger.info("{} products added successfully", productDTOLists.size());
        return this.convertToDto(savedProducts);
    }
    @Override
    public ProductDTO addProduct(ProductDTO productDto) {
        logger.info("Adding new product: {}", productDto);
        var product = productRepository.save(this.convertToEntity(productDto));
        logger.info("Product added: {}", product);
        return this.convertToDto(product);
    }

    @Override
    public ProductDTO updateProduct(long id, ProductDTO productDto) {
        logger.info("Updating Product with ID {}: {}", id, productDto);
        var product = findProductById(id);
        mapper.map(productDto, product);
        var savedProduct = productRepository.save(product);
        logger.info("Product updated: {}", savedProduct);
        return this.convertToDto(savedProduct);
    }

    @Override
    public void removeProduct(long id) {
        logger.info("Deleting product with ID: {}", id);
        productRepository.deleteById(id);
        logger.info("Product deleted with ID: {}", id);
    }

    private Product findProductById(long id){
        return productRepository.findById(id).orElseThrow(()
                -> new ProductNotFoundException("Product not found with id: " + id));
    }

}
