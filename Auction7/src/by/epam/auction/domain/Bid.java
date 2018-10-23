package by.epam.auction.domain;

import java.math.BigDecimal;

public class Bid extends Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6057177696386656553L;

	BigDecimal amount;
	long lotId;
	long bidderId;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public long getLotId() {
		return lotId;
	}

	public void setLotId(long lotId) {
		this.lotId = lotId;
	}

	public long getBidderId() {
		return bidderId;
	}

	public void setBidderId(long bidderId) {
		this.bidderId = bidderId;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		return result;
	}

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

	@Override
	public String toString() {
		return "Bid [amount=" + amount + ", lotId=" + lotId + ", bidderId=" + bidderId + "]";
	}
}
