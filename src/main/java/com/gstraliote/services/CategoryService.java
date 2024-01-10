package com.gstraliote.services;

import com.gstraliote.dto.CategoryDTO;
import com.gstraliote.entities.Category;
import com.gstraliote.repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        return list.map(x -> new CategoryDTO(x.getId(), x.getName(), x.getActive(), x.getPath()));
    }

    @Transactional(readOnly = true)
    public CategoryDTO findCategoryById(Long id) {
        Optional<Category> obj = categoryRepository.findById(id);
        Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new CategoryDTO(entity.getId(), entity.getName(), entity.getActive(), entity.getPath());
    }

    @Transactional
    public CategoryDTO createCategory(CategoryDTO dto) {
        Category entityToSave = new Category();
        entityToSave.setName(dto.name());
        Category savedEntity = categoryRepository.save(entityToSave);
        return new CategoryDTO(savedEntity.getId(), savedEntity.getName(), savedEntity.getActive(), savedEntity.getPath());
    }

    @Transactional
    public CategoryDTO updateCategory(Long id, CategoryDTO dto) {
        try {
            Category entity = categoryRepository.getOne(id);
            entity.setName(dto.name());
            categoryRepository.save(entity);
            return new CategoryDTO(entity.getId(), entity.getName(), entity.getActive(), entity.getPath());
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id " + id + " not found");
        }
    }

    public void deleteCategoryById(Long id) {
        try {
            categoryRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id " + id + " not found");
        }
        catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Integrity violation");
        }
    }
}
