package org.example.productservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.productservice.dto.ProductRequest;
import org.example.productservice.dto.ProductResponse;
import org.example.productservice.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private ProductService productService;

  @Test
  void createProduct() {
    // Given
    ProductRequest productRequest = new ProductRequest("Product 1", "This is product 1", new BigDecimal(999));

    // When
    try {
      mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(objectMapper.writeValueAsString(productRequest)))
              .andExpect(MockMvcResultMatchers.status().isCreated());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    // Then
    verify(productService, times(1)).createProduct(any(ProductRequest.class));
  }

  @Test
  void getAllProducts() {
    // Given
    List<ProductResponse> productResponses = Arrays.asList(
            new ProductResponse("1", "Product 1", "This is product 1", new BigDecimal(999)),
            new ProductResponse("2", "Product 2", "This is product 2", new BigDecimal(1999))
    );

    when(productService.getAllProducts()).thenReturn(productResponses);

    // When
    try {
      mockMvc.perform(MockMvcRequestBuilders.get("/api/products"))
              .andExpect(MockMvcResultMatchers.status().isOk())
              .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(productResponses)));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
