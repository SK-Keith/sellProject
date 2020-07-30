package com.keith.service.impl;

import com.keith.common.model.ProductCategory;
import com.keith.repository.ProductCategoryRepository;
import com.keith.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public List<ProductCategory> findByCategoryType(List<Integer> categoryTypes) {
        List<ProductCategory> productCategories = productCategoryRepository.findByCategoryTypeIn(Arrays.asList(11,22));

        return productCategories;
    }
}
