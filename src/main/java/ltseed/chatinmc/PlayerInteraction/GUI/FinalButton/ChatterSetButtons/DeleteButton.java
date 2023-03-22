package ltseed.chatinmc.PlayerInteraction.GUI.FinalButton.ChatterSetButtons;

import ltseed.chatinmc.ChatInMC;
import ltseed.chatinmc.PlayerInteraction.GUI.Button;
import ltseed.chatinmc.PlayerInteraction.GUI.FinalGUI.ChatterManageGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.UUID;

public class DeleteButton extends Button {
    private final ChatterManageGUI chatterManageGUI;

    public DeleteButton(ChatterManageGUI chatterManageGUI) {
        super((short) 5, (short) 5,
                Material.BARRIER, ChatColor.RED + "Delete", null);
        this.chatterManageGUI = chatterManageGUI;
    }

    @Override
    public void call(Player player) {
        player.closeInventory();
        UUID key = UUID.fromString(chatterManageGUI.getChoose_entity());
        ChatInMC.chatters.get(key).delete();
        ChatInMC.chatters.remove(key);
        ChatterManageGUI.openManageChooseGUI(player);
        player.sendMessage("chatter 成功删除！");
    }
}
