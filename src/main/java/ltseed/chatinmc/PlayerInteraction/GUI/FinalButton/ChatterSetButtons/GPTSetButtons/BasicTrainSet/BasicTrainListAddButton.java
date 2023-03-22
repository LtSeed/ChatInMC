package ltseed.chatinmc.PlayerInteraction.GUI.FinalButton.ChatterSetButtons.GPTSetButtons.BasicTrainSet;

import ltseed.chatinmc.PlayerInteraction.GUI.AbstractGUI.GUIWithChatterInformation;
import ltseed.chatinmc.PlayerInteraction.GUI.Button;
import ltseed.chatinmc.PlayerInteraction.PlayerConversation;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class BasicTrainListAddButton extends Button {
    private final GUIWithChatterInformation gui;

    public BasicTrainListAddButton(GUIWithChatterInformation gui) {
        super(47, Material.ARROW, "添加新的", null);
        this.gui = gui;
    }

    @Override
    public void call(Player player) {
        new PlayerConversation(player).startConversation("请输入新的字符串！", response -> {
            try {
                gui.getBasicTrain().add(response);
                gui.resetStaticMap(player);
            } catch (Exception e) {
                player.sendMessage("格式有误！");
            }
            gui.openBasicTrainSetGUI(player);
        });
        player.closeInventory();
    }
}
