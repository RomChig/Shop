package ua.nure.ihnatov.SummaryTask.controller.services;

import ua.nure.ihnatov.SummaryTask.controller.connectionPool.ConnectionPool;
import ua.nure.ihnatov.SummaryTask.controller.dao.OrderDAO;
import ua.nure.ihnatov.SummaryTask.controller.dao.exception.DAOException;
import ua.nure.ihnatov.SummaryTask.controller.utility.Closer;
import ua.nure.ihnatov.SummaryTask.controller.utility.Fields;
import ua.nure.ihnatov.SummaryTask.controller.utility.Queries;
import ua.nure.ihnatov.SummaryTask.model.Cart;
import ua.nure.ihnatov.SummaryTask.model.Order;
import ua.nure.ihnatov.SummaryTask.model.Product;
import ua.nure.ihnatov.SummaryTask.model.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService implements OrderDAO {

    @Override
    public List<Order> getAllByUserId(Long user_id) throws DAOException {
        List<Order> orderList;
        PreparedStatement getOrderPrepareStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            if (user_id == null) {
                return null;
            }
            int k = 1;
            getOrderPrepareStatement = connection.prepareStatement(Queries.GET_ORDERS_BY_USER_ID);
            getOrderPrepareStatement.setLong(k, user_id);
            resultSet = getOrderPrepareStatement.executeQuery();
            orderList = Collections.synchronizedList(new ArrayList<>());
            while (resultSet.next()) {
                Order order = new Order();
                extractOrder(resultSet, order);
                orderList.add(order);
            }
            return orderList;
        } catch (SQLException e) {
            throw new DAOException();
        } finally {
            Closer.close(getOrderPrepareStatement, resultSet);
        }
    }

    @Override
    public boolean setPaidStatusForOrder(Order order) throws DAOException {
        order.setStatus(new Status(Fields.STATUS_ID_FOR_PAID_ORDERS));
        return update(order);
    }

    @Override
    public boolean setCanceledStatusForOrder(Order order) throws DAOException {
        order.setStatus(new Status(Fields.STATUS_ID_FOR_CANCELED_ORDERS));
        return update(order);
    }

    @Override
    public boolean setOrderProductId(Long orderId, Long productId, Long quantity) throws DAOException {
        PreparedStatement setOrderProductIdPrepareStatement = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            int k = 1;
            setOrderProductIdPrepareStatement = connection.prepareStatement(Queries.CREATE_ORDER_PRODUCT_RELATION);
            setOrderProductIdPrepareStatement.setLong(k++, orderId);
            setOrderProductIdPrepareStatement.setLong(k++, productId);
            setOrderProductIdPrepareStatement.setLong(k, quantity);
            return setOrderProductIdPrepareStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException();
        } finally {
            Closer.close(setOrderProductIdPrepareStatement);
        }
    }

    @Override
    public boolean createRelationOrderAndCart(Order order, Cart cart) throws DAOException {
        for (Map.Entry<Product, Long> map : cart.getMapProduct().entrySet()) {
            boolean isSet = setOrderProductId(order.getId(), map.getKey().getId(), map.getValue());
            if (!isSet) {
                throw new DAOException();
            }
        }
        return true;
    }

    @Override
    public Map<Product, Long> getAllByOrderId(Long orderId) throws DAOException {
        PreparedStatement getAllProductPrepareStatement = null;
        ResultSet resultSet = null;
        Map<Product, Long> productMap;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            int k = 1;
            getAllProductPrepareStatement = connection.prepareStatement(Queries.GET_ALL_BY_ORDER_ID);
            getAllProductPrepareStatement.setLong(k, orderId);
            resultSet = getAllProductPrepareStatement.executeQuery();
            productMap = Collections.synchronizedMap(new HashMap<>());
            while (resultSet.next()) {
                Product product = new ProductService().read(resultSet.getLong(Fields.PRODUCT_ID));
                Long quantity = resultSet.getLong(Fields.PRODUCT_QUANTITY);
                productMap.put(product, quantity);
            }
            return productMap;
        } catch (SQLException e) {
            throw new DAOException();
        } finally {
            Closer.close(getAllProductPrepareStatement, resultSet);
        }
    }

    private Status getStatusById(Long status_id) throws SQLException, DAOException {
        PreparedStatement getStatusPreparedStatement = null;
        ResultSet resultSet = null;
        Status status = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            int k = 1;
            getStatusPreparedStatement = connection.prepareStatement(Queries.GET_ALL_FROM_STATUSES);
            getStatusPreparedStatement.setLong(k, status_id);
            resultSet = getStatusPreparedStatement.executeQuery();
            if (resultSet.next()) {
                status = new Status();
                status.setId(resultSet.getLong(Fields.ID_FOR_EVERYBODY));
                status.setName(resultSet.getString(Fields.NAME_FOR_EVERYBODY));
            }
            return status;
        } catch (SQLException e) {
            throw new SQLException();
        } finally {
            Closer.close(getStatusPreparedStatement, resultSet);
        }
    }

    @Override
    public Order create(Order order) throws DAOException {
        PreparedStatement createOrderPrepareStatement = null;
        ResultSet resultSet;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            int k = 1;
            createOrderPrepareStatement = connection.prepareStatement(Queries.CREATE_ORDER, Statement.RETURN_GENERATED_KEYS);
            createOrderPrepareStatement.setDouble(k++, order.getBill());
            createOrderPrepareStatement.setLong(k, order.getUser().getId());
            if (createOrderPrepareStatement.executeUpdate() > 0) {
                resultSet = createOrderPrepareStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    order.setId(resultSet.getLong(Fields.COLUMN_INDEX_FOR_ID));
                }
            }
        } catch (SQLException e) {
            throw new DAOException();
        } finally {
            Closer.close(createOrderPrepareStatement);
        }
        return order;
    }


    private void extractOrder(ResultSet resultSet, Order order) throws SQLException, DAOException {
        order.setId(resultSet.getLong(Fields.ID_FOR_EVERYBODY));
        order.setBill(resultSet.getDouble(Fields.ORDER_BILL));
        order.setUser(new UserService().read(resultSet.getLong(Fields.USER_ID)));
        order.setStatus(getStatusById(resultSet.getLong(Fields.ORDER_STATUS_ID)));
        order.setDateCreated(resultSet.getDate(Fields.DATE_CREATED_ORDER));
    }

    @Override
    public Order read(Long orderId) throws DAOException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Order order;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            int k = 1;
            preparedStatement = connection.prepareStatement(Queries.GET_ORDER_BY_ID);
            preparedStatement.setLong(k, orderId);
            resultSet = preparedStatement.executeQuery();
            order = new Order();
            if (resultSet.next()) {
                extractOrder(resultSet, order);
            }
            return order;
        } catch (SQLException e) {
            throw new DAOException();
        } finally {
            Closer.close(preparedStatement, resultSet);
        }
    }

    @Override
    public List<Order> getAll() throws DAOException {
        List<Order> orderList;
        PreparedStatement getAllOrdersPrepareStatement = null;
        ResultSet resultSet = null;
        Order order;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            getAllOrdersPrepareStatement = connection.prepareStatement(Queries.GET_ALL_ORDERS);
            resultSet = getAllOrdersPrepareStatement.executeQuery();
            orderList = Collections.synchronizedList(new ArrayList<>());
            while (resultSet.next()) {
                order = new Order();
                extractOrder(resultSet, order);
                orderList.add(order);
            }
            return orderList;
        } catch (SQLException e) {
            throw new DAOException();
        } finally {
            Closer.close(getAllOrdersPrepareStatement, resultSet);
        }
    }

    @Override
    public boolean update(Order order) throws DAOException {
        PreparedStatement updateOrderPrepareStatement = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            int k = 1;
            updateOrderPrepareStatement = connection.prepareStatement(Queries.UPDATE_ORDER);
            updateOrderPrepareStatement.setDouble(k++, order.getBill());
            updateOrderPrepareStatement.setLong(k++, order.getUser().getId());
            updateOrderPrepareStatement.setLong(k++, order.getStatus().getId());
            updateOrderPrepareStatement.setDate(k++, order.getDateCreated());
            updateOrderPrepareStatement.setLong(k, order.getId());
            return updateOrderPrepareStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException();
        } finally {
            Closer.close(updateOrderPrepareStatement);
        }
    }

    @Override
    public boolean delete(Order order) throws DAOException {
        return false;
    }
}
