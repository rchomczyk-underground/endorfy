package moe.rafal.endorfy.datasource;

public class DatasourceSpecification {

    private final String jdbcUri;
    private final String username;
    private final String password;

    public DatasourceSpecification(String jdbcUri, String username, String password) {
        this.jdbcUri = jdbcUri;
        this.username = username;
        this.password = password;
    }

    protected boolean hasAuthorization() {
        return username != null && password != null;
    }

    protected String getJdbcUri() {
        return jdbcUri;
    }

    protected String getUsername() {
        return username;
    }

    protected String getPassword() {
        return password;
    }
}
