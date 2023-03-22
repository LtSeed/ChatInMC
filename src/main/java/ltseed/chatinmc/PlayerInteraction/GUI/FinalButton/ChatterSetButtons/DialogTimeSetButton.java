package ltseed.chatinmc.PlayerInteraction.GUI.FinalButton.ChatterSetButtons;

import ltseed.chatinmc.PlayerInteraction.GUI.AbstractGUI.GUIWithChatterInformation;
import ltseed.chatinmc.PlayerInteraction.GUI.Button;
import ltseed.chatinmc.PlayerInteraction.PlayerConversation;
import ltseed.chatinmc.Utils.TimeConverter;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class DialogTimeSetButton extends Button {
    private final GUIWithChatterInformation gui;

    public DialogTimeSetButton(GUIWithChatterInformation gui, List<String> nameLore) {
        super((short) 7, (short) 4, Material.CLOCK,
                "对话持续时间：" +
                        TimeConverter.getCode(gui.getDur()), nameLore);
        this.gui = gui;
    }

    @Override
    public void call(Player player) {
        new PlayerConversation(player).startConversation("请输入对话持续时间，格式为*天*时*分*秒*毫秒", response -> {
            try {
                gui.setDur(TimeConverter.getTime(response));
                gui.resetStaticMap(player);
            } catch (Exception e) {
                player.sendMessage("你的格式有误！");
            }
            gui.open(player);
        });
    }
}
