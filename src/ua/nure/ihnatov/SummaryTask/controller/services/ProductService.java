package ua.nure.ihnatov.SummaryTask.controller.services;

import ua.nure.ihnatov.SummaryTask.controller.connectionPool.ConnectionPool;
import ua.nure.ihnatov.SummaryTask.controller.dao.ProductDAO;
import ua.nure.ihnatov.SummaryTask.controller.dao.exception.DAOException;
import ua.nure.ihnatov.SummaryTask.controller.utility.Closer;
import ua.nure.ihnatov.SummaryTask.controller.utility.Fields;
import ua.nure.ihnatov.SummaryTask.controller.utility.Messages;
import ua.nure.ihnatov.SummaryTask.controller.utility.Queries;
import ua.nure.ihnatov.SummaryTask.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductService implements ProductDAO {

    @Override
    public Product create(Product product) throws DAOException {
        PreparedStatement insertProductPrepareStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            insertProductPrepareStatement = connection.prepareStatement(Queries.CREATE_PRODUCT, Statement.RETURN_GENERATED_KEYS);
            if (create(product, insertProductPrepareStatement) > 0) {
                resultSet = insertProductPrepareStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    product.setId(resultSet.getLong(Fields.COLUMN_INDEX_FOR_ID));
                }
            }
        } catch (SQLIntegrityConstraintViolationException ex) {
            throw new DAOException(Messages.ERROR_THING_ALREADY_EXIST_WITH_SUCH_PARAMETERS, ex);
        } catch (SQLException e) {
            throw new DAOException(Messages.ERROR_SOMETHING_WAS_WRONG_DUE_OPERATION);
        } finally {
            Closer.close(insertProductPrepareStatement, resultSet);
        }
        return product;
    }

    private int create(Product product, PreparedStatement insertProductPrepareStatement) throws SQLException {
        action(product, insertProductPrepareStatement);
        return insertProductPrepareStatement.executeUpdate();
    }

    private int action(Product product, PreparedStatement insertProductPrepareStatement) throws SQLException {
        int k = 1;
        insertProductPrepareStatement.setString(k++, product.getName());
        insertProductPrepareStatement.setString(k++, product.getType());
        insertProductPrepareStatement.setString(k++, product.getCapacity());
        insertProductPrepareStatement.setString(k++, product.getColor());
        insertProductPrepareStatement.setDouble(k++, product.getPrice());
        insertProductPrepareStatement.setString(k++, product.getCurrency());
        insertProductPrepareStatement.setString(k++, product.getImageName());
        return k;
    }

    @Override
    public Product read(Long productId) throws DAOException {
        ResultSet resultSet = null;
        Product product;
        PreparedStatement preparedStatement = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            int k = 1;
            preparedStatement = connection.prepareStatement(Queries.GET_PRODUCT_BY_ID);
            preparedStatement.setLong(k, productId);
            resultSet = preparedStatement.executeQuery();
            product = new Product();
            if (resultSet.next()) {
                extractProduct(resultSet, product);
            }
            return product;
        } catch (SQLException e) {
            throw new DAOException();
        } finally {
            Closer.close(preparedStatement, resultSet);
        }
    }

    @Override
    public List<Product> getAll() throws DAOException {
        List<Product> productList;
        ResultSet resultSet = null;
        PreparedStatement obtainProductPrepareStatement = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            obtainProductPrepareStatement = connection.prepareStatement(Queries.GET_ALL_PRODUCTS);
            resultSet = obtainProductPrepareStatement.executeQuery();
            productList = Collections.synchronizedList(new ArrayList<>());
            while (resultSet.next()) {
                Product product = new Product();
                extractProduct(resultSet, product);
                productList.add(product);
            }
            return productList;
        } catch (SQLException e) {
            throw new DAOException();
        } finally {
            Closer.close(obtainProductPrepareStatement, resultSet);
        }
    }

    private void extractProduct(ResultSet resultSet, Product product) throws SQLException {
        product.setId(resultSet.getLong(Fields.ID_FOR_EVERYBODY));
        product.setName(resultSet.getString(Fields.NAME_FOR_EVERYBODY));
        product.setType(resultSet.getString(Fields.PRODUCT_TYPE));
        product.setCapacity(resultSet.getString(Fields.CAPACITY));
        product.setColor(resultSet.getString(Fields.PRODUCT_COLOR));
        product.setPrice(resultSet.getDouble(Fields.PRODUCT_PRICE));
        product.setCurrency(resultSet.getString(Fields.PRODUCT_CURRENCY));
        product.setDateAdded(resultSet.getDate(Fields.DATE_ADDED_PRODUCT));
        product.setImageName(resultSet.getString(Fields.PRODUCT_IMAGE_NAME));
    }

    @Override
    public boolean update(Product product) throws DAOException {
        PreparedStatement updateProductPrepareStatement = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            updateProductPrepareStatement = connection.prepareStatement(Queries.UPDATE_PRODUCT_BY_ID);
            return update(product, updateProductPrepareStatement) > 0;
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DAOException(Messages.ERROR_THING_ALREADY_EXIST_WITH_SUCH_PARAMETERS);
        } catch (SQLException e) {
            throw new DAOException(Messages.ERROR_SOMETHING_WAS_WRONG_DUE_OPERATION);
        } finally {
            Closer.close(updateProductPrepareStatement);
        }
    }

    private int update(Product product, PreparedStatement updateProductPrepareStatement) throws SQLException {
        int k = action(product, updateProductPrepareStatement);
        updateProductPrepareStatement.setLong(k, product.getId());
        return updateProductPrepareStatement.executeUpdate();
    }

    @Override
    public boolean delete(Product product) throws DAOException {
        PreparedStatement deleteProductPrepareStatement = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            int k = 1;
            deleteProductPrepareStatement = connection.prepareStatement(Queries.DELETE_PRODUCT);
            deleteProductPrepareStatement.setLong(k, product.getId());
            return deleteProductPrepareStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException();
        } finally {
            Closer.close(deleteProductPrepareStatement);
        }
    }
}
