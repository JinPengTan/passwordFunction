package jwt.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ADD = "ROLE_ADD";

    public static final String READ = "ROLE_READ";

    public static final String UPDATE = "ROLE_UPDATE";

    public static final String DELETE = "ROLE_DELETE";

    public static final String TOKEN = "ROLE_TOKEN";

    public static final String DETOKEN = "ROLE_DETOKEN";

    public static final String ACTIVATE = "ROLE_ACTIVATE";

    public static final String SUSPEND = "ROLE_SUSPEND";

    private AuthoritiesConstants() {
    }
}
