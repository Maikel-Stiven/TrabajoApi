package com.example.AppTienda.controller;

import com.example.AppTienda.dto.HttpGlobalResponseDTO;
import com.example.AppTienda.dto.request.ProductRequestDTO;
import com.example.AppTienda.dto.response.ProductResponseDTO;
import com.example.AppTienda.enums.RoleEnum;
import com.example.AppTienda.security.RequiresRole;
import com.example.AppTienda.service.ProductsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {

    private final ProductsService productsService;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping
    public ResponseEntity<HttpGlobalResponseDTO<List<ProductResponseDTO>>> getProducts(
            @RequestParam(required = false) Integer categoryId) {
        List<ProductResponseDTO> catalog = productsService.getPublicCatalog(categoryId);
        return ResponseEntity.ok(HttpGlobalResponseDTO.of("Catálogo obtenido exitosamente", catalog));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpGlobalResponseDTO<ProductResponseDTO>> getProductById(@PathVariable Integer id) {
        ProductResponseDTO product = productsService.getProductById(id);
        return ResponseEntity.ok(HttpGlobalResponseDTO.of("Detalle de producto obtenido", product));
    }

    @GetMapping("/mine")
    @RequiresRole(RoleEnum.VENDEDOR)
    public ResponseEntity<HttpGlobalResponseDTO<List<ProductResponseDTO>>> getMyProducts(HttpServletRequest request) {
        Integer sellerId = (Integer) request.getAttribute("userId");
        List<ProductResponseDTO> list = productsService.getMyProducts(sellerId);
        return ResponseEntity.ok(HttpGlobalResponseDTO.of("Mis productos obtenidos exitosamente", list));
    }

    @PostMapping
    @RequiresRole(RoleEnum.VENDEDOR)
    public ResponseEntity<HttpGlobalResponseDTO<ProductResponseDTO>> createProduct(
            @Valid @RequestBody ProductRequestDTO body,
            HttpServletRequest request) {
        Integer sellerId = (Integer) request.getAttribute("userId");
        ProductResponseDTO created = productsService.createProduct(body, sellerId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(HttpGlobalResponseDTO.of("Producto creado exitosamente", created));
    }

    @PutMapping("/{id}")
    @RequiresRole({RoleEnum.VENDEDOR, RoleEnum.ADMIN})
    public ResponseEntity<HttpGlobalResponseDTO<ProductResponseDTO>> updateProduct(
            @PathVariable Integer id,
            @Valid @RequestBody ProductRequestDTO body,
            HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("userId");
        Integer rolId = (Integer) request.getAttribute("rolId");
        ProductResponseDTO updated = productsService.updateProduct(id, body, userId, rolId);
        return ResponseEntity.ok(HttpGlobalResponseDTO.of("Producto actualizado exitosamente", updated));
    }

    @DeleteMapping("/{id}")
    @RequiresRole({RoleEnum.VENDEDOR, RoleEnum.ADMIN})
    public ResponseEntity<HttpGlobalResponseDTO<String>> deleteProduct(@PathVariable Integer id, HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("userId");
        Integer rolId = (Integer) request.getAttribute("rolId");
        productsService.deleteProduct(id, userId, rolId);
        return ResponseEntity.ok(HttpGlobalResponseDTO.of("Producto eliminado exitosamente", null));
    }
}