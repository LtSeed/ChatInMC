package ltseed.chatinmc.PlayerInteraction.GUI.FinalButton.ChatterSetButtons.GPTSetButtons;

import ltseed.chatinmc.PlayerInteraction.GUI.AbstractGUI.GUIWithChatterInformation;
import ltseed.chatinmc.PlayerInteraction.GUI.Button;
import ltseed.chatinmc.PlayerInteraction.PlayerConversation;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class Best_ofSetButton extends Button {
    private final GUIWithChatterInformation gui;

    public Best_ofSetButton(GUIWithChatterInformation gui, List<String> lore) {
        super((short) 9, (short) 6, Material.PAPER,
                "best_of：" + gui.getBest_of(), lore);
        this.gui = gui;
    }

    @Override
    public void call(Player player) {
        new PlayerConversation(player).startConversation("请输入best_of！", response -> {
            try {
                gui.setBest_of(Integer.parseInt(response));
                gui.resetStaticMap(player);
            } catch (NumberFormatException e) {
                player.sendMessage("你的数字格式有误！");
            }
            gui.open(player);
        });
    }
}
