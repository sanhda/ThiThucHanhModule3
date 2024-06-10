package com.alltosoftware.thi_thuc_hanh.service;

import com.alltosoftware.thi_thuc_hanh.entity.Category;
import com.alltosoftware.thi_thuc_hanh.entity.Product;
import com.alltosoftware.thi_thuc_hanh.model.CategoryDAO;
import com.alltosoftware.thi_thuc_hanh.model.ProductDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ProductService {

    private ProductDAO productDAO;
    private CategoryDAO categoryDAO;
    public ProductService() {
        this.productDAO = new ProductDAO();
        this.categoryDAO = new CategoryDAO();
    }

    public void renderPageListProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        List<Product> products = this.productDAO.selectAllProducts();
        request.setAttribute("products", products);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/productView/product-list.jsp");
        requestDispatcher.forward(request,response);
    }

    public void renderPageNewProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        List<Category> categories = this.categoryDAO.selectAllCategories();
        request.setAttribute("categories", categories);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/productView/product-new.jsp");
        requestDispatcher.forward(request,response);
    }

    public void renderPageEditProduct(HttpServletRequest request, HttpServletResponse response, int id) throws ServletException, IOException, SQLException {
        Product product = this.productDAO.selectById(id);
        request.setAttribute("product", product);
        List<Category> categories = this.categoryDAO.selectAllCategories();
        request.setAttribute("categories", categories);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/productView/product-edit.jsp");
        requestDispatcher.forward(request,response);
    }

    public void addNewProduct(HttpServletRequest request) throws ServletException, IOException, SQLException {
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String color = request.getParameter("color");
        String description = request.getParameter("description");
        int categoryId = Integer.parseInt(request.getParameter("category"));

        Product newProduct = new Product();
        newProduct.setName(name);
        newProduct.setPrice(price);
        newProduct.setQuantity(quantity);
        newProduct.setColor(color);
        newProduct.setDescription(description);
        newProduct.setCategory(new Category(categoryId));

        productDAO.insertProduct(newProduct);
    }

    public void deleteProduct(HttpServletRequest request, int id) throws ServletException, IOException, SQLException {
        this.productDAO.deleteById(id);
    }

    public void updateProduct(HttpServletRequest request) throws ServletException, IOException, SQLException {
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String color = request.getParameter("color");
        String description = request.getParameter("description");
        int categoryId = Integer.parseInt(request.getParameter("category"));

        Product Product = new Product();
        Product.setId(Integer.parseInt(request.getParameter("id")));
        Product.setName(name);
        Product.setPrice(price);
        Product.setQuantity(quantity);
        Product.setColor(color);
        Product.setDescription(description);
        Product.setCategory(new Category(categoryId));
        this.productDAO.update(Product);
    }
}
