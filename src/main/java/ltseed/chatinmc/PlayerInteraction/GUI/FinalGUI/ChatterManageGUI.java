package ltseed.chatinmc.PlayerInteraction.GUI.FinalGUI;

import lombok.Getter;
import lombok.Setter;
import ltseed.chatinmc.ChatInMC;
import ltseed.chatinmc.PlayerInteraction.GUI.AbstractGUI.GUIWithChatterInformation;
import ltseed.chatinmc.PlayerInteraction.GUI.Button;
import ltseed.chatinmc.PlayerInteraction.GUI.FinalButton.ChatterSetButtons.*;
import ltseed.chatinmc.PlayerInteraction.GUI.FinalButton.ChatterSetButtons.GPTSetButtons.*;
import ltseed.chatinmc.PlayerInteraction.GUI.PaginatedGUI;
import ltseed.chatinmc.Talker.ChatGPT.ChatGPTBuilder;
import ltseed.chatinmc.Talker.Chatter;
import ltseed.chatinmc.Talker.DialogFlow.DialogFlowBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.*;

import static ltseed.chatinmc.Utils.EntityMaterialMapper.getMaterialFromType;

/**
 * A GUI for managing Chatters. Allows the user to modify the name, description, entity, talking distance, and dialog time
 * of a Chatter, as well as its underlying AI core and parameters (if it is a ChatGPTBuilder), or the DialogFlow project ID
 * (if it is a DialogFlowBuilder).
 * The GUI also displays a list of all available Chatters and allows the user to choose which one to modify.
 * This class extends SimpleGUI and uses a HashMap to store GUI elements.
 *
 * @author LtSeed
 * @version 1.0
 */
@Getter
@Setter
public class ChatterManageGUI extends GUIWithChatterInformation {

    public static final Map<Player, ChatterManageGUI> managing = new HashMap<>();



    /**
     * Constructor for ChatterManageGUI. Initializes all necessary variables and calls the update() and open() methods.
     *
     * @param player  the player using the GUI
     * @param chatter the Chatter to modify
     */
    private ChatterManageGUI(Player player, Chatter chatter) {
        super("修改一个Chatter！", new HashMap<>());

        name = chatter.getName();
        description = chatter.getDescription();
        choose_entity = chatter.getUuid().toString();
        try {
            material = getMaterialFromType(Objects.requireNonNull(chatter.getEntity()));
        } catch (Exception e) {
            material = Material.COW_SPAWN_EGG;
        }

        talkDistance = chatter.getTalk_distance();
        dur = chatter.getDialogTime();
        core_name = chatter.getCore().getClass().getName().replace("Builder", "");
        if (core_name.contains("ChatGPT")) {
            ChatGPTBuilder core = (ChatGPTBuilder) chatter.getCore();
            suffix = core.getSuffix();
            max_tokens = core.getMax_tokens();
            temperature = core.getTemperature();
            top_p = core.getTop_p();
            n = core.getN();
            logprobs = core.getLogprobs();
            presence_penalty = core.getPresence_penalty();
            frequency_penalty = core.getFrequency_penalty();
            best_of = core.getBest_of();
            basicTrain = (ArrayList<String>) core.getBasicTrain();
        } else {
            projectId = ((DialogFlowBuilder) chatter.getCore()).getProjectId();
        }
        update();
        open(player);
    }

    /**
     * Returns an instance of ChatterManageGUI for a specific player and Chatter.
     *
     * @param player  the player using the GUI
     * @param chatter the Chatter to modify
     * @return an instance of ChatterManageGUI
     */
    public static ChatterManageGUI getInstance(Player player, Chatter chatter) {
        return new ChatterManageGUI(player, chatter);
    }

