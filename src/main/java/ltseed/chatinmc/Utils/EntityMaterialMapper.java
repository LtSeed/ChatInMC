package ltseed.chatinmc.Utils;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.Map;

/**
 *This code is mapping EntityType objects to their corresponding Material objects in Minecraft. Each EntityType object is associated with a specific Material object, which can be used to spawn an item in the game.
 *
 * The EntityMaterialMapper class contains a static HashMap called ENTITY_MATERIAL_MAP, which is initialized with mappings between various EntityType objects and their corresponding Material objects using the put() method.
 *
 * For example, the AREA_EFFECT_CLOUD EntityType object is mapped to the AIR Material object, while the ARMOR_STAND EntityType object is mapped to the ARMOR_STAND Material object.
 *
 * This code can be used to simplify the process of creating and spawning entities in the game, as it provides an easy way to map EntityType objects to their corresponding Material objects.
 * @author ltseed
 * @version 1.0
 * */
public class EntityMaterialMapper {
    private static final Map<EntityType, Material> ENTITY_MATERIAL_MAP = new HashMap<>();

    static {
        ENTITY_MATERIAL_MAP.put(EntityType.AREA_EFFECT_CLOUD, Material.AIR);
        ENTITY_MATERIAL_MAP.put(EntityType.ARMOR_STAND, Material.ARMOR_STAND);
        ENTITY_MATERIAL_MAP.put(EntityType.ARROW, Material.ARROW);
        ENTITY_MATERIAL_MAP.put(EntityType.BAT, Material.BAT_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.BEE, Material.BEE_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.BLAZE, Material.BLAZE_ROD);
        ENTITY_MATERIAL_MAP.put(EntityType.BOAT, Material.OAK_BOAT);
        ENTITY_MATERIAL_MAP.put(EntityType.CAT, Material.CAT_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.CAVE_SPIDER, Material.CAVE_SPIDER_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.CHICKEN, Material.CHICKEN_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.COD, Material.COD_BUCKET);
        ENTITY_MATERIAL_MAP.put(EntityType.COW, Material.COW_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.CREEPER, Material.CREEPER_HEAD);
        ENTITY_MATERIAL_MAP.put(EntityType.DOLPHIN, Material.DOLPHIN_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.DONKEY, Material.DONKEY_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.DRAGON_FIREBALL, Material.FIRE_CORAL);
        ENTITY_MATERIAL_MAP.put(EntityType.DROWNED, Material.DROWNED_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.EGG, Material.EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.ELDER_GUARDIAN, Material.ELDER_GUARDIAN_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.ENDER_CRYSTAL, Material.END_CRYSTAL);
        ENTITY_MATERIAL_MAP.put(EntityType.ENDER_DRAGON, Material.DRAGON_HEAD);
        ENTITY_MATERIAL_MAP.put(EntityType.ENDERMAN, Material.ENDER_PEARL);
        ENTITY_MATERIAL_MAP.put(EntityType.ENDERMITE, Material.ENDERMITE_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.EVOKER, Material.EVOKER_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.EXPERIENCE_ORB, Material.EXPERIENCE_BOTTLE);
        ENTITY_MATERIAL_MAP.put(EntityType.FALLING_BLOCK, Material.SAND);
        ENTITY_MATERIAL_MAP.put(EntityType.FIREBALL, Material.FIRE_CHARGE);
        ENTITY_MATERIAL_MAP.put(EntityType.FIREWORK, Material.FIREWORK_ROCKET);
        ENTITY_MATERIAL_MAP.put(EntityType.FISHING_HOOK, Material.FISHING_ROD);
        ENTITY_MATERIAL_MAP.put(EntityType.FOX, Material.FOX_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.GHAST, Material.GHAST_TEAR);
        ENTITY_MATERIAL_MAP.put(EntityType.GIANT, Material.ZOMBIE_HEAD);
        ENTITY_MATERIAL_MAP.put(EntityType.GUARDIAN, Material.GUARDIAN_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.HOGLIN, Material.HOGLIN_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.HORSE, Material.HORSE_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.HUSK, Material.HUSK_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.IRON_GOLEM, Material.IRON_BLOCK);
        ENTITY_MATERIAL_MAP.put(EntityType.ITEM_FRAME, Material.ITEM_FRAME);
        ENTITY_MATERIAL_MAP.put(EntityType.LEASH_HITCH, Material.LEAD);
        ENTITY_MATERIAL_MAP.put(EntityType.LIGHTNING, Material.LIGHTNING_ROD);
        ENTITY_MATERIAL_MAP.put(EntityType.LLAMA, Material.LLAMA_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.MAGMA_CUBE, Material.MAGMA_CUBE_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.MINECART, Material.MINECART);
        ENTITY_MATERIAL_MAP.put(EntityType.MINECART_CHEST, Material.CHEST_MINECART);
        ENTITY_MATERIAL_MAP.put(EntityType.MINECART_COMMAND, Material.COMMAND_BLOCK_MINECART);
        ENTITY_MATERIAL_MAP.put(EntityType.MINECART_FURNACE, Material.FURNACE_MINECART);
        ENTITY_MATERIAL_MAP.put(EntityType.MINECART_HOPPER, Material.HOPPER_MINECART);
        ENTITY_MATERIAL_MAP.put(EntityType.MINECART_MOB_SPAWNER, Material.SPAWNER);
        ENTITY_MATERIAL_MAP.put(EntityType.MINECART_TNT, Material.TNT_MINECART);
        ENTITY_MATERIAL_MAP.put(EntityType.MULE, Material.MULE_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.MUSHROOM_COW, Material.MOOSHROOM_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.OCELOT, Material.OCELOT_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.PAINTING, Material.PAINTING);
        ENTITY_MATERIAL_MAP.put(EntityType.PANDA, Material.PANDA_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.PARROT, Material.PARROT_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.PHANTOM, Material.PHANTOM_MEMBRANE);
        ENTITY_MATERIAL_MAP.put(EntityType.PIG, Material.PIG_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.PRIMED_TNT, Material.TNT);
        ENTITY_MATERIAL_MAP.put(EntityType.PIGLIN, Material.PIGLIN_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.PIGLIN_BRUTE, Material.PIGLIN_BRUTE_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.PILLAGER, Material.PILLAGER_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.POLAR_BEAR, Material.POLAR_BEAR_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.PUFFERFISH, Material.PUFFERFISH_BUCKET);
        ENTITY_MATERIAL_MAP.put(EntityType.RABBIT, Material.RABBIT_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.RAVAGER, Material.RAVAGER_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.SALMON, Material.SALMON_BUCKET);
        ENTITY_MATERIAL_MAP.put(EntityType.SHEEP, Material.SHEEP_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.SHULKER, Material.SHULKER_SHELL);
        ENTITY_MATERIAL_MAP.put(EntityType.SHULKER_BULLET, Material.SHULKER_SHELL);
        ENTITY_MATERIAL_MAP.put(EntityType.SILVERFISH, Material.SILVERFISH_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.SKELETON, Material.SKELETON_SKULL);
        ENTITY_MATERIAL_MAP.put(EntityType.SKELETON_HORSE, Material.SKELETON_HORSE_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.SLIME, Material.SLIME_BALL);
        ENTITY_MATERIAL_MAP.put(EntityType.SMALL_FIREBALL, Material.BLAZE_POWDER);
        ENTITY_MATERIAL_MAP.put(EntityType.SNOWBALL, Material.SNOWBALL);
        ENTITY_MATERIAL_MAP.put(EntityType.SPECTRAL_ARROW, Material.SPECTRAL_ARROW);
        ENTITY_MATERIAL_MAP.put(EntityType.SPIDER, Material.SPIDER_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.SQUID, Material.SQUID_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.STRAY, Material.STRAY_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.STRIDER, Material.STRIDER_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.TRADER_LLAMA, Material.TRADER_LLAMA_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.TRIDENT, Material.TRIDENT);
        ENTITY_MATERIAL_MAP.put(EntityType.TROPICAL_FISH, Material.TROPICAL_FISH_BUCKET);
        ENTITY_MATERIAL_MAP.put(EntityType.TURTLE, Material.TURTLE_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.VEX, Material.VEX_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.VILLAGER, Material.VILLAGER_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.VINDICATOR, Material.VINDICATOR_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.WANDERING_TRADER, Material.WANDERING_TRADER_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.WITCH, Material.WITCH_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.WITHER, Material.WITHER_SKELETON_SKULL);
        ENTITY_MATERIAL_MAP.put(EntityType.WITHER_SKELETON, Material.WITHER_SKELETON_SKULL);
        ENTITY_MATERIAL_MAP.put(EntityType.WITHER_SKULL, Material.WITHER_SKELETON_SKULL);
        ENTITY_MATERIAL_MAP.put(EntityType.WOLF, Material.WOLF_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.ZOGLIN, Material.ZOGLIN_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.SNOWMAN, Material.SNOW_BLOCK);
        ENTITY_MATERIAL_MAP.put(EntityType.ZOMBIE, Material.ZOMBIE_HEAD);
        ENTITY_MATERIAL_MAP.put(EntityType.ZOMBIE_HORSE, Material.ZOMBIE_HORSE_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.ZOMBIE_VILLAGER, Material.ZOMBIE_VILLAGER_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.THROWN_EXP_BOTTLE, Material.EXPERIENCE_BOTTLE);
        ENTITY_MATERIAL_MAP.put(EntityType.ALLAY, Material.ALLAY_SPAWN_EGG);
    }
    /**
     * Returns the Material that corresponds to the given EntityType.
     * If the EntityType is not present in the map, returns Material.PAPER.
     *
     * @param entityType the EntityType for which to retrieve the corresponding Material
     * @return the Material that corresponds to the given EntityType, or Material.PAPER if no mapping is present
     */
    public static Material getMaterial(EntityType entityType){
        return ENTITY_MATERIAL_MAP.getOrDefault(entityType, Material.PAPER);
    }
}