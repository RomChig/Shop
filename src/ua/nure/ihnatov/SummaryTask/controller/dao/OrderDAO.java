package ua.nure.ihnatov.SummaryTask.controller.dao;

import ua.nure.ihnatov.SummaryTask.controller.dao.exception.DAOException;
import ua.nure.ihnatov.SummaryTask.model.Cart;
import ua.nure.ihnatov.SummaryTask.model.Order;
import ua.nure.ihnatov.SummaryTask.model.Product;

import java.util.List;
import java.util.Map;

public interface OrderDAO extends DAO<Order> {

    List<Order> getAllByUserId(Long id) throws DAOException;

    boolean setPaidStatusForOrder(Order order) throws DAOException;

    boolean setCanceledStatusForOrder(Order order) throws DAOException;

    boolean setOrderProductId(Long orderId, Long productId, Long quantity) throws DAOException;

    boolean createRelationOrderAndCart(Order order, Cart cart) throws DAOException;

    Map<Product, Long> getAllByOrderId(Long orderId) throws DAOException;
}
