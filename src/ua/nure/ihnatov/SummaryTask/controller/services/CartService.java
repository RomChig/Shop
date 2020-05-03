package ua.nure.ihnatov.SummaryTask.controller.services;

import ua.nure.ihnatov.SummaryTask.controller.connectionPool.ConnectionPool;
import ua.nure.ihnatov.SummaryTask.controller.dao.CartDAO;
import ua.nure.ihnatov.SummaryTask.controller.dao.exception.DAOException;
import ua.nure.ihnatov.SummaryTask.controller.utility.Closer;
import ua.nure.ihnatov.SummaryTask.controller.utility.Fields;
import ua.nure.ihnatov.SummaryTask.controller.utility.Queries;
import ua.nure.ihnatov.SummaryTask.model.Cart;
import ua.nure.ihnatov.SummaryTask.model.Product;
import ua.nure.ihnatov.SummaryTask.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartService implements CartDAO {
    @Override
    public Cart create(Cart cart) throws DAOException {
        PreparedStatement createCartPrepareStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            int k = 1;
            createCartPrepareStatement = connection.prepareStatement(Queries.CREATE_CART, Statement.RETURN_GENERATED_KEYS);
            createCartPrepareStatement.setLong(k++, cart.getUser().getId());
            createCartPrepareStatement.setLong(k, cart.getProduct().getId());
            if (createCartPrepareStatement.executeUpdate() > 0) {
                resultSet = createCartPrepareStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    cart.setId(resultSet.getLong(Fields.COLUMN_INDEX_FOR_ID));
                }
            }
        } catch (SQLException e) {
            throw new DAOException();
        } finally {
            Closer.close(createCartPrepareStatement, resultSet);
        }
        return cart;
    }

    @Override
    public boolean update(Cart cart) throws DAOException {
        PreparedStatement updateCartPrepareStatement = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            int k = 1;
            updateCartPrepareStatement = connection.prepareStatement(Queries.UPDATE_CART_BY_USER_PRODUCT_ID);
            updateCartPrepareStatement.setLong(k++, cart.getMapProduct().get(cart.getProduct()));
            updateCartPrepareStatement.setLong(k++, cart.getUser().getId());
            updateCartPrepareStatement.setLong(k, cart.getProduct().getId());
            return updateCartPrepareStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException();
        } finally {
            Closer.close(updateCartPrepareStatement);
        }
    }

    public boolean raiseQuantity(Cart cart) throws DAOException {
        final int c = 1;
        Cart newCart = getByUserProductId(cart.getUser().getId(), cart.getProduct().getId());
        newCart.getMapProduct().forEach((key, value) -> newCart.getMapProduct().put(key, value + c));
        return update(newCart);
    }

    public boolean reduceQuantity(Cart cart) throws DAOException {
        final int c = 1;
        Cart newCart = getByUserProductId(cart.getUser().getId(), cart.getProduct().getId());
        newCart.getMapProduct().forEach((key, value) -> newCart.getMapProduct().put(key, value - c));
        return update(newCart);
    }

    public Cart getByUserProductId(Long userId, Long productId) throws DAOException {
        PreparedStatement getCartPrepareStatement = null;
        ResultSet resultSet = null;
        Cart cart;
        Product product;
        long quantity;
        Map<Product, Long> mapProduct;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            int k = 1;
            mapProduct = Collections.synchronizedMap(new HashMap<>());
            getCartPrepareStatement = connection.prepareStatement(Queries.GET_ALL_FROM_CART_BY_USER_PRODUCT_ID);
            getCartPrepareStatement.setLong(k++, userId);
            getCartPrepareStatement.setLong(k, productId);
            resultSet = getCartPrepareStatement.executeQuery();
            cart = new Cart();
            if (resultSet.next()) {
                Long idProduct = resultSet.getLong(Fields.PRODUCT_ID);
                product = new ProductService().read(idProduct);
                quantity = resultSet.getLong(Fields.PRODUCT_QUANTITY);
                cart.setId(resultSet.getLong(Fields.ID_FOR_EVERYBODY));
                cart.setUser(new User(userId));
                mapProduct.put(product, quantity);
                cart.setProduct(product);
            }
            cart.setMapProduct(mapProduct);
            return cart;
        } catch (SQLException e) {
            throw new DAOException();
        } finally {
            Closer.close(getCartPrepareStatement, resultSet);
        }
    }

    @Override
    public boolean delete(Cart cart) throws DAOException {
        PreparedStatement deleteProductPrepareStatement = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            int k = 1;
            deleteProductPrepareStatement = connection.prepareStatement(Queries.DELETE_PRODUCT_FROM_CART);
            deleteProductPrepareStatement.setLong(k, cart.getId());
            return deleteProductPrepareStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException();
        } finally {
            Closer.close(deleteProductPrepareStatement);
        }
    }

    @Override
    public Cart getAllProductsByUserId(Long userId) throws DAOException {
        Map<Product, Long> mapProduct;
        Cart cart;
        long quantity;
        ResultSet resultSet = null;
        PreparedStatement getUserProductsPrepareStatement = null;
        Product product;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            if (userId == null) {
                return null;
            }
            int k = 1;
            mapProduct = Collections.synchronizedMap(new HashMap<>());
            getUserProductsPrepareStatement = connection.prepareStatement(Queries.GET_ALL_FROM_CART_BY_USER_ID);
            getUserProductsPrepareStatement.setLong(k, userId);
            resultSet = getUserProductsPrepareStatement.executeQuery();
            cart = new Cart();
            while (resultSet.next()) {
                Long productId = resultSet.getLong(Fields.PRODUCT_ID);
                product = new ProductService().read(productId);
                quantity = resultSet.getLong(Fields.PRODUCT_QUANTITY);
                cart.setId(resultSet.getLong(Fields.ID_FOR_EVERYBODY));
                cart.setUser(new User(userId));
                mapProduct.put(product, quantity);
            }
            cart.setMapProduct(mapProduct);
            return cart;
        } catch (SQLException e) {
            throw new DAOException();
        } finally {
            Closer.close(getUserProductsPrepareStatement, resultSet);
        }
    }

    @Override
    public boolean isProductExist(Product product, User user) throws DAOException {
        PreparedStatement checkProductPrepareStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            int k = 1;
            checkProductPrepareStatement = connection.prepareStatement(Queries.GET_PRODUCT_ID_FROM_CART_BY_PRODUCT_ID);
            checkProductPrepareStatement.setLong(k++, product.getId());
            checkProductPrepareStatement.setLong(k, user.getId());
            resultSet = checkProductPrepareStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new DAOException();
        } finally {
            Closer.close(checkProductPrepareStatement,resultSet);
        }
    }

    @Override
    public boolean deleteByUserId(Long userId) throws DAOException {
        PreparedStatement deletePrepareStatement = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            int k = 1;
            deletePrepareStatement = connection.prepareStatement(Queries.DELETE_CART);
            deletePrepareStatement.setLong(k, userId);
            return deletePrepareStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException();
        } finally {
            Closer.close(deletePrepareStatement);
        }
    }

    @Override
    public Cart read(Long id) throws DAOException {
        return null;
    }

    @Override
    public List<Cart> getAll() throws DAOException {
        return null;
    }

}
