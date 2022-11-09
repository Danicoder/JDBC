package com.example.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.PooledConnection;

import com.example.entidades.Product;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductDAOMariaDB implements ProductDAO{

    private final PooledConnection pcon;

    @Override
    public List<Product> findByCategory(int id) {
        List<Product> Procat = new ArrayList<>();
        try(Connection conn = pcon.getConnection()) {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM product WHERE category = ?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if(rs.first()){
                Procat.add(new Product(rs.getInt("id"),rs.getString("reference"), rs.getString("name"), rs.getDouble("price"), rs.getInt("category")));
            }
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        return Procat;
    }

    @Override
    public Product insertarProducto(Product p) {
        try(Connection conn = pcon.getConnection()) {
            PreparedStatement st = conn.prepareStatement("INSERT INTO (reference,name,price,category) VALUES(?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            st.setString(1, p.getReference());
            st.setString(2, p.getNombre());
            st.setDouble(3, p.getPrecio());
            st.setInt(4, p.getCategory());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.first();//devuelve una fila con una clave
            p = new Product(rs.getInt(1),p.getReference(),p.getNombre(),p.getPrecio(),p.getCategory());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return p;
    }

}
