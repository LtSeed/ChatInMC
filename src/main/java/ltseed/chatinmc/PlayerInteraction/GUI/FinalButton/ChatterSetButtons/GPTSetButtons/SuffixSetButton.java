package ltseed.chatinmc.PlayerInteraction.GUI.FinalButton.ChatterSetButtons.GPTSetButtons;

import ltseed.chatinmc.PlayerInteraction.GUI.AbstractGUI.GUIWithChatterInformation;
import ltseed.chatinmc.PlayerInteraction.GUI.Button;
import ltseed.chatinmc.PlayerInteraction.PlayerConversation;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class SuffixSetButton extends Button {
    GUIWithChatterInformation gui;
    public SuffixSetButton(GUIWithChatterInformation gui, List<String> lore) {
        super((short) 1, (short) 6, Material.PAPER, "suffix：" + gui.getSuffix(), lore);
        this.gui = gui;
    }

    @Override
    public void call(Player player) {
        new PlayerConversation(player).startConversation("请输入suffix！", response -> {
            gui.setSuffix(response);
            gui.resetStaticMap(player);
            gui.open(player);
        });
    }
}
