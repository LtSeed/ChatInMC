package ltseed.chatinmc.PlayerInteraction.GUI.FinalButton;

import ltseed.chatinmc.PlayerInteraction.GUI.AbstractGUI.GUIWithChatterInformation;
import ltseed.chatinmc.PlayerInteraction.GUI.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class BackButton extends Button {
    private final GUIWithChatterInformation gui;

    public BackButton(GUIWithChatterInformation gui) {
        super(46, Material.ARROW, "返回", null);
        this.gui = gui;
    }

    @Override
    public void call(Player player) {
        gui.open(player);
    }
}
