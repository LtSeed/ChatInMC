package ltseed.chatinmc.PlayerInteraction.GUI.FinalButton.ChatterSetButtons;

import ltseed.chatinmc.PlayerInteraction.GUI.AbstractGUI.GUIWithChatterInformation;
import ltseed.chatinmc.PlayerInteraction.GUI.Button;
import ltseed.chatinmc.PlayerInteraction.PlayerConversation;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class DescriptionSetButton extends Button {

    GUIWithChatterInformation gui;
    public DescriptionSetButton(GUIWithChatterInformation gui, List<String> descLore) {
        super((short) 3, (short) 2, Material.PAPER,
                "描述：" + gui.getDescription(), descLore);
        this.gui = gui;
    }

    /**
     * Opens a {@link PlayerConversation} with the player to get a new name for the Chatter.
     * Updates the GUI with the new name.
     *
     * @param player The player who clicked the button
     */
    @Override
    public void call(Player player) {
        new PlayerConversation(player).startConversation("请输入Chatter的描述！", response -> {
            gui.setDescription(response);
            gui.resetStaticMap(player);
            gui.open(player);
        });
    }
}
