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
    private long id;

    /**
     * Getter on Id.
     */
    public long getId() {
        return id;
    }

    /**
     * Setter on Id.
     * 
     * @param id
     *            Value to set.
     */
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
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
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Entity [id=" + id + "]";
    }
}
