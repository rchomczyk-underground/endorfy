package moe.rafal.endorfy.mapping;

import moe.rafal.endorfy.datasource.PooledDatasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class MappingRepository {

    private static final String MAPPINGS_TABLE_NAME = "endorfy_mappings";
    private static final String CREATE_SCHEMA_QUERY =
        String.format("CREATE TABLE IF NOT EXISTS %s (`uuid` VARCHAR(36) PRIMARY KEY, `name` VARCHAR(32) NOT NULL);", MAPPINGS_TABLE_NAME);
    private static final String FIND_MAPPING_BY_UNIQUE_ID_QUERY = String.format("SELECT `uuid`, `name` FROM %s WHERE `uuid` = ?;", MAPPINGS_TABLE_NAME);
    private static final String FIND_MAPPING_BY_USERNAME_QUERY  = String.format("SELECT `uuid`, `name` FROM %s WHERE `name` = ?;", MAPPINGS_TABLE_NAME);
    private static final String SAVE_MAPPING_QUERY = String.format("REPLACE INTO %s (`uuid`, `name`) VALUES (?, ?);", MAPPINGS_TABLE_NAME);

    private final PooledDatasource datasource;

    public MappingRepository(PooledDatasource datasource) {
        this.datasource = datasource;
    }

    public void createSchema() {
        try (Connection connection = datasource.borrowConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_SCHEMA_QUERY);
        } catch (SQLException exception) {
            throw new IllegalStateException("Could not create mapping schema, because of an unexpected exception.", exception);
        }
    }

    public EndorfyMapping findMappingByUniqueId(UUID uniqueId) {
        return findMappingUnsafely(FIND_MAPPING_BY_UNIQUE_ID_QUERY, uniqueId.toString());
    }

    public EndorfyMapping findMappingByUsername(String username) {
        return findMappingUnsafely(FIND_MAPPING_BY_USERNAME_QUERY, username);
    }

    private EndorfyMapping findMappingUnsafely(String query, String... values) {
        try (Connection connection = datasource.borrowConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            for (int index = 0; index < values.length; index++) {
                statement.setString(index + 1, values[index]);
            }

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new EndorfyMapping(UUID.fromString(
                    resultSet.getString("uuid")),
                    resultSet.getString("name"));
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("Could not find mapping, because of an unexpected exception.", exception);
        }

        return null;
    }

    public void saveMapping(EndorfyMapping mapping) {
        try (Connection connection = datasource.borrowConnection(); PreparedStatement statement = connection.prepareStatement(SAVE_MAPPING_QUERY)) {
            statement.setString(1, mapping.getUniqueId().toString());
            statement.setString(2, mapping.getUsername());
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new IllegalStateException("Could not save mapping, because of an unexpected exception.", exception);
        }
    }
}
