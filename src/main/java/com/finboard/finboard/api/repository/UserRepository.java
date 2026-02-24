package com.finboard.finboard.api.repository;

import com.finboard.finboard.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    //generuje automatycznie findAll, findById, save, deleteById, count
}
