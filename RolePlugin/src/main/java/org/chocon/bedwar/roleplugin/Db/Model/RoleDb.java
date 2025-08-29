package org.chocon.bedwar.roleplugin.Db.Model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.chocon.bedwar.roleplugin.Db.Data.RoleDbData;

import java.sql.SQLException;
import java.util.List;

public class RoleDb {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/minecraft?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "chocon";
    private static final String DB_PASS = "password123";

    private static ConnectionSource connectionSource;
    private static Dao<RoleDbData, Integer> roleDao;

    //===========================================Method===========================================
    public static void init() throws SQLException {
        connectionSource = new JdbcPooledConnectionSource(DATABASE_URL, DB_USER, DB_PASS);
        roleDao = DaoManager.createDao(connectionSource, RoleDbData.class);

        TableUtils.createTableIfNotExists(connectionSource, RoleDbData.class);
    }

    public static void close() {
        if (connectionSource != null) {
            try {
                connectionSource.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void insertRole(String playerName, String role) throws SQLException {
        RoleDbData existing = getRoleByName(playerName);
        if (existing == null) {
            roleDao.create(new RoleDbData(playerName, role));
        } else {
            existing.setRole(role);
            roleDao.update(existing);
        }
    }

    public static RoleDbData getRoleByName(String playerName) throws SQLException {
        List<RoleDbData> results = roleDao.queryBuilder().where()
                .eq("playerName", playerName).query();
        return results.isEmpty() ? null : results.getFirst();
    }

    public static List<RoleDbData> getAllRoles() throws SQLException {
        return roleDao.queryForAll();
    }

    public static void deleteByName(String playerName) throws SQLException {
        roleDao.deleteBuilder().where().eq("playerName", playerName).prepare();
    }
}
