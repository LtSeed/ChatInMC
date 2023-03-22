package ltseed.chatinmc.PlayerInteraction.GUI.FinalButton.ChatterSetButtons;

import ltseed.chatinmc.ChatInMC;
import ltseed.chatinmc.PlayerInteraction.GUI.AbstractGUI.GUIWithChatterInformation;
import ltseed.chatinmc.PlayerInteraction.GUI.Button;
import ltseed.chatinmc.PlayerInteraction.GUI.FinalButton.BackButton;
import ltseed.chatinmc.PlayerInteraction.GUI.PaginatedGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CoreChooseButton extends Button {
    private final GUIWithChatterInformation gui;

    public CoreChooseButton(GUIWithChatterInformation gui, List<String> coreChooseLore) {
        super((short) 6, (short) 4, Material.PAPER,
                "核心名：" + gui.getCore_name(), coreChooseLore);
        this.gui = gui;
    }

    /**
     *
     */
    @Override
    public void call(Player player) {
        List<Button> buttons = new ArrayList<>();
        for (String s : ChatInMC.models) {
            // 将模型转化为按钮

            Button button = new Button(-1, Material.WRITABLE_BOOK, s, null) {
                @Override
                public void call(Player p) {
                    gui.setCore_name(s);
                    gui.setCore(s);
                    gui.resetStaticMap(p);
                    gui.open(p);
                }
            };
            buttons.add(button);
        }
        ArrayList<Button> otherButtons = new ArrayList<>();
        otherButtons.add(new BackButton(gui));
        // 创建一个分页GUI
        PaginatedGUI gui = new PaginatedGUI("核心列表", buttons, otherButtons);

        // 打开指定页码的页面
        gui.open(player, 1);
    }
}
