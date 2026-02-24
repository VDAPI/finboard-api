package com.finboard.finboard.api.repository;

import com.finboard.finboard.api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    //generuje automatycznie findAll, findById, save, deleteById, count
}
