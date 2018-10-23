package by.epam.auction.domain;

import java.time.LocalDateTime;
import java.util.Set;

public class Auction extends Entity {

	private static final long serialVersionUID = 8294739795202997742L;

	private LocalDateTime startTime;
	private LocalDateTime finishTime;
	private AuctionType type;
	private String description;

	private Set<Lot> lotSet;

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

	public AuctionType getType() {
		return type;
	}

	public void setType(AuctionType type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Lot> getLotSet() {
		return lotSet;
	}

	public void setLotSet(Set<Lot> lotSet) {
		this.lotSet = lotSet;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

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

	@Override
	public String toString() {
		return "Auction [startTime=" + startTime + ", finishTime=" + finishTime + ", type=" + type + ", description="
				+ description + ", lotSet=" + lotSet + "]";
	}

}
