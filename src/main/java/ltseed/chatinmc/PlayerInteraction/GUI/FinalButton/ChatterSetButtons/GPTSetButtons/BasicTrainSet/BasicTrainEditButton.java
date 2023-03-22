package ltseed.chatinmc.PlayerInteraction.GUI.FinalButton.ChatterSetButtons.GPTSetButtons.BasicTrainSet;

import ltseed.chatinmc.PlayerInteraction.GUI.AbstractGUI.GUIWithChatterInformation;
import ltseed.chatinmc.PlayerInteraction.GUI.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class BasicTrainEditButton extends Button {
    private final GUIWithChatterInformation gui;

    public BasicTrainEditButton(GUIWithChatterInformation gui, List<String> basicTrainEditLore) {
        super((short) 9, (short) 1, Material.WRITTEN_BOOK, "基本训练字符串: size = " + gui.getBasicTrain().size(), basicTrainEditLore);
        this.gui = gui;
    }

    /**
     *
     */
    @Override
    public void call(Player player) {
        gui.openBasicTrainSetGUI(player);
    }
}
