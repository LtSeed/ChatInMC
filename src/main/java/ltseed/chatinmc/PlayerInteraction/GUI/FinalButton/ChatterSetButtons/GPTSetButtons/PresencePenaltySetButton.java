package ltseed.chatinmc.PlayerInteraction.GUI.FinalButton.ChatterSetButtons.GPTSetButtons;

import ltseed.chatinmc.PlayerInteraction.GUI.AbstractGUI.GUIWithChatterInformation;
import ltseed.chatinmc.PlayerInteraction.GUI.Button;
import ltseed.chatinmc.PlayerInteraction.PlayerConversation;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class PresencePenaltySetButton extends Button {
    private final GUIWithChatterInformation gui;

    public PresencePenaltySetButton(GUIWithChatterInformation gui, List<String> lore) {
        super((short) 7, (short) 6, Material.PAPER,
                "presence_penalty：" + gui.getPresence_penalty(), lore);
        this.gui = gui;
    }

    @Override
    public void call(Player player) {
        new PlayerConversation(player).startConversation("请输入presence_penalty_setButton！", response -> {
            try {
                gui.setPresence_penalty(Double.parseDouble(response));
                gui.resetStaticMap(player);
            } catch (NumberFormatException e) {
                player.sendMessage("你的数字格式有误！");
            }
            gui.open(player);
        });
    }
}
