package ltseed.chatinmc.PlayerInteraction.GUI;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A paginated graphical user interface (GUI) for displaying multiple buttons on multiple pages.
 */
public class PaginatedGUI {

    private final String title;
    private final List<Button> allButtons;
    private final Map<Integer, List<Button>> pages;
    private final List<Button> otherButtons;
    private final int totalPages;

    private final ItemStack prevPageItem;
    private final ItemStack nextPageItem;

    /**
     * Constructs a new PaginatedGUI with the given title and buttons.
     *
     * @param title        the title of the GUI
     * @param allButtons   a list of all buttons to be displayed
     * @param otherButtons a list of additional buttons to be displayed on every page
     */
    public PaginatedGUI(String title, List<Button> allButtons, List<Button> otherButtons) {
        this.title = title;
        this.allButtons = allButtons;
        this.pages = new HashMap<>();
        this.totalPages = (int) Math.ceil((double) allButtons.size() / 45);

        this.prevPageItem = new ItemStack(Material.ARROW);
        ItemMeta prevPageItemMeta = prevPageItem.getItemMeta();
        assert prevPageItemMeta != null;
        prevPageItemMeta.setDisplayName(ChatColor.GREEN + "上一页");
        prevPageItem.setItemMeta(prevPageItemMeta);

        this.nextPageItem = new ItemStack(Material.ARROW);
        ItemMeta nextPageItemMeta = nextPageItem.getItemMeta();
        assert nextPageItemMeta != null;
        nextPageItemMeta.setDisplayName(ChatColor.GREEN + "下一页");
        nextPageItem.setItemMeta(nextPageItemMeta);

        this.otherButtons = otherButtons;

        setupPages();
    }

    /**
     * Splits the list of all buttons into pages of 45 buttons each and stores them in the pages map.
     */
    private void setupPages() {
        int currentPage = 1;
        List<Button> currentPageButtons = new ArrayList<>();

        for (Button button : allButtons) {
            if (currentPageButtons.size() == 45) {
                pages.put(currentPage, currentPageButtons);
                currentPage++;
                currentPageButtons = new ArrayList<>();
            }
            currentPageButtons.add(button);
        }
        if (!currentPageButtons.isEmpty()) {
            pages.put(currentPage, currentPageButtons);
        }
    }

    /**
     * Opens the GUI for the specified player on the specified page.
     *
     * @param player the player to open the GUI for
     * @param page   the page to open the GUI on
     */
    public void open(Player player, int page) {
        SimpleGUI simpleGUI = new SimpleGUI(title, new HashMap<>());
        List<Button> pageButtons = pages.get(page);

        if (pageButtons != null) {
            for (Button button : pageButtons) {
                simpleGUI.addButton(button);
            }
        }

        if (page > 1) {
            simpleGUI.addButton(new Button(45, prevPageItem) {
                @Override
                public void call(Player p) {
                    open(p, page - 1);
                }
            });
        }

        if (otherButtons != null && otherButtons.size() <= 7) {
            for (Button otherButton : otherButtons) {
                if (otherButton.getSlot() < 53 && otherButton.getSlot() > 45)
                    simpleGUI.addButton(otherButton);
            }
        }

        if (page < totalPages) {
            simpleGUI.addButton(new Button(53, nextPageItem) {
                @Override
                public void call(Player p) {
                    open(p, page + 1);
                }
            });
        }

        simpleGUI.open(player);
    }
}
