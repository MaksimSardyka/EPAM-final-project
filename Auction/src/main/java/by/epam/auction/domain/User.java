package by.epam.auction.domain;

import java.math.BigDecimal;
/**
 * Auction user entity.
 */
public class User extends Entity {
    
    /** serialVersionUID. */
    private static final long serialVersionUID = 9078051689084085786L;

    /**
     * Login of this user.
     */
    private String login;
    /**
     * Email of this user.
     */
    private String email;
    /**
     * Role of this user.
     */
    private Role role;
    /**
     * Money of this user.
     */
    private BigDecimal money;
    /**
     * Frozen money of this user.
     */
    private BigDecimal frozenMoney;
    
    /** Is this user blocked?. */
    private boolean isBlocked;

    /**
     * Gets the role.
     *
     * @return the role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets the role.
     *
     * @param role the new role
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email the new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the login.
     *
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets the login.
     *
     * @param login the new login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Gets the money.
     *
     * @return the money
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * Sets the money.
     *
     * @param money the new money
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * Checks if is blocked.
     *
     * @return true, if is blocked
     */
    public boolean isBlocked() {
        return isBlocked;
    }

    /**
     * Sets the blocked.
     *
     * @param isBlocked the new blocked
     */
    public void setBlocked(boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    /**
     * Gets the frozen money.
     *
     * @return the frozen money
     */
    public BigDecimal getFrozenMoney() {
        return frozenMoney;
    }

    /**
     * Sets the frozen money.
     *
     * @param frozenMoney the new frozen money
     */
    public void setFrozenMoney(BigDecimal frozenMoney) {
        this.frozenMoney = frozenMoney;
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
        User other = (User) obj;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (frozenMoney == null) {
            if (other.frozenMoney != null)
                return false;
        } else if (!frozenMoney.equals(other.frozenMoney))
            return false;
        if (isBlocked != other.isBlocked)
            return false;
        if (login == null) {
            if (other.login != null)
                return false;
        } else if (!login.equals(other.login))
            return false;
        if (money == null) {
            if (other.money != null)
                return false;
        } else if (!money.equals(other.money))
            return false;
        if (role != other.role)
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
		return "User [login=" + login + ", email=" + email + ", role=" + role + ", money=" + money + ", frozenMoney="
				+ frozenMoney + ", isBlocked=" + isBlocked + ", getId()=" + getId() + "]";
	}

}
