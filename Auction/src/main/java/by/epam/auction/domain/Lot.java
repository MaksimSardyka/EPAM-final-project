package by.epam.auction.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Auction's lot entity.
 */
public class Lot extends Entity {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -1029232811807328257L;

	/** The name. */
	private String name;
	
	/** The description. */
	private String description;
	
	/** The is approved. */
	private boolean isApproved;
	
	/** The is paid. */
	private boolean isPaid;
	
	/** The category. */
	private String category;
	
	/** The picture set. */
	private Set<String> pictureSet;

	/** The bid price. */
	private BigDecimal bidPrice;
	
	/** The bid id. */
	private Long bidId;
	
	/** The bidder id. */
	private Long bidderId;

	/** The auction type. */
	private AuctionType auctionType;
	
	/** The start time. */
	private LocalDateTime startTime;
	
	/** The finish time. */
	private LocalDateTime finishTime;

	/** The owner id. */
	private Long ownerId;
	
	/** The owner email. */
	private String ownerEmail;
	
	/** The owner login. */
	private String ownerLogin;
	
	/** The owner is blocked. */
	private boolean ownerIsBlocked;
	
	/** The owner frozen money. */
	private BigDecimal ownerFrozenMoney;

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
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
	 * Checks if is approved.
	 *
	 * @return true, if is approved
	 */
	public boolean isApproved() {
		return isApproved;
	}

	/**
	 * Sets the approved.
	 *
	 * @param isApproved the new approved
	 */
	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	/**
	 * Checks if is paid.
	 *
	 * @return true, if is paid
	 */
	public boolean isPaid() {
		return isPaid;
	}

	/**
	 * Sets the paid.
	 *
	 * @param isPaid the new paid
	 */
	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	/**
	 * Gets the category.
	 *
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Sets the category.
	 *
	 * @param category the new category
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * Gets the picture set.
	 *
	 * @return the picture set
	 */
	public Set<String> getPictureSet() {
		return pictureSet;
	}

	/**
	 * Sets the picture set.
	 *
	 * @param pictureSet the new picture set
	 */
	public void setPictureSet(Set<String> pictureSet) {
		this.pictureSet = pictureSet;
	}

	/**
	 * Gets the bid price.
	 *
	 * @return the bid price
	 */
	public BigDecimal getBidPrice() {
		return bidPrice;
	}

	/**
	 * Sets the bid price.
	 *
	 * @param bidPrice the new bid price
	 */
	public void setBidPrice(BigDecimal bidPrice) {
		this.bidPrice = bidPrice;
	}

	/**
	 * Gets the bid id.
	 *
	 * @return the bid id
	 */
	public Long getBidId() {
		return bidId;
	}

	/**
	 * Sets the bid id.
	 *
	 * @param bidId the new bid id
	 */
	public void setBidId(Long bidId) {
		this.bidId = bidId;
	}

	/**
	 * Gets the bidder id.
	 *
	 * @return the bidder id
	 */
	public Long getBidderId() {
		return bidderId;
	}

	/**
	 * Sets the bidder id.
	 *
	 * @param bidderId the new bidder id
	 */
	public void setBidderId(Long bidderId) {
		this.bidderId = bidderId;
	}

	/**
	 * Gets the auction type.
	 *
	 * @return the auction type
	 */
	public AuctionType getAuctionType() {
		return auctionType;
	}

	/**
	 * Sets the auction type.
	 *
	 * @param auctionType the new auction type
	 */
	public void setAuctionType(AuctionType auctionType) {
		this.auctionType = auctionType;
	}

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
	 * Gets the owner id.
	 *
	 * @return the owner id
	 */
	public Long getOwnerId() {
		return ownerId;
	}

	/**
	 * Sets the owner id.
	 *
	 * @param ownerId the new owner id
	 */
	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * Gets the owner email.
	 *
	 * @return the owner email
	 */
	public String getOwnerEmail() {
		return ownerEmail;
	}

