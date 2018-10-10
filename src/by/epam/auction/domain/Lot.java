package by.epam.auction.domain;

import java.math.BigDecimal;

/**
 * Auction's lot entity.
 */
public class Lot extends Entity {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -1029232811807328257L;

	/**
	 * Name of this lot.
	 */
	String name;
	/**
	 * Descripption of this lot.
	 */
	String description;

	/**
	 * Price of this lot.
	 */
	BigDecimal price;

	/**
	 * Is this lot approved by Admin?
	 */
	boolean isApproved;
	/**
	 * Is this lot paid?
	 */
	boolean isPaid;
	/**
	 * Category name of this lot.
	 */

	Auction auction;
	String category;

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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
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

	public Auction getAuction() {
		return auction;
	}

	public void setAuction(Auction auction) {
		this.auction = auction;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((auction == null) ? 0 : auction.hashCode());
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + (isApproved ? 1231 : 1237);
		result = prime * result + (isPaid ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
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
		if (auction == null) {
			if (other.auction != null)
				return false;
		} else if (!auction.equals(other.auction))
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
		if (isApproved != other.isApproved)
			return false;
		if (isPaid != other.isPaid)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Lot [name=" + name + ", description=" + description + ", price=" + price + ", isApproved=" + isApproved
				+ ", isPaid=" + isPaid + ", auction=" + auction + ", category=" + category + "]";
	}

}
