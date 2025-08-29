package org.chocon.bedwar.shopplugin.Code;

public enum ShopCode {
    //============================================Code============================================
    UPGRADE("upgrade"),
    ITEM("item");

    //==========================================Variable==========================================
    private final String code;

    //========================================Constructor=========================================
    ShopCode(String code) {
        this.code = code;
    }

    //===========================================Method===========================================
    @Override
    public String toString() {
        return code;
    }
}
