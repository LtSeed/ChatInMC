package ltseed.chatinmc.PlayerInteraction.GUI.FinalButton.ChatterSetButtons.GPTSetButtons;

import ltseed.chatinmc.PlayerInteraction.GUI.AbstractGUI.GUIWithChatterInformation;
import ltseed.chatinmc.PlayerInteraction.GUI.Button;
import ltseed.chatinmc.PlayerInteraction.PlayerConversation;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class Top_p_SetButton extends Button {
    private final GUIWithChatterInformation gui;

    public Top_p_SetButton(GUIWithChatterInformation gui, List<String> lore) {
        super((short) 4, (short) 6, Material.PAPER,
                "top_p：" + gui.getTop_p(), lore);
        this.gui = gui;
    }

    @Override
    public void call(Player player) {
        new PlayerConversation(player).startConversation("请输入top_p！", response -> {
            try {
                gui.setTop_p(Integer.parseInt(response));
            } catch (NumberFormatException e) {
                player.sendMessage("你的数字格式有误！");
            }
            gui.resetStaticMap(player);
            gui.open(player);
        });
    }
}
