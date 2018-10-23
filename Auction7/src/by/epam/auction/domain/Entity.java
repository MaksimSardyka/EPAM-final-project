package by.epam.auction.domain;

import java.io.Serializable;

/**
 * Base entity.
 */
abstract public class Entity implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1090276092640568636L;

	/**
	 * Unique Id.
	 */
	private Long id;

	public Entity() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		int result;
		result = (id == null) ? 0 : id.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entity other = (Entity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Entity [id=" + id + "]";
	}

}
