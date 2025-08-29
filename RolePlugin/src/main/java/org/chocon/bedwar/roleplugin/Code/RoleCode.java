package org.chocon.bedwar.roleplugin.Code;

public enum RoleCode {
    //============================================Code============================================
    ADMIN("Admin"),
    INNOCENT("Innocent"),
    WATCHER("Watcher"),
    UNKNOWN("Unknown");

    //==========================================Variable==========================================
    private final String displayName;

    //========================================Constructor=========================================
    RoleCode(String displayName) {
        this.displayName = displayName;
    }

    //===========================================Method===========================================
    @Override
    public String toString() {
        return displayName;
    }

    public static RoleCode fromString(String name) {
        for (RoleCode role : values()) {
            if (role.displayName.equalsIgnoreCase(name)) {
                return role;
            }
        }
        return UNKNOWN;
    }
}
