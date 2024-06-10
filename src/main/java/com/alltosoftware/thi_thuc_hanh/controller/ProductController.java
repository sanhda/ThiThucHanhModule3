package com.alltosoftware.thi_thuc_hanh.controller;

import com.alltosoftware.thi_thuc_hanh.entity.Product;
import com.alltosoftware.thi_thuc_hanh.model.ProductDAO;
import com.alltosoftware.thi_thuc_hanh.service.ProductService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ProductServlet", urlPatterns = "/product/*")
public class ProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductService productService;

    public void init() {
        productService = new ProductService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = request.getPathInfo();
        switch (url) {
            case "/list":
                try {
                    this.productService.renderPageListProduct(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            case "/new":
                try {
                    this.productService.renderPageNewProduct(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            case "/edit":
                try {
                    int id = Integer.parseInt(request.getParameter("id"));
                    this.productService.renderPageEditProduct(request, response, id);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getPathInfo();
        switch (url) {
            case "/insert":
                try {
                    this.productService.addNewProduct(req);
                    resp.sendRedirect(req.getContextPath() + "/product/list");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;

            case "/delete":
                try {
                    int id = Integer.parseInt(req.getParameter("id"));
                    this.productService.deleteProduct(req, id);
                    resp.sendRedirect(req.getContextPath() + "/product/list");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;

            case "/update":
                try{
                    this.productService.updateProduct(req);
                    resp.sendRedirect(req.getContextPath() + "/product/list");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }
}