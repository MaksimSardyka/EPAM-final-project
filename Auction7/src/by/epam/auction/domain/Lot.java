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

	private String name;
	private String description;
	private boolean isApproved;
	private boolean isPaid;
	private String category;
	private Set<String> pictureSet;

	private BigDecimal bidPrice;
	private Long bidId;
	private Long bidderId;

	private AuctionType auctionType;
	private LocalDateTime startTime;
	private LocalDateTime finishTime;

	private Long ownerId;
	private String ownerEmail;
	private String ownerLogin;
	private boolean ownerIsBlocked;
	private BigDecimal ownerFrozenMoney;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Set<String> getPictureSet() {
		return pictureSet;
	}

	public void setPictureSet(Set<String> pictureSet) {
		this.pictureSet = pictureSet;
	}

	public BigDecimal getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(BigDecimal bidPrice) {
		this.bidPrice = bidPrice;
	}

	public Long getBidId() {
		return bidId;
	}

	public void setBidId(Long bidId) {
		this.bidId = bidId;
	}

	public Long getBidderId() {
		return bidderId;
	}

	public void setBidderId(Long bidderId) {
		this.bidderId = bidderId;
	}

	public AuctionType getAuctionType() {
		return auctionType;
	}

	public void setAuctionType(AuctionType auctionType) {
		this.auctionType = auctionType;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(LocalDateTime finishTime) {
		this.finishTime = finishTime;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}

	public String getOwnerLogin() {
		return ownerLogin;
	}

	public void setOwnerLogin(String ownerLogin) {
		this.ownerLogin = ownerLogin;
	}

	public boolean isOwnerIsBlocked() {
		return ownerIsBlocked;
	}

	public void setOwnerIsBlocked(boolean ownerIsBlocked) {
		this.ownerIsBlocked = ownerIsBlocked;
	}

	public BigDecimal getOwnerFrozenMoney() {
		return ownerFrozenMoney;
	}

	public void setOwnerFrozenMoney(BigDecimal ownerFrozenMoney) {
		this.ownerFrozenMoney = ownerFrozenMoney;
	}

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
