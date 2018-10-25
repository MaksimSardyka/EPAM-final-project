package by.epam.auction.domain;

import java.math.BigDecimal;
/**
 * The Class Bid.
 */
public class Bid extends Entity {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6057177696386656553L;

	/** The amount. */
	BigDecimal amount;
	
	/** The lot id. */
	long lotId;
	
	/** The bidder id. */
	long bidderId;

	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * Sets the amount.
	 *
	 * @param amount the new amount
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * Gets the lot id.
	 *
	 * @return the lot id
	 */
	public long getLotId() {
		return lotId;
	}

	/**
	 * Sets the lot id.
	 *
	 * @param lotId the new lot id
	 */
	public void setLotId(long lotId) {
		this.lotId = lotId;
	}

	/**
	 * Gets the bidder id.
	 *
	 * @return the bidder id
	 */
	public long getBidderId() {
		return bidderId;
	}

	/**
	 * Sets the bidder id.
	 *
	 * @param bidderId the new bidder id
	 */
	public void setBidderId(long bidderId) {
		this.bidderId = bidderId;
	}

	/**
	 * The overridden method class {@code Object} to obtain the object hash
	 * code.
	 * 
	 * @see java.lang.Object
	 * @return int type value.
	 */
	@Override
	public int hashCode() {
		int result = super.hashCode();
		return result;
	}

	/**
	 * The overridden method class {@code Object} to obtain the equality of the
	 * current object to another.
	 * 
	 * @see java.lang.Object
	 * @return boolean type value.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bid other = (Bid) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (bidderId != other.bidderId)
			return false;
		if (lotId != other.lotId)
			return false;
		return true;
	}
	
	/**
	 * The overridden method class {@code Object} returns string description of
	 * the object fields.
	 * 
	 * @see java.lang.Object
	 * @return string class object.
	 */
	@Override
	public String toString() {
		return "Bid [amount=" + amount + ", lotId=" + lotId + ", bidderId=" + bidderId + "]";
	}
}
