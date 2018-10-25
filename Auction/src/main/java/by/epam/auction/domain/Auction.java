package by.epam.auction.domain;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * The Class Auction.
 */
public class Auction extends Entity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8294739795202997742L;

	/** The start time. */
	private LocalDateTime startTime;
	
	/** The finish time. */
	private LocalDateTime finishTime;
	
	/** The type. */
	private AuctionType type;
	
	/** The description. */
	private String description;

	/** The lot set. */
	private Set<Lot> lotSet;

	/**
	 * Gets the start time.
	 *
	 * @return the start time
	 */
	public LocalDateTime getStartTime() {
		return startTime;
	}

	/**
	 * Sets the start time.
	 *
	 * @param startTime the new start time
	 */
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	/**
	 * Gets the finish time.
	 *
	 * @return the finish time
	 */
	public LocalDateTime getFinishTime() {
		return finishTime;
	}

	/**
	 * Sets the finish time.
	 *
	 * @param finishTime the new finish time
	 */
	public void setFinishTime(LocalDateTime finishTime) {
		this.finishTime = finishTime;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public AuctionType getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(AuctionType type) {
		this.type = type;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the lot set.
	 *
	 * @return the lot set
	 */
	public Set<Lot> getLotSet() {
		return lotSet;
	}

	/**
	 * Sets the lot set.
	 *
	 * @param lotSet the new lot set
	 */
	public void setLotSet(Set<Lot> lotSet) {
		this.lotSet = lotSet;
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
		return super.hashCode();
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
		Auction other = (Auction) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (finishTime == null) {
			if (other.finishTime != null)
				return false;
		} else if (!finishTime.equals(other.finishTime))
			return false;
		if (lotSet == null) {
			if (other.lotSet != null)
				return false;
		} else if (!lotSet.equals(other.lotSet))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		if (type != other.type)
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
		return "Auction [startTime=" + startTime + ", finishTime=" + finishTime + ", type=" + type + ", description="
				+ description + ", lotSet=" + lotSet + "]";
	}

}
