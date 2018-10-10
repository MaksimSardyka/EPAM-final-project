package by.epam.auction.resource;

import java.util.ResourceBundle;

/**
 * Provides access to DB properties.
 */
public class DBResourceManager {

    /**
     * Constant instance.
     */
    private static final DBResourceManager instance = new DBResourceManager();

    /**
     * Constant RESOURCES_DB.
     */
    private static final String RESOURCES_DB = "resources.db";

    /**
     * Bundle.
     */
    private ResourceBundle bundle;

    /**
     * Instantiate a new DB resource manager.
     */
    private DBResourceManager() {
        bundle = ResourceBundle.getBundle(RESOURCES_DB);
    }

    /**
     * Get value.
     *
     * @param key
     *            Key to value
     * @return Value matching provided key.
     */
    public String getValue(String key) {
        return bundle.getString(key);
    }

    /**
     * Gets this single instance of DBResourceManager.
     *
     * @return single instance of DBResourceManager
     */
    public static DBResourceManager getInstance() {
        return instance;
    }
}
