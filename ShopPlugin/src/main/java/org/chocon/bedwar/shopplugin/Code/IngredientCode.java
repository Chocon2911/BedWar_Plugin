package org.chocon.bedwar.shopplugin.Code;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.chocon.bedwar.shopplugin.Listener.Shop.Model.ShopListener;

public enum IngredientCode {
    //============================================Code============================================
    IRON("iron", Material.IRON_INGOT),
    GOLD("gold", Material.GOLD_INGOT),
    DIAMOND("diamond", Material.DIAMOND),
    EMERALD("emerald", Material.EMERALD);

    //==========================================Variable==========================================
    private final String code;
    private final Material material;

    //========================================Constructor=========================================
    IngredientCode(String code, Material material) {
        this.code = code;
        this.material = material;
    }

    //===========================================Method===========================================
    @Override
    public String toString() {
        return this.code;
    }

    public static IngredientCode fromString(String str) {
        for (IngredientCode ingredientCode : IngredientCode.values()) {
            if (ingredientCode.code.equalsIgnoreCase(str)) {
                return ingredientCode;
            }
        }
        return null;
    }

    public ItemStack getItem(int amount) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();

        if (meta != null && ShopListener.INGREDIENT_KEY != null) {
            meta.getPersistentDataContainer().set(
                    ShopListener.INGREDIENT_KEY,
                    PersistentDataType.STRING,
                    this.code
            );
            item.setItemMeta(meta);
        }

        return item;
    }
}
