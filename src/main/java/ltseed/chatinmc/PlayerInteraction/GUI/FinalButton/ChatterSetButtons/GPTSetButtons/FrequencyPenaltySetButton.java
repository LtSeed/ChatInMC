package ltseed.chatinmc.PlayerInteraction.GUI.FinalButton.ChatterSetButtons.GPTSetButtons;

import ltseed.chatinmc.PlayerInteraction.GUI.AbstractGUI.GUIWithChatterInformation;
import ltseed.chatinmc.PlayerInteraction.GUI.Button;
import ltseed.chatinmc.PlayerInteraction.PlayerConversation;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class FrequencyPenaltySetButton extends Button {
    private final GUIWithChatterInformation gui;

    public FrequencyPenaltySetButton(GUIWithChatterInformation gui, List<String> lore) {
        super((short) 8, (short) 6,
                Material.PAPER,
                "frequency_penalty：" + gui.getFrequency_penalty(), lore);
        this.gui = gui;
    }

    @Override
    public void call(Player player) {
        new PlayerConversation(player).startConversation("请输入frequency_penalty！", response -> {
            try {
                gui.setFrequency_penalty(Double.parseDouble(response));
                gui.resetStaticMap(player);
            } catch (NumberFormatException e) {
                player.sendMessage("你的数字格式有误！");
            }
            gui.open(player);
        });
    }
}
