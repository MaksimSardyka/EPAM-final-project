package by.epam.auction.dao.bid;

import java.math.BigDecimal;
import java.util.Optional;

import by.epam.auction.dao.EntityDAO;
import by.epam.auction.dao.exception.DAOException;
import by.epam.auction.domain.Bid;

public interface BidDAO extends EntityDAO<Bid>{
    public Optional<BigDecimal> findLotMaxBid(long l) throws DAOException;

    Optional<BigDecimal> findLotMinBid(long lotId) throws DAOException;
}
