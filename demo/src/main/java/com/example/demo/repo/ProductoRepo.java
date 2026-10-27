package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.demo.model.Producto;


public interface ProductoRepo extends JpaRepository <Producto,Long>{


}
