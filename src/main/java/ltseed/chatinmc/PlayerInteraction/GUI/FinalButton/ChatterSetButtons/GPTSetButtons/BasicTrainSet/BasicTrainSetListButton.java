package ltseed.chatinmc.PlayerInteraction.GUI.FinalButton.ChatterSetButtons.GPTSetButtons.BasicTrainSet;

import ltseed.chatinmc.PlayerInteraction.GUI.AbstractGUI.GUIWithChatterInformation;
import ltseed.chatinmc.PlayerInteraction.GUI.Button;
import ltseed.chatinmc.PlayerInteraction.GUI.FinalGUI.ChatterCreateGUI;
import ltseed.chatinmc.PlayerInteraction.PlayerConversation;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class BasicTrainSetListButton extends Button {
    private final GUIWithChatterInformation gui;
    private final String s;
    private final Player player;

    public BasicTrainSetListButton(GUIWithChatterInformation gui, int i, String s, Player player) {
        super(-1, Material.WRITABLE_BOOK, String.valueOf(i), ChatterCreateGUI.splitString(s));
        this.gui = gui;
        this.s = s;
        this.player = player;
    }

    @Override
    public void call(Player p) {
        new PlayerConversation(player).startConversation("请输入新的字符串！", response -> {
            try {
                ArrayList<String> n = new ArrayList<>();
                for (String s1 : gui.getBasicTrain()) {
                    if (s1.equalsIgnoreCase(s)) n.add(response);
                    else n.add(s1);
                }
                gui.setBasicTrain(n);
                gui.resetStaticMap(player);
            } catch (Exception e) {
                player.sendMessage("格式有误！");
            }
            gui.openBasicTrainSetGUI(player);
        });
        player.closeInventory();
    }
}
