package ltseed.chatinmc.PlayerInteraction.GUI.FinalGUI;

import ltseed.chatinmc.PlayerInteraction.GUI.Button;
import ltseed.chatinmc.PlayerInteraction.GUI.EnabledView;
import ltseed.chatinmc.PlayerInteraction.GUI.PaginatedGUI;
import ltseed.chatinmc.PlayerInteraction.GUI.SimpleGUI;
import ltseed.chatinmc.Talker.Chatter;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;

@EnabledView
public class ChatterCreateGUI extends SimpleGUI {

    private static final Map<Player, Chatter> creating = new HashMap<>();

    ChatterCreateGUI(Player player) {
        super("新建一个Chatter！", new HashMap<>());

        Chatter chatter = creating.getOrDefault(player, new Chatter());
        List<String> nameLore = new ArrayList<>();
        nameLore.add("Click to edit");
        Button nameButton = new Button((short) 2, (short) 3, Material.NAME_TAG, "Name", nameLore) {
            /**
             * @param player
             */
            @Override
            public void call(Player player) {

            }
        };
        addButton(nameButton);

        List<String> descLore = new ArrayList<>();
        descLore.add("Click to edit");
        Button descButton = new Button((short) 4, (short) 3, Material.PAPER, "Description", descLore) {
            /**
             * @param player
             */
            @Override
            public void call(Player player) {

            }
        };
        addButton(descButton);


        List<String> entityChooseLore = new ArrayList<>();
        entityChooseLore.add("Click to edit");
        Button entityChooseButton = new Button((short) 4, (short) 3, Material.PAPER, "Description", entityChooseLore) {
            /**
             * @param player
             */
            @Override
            public void call(Player player) {
                List<Button> buttons = new ArrayList<>();
                List<Entity> entities = player.getNearbyEntities(10, 10, 10);

                for (Entity entity : entities) {
                    // 将实体转化为按钮
                    Button button = new Button(entity.getName(), entity.getType().) {
                        @Override
                        public void call(Player p) {

                            new ChatterCreateGUI()
                        }
                    };
                    buttons.add(button);
                }

                // 创建一个分页GUI
                PaginatedGUI gui = new PaginatedGUI("实体列表", buttons);

                // 打开指定页码的页面
                gui.open(player, page);
            }
        };
        addButton(descButton);

        Button createButton = new Button((short) 5, (short) 5, Material.GREEN_WOOL, "Create", null) {
            @Override
            public void call(Player player) {
                ChatRoom chatRoom = new ChatRoom(nameButton.getDisplayName(), descButton.getDisplayName());
                plugin.addChatRoom(chatRoom);
                player.sendMessage("Chat room created!");
                player.closeInventory();
            }
        };
        addButton(createButton);

        Button cancelButton = new Button((short) 4, (short) 5, Material.RED_WOOL, "Cancel", null) {
            @Override
            public void call(Player player) {
                player.closeInventory();
            }
        };
        addButton(cancelButton);

        open(player);
        creating.put(player,chatter);
    }

    private static final Map<EntityType, Material> ENTITY_MATERIAL_MAP = new HashMap<>();

    static {
        // Get all classes that implement LivingEntity
        for (Class<?> clazz : LivingEntity.class.getDeclaredClasses()) {
            if (LivingEntity.class.isAssignableFrom(clazz)) {
                // Try to get the EntityType field
                try {
                    Field entityTypeField = clazz.getDeclaredField("TYPE");
                    entityTypeField.setAccessible(true);
                    EntityType entityType = (EntityType) entityTypeField.get(null);
                    // Try to get the Material field
                    Field materialField = clazz.getDeclaredField("MATERIAL");
                    materialField.setAccessible(true);
                    Material material = (Material) materialField.get(null);
                    ENTITY_MATERIAL_MAP.put(entityType, material);
                } catch (NoSuchFieldException | IllegalAccessException ignored) {
                }
            }
        }
    }

    public static Material getMaterial(EntityType entityType) {
        return ENTITY_MATERIAL_MAP.getOrDefault(entityType, Material.STONE);
    }
}
