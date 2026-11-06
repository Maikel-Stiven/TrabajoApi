package com.example.AppTienda.service;

import com.example.AppTienda.dto.request.ProductRequestDTO;
import com.example.AppTienda.dto.response.CategoryResponseDTO;
import com.example.AppTienda.dto.response.ProductResponseDTO;
import com.example.AppTienda.entity.Categories;
import com.example.AppTienda.entity.Products;
import com.example.AppTienda.entity.Users;
import com.example.AppTienda.enums.RoleEnum;
import com.example.AppTienda.exception.CustomException;
import com.example.AppTienda.repository.CategoryRepository;
import com.example.AppTienda.repository.ProductsRepository;
import com.example.AppTienda.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductsService {

    private final ProductsRepository productsRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final UsersService usersService;

    public ProductsService(ProductsRepository productsRepository,
                          CategoryRepository categoryRepository,
                           UserRepository userRepository,
                           UsersService usersService) {
        this.productsRepository = productsRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.usersService = usersService;
    }

    public List<ProductResponseDTO> getPublicCatalog(Integer categoryId) {
        List<Products> products = (categoryId != null) 
                ? productsRepository.findByCategoryId(categoryId)
                : productsRepository.findAll();

        return products.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public ProductResponseDTO getProductById(Integer id) {
        Products product = productsRepository.findById(id)
                .orElseThrow(() -> new CustomException("Producto no encontrado", HttpStatus.NOT_FOUND));
        return mapToDTO(product);
    }

    public List<ProductResponseDTO> getMyProducts(Integer sellerId) {
        return productsRepository.findBySellerId(sellerId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public ProductResponseDTO createProduct(ProductRequestDTO dto, Integer sellerId) {
        Categories category = categoriesRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CustomException("Categoría no encontrada", HttpStatus.NOT_FOUND));

        Users seller = usersRepository.findById(sellerId)
                .orElseThrow(() -> new CustomException("Vendedor no encontrado", HttpStatus.NOT_FOUND));

        Products product = Products.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .category(category)
                .seller(seller)
                .build();

        return mapToDTO(productsRepository.save(product));
    }

    public ProductResponseDTO updateProduct(Integer productId, ProductRequestDTO dto, Integer currentUserId, Integer currentUserRolId) {
        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new CustomException("Producto no encontrado", HttpStatus.NOT_FOUND));

        validateOwnership(product, currentUserId, currentUserRolId);

        Categories category = categoriesRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CustomException("Categoría no encontrada", HttpStatus.NOT_FOUND));

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setCategory(category);

        return mapToDTO(productsRepository.save(product));
    }

    public void deleteProduct(Integer productId, Integer currentUserId, Integer currentUserRolId) {
        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new CustomException("Producto no encontrado", HttpStatus.NOT_FOUND));

        validateOwnership(product, currentUserId, currentUserRolId);
        productsRepository.delete(product);
    }

    private void validateOwnership(Products product, Integer currentUserId, Integer currentUserRolId) {
        if (currentUserRolId == RoleEnum.VENDEDOR.getId() && !product.getSeller().getId().equals(currentUserId)) {
            throw new CustomException("Acceso denegado: no eres el propietario de este producto", HttpStatus.FORBIDDEN);
        }
    }

    public ProductResponseDTO mapToDTO(Products product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .category(CategoryResponseDTO.builder()
                        .id(product.getCategory().getId())
                        .name(product.getCategory().getName())
                        .build())
                .seller(usersService.mapToUserResponse(product.getSeller()))
                .build();
    }
}