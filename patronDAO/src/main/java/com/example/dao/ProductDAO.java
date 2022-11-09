package com.example.dao;

import java.util.List;

import com.example.entidades.Product;

public interface ProductDAO {
    /**
     * Devuelve todas los productos
     * @return Lista de objetos Product
     */
    List<Product> findByCategory(int id);
    /**
     * Devuelve todas los productos
     * @return Lista de objetos Product
     */
    Product insertarProducto(Product p);
}
