package ltseed.chatinmc.PlayerInteraction.GUI.FinalButton.ChatterSetButtons.GPTSetButtons;

import ltseed.chatinmc.PlayerInteraction.GUI.AbstractGUI.GUIWithChatterInformation;
import ltseed.chatinmc.PlayerInteraction.GUI.Button;
import ltseed.chatinmc.PlayerInteraction.PlayerConversation;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class LogprobsSetButton extends Button {
    private final GUIWithChatterInformation gui;

    public LogprobsSetButton(GUIWithChatterInformation gui, List<String> lore) {
        super((short) 6, (short) 6, Material.PAPER,
                "logprobs：" + gui.getLogprobs(), lore);
        this.gui = gui;
    }

    @Override
    public void call(Player player) {
        new PlayerConversation(player).startConversation("请输入logprobs！", response -> {
            try {
                gui.setLogprobs(Integer.parseInt(response));
                gui.resetStaticMap(player);
            } catch (NumberFormatException e) {
                player.sendMessage("你的数字格式有误！");
            }
            gui.open(player);
        });
    }
}
