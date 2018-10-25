package by.epam.auction.domain;

// TODO: Auto-generated Javadoc
/**
 * The Enum Role.
 */
public enum Role {
    /**
     * Admin.
     */
    ADMINISTRATOR("admin"),
    /**
     * Avarage user.
     */
    USER("user");

    /** The name. */
    private String name;

    /**
     * Instantiates a new role.
     *
     * @param name the name
     */
    private Role(String name) {
        this.name = name;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public Integer getId() {
        return ordinal();
    }

    /**
     * Gets the by id.
     *
     * @param id the id
     * @return the by id
     */
    public static Role getById(Integer id) {
        return Role.values()[id];
    }
}
