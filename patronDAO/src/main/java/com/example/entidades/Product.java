package com.example.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @NoArgsConstructor @AllArgsConstructor@ToString
public class Product {
    private int id;
    private String reference;
    private String nombre;
    private double precio;
    private int Category;
}
