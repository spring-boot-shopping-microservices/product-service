package org.example.productservice.service;

import org.example.productservice.dto.ProductRequest;
import org.example.productservice.dto.ProductResponse;
import org.example.productservice.model.Product;
import org.example.productservice.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ProductServiceUnitTest {
  @Mock
  private ProductRepository productRepository;

  @InjectMocks
  private ProductService productService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  void tearDown() {
    productRepository.deleteAll();
  }

  @Test
  void createProduct() {
    // Given
    ProductRequest productRequest = new ProductRequest("Product 1", "This is product 1", new BigDecimal(999));

    // When
    productService.createProduct(productRequest);

    // Then
    verify(productRepository, times(1)).save(any(Product.class));
  }

  @Test
  void getAllProducts() {
    // Given
    Product product = new Product("1", "Product 1", "This is product 1", new BigDecimal(999));
    Product product2 = new Product("2", "Product 2", "This is product 2", new BigDecimal(1999));
    Product product3 = new Product("3", "Product 3", "This is product 3", new BigDecimal(2999));

    List<Product> products = Arrays.asList(product, product2, product3);

    when(productRepository.findAll()).thenReturn(products);

    // When
    List<ProductResponse> result = productService.getAllProducts();

    // Then
    assertNotNull(result);
    assertEquals(3, result.size());

    ProductResponse firstProduct = result.get(0);

    assertEquals(product.getId(), firstProduct.getId());
    assertEquals(product.getName(), firstProduct.getName());
    assertEquals(product.getDescription(), firstProduct.getDescription());
    assertEquals(product.getPrice(), firstProduct.getPrice());

    verify(productRepository, times(1)).findAll();
  }
}