    /**
     * Static method to open the ChatterManageGUI for the specified player.
     * Displays a message if there are no Chatters available.
     *
     * @param player the player using the GUI
     */
    public static void openManageChooseGUI(Player player) {
        if (ChatInMC.chatters.size() == 0) {
            player.sendMessage("现在没有Chatter！");
            return;
        }
        ArrayList<Button> allButtons = new ArrayList<>();

        for (Chatter value : ChatInMC.chatters.values()) {
            List<String> d = new ArrayList<>();
            value.describe().forEach((k, v) -> d.add(k + ": " + v));
            allButtons.add(new Button(-1, Material.WRITABLE_BOOK, value.getName(), d) {
                @Override
                public void call(Player player) {
                    getInstance(player, value).open(player);
                }
            });
        }

        ArrayList<Button> otherButtons = new ArrayList<>();

        new PaginatedGUI("选择你要修改的Chatter!", allButtons, otherButtons).open(player, 1);
    }

    /**
     * Updates the buttons in the GUI with the latest information.
     */
    public void update() {
        List<String> nameLore = new ArrayList<>();
        nameLore.add("Click to edit");
        Button nameButton = new NameSetButton(this,nameLore);
        addButton(nameButton);

        List<String> descLore = new ArrayList<>();
        descLore.add("Click to edit");
        Button descButton = new DescriptionSetButton(this,descLore);
        addButton(descButton);


        List<String> entityChooseLore = new ArrayList<>();
        entityChooseLore.add("Click to edit");
        if (material == null) material = Material.COW_SPAWN_EGG;
        Button entityChooseButton = new EntityChooseButton(this, entityChooseLore);
        addButton(entityChooseButton);


        List<String> coreChooseLore = new ArrayList<>();
        coreChooseLore.add("Click to edit");
        Button coreChooseButton = new CoreChooseButton(this,coreChooseLore);
        addButton(coreChooseButton);

        Button distanceSetButton = new DistanceSetButton(this,nameLore);
        addButton(distanceSetButton);

        if (core_name.equalsIgnoreCase("DialogFlow")) {
            List<String> proIdSetLore = new ArrayList<>();
            proIdSetLore.add("Click to edit");
            Button proIdSetButton = new ProjectIdSetButton(this, proIdSetLore);
            addButton(proIdSetButton);
        } else if (!core_name.equals("未指定核心")) {
            List<String> lore = new ArrayList<>();
            lore.add("Click to edit");
            lore.add("Reading docs of ChatGPT before editing!");
            lore.add("String, default to null");
            Button suffixSetButton = new SuffixSetButton(this,lore);
            addButton(suffixSetButton);
            lore.remove(2);

            lore.add("int, default to 512");
            Button max_tokens_setButton = new Max_tokens_setButton(this,lore);
            addButton(max_tokens_setButton);
            lore.remove(2);

            lore.add("double, default to 1, range in 0.0-2.0");
            Button temperature_setButton = new TemperatureSetButton(this,lore);
            addButton(temperature_setButton);
            lore.remove(2);

            lore.add("int, default to 1");
            Button top_p_setButton = new Top_p_SetButton(this,lore);
            addButton(top_p_setButton);
            lore.remove(2);

            lore.add("int, default to 1");
            Button n_setButton = new N_SetButton(this,lore);
            addButton(n_setButton);
            lore.remove(2);

            lore.add("Integer, default to null");
            Button logprobs_setButton = new LogprobsSetButton(this,lore);
            addButton(logprobs_setButton);
            lore.remove(2);

            lore.add("double, default to 0.0");
            Button presence_penalty_setButton = new PresencePenaltySetButton(this,lore);
            addButton(presence_penalty_setButton);
            lore.remove(2);

            lore.add("double, default to 0.0");
            Button frequency_penalty_setButton = new FrequencyPenaltySetButton(this,lore);
            addButton(frequency_penalty_setButton);
            lore.remove(2);

            lore.add("int, default to 1");
            Button best_of_setButton = new Best_ofSetButton(this,lore);
            addButton(best_of_setButton);
            lore.remove(2);
        }

        Button dialogTimeSetButton = new DialogTimeSetButton(this,nameLore);
        addButton(dialogTimeSetButton);
        Button resetButton = new ResetButton(this);
        addButton(resetButton);

        Button deleteButton = new DeleteButton(this);
        addButton(deleteButton);
    }

    public void resetStaticMap(Player player) {
        managing.put(player, this);
    }


}
