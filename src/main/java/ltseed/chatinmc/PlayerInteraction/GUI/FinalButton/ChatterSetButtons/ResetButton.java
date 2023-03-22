package ltseed.chatinmc.PlayerInteraction.GUI.FinalButton.ChatterSetButtons;

import ltseed.Exception.InvalidChatterException;
import ltseed.chatinmc.ChatInMC;
import ltseed.chatinmc.PlayerInteraction.GUI.Button;
import ltseed.chatinmc.PlayerInteraction.GUI.FinalGUI.ChatterManageGUI;
import ltseed.chatinmc.Talker.ChatGPT.ChatGPTBuilder;
import ltseed.chatinmc.Talker.Chatter;
import ltseed.chatinmc.Talker.MessageBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

import static ltseed.chatinmc.Talker.Chatter.getMessageBuilder;

public class ResetButton extends Button {
    private final ChatterManageGUI chatterManageGUI;

    public ResetButton(ChatterManageGUI chatterManageGUI) {
        super((short) 5, (short) 3, Material.GREEN_WOOL, "Reset", null);
        this.chatterManageGUI = chatterManageGUI;
    }

    @Override
    public void call(Player player) {
        if (!Objects.equals(chatterManageGUI.getChoose_entity(), "未选择实体")
                && !Objects.equals(chatterManageGUI.getName(), "Name")
                && !Objects.equals(chatterManageGUI.getDescription(), "Description")
                && chatterManageGUI.getMaterial() != null
                && chatterManageGUI.getCore_name() != null) {
            Chatter chatter = new Chatter();
            UUID uuid = UUID.fromString(chatterManageGUI.getChoose_entity());
            chatter.setUuid(uuid);
            chatter.getEntity();
            chatter.setTalk_distance(chatterManageGUI.getTalkDistance());
            chatter.setDialogTime(chatterManageGUI.getDur());
            chatter.setName(chatterManageGUI.getName());
            chatter.setDescription(chatterManageGUI.getDescription());
            try {
                MessageBuilder core1 = getMessageBuilder(chatterManageGUI.getDur(),
                        chatterManageGUI.getCore_name(), chatterManageGUI.getProjectId());
                if (core1 instanceof ChatGPTBuilder) {
                    ChatGPTBuilder gptBuilder = (ChatGPTBuilder) core1;
                    gptBuilder.setBest_of(chatterManageGUI.getBest_of());
                    gptBuilder.setFrequency_penalty(chatterManageGUI.getFrequency_penalty());
                    gptBuilder.setLogprobs(chatterManageGUI.getLogprobs());
                    gptBuilder.setMax_tokens(chatterManageGUI.getMax_tokens());
                    gptBuilder.setN(chatterManageGUI.getN());
                    if (chatterManageGUI.getSuffix() != null && !chatterManageGUI.getSuffix().equals("默认值"))
                        gptBuilder.setSuffix(chatterManageGUI.getSuffix());
                    gptBuilder.setPresence_penalty(chatterManageGUI.getPresence_penalty());
                    gptBuilder.setTop_p(chatterManageGUI.getTop_p());
                    gptBuilder.setTemperature(chatterManageGUI.getTemperature());
                    gptBuilder.setBasicTrain(chatterManageGUI.getBasicTrain());
                    chatter.setCore(gptBuilder);
                } else chatter.setCore(core1);
            } catch (InvalidChatterException e) {
                ChatInMC.debug.err(e.getLocalizedMessage());
            }
            ChatInMC.chatters.put(uuid, chatter);
            player.closeInventory();
            player.sendMessage("chatter 成功修改！");
        }
    }
}
