package ltseed.chatinmc.PlayerInteraction.GUI.FinalButton;

import ltseed.chatinmc.PlayerInteraction.GUI.Button;
import ltseed.chatinmc.PlayerInteraction.GUI.FinalGUI.ChatterCreateGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CancelButton extends Button {
    public CancelButton() {
        super((short) 5, (short) 5, Material.BARRIER, ChatColor.RED + "Cancel", null);
    }

    @Override
    public void call(Player player) {
        player.closeInventory();
        ChatterCreateGUI.creating.remove(player);
        player.sendMessage("chatter 成功删除！");
    }
}
