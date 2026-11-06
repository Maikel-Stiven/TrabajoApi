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

   private final CategoryRepository categoryRepository;
    private final ProductsRepository productsRepository;

    public CategoriesService(CategoriesService categoriesRepository, ProductsRepository productsRepository) {
        this.categoryRepository = categoryRepository;
        this.productsRepository = productsRepository;
    }

    public List<CategoryResponseDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private Collection<CategoryResponseDTO> findAll() {
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    public CategoryResponseDTO createCategory(CategoryRequestDTO dto) {
        if (categoryRepository.existsByName(dto.getName())) {
            throw new CustomException("La categoría ya existe", HttpStatus.CONFLICT);
        }

        Categories category = Categories.builder()
                .name(dto.getName())
                .build();

        return mapToDTO(categoryRepository.save(category));
    }

    public void deleteCategory(Integer id) {
        Categories category = categoryRepository.findById(id)
                .orElseThrow(() -> new CustomException("Categoría no encontrada", HttpStatus.NOT_FOUND));

        if (productsRepository.existsByCategoryId(id)) {
            throw new CustomException("No se puede eliminar la categoría porque tiene productos asociados", HttpStatus.CONFLICT);
        }

        categoryRepository.delete(category);
    }

    public CategoryResponseDTO mapToDTO(Categories category) {
        return CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}