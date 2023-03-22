package ltseed.chatinmc.Utils;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;

import java.util.HashMap;
import java.util.Map;

/**
 * This code is mapping EntityType objects to their corresponding Material objects in Minecraft. Each EntityType object is associated with a specific Material object, which can be used to spawn an item in the game.
 * <p>
 * The EntityMaterialMapper class contains a static HashMap called ENTITY_MATERIAL_MAP, which is initialized with mappings between various EntityType objects and their corresponding Material objects using the put() method.
 * <p>
 * For example, the AREA_EFFECT_CLOUD EntityType object is mapped to the AIR Material object, while the ARMOR_STAND EntityType object is mapped to the ARMOR_STAND Material object.
 * <p>
 * This code can be used to simplify the process of creating and spawning entities in the game, as it provides an easy way to map EntityType objects to their corresponding Material objects.
 *
 * @author ltseed
 * @version 1.0
 */
public class EntityMaterialMapper {
    private static final Map<EntityType, Material> ENTITY_MATERIAL_MAP = new HashMap<>();

    static {
        ENTITY_MATERIAL_MAP.put(EntityType.EXPERIENCE_ORB, Material.EXPERIENCE_BOTTLE);
        ENTITY_MATERIAL_MAP.put(EntityType.AREA_EFFECT_CLOUD, Material.LINGERING_POTION);
        ENTITY_MATERIAL_MAP.put(EntityType.ELDER_GUARDIAN, Material.PRISMARINE_SHARD);
        ENTITY_MATERIAL_MAP.put(EntityType.WITHER_SKELETON, Material.COAL_BLOCK);
        ENTITY_MATERIAL_MAP.put(EntityType.STRAY, Material.ICE);
        ENTITY_MATERIAL_MAP.put(EntityType.EGG, Material.EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.LEASH_HITCH, Material.LEAD);
        ENTITY_MATERIAL_MAP.put(EntityType.PAINTING, Material.PAINTING);
        ENTITY_MATERIAL_MAP.put(EntityType.ARROW, Material.ARROW);
        ENTITY_MATERIAL_MAP.put(EntityType.SNOWBALL, Material.SNOWBALL);
        ENTITY_MATERIAL_MAP.put(EntityType.FIREBALL, Material.FIRE_CHARGE);
        ENTITY_MATERIAL_MAP.put(EntityType.SMALL_FIREBALL, Material.BLAZE_POWDER);
        ENTITY_MATERIAL_MAP.put(EntityType.ENDER_PEARL, Material.ENDER_PEARL);
        ENTITY_MATERIAL_MAP.put(EntityType.ENDER_SIGNAL, Material.ENDER_EYE);
        ENTITY_MATERIAL_MAP.put(EntityType.SPLASH_POTION, Material.SPLASH_POTION);
        ENTITY_MATERIAL_MAP.put(EntityType.THROWN_EXP_BOTTLE, Material.EXPERIENCE_BOTTLE);
        ENTITY_MATERIAL_MAP.put(EntityType.ITEM_FRAME, Material.ITEM_FRAME);
        ENTITY_MATERIAL_MAP.put(EntityType.WITHER_SKULL, Material.WITHER_SKELETON_SKULL);
        ENTITY_MATERIAL_MAP.put(EntityType.PRIMED_TNT, Material.TNT);
        ENTITY_MATERIAL_MAP.put(EntityType.FALLING_BLOCK, Material.SAND);
        ENTITY_MATERIAL_MAP.put(EntityType.FIREWORK, Material.FIREWORK_ROCKET);
        ENTITY_MATERIAL_MAP.put(EntityType.HUSK, Material.ROTTEN_FLESH);
        ENTITY_MATERIAL_MAP.put(EntityType.SPECTRAL_ARROW, Material.SPECTRAL_ARROW);
        ENTITY_MATERIAL_MAP.put(EntityType.SHULKER_BULLET, Material.SHULKER_SHELL);
        ENTITY_MATERIAL_MAP.put(EntityType.DRAGON_FIREBALL, Material.DRAGON_BREATH);
        ENTITY_MATERIAL_MAP.put(EntityType.ZOMBIE_VILLAGER, Material.ROTTEN_FLESH);
        ENTITY_MATERIAL_MAP.put(EntityType.SKELETON_HORSE, Material.BONE_BLOCK);
        ENTITY_MATERIAL_MAP.put(EntityType.ZOMBIE_HORSE, Material.ROTTEN_FLESH);
        ENTITY_MATERIAL_MAP.put(EntityType.ARMOR_STAND, Material.ARMOR_STAND);
        ENTITY_MATERIAL_MAP.put(EntityType.DONKEY, Material.HAY_BLOCK);
        ENTITY_MATERIAL_MAP.put(EntityType.MULE, Material.CHEST);
        ENTITY_MATERIAL_MAP.put(EntityType.EVOKER_FANGS, Material.DIAMOND);
        ENTITY_MATERIAL_MAP.put(EntityType.EVOKER, Material.TOTEM_OF_UNDYING);
        ENTITY_MATERIAL_MAP.put(EntityType.VEX, Material.EMERALD);
        ENTITY_MATERIAL_MAP.put(EntityType.VINDICATOR, Material.IRON_AXE);
        ENTITY_MATERIAL_MAP.put(EntityType.ILLUSIONER, Material.LAPIS_BLOCK);
        ENTITY_MATERIAL_MAP.put(EntityType.MINECART, Material.MINECART);
        ENTITY_MATERIAL_MAP.put(EntityType.MINECART_CHEST, Material.CHEST_MINECART);
        ENTITY_MATERIAL_MAP.put(EntityType.MINECART_FURNACE, Material.FURNACE_MINECART);
        ENTITY_MATERIAL_MAP.put(EntityType.MINECART_TNT, Material.TNT_MINECART);
        ENTITY_MATERIAL_MAP.put(EntityType.MINECART_HOPPER, Material.HOPPER_MINECART);
        ENTITY_MATERIAL_MAP.put(EntityType.MINECART_MOB_SPAWNER, Material.COMMAND_BLOCK_MINECART);
        ENTITY_MATERIAL_MAP.put(EntityType.CREEPER, Material.CREEPER_HEAD);
        ENTITY_MATERIAL_MAP.put(EntityType.SKELETON, Material.SKELETON_SKULL);
        ENTITY_MATERIAL_MAP.put(EntityType.SPIDER, Material.COBWEB);
        ENTITY_MATERIAL_MAP.put(EntityType.GIANT, Material.IRON_BLOCK);
        ENTITY_MATERIAL_MAP.put(EntityType.ZOMBIE, Material.ZOMBIE_HEAD);
        ENTITY_MATERIAL_MAP.put(EntityType.SLIME, Material.SLIME_BLOCK);
        ENTITY_MATERIAL_MAP.put(EntityType.GHAST, Material.GHAST_TEAR);
        ENTITY_MATERIAL_MAP.put(EntityType.ZOMBIFIED_PIGLIN, Material.GOLD_NUGGET);
        ENTITY_MATERIAL_MAP.put(EntityType.ENDERMAN, Material.ENDER_PEARL);
        ENTITY_MATERIAL_MAP.put(EntityType.CAVE_SPIDER, Material.COBWEB);
        ENTITY_MATERIAL_MAP.put(EntityType.SILVERFISH, Material.STONE_BRICKS);
        ENTITY_MATERIAL_MAP.put(EntityType.BLAZE, Material.BLAZE_ROD);
        ENTITY_MATERIAL_MAP.put(EntityType.MAGMA_CUBE, Material.MAGMA_BLOCK);
        ENTITY_MATERIAL_MAP.put(EntityType.ENDER_DRAGON, Material.DRAGON_HEAD);
        ENTITY_MATERIAL_MAP.put(EntityType.WITHER, Material.WITHER_SKELETON_SKULL);
        ENTITY_MATERIAL_MAP.put(EntityType.BAT, Material.BAT_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.WITCH, Material.WITCH_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.ENDERMITE, Material.END_PORTAL_FRAME);
        ENTITY_MATERIAL_MAP.put(EntityType.GUARDIAN, Material.PRISMARINE_SHARD);
        ENTITY_MATERIAL_MAP.put(EntityType.SHULKER, Material.SHULKER_SHELL);
        ENTITY_MATERIAL_MAP.put(EntityType.PIG, Material.PORKCHOP);
        ENTITY_MATERIAL_MAP.put(EntityType.SHEEP, Material.WHITE_WOOL);
        ENTITY_MATERIAL_MAP.put(EntityType.COW, Material.LEATHER);
        ENTITY_MATERIAL_MAP.put(EntityType.CHICKEN, Material.CHICKEN);
        ENTITY_MATERIAL_MAP.put(EntityType.SQUID, Material.INK_SAC);
        ENTITY_MATERIAL_MAP.put(EntityType.WOLF, Material.WHITE_WOOL);
        ENTITY_MATERIAL_MAP.put(EntityType.MUSHROOM_COW, Material.RED_MUSHROOM_BLOCK);
        ENTITY_MATERIAL_MAP.put(EntityType.SNOWMAN, Material.SNOW_BLOCK);
        ENTITY_MATERIAL_MAP.put(EntityType.OCELOT, Material.CAT_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.IRON_GOLEM, Material.IRON_BLOCK);
        ENTITY_MATERIAL_MAP.put(EntityType.HORSE, Material.HAY_BLOCK);
        ENTITY_MATERIAL_MAP.put(EntityType.RABBIT, Material.RABBIT_HIDE);
        ENTITY_MATERIAL_MAP.put(EntityType.POLAR_BEAR, Material.PACKED_ICE);
        ENTITY_MATERIAL_MAP.put(EntityType.LLAMA, Material.WHITE_CARPET);
        ENTITY_MATERIAL_MAP.put(EntityType.LLAMA_SPIT, Material.WHITE_WOOL);
        ENTITY_MATERIAL_MAP.put(EntityType.PARROT, Material.PARROT_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.VILLAGER, Material.EMERALD);
        ENTITY_MATERIAL_MAP.put(EntityType.ENDER_CRYSTAL, Material.END_CRYSTAL);
        ENTITY_MATERIAL_MAP.put(EntityType.TURTLE, Material.TURTLE_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.PHANTOM, Material.PHANTOM_MEMBRANE);
        ENTITY_MATERIAL_MAP.put(EntityType.TRIDENT, Material.TRIDENT);
        ENTITY_MATERIAL_MAP.put(EntityType.COD, Material.COD);
        ENTITY_MATERIAL_MAP.put(EntityType.SALMON, Material.SALMON);
        ENTITY_MATERIAL_MAP.put(EntityType.PUFFERFISH, Material.PUFFERFISH);
        ENTITY_MATERIAL_MAP.put(EntityType.TROPICAL_FISH, Material.TROPICAL_FISH);
        ENTITY_MATERIAL_MAP.put(EntityType.DROWNED, Material.ZOMBIE_HEAD);
        ENTITY_MATERIAL_MAP.put(EntityType.DOLPHIN, Material.PRISMARINE);
        ENTITY_MATERIAL_MAP.put(EntityType.CAT, Material.CAT_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.PANDA, Material.PANDA_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.PILLAGER, Material.CROSSBOW);
        ENTITY_MATERIAL_MAP.put(EntityType.RAVAGER, Material.RAVAGER_SPAWN_EGG);
        ENTITY_MATERIAL_MAP.put(EntityType.TRADER_LLAMA, Material.WHITE_CARPET);
        ENTITY_MATERIAL_MAP.put(EntityType.WANDERING_TRADER, Material.EMERALD_BLOCK);
        ENTITY_MATERIAL_MAP.put(EntityType.FOX, Material.ORANGE_WOOL);
        ENTITY_MATERIAL_MAP.put(EntityType.BEE, Material.YELLOW_WOOL);
        ENTITY_MATERIAL_MAP.put(EntityType.HOGLIN, Material.CRIMSON_NYLIUM);
        ENTITY_MATERIAL_MAP.put(EntityType.PIGLIN, Material.GOLD_BLOCK);
        ENTITY_MATERIAL_MAP.put(EntityType.STRIDER, Material.LAVA);
        ENTITY_MATERIAL_MAP.put(EntityType.ZOGLIN, Material.WARPED_NYLIUM);
        ENTITY_MATERIAL_MAP.put(EntityType.PIGLIN_BRUTE, Material.OBSIDIAN);
        ENTITY_MATERIAL_MAP.put(EntityType.AXOLOTL, Material.AZURE_BLUET);
        ENTITY_MATERIAL_MAP.put(EntityType.GLOW_ITEM_FRAME, Material.GLOW_ITEM_FRAME);
        ENTITY_MATERIAL_MAP.put(EntityType.GLOW_SQUID, Material.GLOW_INK_SAC);
        ENTITY_MATERIAL_MAP.put(EntityType.GOAT, Material.WHITE_WOOL);
        ENTITY_MATERIAL_MAP.put(EntityType.MARKER, Material.BEACON);
        ENTITY_MATERIAL_MAP.put(EntityType.ALLAY, Material.SHIELD);
        ENTITY_MATERIAL_MAP.put(EntityType.CHEST_BOAT, Material.CHEST);
        ENTITY_MATERIAL_MAP.put(EntityType.FROG, Material.GREEN_WOOL);
        ENTITY_MATERIAL_MAP.put(EntityType.TADPOLE, Material.TROPICAL_FISH);
        ENTITY_MATERIAL_MAP.put(EntityType.WARDEN, Material.NETHERITE_BLOCK);
        ENTITY_MATERIAL_MAP.put(EntityType.CAMEL, Material.SAND);
        ENTITY_MATERIAL_MAP.put(EntityType.FISHING_HOOK, Material.TRIPWIRE_HOOK);
        ENTITY_MATERIAL_MAP.put(EntityType.LIGHTNING, Material.FIREWORK_STAR);
        ENTITY_MATERIAL_MAP.put(EntityType.PLAYER, Material.PLAYER_HEAD);
        ENTITY_MATERIAL_MAP.put(EntityType.UNKNOWN, Material.BARRIER);
        ENTITY_MATERIAL_MAP.put(EntityType.BOAT, Material.OAK_BOAT);
        ENTITY_MATERIAL_MAP.put(EntityType.MINECART_COMMAND, Material.COMMAND_BLOCK_MINECART);
    }

    /**
     * Returns the Material that corresponds to the given EntityType.
     * If the EntityType is not present in the map, returns Material.PAPER.
     *
     * @param entity the Entity for which to retrieve the corresponding Material
     * @return the Material that corresponds to the given EntityType, or Material.PAPER if no mapping is present
     */
    public static Material getMaterialFromType(Entity entity) {
        if(entity instanceof Item){
            Item item = (Item) entity;
            return item.getItemStack().getType();
        }
        return ENTITY_MATERIAL_MAP.getOrDefault(entity.getType(), Material.PAPER);
    }
}