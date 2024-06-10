package com.alltosoftware.thi_thuc_hanh.model;

import com.alltosoftware.thi_thuc_hanh.database.DBConnect;
import com.alltosoftware.thi_thuc_hanh.entity.Category;
import com.alltosoftware.thi_thuc_hanh.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    private Connection connection;

    public ProductDAO(){
        DBConnect dbConnection =new DBConnect();
        connection = dbConnection.getConnection();
    }

    private static final String SELECT_ALL_PRODUCTS = "SELECT p.id, p.name, p.price, p.quantity, p.color, p.description, c.name AS category FROM products p INNER JOIN categories c ON p.category_id = c.id";
    private static final String INSERT_PRODUCT_SQL = "INSERT INTO products (name, price, quantity, color, description, category_id) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_PRODUCT_SQL = "UPDATE products set name=?, price=?, quantity=?, color=?, description=?, category_id=? WHERE id = ?;";
    private static final String DELETE_PRODUCT_SQL = "DELETE FROM products WHERE id = ?";
    private static final String SELECT_BYID_PRODUCT_SQL = "SELECT p.*, c.name as CategoryName FROM products p INNER JOIN categories c ON p.category_id = c.id WHERE p.id = ?";

    public List<Product> selectAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        PreparedStatement preparedStatement = this.connection.prepareStatement(SELECT_ALL_PRODUCTS);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                String color = rs.getString("color");
                String description = rs.getString("description");
                String categoryName = rs.getString("category");

                Category category = new Category();
                category.setName(categoryName);

                Product product = new Product();
                product.setId(id);
                product.setName(name);
                product.setPrice(price);
                product.setQuantity(quantity);
                product.setColor(color);
                product.setDescription(description);
                product.setCategory(category);

                products.add(product);
        }
        return products;
    }

    public Product selectById(int id) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement(SELECT_BYID_PRODUCT_SQL);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            String name = rs.getString("name");
            double price = rs.getDouble("price");
            int quantity = rs.getInt("quantity");
            String color = rs.getString("color");
            String description = rs.getString("description");
            String categoryName = rs.getString("CategoryName");

            Category category = new Category(categoryName);

            Product product = new Product();
            product.setId(id);
            product.setName(name);
            product.setPrice(price);
            product.setQuantity(quantity);
            product.setColor(color);
            product.setDescription(description);
            product.setCategory(category);

            return product;
        }

        return null;
    }

    public void insertProduct(Product product) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_SQL)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setString(4, product.getColor());
            preparedStatement.setString(5, product.getDescription());
            preparedStatement.setInt(6, product.getCategory().getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean deleteById(int id) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement(DELETE_PRODUCT_SQL);
        preparedStatement.setInt(1, id);
        return preparedStatement.executeUpdate() > 0;
    }

    public boolean update(Product product) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE_PRODUCT_SQL);
        preparedStatement.setString(1, product.getName());
        preparedStatement.setDouble(2, product.getPrice());
        preparedStatement.setInt(3, product.getQuantity());
        preparedStatement.setString(4, product.getColor());
        preparedStatement.setString(5, product.getDescription());
        preparedStatement.setInt(6, product.getCategory().getId());
        preparedStatement.setInt(7, product.getId());
        return preparedStatement.executeUpdate() > 0;
    }
}
