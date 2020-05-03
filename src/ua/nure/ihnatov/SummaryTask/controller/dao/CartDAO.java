package ua.nure.ihnatov.SummaryTask.controller.dao;

import ua.nure.ihnatov.SummaryTask.controller.dao.exception.DAOException;
import ua.nure.ihnatov.SummaryTask.model.Cart;
import ua.nure.ihnatov.SummaryTask.model.Product;
import ua.nure.ihnatov.SummaryTask.model.User;

public interface CartDAO extends DAO<Cart> {
    Cart getAllProductsByUserId(Long userId) throws DAOException;

    boolean isProductExist(Product product, User user) throws DAOException;

    boolean deleteByUserId(Long userId) throws DAOException;

}
