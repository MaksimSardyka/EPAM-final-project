package by.epam.auction.domain;

public enum Role {
    /**
     * Admin.
     */
    ADMINISTRATOR("admin"),
    /**
     * Avarage user.
     */
    USER("user");

    private String name;

    private Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return ordinal();
    }

    public static Role getById(Integer id) {
        return Role.values()[id];
    }
}
