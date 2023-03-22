package ltseed.chatinmc.PlayerInteraction.GUI.FinalButton.ChatterSetButtons;

import ltseed.chatinmc.PlayerInteraction.GUI.AbstractGUI.GUIWithChatterInformation;
import ltseed.chatinmc.PlayerInteraction.GUI.Button;
import ltseed.chatinmc.PlayerInteraction.PlayerConversation;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class NameSetButton extends Button {

    GUIWithChatterInformation gui;
    public NameSetButton(GUIWithChatterInformation gui, List<String> nameLore) {
        super((short) 3, (short) 3,
                Material.NAME_TAG,
                "名称：" + gui.getName(), nameLore);
        this.gui = gui;
    }

    /**
     *
     */
    @Override
    public void call(Player player) {
        new PlayerConversation(player).startConversation("请输入Chatter的名字！", response -> {
            gui.setName(response);
            gui.resetStaticMap(player);
            gui.open(player);
        });
    }
}
