package com.alltosoftware.thi_thuc_hanh.model;

import com.alltosoftware.thi_thuc_hanh.database.DBConnect;
import com.alltosoftware.thi_thuc_hanh.entity.Category;
import com.alltosoftware.thi_thuc_hanh.entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    private static final String SELECT_ALL_CATEGORIES = "SELECT * FROM categories";
    private Connection connection;

    public CategoryDAO(){
        DBConnect dbConnection =new DBConnect();
        connection = dbConnection.getConnection();
    }

    public List<Category> selectAllCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();
        PreparedStatement preparedStatement = this.connection.prepareStatement(SELECT_ALL_CATEGORIES);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            Category category = new Category();
            category.setId(id);
            category.setName(name);
            categories.add(category);
        }
        return categories;
    }
}
