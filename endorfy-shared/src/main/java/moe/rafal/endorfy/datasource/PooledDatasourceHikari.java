package moe.rafal.endorfy.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class PooledDatasourceHikari implements PooledDatasource {

    private final HikariDataSource underlyingDatasource;

    public PooledDatasourceHikari(DatasourceSpecification datasourceSpecification) {
        this.underlyingDatasource = new HikariDataSource(getHikariConfig(datasourceSpecification));
    }

    @Override
    public Connection borrowConnection() throws SQLException {
        return underlyingDatasource.getConnection();
    }

    @Override
    public void ditchConnections() {
        underlyingDatasource.close();
    }

    private HikariConfig getHikariConfig(DatasourceSpecification datasourceSpecification) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(datasourceSpecification.getJdbcUri());
        if (datasourceSpecification.hasAuthorization()) {
            hikariConfig.setUsername(datasourceSpecification.getUsername());
            hikariConfig.setPassword(datasourceSpecification.getPassword());
        }

        return hikariConfig;
    }
}
