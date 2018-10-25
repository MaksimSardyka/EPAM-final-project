package by.epam.auction.domain;

import java.io.Serializable;

/**
 * Base entity.
 */
abstract public class Entity implements Serializable {
	
	/** serialVersionUID. */
	private static final long serialVersionUID = 1090276092640568636L;

	/**
	 * Unique Id.
	 */
	private Long id;

	/**
	 * Instantiates a new entity.
	 */
	public Entity() {
		super();
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
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
		int result;
		result = (id == null) ? 0 : id.hashCode();
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

	/**
	 * The overridden method class {@code Object} returns string description of
	 * the object fields.
	 * 
	 * @see java.lang.Object
	 * @return string class object.
	 */
	@Override
	public String toString() {
		return "Entity [id=" + id + "]";
	}

}
