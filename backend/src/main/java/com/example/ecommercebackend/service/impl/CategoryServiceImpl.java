package com.example.ecommercebackend.service.impl;

import com.example.ecommercebackend.dto.CategoryDTO;
import com.example.ecommercebackend.dto.CategoryResponse;
import com.example.ecommercebackend.model.Category;
import com.example.ecommercebackend.repository.CategoryRepository;
import com.example.ecommercebackend.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryResponse fetchCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        Page<CategoryDTO> categoryDTOPage = categoryPage.map(category -> modelMapper.map(category, CategoryDTO.class));
        return new CategoryResponse(
                categoryDTOPage.getContent(),
                categoryDTOPage.getNumber(),
                categoryDTOPage.getSize(),
                categoryDTOPage.getTotalElements(),
                categoryDTOPage.getTotalPages(),
                categoryDTOPage.isLast()
        );
    }


    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Optional<Category> existing = categoryRepository.findByCategoryName(categoryDTO.getCategoryName());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Category with name '" + categoryDTO.getCategoryName() + "' already exists");
        }
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    existingCategory.setCategoryName(categoryDTO.getCategoryName());
                    Category updatedCategory = categoryRepository.save(existingCategory);
                    return modelMapper.map(updatedCategory, CategoryDTO.class);
                })
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    @Override
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public List<CategoryDTO> fetchAllCategory() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream().map(category -> modelMapper.map(category, CategoryDTO.class)).toList();
    }
}
