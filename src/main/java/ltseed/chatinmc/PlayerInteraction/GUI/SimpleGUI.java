package ltseed.chatinmc.PlayerInteraction.GUI;

import ltseed.chatinmc.ChatInMC;
import ltseed.chatinmc.PlayerInteraction.GUI.FinalGUI.ChatterCreateGUI;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.*;

import static java.lang.Math.max;
import static ltseed.chatinmc.ChatInMC.tp;

public class SimpleGUI implements Listener {

    private final Inventory inventory;
    private final List<Button> buttons;
    private boolean clicked = false;

    protected SimpleGUI(String inventoryName, Map<Integer, Button> buttons) {
        this.buttons = new ArrayList<>(buttons.values());
        int inventorySize = 54;
        inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
        Bukkit.getPluginManager().registerEvents(this, tp);
    }

    public SimpleGUI() {
        inventory = null;
        buttons = null;
    }

    public void enableView() {
        clicked = false;
        setupButtons(buttons);
    }

    public void addButton(Button button){
        int slot = button.getSlot();
        if(slot > 53 || slot < -1) return;
        if(slot == -1) {
            int maxSlot = -1;
            for (Button button1 : buttons) {
                if(button1.getSlot()<45){
                    maxSlot = max(maxSlot,button1.getSlot());
                }
            }
            if(maxSlot == 44){
                ChatInMC.debug.err("Program went wrong in SimpleGUI.addButton");
            }
            button.resetSlot(maxSlot + 1);
            //现在它的Slot正常了
        }
        List<Button> delete = new ArrayList<>();
        for (Button button1 : buttons) {
            if(button1.getSlot() == slot) delete.add(button1);
        }
        delete.forEach(buttons::remove);
        buttons.add(button);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(clicked) return;
        Player player = (Player) event.getWhoClicked();
        if (event.getInventory().equals(inventory)) {
            event.setCancelled(true);
            int slot = event.getRawSlot();
            if (buttons.stream().anyMatch(button -> button.getSlot() == slot)) {
                buttons.stream().filter(button -> button.getSlot() == slot).forEach(button -> button.callOnce(player));
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                clicked = true;
            }
        }
    }

    protected void setupButtons(Collection<Button> buttons){
        for (Button button : buttons) {
            button.setupButton(inventory);
        }
    }

    public void open(Player player) {
        if(this instanceof ChatterCreateGUI && ChatterCreateGUI.creating.containsKey(player)){
            ((ChatterCreateGUI)this).update();
        }
        enableView();
        Bukkit.getScheduler().runTaskLater(tp, () -> player.openInventory(inventory), 5);
    }


}
