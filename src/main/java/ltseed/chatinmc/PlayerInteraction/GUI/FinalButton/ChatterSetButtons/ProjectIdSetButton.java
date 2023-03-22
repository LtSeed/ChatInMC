package ltseed.chatinmc.PlayerInteraction.GUI.FinalButton.ChatterSetButtons;

import ltseed.chatinmc.PlayerInteraction.GUI.AbstractGUI.GUIWithChatterInformation;
import ltseed.chatinmc.PlayerInteraction.GUI.Button;
import ltseed.chatinmc.PlayerInteraction.PlayerConversation;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class ProjectIdSetButton extends Button {

    GUIWithChatterInformation GUI;
    public ProjectIdSetButton(GUIWithChatterInformation chatterCreateGUI,
                              List<String> proIdSetLore) {
        super((short) 5, (short) 4, Material.BOOK, "projectId：" +
                chatterCreateGUI.getProjectId(), proIdSetLore);
        this.GUI = chatterCreateGUI;
    }

    /**
     *
     */
    @Override
    public void call(Player player) {
        new PlayerConversation(player).startConversation("请输入DialogFlow ProjectID:", response -> {
            GUI.setProjectId(response);
            GUI.resetStaticMap(player);
            GUI.open(player);
        });
    }
}
