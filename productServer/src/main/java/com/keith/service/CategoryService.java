package com.keith.service;


import com.keith.common.model.ProductCategory;

import java.util.List;

public interface CategoryService {
    List<ProductCategory>  findByCategoryType(List<Integer> categoryTypes);
}
