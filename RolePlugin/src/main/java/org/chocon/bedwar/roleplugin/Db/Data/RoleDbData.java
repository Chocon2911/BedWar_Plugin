package org.chocon.bedwar.roleplugin.Db.Data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "roles")
public class RoleDbData {
    //==========================================Variable==========================================
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(unique = true, canBeNull = false)
    private String playerName;

    @DatabaseField(canBeNull = false)
    private String role;

    //========================================Constructor=========================================
    public RoleDbData() {}

    public RoleDbData(String playerName, String role) {
        this.playerName = playerName;
        this.role = role;
    }

    //==========================================Get Set===========================================
    public int getId() { return id; }
    public String getPlayerName() { return playerName; }

    public void setPlayerName(String playerName) { this.playerName = playerName; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
