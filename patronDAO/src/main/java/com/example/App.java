package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.PooledConnection;

import org.mariadb.jdbc.MariaDbPoolDataSource;

import com.example.dao.CategoryDAO;
import com.example.dao.CategoryDAOMariaDB;
import com.example.dao.ProductDAO;
import com.example.dao.ProductDAOMariaDB;
import com.example.entidades.Category;
import com.example.entidades.Product;

/**
 * Ejemplo 3 de JDBC pero con Pool de conexiones
 */
public class App {
    private static PooledConnection pcon;
    private static CategoryDAO catDao;
    private static ProductDAO prodDao;

    public static void mostrarCategorias() {
        List<Category> cats = catDao.findAll();
        cats.forEach(c -> System.out.println(c));
    }

    public static void insertarCategoria() {
        System.out.print("Nombre categoría: ");
        String nombre = System.console().readLine();
        Category c = new Category(0, nombre);
        Category insertedCat = catDao.insert(c);
        System.out.println("Categoría insertada: " + insertedCat);
    }

    public static void actualizaCategoria() {
        mostrarCategorias();
        System.out.print("Eligue la id de categoría a modificar: ");
        int id = Integer.parseInt(System.console().readLine());
        Category c = catDao.findById(id);
       if(c != null){
            System.out.print("Nombre categoría: ");
            String nombre = System.console().readLine();
            c.setName(nombre);
            catDao.update(c);
       }
       else{
        System.out.println("La categoría indicada no existe");
       }
    }

    public static void borraCategoria() {
        mostrarCategorias();
        System.out.print("Eligue la id de categoría a modificar: ");
        int id = Integer.parseInt(System.console().readLine());
        catDao.delete(id);
    }

    public static void mostrarProducto() {
        mostrarCategorias();
        System.out.print("Dime la id de la categoría: ");
        int id = Integer.parseInt(System.console().readLine());

        List<Product> prods = prodDao.findByCategory(id);
        prods.forEach(c -> System.out.println(c));
    }
    public static void insertarProducto() {
        System.out.print("Referencia a modificar: ");
        String reference = System.console().readLine();
        System.out.print("Nombre a modificar: ");
        String name = System.console().readLine();
        System.out.print("Precio a modificar: ");
        Double precio = Double.parseDouble(System.console().readLine());
        System.out.print("Categoria a modificar: ");
        int categoria = Integer.parseInt(System.console().readLine());
       
        Product product = new Product(0, reference, name, precio, categoria);
        Product productosIntroducidos = prodDao.insertarProducto(product);
        System.out.println("Producto insertado: " + productosIntroducidos);
    }
    public static void showMenu() {
        int opcion;
        do {
            System.out.println("\n-------- MENU --------");
            System.out.println("1. Mostrar categorías");
            System.out.println("2. Añadir categoría");
            System.out.println("3. Modificar categoría");
            System.out.println("4. Borrar categoría");
            System.out.println("5. Mostrar Productos");
            System.out.println("6. Añadir Producto");
            System.out.println("0. Salir");

            System.out.print("Elige una opción: ");
            try {
                opcion = Integer.parseInt(System.console().readLine());
            } catch(NumberFormatException e) {
                System.err.println("La opción debe ser numérica");
                opcion = -1;
            }

            switch(opcion) {
                case 1 -> mostrarCategorias();
                case 2 -> insertarCategoria();
                case 3 -> actualizaCategoria();
                case 4 -> borraCategoria();
                case 5 -> mostrarProducto();
                case 6 -> insertarProducto();
            }
        } while(opcion != 0);
    }

    public static void main( String[] args ) throws SQLException {
        try (// Creamos Pool de conexiones MariaDB
        MariaDbPoolDataSource pool = new MariaDbPoolDataSource("jdbc:mariadb://localhost:3306/product-manager?user=root&maxPoolSize=10")) {
            pcon = pool.getPooledConnection();
        }
        /*CategoryDAO catDao = new CategoryDAOMariaDB(pcon);
        List<Category> cats = catDao.findAll();
        cats.forEach(c -> System.out.println(c));*/
        catDao = new CategoryDAOMariaDB(pcon);
        prodDao = new ProductDAOMariaDB(pcon);
        /*System.out.println("Obtener la categoría 1");
        Category c1 = catDao.findById(1);
        System.out.println(c1);*/
        showMenu();

        /*System.out.println("Obtener categoría que no existe");
        Category c99 = catDao.findById(99);
        System.out.println(c99);*/
         // System.out.println("Obtener la categoría 1");
        // Category c1 = catDao.findById(1);
        // System.out.println(c1);

        /*Category c = new Category(0,"Nueva");
        Category cInsert = catDao.insert(c);
        System.out.println(cInsert);

        Category c25 = catDao.findById(25);
        c25.setName("Nombre cambiado");
        System.out.println(c25);*/

        //catDao.delete(25);
    }
}
