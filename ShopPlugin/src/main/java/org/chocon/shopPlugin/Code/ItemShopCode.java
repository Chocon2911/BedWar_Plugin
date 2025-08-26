package org.chocon.shopPlugin.Code;

public enum ItemShopCode {
    // Weapons
    WOODEN_SWORD("wooden_sword"),
    STONE_SWORD("stone_sword"),
    IRON_SWORD("iron_sword"),

    // Blocks
    WOOL("wool"),
    WOODEN_PLANK("wooden_plank"),
    OBSIDIAN("obsidian"),

    // Armors
    CHAIN_AMOR("chain_armor"),
    IRON_AMOR("iron_armor"),
    DIAMOND_AMOR("diamond_armor"),

    // Utilities
    FIRE_BALL("fire_ball"),
    ENDER_PEARL("ender_pearl"),
    IRON_GOLEM_EGG("iron_golem_egg"),
    TNT("tnt"),
    GOLDEN_APPLE("golden_apple"),

    // Navigation
    BACK("back"),
    OPEN_WEAPON_MENU("open_weapons"),
    OPEN_BLOCK_MENU("open_blocks"),
    OPEN_ARMOR_MENU("open_armor"),
    OPEN_UTILITY_MENU("open_utilities");

    private final String code;

    ItemShopCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }

    public static ItemShopCode fromString(String str) {
        for (ItemShopCode code : values()) {
            if (code.code.equalsIgnoreCase(str)) {
                return code;
            }
        }
        return null;
    }
}

