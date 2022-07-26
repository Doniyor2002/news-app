package com.example.news_app.service;

import com.example.news_app.entity.Category;
import com.example.news_app.exception.ResourceNotFoundException;
import com.example.news_app.repository.CategoryRepository;
import com.example.news_app.response.Apiresponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Apiresponse add(Category category) {
        boolean byName = categoryRepository.existsByName(category.getName());
        if (byName){
            return Apiresponse.builder().message("Bunday nomli category mavjud").succes(false).build();
        }
        else {
            Category save = categoryRepository.save(category);
            if (save!=null){
                return Apiresponse.builder().message("Added").succes(true).data(save).build();
            }
            else return Apiresponse.builder().message("Xatolik yuz berdi").succes(false).build();
        }
    }

    public Apiresponse getAll(int page, int size, String name) {
        Pageable pageable=PageRequest.of(page, size);
        Page<Category> all=null;
        if (name.equals("")){
            all=categoryRepository.findAll(pageable);
        }
        else {
          all =  categoryRepository.findAllByNameContainingIgnoreCase(name,pageable);
        }
        if (all==null&&name!=null){
            return Apiresponse.builder().succes(false).message("Bunday nomli Category mavjud emas").build();
        }
        return Apiresponse.builder().succes(true).message("Category list").data(all).build();

    }

    public Apiresponse getOne(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        return Apiresponse.builder().message("Category").succes(true).data(category).build();
    }

    public Apiresponse update(Category category, Long id) {
        Category category1 = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        category1.setName(category.getName());
        Category save = categoryRepository.save(category1);
        return Apiresponse.builder().succes(true).data(save).message("Updated").build();
    }

    public Apiresponse delete(Long id) {
        boolean exists = categoryRepository.existsById(id);
        if (exists){
            categoryRepository.deleteById(id);
            return Apiresponse.builder().message("Deleted").succes(true).build();
        }
        else return Apiresponse.builder().message("Bunday id li category mavjud emas").succes(true).build();
    }
}
