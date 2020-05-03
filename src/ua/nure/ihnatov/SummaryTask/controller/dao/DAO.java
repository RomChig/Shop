package ua.nure.ihnatov.SummaryTask.controller.dao;

import ua.nure.ihnatov.SummaryTask.controller.dao.exception.DAOException;
import ua.nure.ihnatov.SummaryTask.model.Entity;

import java.util.List;

public interface DAO<T extends Entity> {
    //create
    T create(T entity) throws DAOException;

    //read
    T read(Long id) throws DAOException;

    List<T> getAll() throws DAOException;

    //update
    boolean update(T entity) throws DAOException;

    //delete
    boolean delete(T entity) throws DAOException;
}
