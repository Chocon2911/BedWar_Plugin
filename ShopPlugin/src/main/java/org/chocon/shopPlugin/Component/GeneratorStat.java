package org.chocon.shopPlugin.Component;

import org.chocon.shopPlugin.Code.IngredientCode;

public class GeneratorStat {
    //==========================================Variable==========================================
    private final long delay;
    private long lastGenerate;
    private final int amount;
    private final IngredientCode ingredient;

    //========================================Constructor=========================================
    public GeneratorStat(long delay, int amount, IngredientCode ingredient) {
        this.delay = delay;
        this.amount = amount;
        this.ingredient = ingredient;
        this.lastGenerate = 0;
    }

    //===========================================Method===========================================
    public boolean canGenerate() {
        return System.currentTimeMillis() - lastGenerate >= delay;
    }

    public void markAsGenerated() {
        lastGenerate = System.currentTimeMillis();
    }

    public int getAmount() {
        return amount;
    }

    public IngredientCode getIngredient() {
        return ingredient;
    }
}
