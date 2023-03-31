package moe.rafal.endorfy.datasource;

import java.sql.Connection;
import java.sql.SQLException;

public interface PooledDatasource {

    Connection borrowConnection() throws SQLException;

    void ditchConnections();
}
