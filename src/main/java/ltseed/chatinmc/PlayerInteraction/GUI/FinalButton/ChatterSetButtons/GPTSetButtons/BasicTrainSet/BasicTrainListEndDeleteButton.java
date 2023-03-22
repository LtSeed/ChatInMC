package ltseed.chatinmc.PlayerInteraction.GUI.FinalButton.ChatterSetButtons.GPTSetButtons.BasicTrainSet;

import ltseed.chatinmc.PlayerInteraction.GUI.AbstractGUI.GUIWithChatterInformation;
import ltseed.chatinmc.PlayerInteraction.GUI.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class BasicTrainListEndDeleteButton extends Button {
    private final GUIWithChatterInformation gui;

    public BasicTrainListEndDeleteButton(GUIWithChatterInformation gui) {
        super(48, Material.EMERALD, "退出删除界面", null);
        this.gui = gui;
    }

    @Override
    public void call(Player player) {
        gui.openBasicTrainSetGUI(player);
    }
}
