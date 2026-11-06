package com.example.AppTienda.service;

import com.example.AppTienda.dto.request.CategoryRequestDTO;
import com.example.AppTienda.dto.response.CategoryResponseDTO;
import com.example.AppTienda.entity.Categories;
import com.example.AppTienda.exception.CustomException;
import com.example.AppTienda.repository.CategoryRepository;
import com.example.AppTienda.repository.ProductsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriesService {

    private final CategoriesService categoriesRepository;
    private final ProductsRepository productsRepository;

    public CategoriesService(CategoriesService categoriesRepository, ProductsRepository productsRepository) {
        this.categoriesRepository = categoriesRepository;
        this.productsRepository = productsRepository;
    }

    public List<CategoryResponseDTO> getAllCategories() {
        return categoriesRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public CategoryResponseDTO createCategory(CategoryRequestDTO dto) {
        if (categoriesRepository.existsByName(dto.getName())) {
            throw new CustomException("La categoría ya existe", HttpStatus.CONFLICT);
        }

        Categories category = Categories.builder()
                .name(dto.getName())
                .build();

        return mapToDTO(categoriesRepository.save(category));
    }

    public void deleteCategory(Integer id) {
        Categories category = categoriesRepository.findById(id)
                .orElseThrow(() -> new CustomException("Categoría no encontrada", HttpStatus.NOT_FOUND));

        if (productsRepository.existsByCategoryId(id)) {
            throw new CustomException("No se puede eliminar la categoría porque tiene productos asociados", HttpStatus.CONFLICT);
        }

        categoriesRepository.delete(category);
    }

    public CategoryResponseDTO mapToDTO(Categories category) {
        return CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}