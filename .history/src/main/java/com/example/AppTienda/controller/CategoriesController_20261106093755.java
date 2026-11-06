package com.example.AppTienda.controller;

import com.example.AppTienda.dto.HttpGlobalResponseDTO;
import com.example.AppTienda.dto.request.CategoryRequestDTO;
import com.example.AppTienda.dto.response.CategoryResponseDTO;
import com.example.AppTienda.enums.RoleEnum;
import com.example.AppTienda.security.RequiresRole;
import com.example.AppTienda.service.CategoriesService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tienda/categories")
public class CategoriesController {

    private final CategoriesService categoriesService;

    public CategoriesController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    @GetMapping
    @RequiresRole({RoleEnum.ADMIN, RoleEnum.CLIENTE, RoleEnum.VENDEDOR})
    public ResponseEntity<HttpGlobalResponseDTO<List<CategoryResponseDTO>>> getAllCategories() {
        List<CategoryResponseDTO> list = categoriesService.getAllCategories();
        return ResponseEntity.ok(HttpGlobalResponseDTO.of("Categorías obtenidas exitosamente", list));
    }

    @PostMapping
    @RequiresRole(RoleEnum.ADMIN)
    public ResponseEntity<HttpGlobalResponseDTO<CategoryResponseDTO>> createCategory(@Valid @RequestBody CategoryRequestDTO request) {
        CategoryResponseDTO category = categoriesService.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(HttpGlobalResponseDTO.of("Categoría creada exitosamente", category));
    }

    @DeleteMapping("/{id}")
    @RequiresRole(RoleEnum.ADMIN)
    public ResponseEntity<HttpGlobalResponseDTO<String>> deleteCategory(@PathVariable Integer id) {
        categoriesService.deleteCategory(id);
        return ResponseEntity.ok(HttpGlobalResponseDTO.of("Categoría eliminada exitosamente", null));
    }
}