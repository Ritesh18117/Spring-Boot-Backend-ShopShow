package com.shopshow.backend.dao;

import com.shopshow.backend.entities.Size;
import org.springframework.data.repository.CrudRepository;

public interface SizeRepository extends CrudRepository<Size,Long> {
    Size findBySize(String size);
}
