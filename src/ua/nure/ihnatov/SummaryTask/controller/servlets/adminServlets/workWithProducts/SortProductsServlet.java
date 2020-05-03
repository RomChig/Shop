package ua.nure.ihnatov.SummaryTask.controller.servlets.adminServlets.workWithProducts;

import ua.nure.ihnatov.SummaryTask.controller.dao.exception.DAOException;
import ua.nure.ihnatov.SummaryTask.controller.services.ProductService;
import ua.nure.ihnatov.SummaryTask.controller.utility.Fields;
import ua.nure.ihnatov.SummaryTask.controller.utility.Paths;
import ua.nure.ihnatov.SummaryTask.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@WebServlet("/SortProducts")
public class SortProductsServlet extends HttpServlet {
    private static final String SORT_BY_NAME = "sortByName";
    private static final String SORT_BY_PRICE = "sortByPrice";
    private static final String SORT_BY_DATE_ADDED = "sortByDateAdded";
    private static final String SORT_BY_CAPACITY = "sortByCapacity";
    private static final String SORT_BY_COLOR = "sortByColor";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sortByName = req.getParameter(Fields.NAME_FOR_EVERYBODY);
        String sortByPrice = req.getParameter(Fields.PRODUCT_PRICE);
        String sortByDateAdded = req.getParameter(Fields.DATE_ADDED_PRODUCT);
        String sortByCapacity = req.getParameter(Fields.CAPACITY);
        String sortByColor = req.getParameter(Fields.PRODUCT_COLOR);
        List<Product> productList;
        try {
            productList = new ProductService().getAll();
            switch (sortByName) {
                case "Default":
                    break;
                case "a-z":
                    productList.sort(Comparator.comparing(Product::getName));
                    break;
                case "z-a":
                    productList.sort(Comparator.comparing(Product::getName));
                    Collections.reverse(productList);
                    break;
            }
            switch (sortByPrice) {
                case "Default":
                    break;
                case "priceUp":
                    productList.sort(Comparator.comparing(Product::getPrice));
                    break;
                case "priceDown":
                    productList.sort(Comparator.comparing(Product::getPrice));
                    Collections.reverse(productList);
                    break;
            }
            switch (sortByDateAdded) {
                case "Default":
                    break;
                case "new":
                    productList.sort(Comparator.comparing(Product::getDateAdded));
                    Collections.reverse(productList);
                    break;
            }
            switch (sortByCapacity) {
                case "Default":
                    break;
                case "64Gb":
                case "128Gb":
                case "256Gb":
                case "512Gb":
                    productList = sortBy(productList, sortByCapacity);
                    break;
            }
            switch (sortByColor) {
                case "Default":
                    break;
                case "black":
                case "gold":
                case "purple":
                case "yellow":
                case "green":
                case "red":
                case "white":
                case "blue":
                    productList = sortBy(productList, sortByColor);
                    break;
            }
        } catch (DAOException e) {
            throw new ServletException();
        }
        req.getSession(false).setAttribute(Fields.LIST_OF_PRODUCTS, productList);
        req.getSession(false).setAttribute(SORT_BY_NAME, sortByName);
        req.getSession(false).setAttribute(SORT_BY_PRICE, sortByPrice);
        req.getSession(false).setAttribute(SORT_BY_DATE_ADDED, sortByDateAdded);
        req.getSession(false).setAttribute(SORT_BY_CAPACITY, sortByCapacity);
        req.getSession(false).setAttribute(SORT_BY_COLOR, sortByColor);
        resp.sendRedirect(Paths.PATH_TO_SORT_PRODUCTS_SERVLET);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(Paths.PATH_TO_MAIN_JSP).forward(req, resp);
    }

    List<Product> sortBy(List<Product> productList, String sortParam) {
        List<Product> newProductList = new ArrayList<>();
        String param;
        String patternOnlyLetters = "\\p{L}+";
        for (Product p : productList) {
            if (sortParam.matches(patternOnlyLetters)) {
                param = p.getColor();
            } else {
                param = p.getCapacity();
            }
            if (sortParam.equals(param)) {
                newProductList.add(p);
            }
        }
        return newProductList;
    }
}
