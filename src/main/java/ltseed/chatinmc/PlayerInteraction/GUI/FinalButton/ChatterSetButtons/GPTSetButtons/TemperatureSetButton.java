package ltseed.chatinmc.PlayerInteraction.GUI.FinalButton.ChatterSetButtons.GPTSetButtons;

import ltseed.chatinmc.PlayerInteraction.GUI.AbstractGUI.GUIWithChatterInformation;
import ltseed.chatinmc.PlayerInteraction.GUI.Button;
import ltseed.chatinmc.PlayerInteraction.PlayerConversation;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class TemperatureSetButton extends Button {
    private final GUIWithChatterInformation gui;

    public TemperatureSetButton(GUIWithChatterInformation gui, List<String> lore) {
        super((short) 3, (short) 6,
                Material.PAPER,
                "temperature：" +
                        gui.getTemperature(), lore);
        this.gui = gui;
    }

    @Override
    public void call(Player player) {
        new PlayerConversation(player).startConversation("请输入temperature！", response -> {
            try {
                gui.setTemperature(Double.parseDouble(response));
            } catch (NumberFormatException e) {
                player.sendMessage("你的数字格式有误！");
            }
            gui.resetStaticMap(player);
            gui.open(player);
        });
    }
}
