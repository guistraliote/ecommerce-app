package com.gstraliote.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAllPaged(PageRequest pageRequest) {
        Page<Category> list = categoryRepository.findAll(pageRequest);

        return list.map(this::convertToDTO);
    }

    @Transactional(readOnly = true)
    public CategoryDTO findCategoryById(Long id) {
        Category entity = getCategoryById(id);

        return convertToDTO(entity);
    }

    @Transactional
    public CategoryDTO createCategory(CategoryDTO dto) {
        Category entityToSave = convertToEntity(dto);
        Category savedEntity = categoryRepository.save(entityToSave);

        return convertToDTO(savedEntity);
    }

    @Transactional
    public CategoryDTO updateCategory(Long id, CategoryDTO dto) {
        Category entity = getCategoryById(id);

        updateEntityFromDTO(entity, dto);

        categoryRepository.save(entity);

        return convertToDTO(entity);
    }

    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }

    private Category getCategoryById(Long id) {
        Optional<Category> obj = categoryRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
    }

    private CategoryDTO convertToDTO(Category entity) {
        return new CategoryDTO(
                entity.getId(),
                entity.getName(),
                entity.getActive(),
                entity.getPath()
        );
    }

    private Category convertToEntity(CategoryDTO dto) {
        Category entity = new Category();
        updateEntityFromDTO(entity, dto);
        return entity;
    }

    private void updateEntityFromDTO(Category entity, CategoryDTO dto) {
        entity.setActive(dto.active());
        entity.setName(dto.name());
        entity.setPath(dto.path());
    }
}