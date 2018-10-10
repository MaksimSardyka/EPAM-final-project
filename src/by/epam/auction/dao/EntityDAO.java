package by.epam.auction.dao;

import java.util.Optional;
import java.util.Set;

import by.epam.auction.dao.exception.DAOException;
import by.epam.auction.domain.Entity;

public interface EntityDAO<T extends Entity> extends AutoCloseable{
   Set<T> findAll() throws DAOException;

   Optional<T> findEntityById(long l) throws DAOException;

   boolean delete(int id) throws DAOException;

   boolean delete(T entity) throws DAOException;

   boolean create(T entity) throws DAOException;

   boolean update(T entity) throws DAOException;//TODO return type User -> boolean(change back for transaction)
   
   @Override
   void close() throws DAOException;
}
