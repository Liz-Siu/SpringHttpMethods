package com.example.demo.producto;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Producto;
import com.example.demo.repo.ProductoRepo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
public class ProductoController {
@Autowired
ProductoRepo repo;
@GetMapping("productos")
  public List<Producto> findAllProductos() {
    return this.repo.findAll();
  }

@PostMapping("producto")
  public Producto addProducto(@RequestBody Producto producto) {
    return repo.save(producto);
  }

@GetMapping("/producto/{id}")
  public Optional<Producto> getById(@PathVariable Long id) {
    return repo.findById(id);
  }

@PutMapping("/producto/{id}")
  public Producto putProduct(@PathVariable Long id, @RequestBody Producto producto) {
    producto.setId(id);
     return repo.save(producto);
  }

@DeleteMapping("/producto/{id}")
  public String deleteProduct(@PathVariable("id") Long id) {
    repo.deleteById(id);
      return "{\"success\":true}";
    }

}

