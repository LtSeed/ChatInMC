package ltseed.chatinmc.PlayerInteraction.GUI.FinalButton.ChatterSetButtons.GPTSetButtons.BasicTrainSet;

import ltseed.chatinmc.PlayerInteraction.GUI.AbstractGUI.GUIWithChatterInformation;
import ltseed.chatinmc.PlayerInteraction.GUI.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class BasicTrainListStartDeleteButton extends Button {
    private final GUIWithChatterInformation gui;

    public BasicTrainListStartDeleteButton(GUIWithChatterInformation gui) {
        super(48, Material.BARRIER, "进入删除界面", null);
        this.gui = gui;
    }

    @Override
    public void call(Player player) {
        gui.openBasicTrainDeleteGUI(player);
    }
}
