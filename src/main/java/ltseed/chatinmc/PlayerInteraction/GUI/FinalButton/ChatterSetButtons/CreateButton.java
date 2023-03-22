package ltseed.chatinmc.PlayerInteraction.GUI.FinalButton.ChatterSetButtons;

import lombok.Getter;
import lombok.Setter;
import ltseed.Exception.InvalidChatterException;
import ltseed.chatinmc.ChatInMC;
import ltseed.chatinmc.PlayerInteraction.GUI.AbstractGUI.GUIWithChatterInformation;
import ltseed.chatinmc.PlayerInteraction.GUI.Button;
import ltseed.chatinmc.Talker.ChatGPT.ChatGPTBuilder;
import ltseed.chatinmc.Talker.Chatter;
import ltseed.chatinmc.Talker.MessageBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

import static ltseed.chatinmc.Talker.Chatter.getMessageBuilder;

@Setter
@Getter
public class CreateButton extends Button {
    private final GUIWithChatterInformation gui;

    public CreateButton(GUIWithChatterInformation gui) {
        super((short) 5, (short) 3, Material.GREEN_WOOL, "Create", null);
        this.gui = gui;
    }

    @Override
    public void call(Player player) {
        if (!Objects.equals(gui.getChoose_entity(), "未选择实体")
                && !Objects.equals(gui.getName(), "Name")
                && !Objects.equals(gui.getDescription(), "Description")
                && gui.getMaterial() != null
                && gui.getCore() != null) {
            Chatter chatter = new Chatter();
            UUID uuid = UUID.fromString(gui.getChoose_entity());
            chatter.setUuid(uuid);
            chatter.getEntity();
            chatter.setTalk_distance(gui.getTalkDistance());
            chatter.setDialogTime(gui.getDur());
            chatter.setName(gui.getName());
            chatter.setDescription(gui.getDescription());
            try {
                MessageBuilder core1 = getMessageBuilder(gui.getDur(),
                        gui.getCore_name(),
                        gui.getProjectId());
                if (core1 instanceof ChatGPTBuilder) {
                    ChatGPTBuilder gptBuilder = (ChatGPTBuilder) core1;
                    gptBuilder.setBest_of(gui.getBest_of());
                    gptBuilder.setFrequency_penalty(gui.getFrequency_penalty());
                    gptBuilder.setLogprobs(gui.getLogprobs());
                    gptBuilder.setMax_tokens(gui.getMax_tokens());
                    gptBuilder.setN(gui.getN());
                    if (!gui.getSuffix().equals("默认值"))
                        gptBuilder.setSuffix(gui.getSuffix());
                    gptBuilder.setPresence_penalty(gui.getPresence_penalty());
                    gptBuilder.setTop_p(gui.getTop_p());
                    gptBuilder.setTemperature(gui.getTemperature());
                    gptBuilder.setBasicTrain(gui.getBasicTrain());
                    chatter.setCore(gptBuilder);
                } else chatter.setCore(core1);
            } catch (InvalidChatterException e) {
                ChatInMC.debug.err(e.getLocalizedMessage());
            }
            ChatInMC.chatters.put(uuid, chatter);
            player.closeInventory();
            player.sendMessage("chatter 成功创建！");
        } else {
            gui.open(player);
        }
    }
}
