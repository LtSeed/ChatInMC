package ltseed.chatinmc.PlayerInteraction.GUI.FinalButton.ChatterSetButtons;

import ltseed.chatinmc.PlayerInteraction.GUI.AbstractGUI.GUIWithChatterInformation;
import ltseed.chatinmc.PlayerInteraction.GUI.Button;
import ltseed.chatinmc.PlayerInteraction.GUI.FinalButton.BackButton;
import ltseed.chatinmc.PlayerInteraction.GUI.PaginatedGUI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static ltseed.chatinmc.Utils.EntityMaterialMapper.getMaterialFromType;

public class EntityChooseButton extends Button {
    private final GUIWithChatterInformation gui;

    public EntityChooseButton(GUIWithChatterInformation gui, List<String> entityChooseLore) {
        super((short) 4, (short) 2,
                gui.getMaterial(),
                "选择实体id：" + gui.getChoose_entity(),
                entityChooseLore);
        this.gui = gui;
    }

    /**
     *
     */
    @Override
    public void call(Player player) {
        List<Button> buttons = new ArrayList<>();
        List<Entity> entities = player.getNearbyEntities(10, 10, 10);
        for (Entity entity : entities) {
            // 将实体转化为按钮
            List<String> lore = new ArrayList<>();
            lore.add(entity.getName());
            Location location = entity.getLocation();
            lore.add("位置: " + location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ());
            lore.add("实体ID: " + entity.getEntityId());

            Button button = new Button(-1, getMaterialFromType(entity.getType()), entity.getName(), lore) {
                final UUID uuid = entity.getUniqueId();
                final Material m = getMaterialFromType(entity.getType());

                @Override
                public void call(Player p) {
                    gui.setChoose_entity(uuid.toString());
                    gui.setMaterial(m);
                    gui.resetStaticMap(p);
                    gui.open(p);
                }
            };
            buttons.add(button);
        }
        ArrayList<Button> otherButtons = new ArrayList<>();
        otherButtons.add(new BackButton(gui));
        // 创建一个分页GUI
        PaginatedGUI gui = new PaginatedGUI("实体列表", buttons, otherButtons);

        // 打开指定页码的页面
        gui.open(player, 1);
    }
}
