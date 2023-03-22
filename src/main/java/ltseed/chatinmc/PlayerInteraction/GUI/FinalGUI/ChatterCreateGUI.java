package ltseed.chatinmc.PlayerInteraction.GUI.FinalGUI;

import lombok.Getter;
import lombok.Setter;
import ltseed.chatinmc.PlayerInteraction.GUI.AbstractGUI.GUIWithChatterInformation;
import ltseed.chatinmc.PlayerInteraction.GUI.Button;
import ltseed.chatinmc.PlayerInteraction.GUI.EnabledView;
import ltseed.chatinmc.PlayerInteraction.GUI.FinalButton.*;
import ltseed.chatinmc.PlayerInteraction.GUI.FinalButton.ChatterSetButtons.*;
import ltseed.chatinmc.PlayerInteraction.GUI.FinalButton.ChatterSetButtons.GPTSetButtons.*;
import ltseed.chatinmc.PlayerInteraction.GUI.FinalButton.ChatterSetButtons.GPTSetButtons.BasicTrainSet.BasicTrainEditButton;
import ltseed.chatinmc.PlayerInteraction.GUI.SimpleGUI;
import ltseed.chatinmc.Talker.Chatter;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * A GUI for creating a new {@link Chatter}. This GUI is used to gather information
 * <p>
 * from the player about the new Chatter that they want to create.
 * <p>
 * It extends {@link SimpleGUI} and is annotated with {@link EnabledView}.
 *
 * @author LtSeed
 * @version 1.0
 */
@EnabledView
@Getter
@Setter
public class ChatterCreateGUI extends GUIWithChatterInformation {

    /**
     * A map of player instances that are creating a new Chatter and their associated
     * {@link ChatterCreateGUI} instances.
     */
    public static final Map<Player, ChatterCreateGUI> creating = new HashMap<>();



    /**
     * Constructs a new {@link ChatterCreateGUI} instance for the specified player.
     * The GUI is opened immediately and added to the {@link #creating} map.
     *
     * @param player The player to create the GUI for
     */
    private ChatterCreateGUI(Player player) {
        super("新建一个Chatter！", new HashMap<>());
        update();
        open(player);
        creating.put(player, this);
    }

    ChatterCreateGUI() {
        super();
    }


    public void resetStaticMap(Player player) {
        ChatterCreateGUI.creating.put(player, this);
    }

    /**
     * Returns a singleton instance of the {@link ChatterCreateGUI} for the specified player.
     * If the player does not already have a {@link ChatterCreateGUI} instance, a new one is created.
     *
     * @param player The player to get the {@link ChatterCreateGUI} instance for
     * @return The {@link ChatterCreateGUI} instance for the player
     */
    public static ChatterCreateGUI getGUIInstance(Player player) {
        ChatterCreateGUI orDefault = creating.getOrDefault(player, new ChatterCreateGUI(player));
        orDefault.update();
        return orDefault;
    }

    /**
     * Splits a given string into substrings of maximum length 10 and returns them in a list.
     *
     * @param input the string to be split
     * @return a list of substrings of maximum length 10
     */
    public static List<String> splitString(String input) {
        List<String> result = new ArrayList<>();
        int startIndex = 0;
        int endIndex = Math.min(input.length(), 10);
        while (startIndex < input.length()) {
            String substring = input.substring(startIndex, endIndex);
            result.add(substring);
            startIndex = endIndex;
            endIndex = Math.min(startIndex + 10, input.length());
        }
        return result;
    }

    /**
     * Updates the buttons in the GUI with the latest information.
     */
    public void update() {
        List<String> nameLore = new ArrayList<>();
        nameLore.add("Click to edit");
        Button nameButton = new NameSetButton(this, nameLore);
        addButton(nameButton);

        List<String> descLore = new ArrayList<>();
        descLore.add("Click to edit");
        Button descButton = new DescriptionSetButton(this, descLore);
        addButton(descButton);


        List<String> entityChooseLore = new ArrayList<>();
        entityChooseLore.add("Click to edit");
        if (material == null) material = Material.COW_SPAWN_EGG;
        Button entityChooseButton = new EntityChooseButton(this, entityChooseLore);
        addButton(entityChooseButton);


        List<String> coreChooseLore = new ArrayList<>();
        coreChooseLore.add("Click to edit");

        Button coreChooseButton = new CoreChooseButton(this, coreChooseLore);
        addButton(coreChooseButton);

        List<String> basicTrainEditLore = new ArrayList<>();
        basicTrainEditLore.add("Click to edit");
        basicTrainEditLore.addAll(basicTrain);
        Button basicTrainEditButton = new BasicTrainEditButton(this, basicTrainEditLore);
        addButton(basicTrainEditButton);

        Button distanceSetButton = new DistanceSetButton(this, nameLore);
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
            Button suffixSetButton = new SuffixSetButton(this, lore);
            addButton(suffixSetButton);
            lore.remove(2);

            lore.add("int, default to 512");
            Button max_tokens_setButton = new Max_tokens_setButton(this, lore);
            addButton(max_tokens_setButton);
            lore.remove(2);

            lore.add("double, default to 1, range in 0.0-2.0");
            Button temperature_setButton = new TemperatureSetButton(this, lore);
            addButton(temperature_setButton);
            lore.remove(2);

            lore.add("int, default to 1");
            Button top_p_setButton = new Top_p_SetButton(this, lore);
            addButton(top_p_setButton);
            lore.remove(2);

            lore.add("int, default to 1");
            Button n_setButton = new N_SetButton(this, lore);
            addButton(n_setButton);
            lore.remove(2);

            lore.add("Integer, default to null");
            Button logprobs_setButton = new LogprobsSetButton(this, lore);
            addButton(logprobs_setButton);
            lore.remove(2);

            lore.add("double, default to 0.0");
            Button presence_penalty_setButton = new PresencePenaltySetButton(this, lore);
            addButton(presence_penalty_setButton);
            lore.remove(2);

            lore.add("double, default to 0.0");
            Button frequency_penalty_setButton = new FrequencyPenaltySetButton(this, lore);
            addButton(frequency_penalty_setButton);
            lore.remove(2);

            lore.add("int, default to 1");
            Button best_of_setButton = new Best_ofSetButton(this, lore);
            addButton(best_of_setButton);
            lore.remove(2);
        }

        Button dialogTimeSetButton = new DialogTimeSetButton(this, nameLore);
        addButton(dialogTimeSetButton);
        Button createButton = new CreateButton(this);
        if (!Objects.equals(choose_entity, "未选择实体") && !Objects.equals(name, "Name") && !Objects.equals(description, "Description") && material != null && core != null)
            addButton(createButton);

        Button cancelButton = new CancelButton();
        addButton(cancelButton);
    }


}
