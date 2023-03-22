package ltseed.chatinmc.PlayerInteraction.GUI.FinalButton.ChatterSetButtons.GPTSetButtons;

import ltseed.chatinmc.PlayerInteraction.GUI.AbstractGUI.GUIWithChatterInformation;
import ltseed.chatinmc.PlayerInteraction.GUI.Button;
import ltseed.chatinmc.PlayerInteraction.PlayerConversation;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class N_SetButton extends Button {
    private final GUIWithChatterInformation gui;

    public N_SetButton(GUIWithChatterInformation gui, List<String> lore) {
        super((short) 5, (short) 6, Material.PAPER,
                "n：" + gui.getN(), lore);
        this.gui = gui;
    }

    @Override
    public void call(Player player) {
        new PlayerConversation(player).startConversation("请输入n！", response -> {
            try {
                gui.setN(Integer.parseInt(response));
                gui.resetStaticMap(player);
            } catch (NumberFormatException e) {
                player.sendMessage("你的数字格式有误！");
            }
            gui.open(player);
        });
    }
}
