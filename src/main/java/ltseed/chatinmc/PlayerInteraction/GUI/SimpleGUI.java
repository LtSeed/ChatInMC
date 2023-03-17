package ltseed.chatinmc.PlayerInteraction.GUI;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.*;

import static ltseed.chatinmc.ChatInMC.tp;

public class SimpleGUI implements Listener {

    private final Inventory inventory;
    private final Map<Integer, Button> buttons;

    protected SimpleGUI(String inventoryName, Map<Integer, Button> buttons) {
        this.buttons = buttons;
        int inventorySize = 54;
        inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
    }

    public void enableView() {
        Bukkit.getPluginManager().registerEvents(this, tp);
        setupButtons(buttons.values());
    }

    public void addButton(Button button){
        int slot = button.getSlot();
        if(slot > 53 || slot < 0) return;
        buttons.put(slot,button);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getInventory().equals(inventory)) {
            event.setCancelled(true);
            int slot = event.getRawSlot();
            if (buttons.containsKey(slot)) {
                buttons.get(slot).call(player);
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            }
        }
    }

    protected void setupButtons(Collection<Button> buttons){
        for (Button button : buttons) {
            button.setupButton(inventory);
        }
    }

    public void open(Player player) {
        enableView();
        player.openInventory(inventory);
    }


}
