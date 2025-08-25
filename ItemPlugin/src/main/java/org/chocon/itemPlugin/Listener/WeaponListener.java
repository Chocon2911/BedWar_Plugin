package org.chocon.itemPlugin.Listener;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.chocon.itemPlugin.Item.Weapon.WeaponAttributeType;
import org.chocon.itemPlugin.Item.Weapon.Weapon;
import org.chocon.itemPlugin.ItemPlugin;

import java.util.HashMap;
import java.util.UUID;

public class WeaponListener implements Listener {
    //==========================================Variable==========================================
    private HashMap<UUID, Long> cooldowns = new HashMap<>();
    private final ItemPlugin plugin;

    public WeaponListener(ItemPlugin plugin) {
        this.cooldowns = new HashMap<>();
        this.plugin = plugin;
    }

    //===========================================Event============================================
    @EventHandler
    public void onLeftClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack weapon = player.getInventory().getItemInMainHand();

        if (!Weapon.isWeapon(weapon)) return;
        Action action = event.getAction();

        if (action != Action.LEFT_CLICK_AIR && action != Action.LEFT_CLICK_BLOCK) return;
        PersistentDataContainer container = weapon.getItemMeta().getPersistentDataContainer();

        if (!this.cooldowns.containsKey(container.get(WeaponAttributeType.UUID.getKey(),
                PersistentDataType.STRING))) {
            event.setCancelled(true);
            return;
        }
        Long lastShotTime = this.cooldowns.get(container.get(WeaponAttributeType.UUID.getKey(),
                PersistentDataType.STRING));
        Long currTime = System.currentTimeMillis();
        Long delay = (long) (1000 / container.get(WeaponAttributeType.ATTACK_SPEED.getKey(),
                PersistentDataType.DOUBLE));

        if (currTime - lastShotTime < delay) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {

    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof LivingEntity)) return;
        LivingEntity damager = (LivingEntity) event.getDamager();
        ItemStack weapon = damager.getEquipment().getItemInMainHand();

        if (!Weapon.isWeapon(weapon)) return;
        if (!this.isCooldownFinished(weapon)) {
            event.setCancelled(true);
            return;
        }
        if (!(event.getEntity() instanceof LivingEntity)) return;
        event.setDamage(0);
        LivingEntity victim = (LivingEntity) event.getEntity();
        this.dealDamage(victim, weapon);
    }

    @EventHandler
    public void onHoldWeapon(PlayerItemHeldEvent event) {
        Bukkit.getScheduler().runTask(this.plugin, () -> {
            Player player = event.getPlayer();
            ItemStack item = player.getEquipment().getItemInMainHand();
            this.setAttackSpeed(player, item);
        });
    }

    @EventHandler
    public void onPickUpItem(EntityPickupItemEvent event) {
        Bukkit.getScheduler().runTask(this.plugin, () -> {
            LivingEntity entity = event.getEntity();
            ItemStack weapon = event.getItem().getItemStack();
            this.setAttackSpeed(entity, weapon);
        });
    }

    //==========================================Support===========================================
    private void setAttackSpeed(LivingEntity entity, ItemStack weapon) {
        System.out.println("This shit is null");
        if (!Weapon.isWeapon(weapon)) return;
        PersistentDataContainer container = weapon.getItemMeta().getPersistentDataContainer();
        double weaponBaseAtkSpeed = Weapon.getBaseAttackSpeed(weapon);
        double attackSpeed = container.get(WeaponAttributeType.ATTACK_SPEED.getKey(),
                PersistentDataType.DOUBLE);
        entity.getAttribute(Attribute.ATTACK_SPEED).setBaseValue(attackSpeed - weaponBaseAtkSpeed);
        this.cooldowns.put(UUID.fromString(container.get(WeaponAttributeType.UUID.getKey(),
                PersistentDataType.STRING)), System.currentTimeMillis());
    }

    private boolean isCooldownFinished(ItemStack weapon) {
        PersistentDataContainer container = weapon.getItemMeta().getPersistentDataContainer();

        if (!this.cooldowns.containsKey(container.get(WeaponAttributeType.UUID.getKey(),
                PersistentDataType.STRING))) return false;
        Long lastShotTime = this.cooldowns.get(container.get(WeaponAttributeType.UUID.getKey(),
                PersistentDataType.STRING));
        Long currTime = System.currentTimeMillis();
        Long delay = (long) (1000 / container.get(WeaponAttributeType.ATTACK_SPEED.getKey(),
                PersistentDataType.DOUBLE));

        if (currTime - lastShotTime < delay) return false;
        return true;
    }

    private void dealDamage(LivingEntity victim, ItemStack weapon) {
        PersistentDataContainer weaponContainer = weapon.getItemMeta().getPersistentDataContainer();
        double newHealth = victim.getHealth() - weaponContainer.get(WeaponAttributeType.PHYSIC_DAMAGE.
                        getKey(), PersistentDataType.DOUBLE) - weaponContainer.get(WeaponAttributeType.
                        MAGIC_DAMAGE.getKey(), PersistentDataType.DOUBLE);

        if (newHealth < 0) newHealth = 0;
        victim.setHealth(newHealth);
    }
}
