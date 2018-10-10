package by.epam.auction.dao.lot;

import java.util.Set;

import by.epam.auction.dao.EntityDAO;
import by.epam.auction.dao.exception.DAOException;
import by.epam.auction.domain.Lot;

public interface LotDAO extends EntityDAO<Lot> {
    Set<Lot> getLotLimit(int startIndex, int quantity) throws DAOException;

    Set<Lot> getUnfinishedLotListLimit(int startIndex, int quantity)
            throws DAOException;

    public Set<Lot> getAuctionLotLimit(long auctionId, int startIndex,
            int quantity) throws DAOException;
}