	/**
	 * Sets the owner email.
	 *
	 * @param ownerEmail the new owner email
	 */
	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}

	/**
	 * Gets the owner login.
	 *
	 * @return the owner login
	 */
	public String getOwnerLogin() {
		return ownerLogin;
	}

	/**
	 * Sets the owner login.
	 *
	 * @param ownerLogin the new owner login
	 */
	public void setOwnerLogin(String ownerLogin) {
		this.ownerLogin = ownerLogin;
	}

	/**
	 * Checks if is owner is blocked.
	 *
	 * @return true, if is owner is blocked
	 */
	public boolean isOwnerIsBlocked() {
		return ownerIsBlocked;
	}

	/**
	 * Sets the owner is blocked.
	 *
	 * @param ownerIsBlocked the new owner is blocked
	 */
	public void setOwnerIsBlocked(boolean ownerIsBlocked) {
		this.ownerIsBlocked = ownerIsBlocked;
	}

	/**
	 * Gets the owner frozen money.
	 *
	 * @return the owner frozen money
	 */
	public BigDecimal getOwnerFrozenMoney() {
		return ownerFrozenMoney;
	}

	/**
	 * Sets the owner frozen money.
	 *
	 * @param ownerFrozenMoney the new owner frozen money
	 */
	public void setOwnerFrozenMoney(BigDecimal ownerFrozenMoney) {
		this.ownerFrozenMoney = ownerFrozenMoney;
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
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((auctionType == null) ? 0 : auctionType.hashCode());
		result = prime * result + ((bidId == null) ? 0 : bidId.hashCode());
		result = prime * result + ((bidPrice == null) ? 0 : bidPrice.hashCode());
		result = prime * result + ((bidderId == null) ? 0 : bidderId.hashCode());
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((finishTime == null) ? 0 : finishTime.hashCode());
		result = prime * result + (isApproved ? 1231 : 1237);
		result = prime * result + (isPaid ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((ownerEmail == null) ? 0 : ownerEmail.hashCode());
		result = prime * result + ((ownerFrozenMoney == null) ? 0 : ownerFrozenMoney.hashCode());
		result = prime * result + ((ownerId == null) ? 0 : ownerId.hashCode());
		result = prime * result + (ownerIsBlocked ? 1231 : 1237);
		result = prime * result + ((ownerLogin == null) ? 0 : ownerLogin.hashCode());
		result = prime * result + ((pictureSet == null) ? 0 : pictureSet.hashCode());
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
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
		Lot other = (Lot) obj;
		if (auctionType != other.auctionType)
			return false;
		if (bidId == null) {
			if (other.bidId != null)
				return false;
		} else if (!bidId.equals(other.bidId))
			return false;
		if (bidPrice == null) {
			if (other.bidPrice != null)
				return false;
		} else if (!bidPrice.equals(other.bidPrice))
			return false;
		if (bidderId == null) {
			if (other.bidderId != null)
				return false;
		} else if (!bidderId.equals(other.bidderId))
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
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
		if (isApproved != other.isApproved)
			return false;
		if (isPaid != other.isPaid)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (ownerEmail == null) {
			if (other.ownerEmail != null)
				return false;
		} else if (!ownerEmail.equals(other.ownerEmail))
			return false;
		if (ownerFrozenMoney == null) {
			if (other.ownerFrozenMoney != null)
				return false;
		} else if (!ownerFrozenMoney.equals(other.ownerFrozenMoney))
			return false;
		if (ownerId == null) {
			if (other.ownerId != null)
				return false;
		} else if (!ownerId.equals(other.ownerId))
			return false;
		if (ownerIsBlocked != other.ownerIsBlocked)
			return false;
		if (ownerLogin == null) {
			if (other.ownerLogin != null)
				return false;
		} else if (!ownerLogin.equals(other.ownerLogin))
			return false;
		if (pictureSet == null) {
			if (other.pictureSet != null)
				return false;
		} else if (!pictureSet.equals(other.pictureSet))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
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
		return "Lot [name=" + name + ", description=" + description + ", isApproved=" + isApproved + ", isPaid="
				+ isPaid + ", category=" + category + ", pictureSet=" + pictureSet + ", bidPrice=" + bidPrice
				+ ", bidId=" + bidId + ", bidderId=" + bidderId + ", auctionType=" + auctionType + ", startTime="
				+ startTime + ", finishTime=" + finishTime + ", ownerId=" + ownerId + ", ownerEmail=" + ownerEmail
				+ ", ownerLogin=" + ownerLogin + ", ownerIsBlocked=" + ownerIsBlocked + ", ownerFrozenMoney="
				+ ownerFrozenMoney + ", hashCode()=" + hashCode() + "]";
	}
}
