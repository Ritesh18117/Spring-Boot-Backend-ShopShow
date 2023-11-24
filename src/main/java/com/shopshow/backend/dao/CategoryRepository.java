package com.shopshow.backend.dao;

import com.shopshow.backend.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category,Long> {
}
