package ltseed.chatinmc.PlayerInteraction.GUI.AbstractGUI;

import lombok.Getter;
import lombok.Setter;
import ltseed.chatinmc.PlayerInteraction.GUI.Button;
import ltseed.chatinmc.PlayerInteraction.GUI.FinalButton.*;
import ltseed.chatinmc.PlayerInteraction.GUI.FinalButton.ChatterSetButtons.GPTSetButtons.BasicTrainSet.BasicTrainListAddButton;
import ltseed.chatinmc.PlayerInteraction.GUI.FinalButton.ChatterSetButtons.GPTSetButtons.BasicTrainSet.BasicTrainListEndDeleteButton;
import ltseed.chatinmc.PlayerInteraction.GUI.FinalButton.ChatterSetButtons.GPTSetButtons.BasicTrainSet.BasicTrainListStartDeleteButton;
import ltseed.chatinmc.PlayerInteraction.GUI.FinalButton.ChatterSetButtons.GPTSetButtons.BasicTrainSet.BasicTrainSetListButton;
import ltseed.chatinmc.PlayerInteraction.GUI.PaginatedGUI;
import ltseed.chatinmc.PlayerInteraction.GUI.SimpleGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ltseed.chatinmc.PlayerInteraction.GUI.FinalGUI.ChatterCreateGUI.splitString;

@Getter
@Setter
public abstract class GUIWithChatterInformation extends SimpleGUI {

    protected String name = "未设置";
    protected String description = "未设置";
    protected String choose_entity = "未选择";
    protected Material material = null;
    protected String core = null;
    protected double talkDistance = 15;
    protected long dur = 60 * 1000 * 60;
    protected String core_name = "未指定";
    protected String projectId = "未设置";
    protected String suffix = "默认值";
    protected int max_tokens = 512;
    protected double temperature = 1;
    protected int top_p = 1;
    protected int n = 1;
    protected Integer logprobs = null;
    protected double presence_penalty = 0;
    protected double frequency_penalty = 0;
    protected int best_of = 1;
    protected ArrayList<String> basicTrain = new ArrayList<>();

    public GUIWithChatterInformation(String s, Map<Integer, Button> hashMap) {
        super(s,hashMap);
    }

    public GUIWithChatterInformation() {
        super();
    }

    public abstract void update();

    public abstract void resetStaticMap(Player player);


    /**
     * Opens the GUI for setting the basic training data for the AI.
     *
     * @param player The player for whom the GUI is being opened.
     */
    public void openBasicTrainSetGUI(Player player) {
        List<Button> buttons = new ArrayList<>();
        int i = 1;
        for (String s : basicTrain) {
            // 将basicTrain转化为按钮
            Button button = new BasicTrainSetListButton(this, i++, s, player);
            buttons.add(button);
        }
        ArrayList<Button> otherButtons = new ArrayList<>();
        otherButtons.add(new BackButton(this));
        otherButtons.add(new BasicTrainListAddButton(this));
        otherButtons.add(new BasicTrainListStartDeleteButton(this));
        // 创建一个分页GUI
        PaginatedGUI gui = new PaginatedGUI("核心列表", buttons, otherButtons);

        // 打开指定页码的页面
        gui.open(player, 1);
    }

    /**
     * Opens the GUI for deleting the basic training data for the AI.
     *
     * @param player The player for whom the GUI is being opened.
     */
    public void openBasicTrainDeleteGUI(Player player) {
        List<Button> buttons = new ArrayList<>();
        int i = 1;
        for (String s : basicTrain) {
            // 将basicTrain转化为按钮
            Button button = new Button(-1, Material.BARRIER, String.valueOf(i++), splitString(s)) {
                @Override
                public void call(Player p) {
                    try {
                        basicTrain.remove(s);
                        resetStaticMap(player);
                    } catch (Exception e) {
                        player.sendMessage("出现了未知问题！");
                    }
                    openBasicTrainDeleteGUI(player);
                }
            };
            buttons.add(button);
        }
        ArrayList<Button> otherButtons = new ArrayList<>();
        otherButtons.add(new BackButton(this));
        otherButtons.add(new BasicTrainListEndDeleteButton(this));
        // 创建一个分页GUI
        PaginatedGUI gui = new PaginatedGUI("核心列表", buttons, otherButtons);

        // 打开指定页码的页面
        gui.open(player, 1);
    }
}
