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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.lang.Math.max;
import static ltseed.chatinmc.ChatInMC.tp;

/**
 * This class represents a simple GUI with clickable buttons that perform actions when clicked.
 * It implements the Listener interface to handle events related to inventory clicks.
 * The GUI is constructed by providing a name for the inventory and a map of buttons with their respective slots.
 * The maximum inventory size is 54, and buttons can be added or removed dynamically using the addButton() method.
 * When the GUI is opened using the open() method, the enableView() method is called to set up the buttons and register
 * the GUI as an event listener.
 * When a button is clicked, the onInventoryClick() method is called to check if the clicked slot corresponds to a button,
 * and if so, the corresponding action is executed. The button can only be clicked once per open session.
 * This class also provides a constructor with no parameters for use by subclasses.
 *
 * @author LtSeed
 * @version 1.0
 */
public class SimpleGUI implements Listener {

    private final Inventory inventory;
    private final List<Button> buttons;
    //private boolean clicked = false;


    /**
     * Constructs a SimpleGUI object with the given name and buttons.
     * The inventory size is set to 54 by default.
     * The Bukkit plugin manager is used to register this object as a listener.
     *
     * @param inventoryName the name of the inventory
     * @param buttons       a map of buttons with their respective slot numbers
     */
    protected SimpleGUI(String inventoryName, Map<Integer, Button> buttons) {
        this.buttons = new ArrayList<>(buttons.values());
        int inventorySize = 54;
        inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
        Bukkit.getPluginManager().registerEvents(this, tp);
    }

    /**
     * Constructs a SimpleGUI object with no inventory and buttons.
     */
    public SimpleGUI() {
        inventory = null;
        buttons = null;
    }

    /**
     * Enables the view of the GUI by setting up the buttons.
     * This method should be called before opening the inventory.
     */
    public void enableView() {
        //clicked = false;
        setupButtons(buttons);
    }

    /**
     * Adds a button to the GUI.
     * If the slot number is out of range, the button is not added.
     * If the slot number is -1, the button is assigned a slot number
     * that is one higher than the highest existing slot number.
     *
     * @param button the button to add
     */
    public void addButton(Button button) {
        int slot = button.getSlot();
        if (slot > 53 || slot < -1) return;
        if (slot == -1) {
            int maxSlot = -1;
            for (Button button1 : buttons) {
                if (button1.getSlot() < 45) {
                    maxSlot = max(maxSlot, button1.getSlot());
                }
            }
            if (maxSlot == 44) {
                ChatInMC.debug.err("Program went wrong in SimpleGUI.addButton");
            }
            button.resetSlot(maxSlot + 1);
            //现在它的Slot正常了
        }
        List<Button> delete = new ArrayList<>();
        for (Button button1 : buttons) {
            if (button1.getSlot() == slot) delete.add(button1);
        }
        delete.forEach(buttons::remove);
        buttons.add(button);
    }

    /**
     * Handles the inventory click event.
     * When a player clicks a button in the inventory, the button's action is executed.
     * The event is cancelled to prevent the default inventory behavior.
     * The player hears a sound effect when the button is clicked.
     *
     * @param event the inventory click event
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        //if(clicked) return;
        Player player = (Player) event.getWhoClicked();
        if (event.getInventory().equals(inventory)) {
            event.setCancelled(true);
            int slot = event.getRawSlot();
            if (buttons.stream().anyMatch(button -> button.getSlot() == slot)) {
                buttons.stream().filter(button -> button.getSlot() == slot).forEach(button -> button.callOnce(player));
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                //clicked = true;
            }
        }
    }

    /**
     * Sets up the buttons in the inventory.
     *
     * @param buttons The collection of buttons to be set up.
     */
    protected void setupButtons(Collection<Button> buttons) {
        for (Button button : buttons) {
            button.setupButton(inventory);
        }
    }

    /**
     * Opens the inventory for the specified player.
     *
     * @param player The player who will see the inventory.
     */
    public void open(Player player) {
        if (this instanceof ChatterCreateGUI && ChatterCreateGUI.creating.containsKey(player)) {
            ((ChatterCreateGUI) this).update();
        }
        enableView();
        Bukkit.getScheduler().runTaskLater(tp, () -> player.openInventory(inventory), 5);
    }


}
