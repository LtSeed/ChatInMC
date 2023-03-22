package ltseed.chatinmc.PlayerInteraction.GUI.FinalButton.ChatterSetButtons;

import ltseed.chatinmc.PlayerInteraction.GUI.AbstractGUI.GUIWithChatterInformation;
import ltseed.chatinmc.PlayerInteraction.GUI.Button;
import ltseed.chatinmc.PlayerInteraction.PlayerConversation;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class DistanceSetButton extends Button {
    private final GUIWithChatterInformation gui;

    public DistanceSetButton(GUIWithChatterInformation gui, List<String> nameLore) {
        super((short) 7, (short) 3, Material.ARROW,
                "触发距离：" + gui.getTalkDistance(), nameLore);
        this.gui = gui;
    }

    @Override
    public void call(Player player) {
        new PlayerConversation(player).startConversation("请输入能够触发聊天的距离！", response -> {
            try {
                gui.setTalkDistance(Double.parseDouble(response));
                gui.resetStaticMap(player);
            } catch (NumberFormatException e) {
                player.sendMessage("你的数字格式有误！");
            }
            gui.open(player);
        });
    }
}
