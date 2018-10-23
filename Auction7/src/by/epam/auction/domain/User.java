package by.epam.auction.domain;

import java.math.BigDecimal;

/**
 * Auction user entity.
 */
public class User extends Entity {
    /**
     * serialVersionUID
     */
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
    /**
     * Is this user blocked?
     */
    private boolean isBlocked;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    public BigDecimal getFrozenMoney() {
        return frozenMoney;
    }

    public void setFrozenMoney(BigDecimal frozenMoney) {
        this.frozenMoney = frozenMoney;
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

    @Override
    public String toString() {
        return "User [role=" + role + ", email=" + email + ", login=" + login
                + ", money=" + money + ", isBlocked="
                + isBlocked + ", frozenMoney=" + frozenMoney + "]";
    }

}
