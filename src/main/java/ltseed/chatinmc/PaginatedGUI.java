package ltseed.chatinmc;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaginatedGUI {

    private final String title;
    private final List<Button> allButtons;
    private final Map<Integer, List<Button>> pages;
    private final int totalPages;

    private final ItemStack prevPageItem;
    private final ItemStack nextPageItem;

    PaginatedGUI(String title, List<Button> allButtons) {
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

        setupPages();
    }

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

    public void open(Player player, int page) {
        SimpleGUI simpleGUI = new SimpleGUI(title, new HashMap<>());
        List<Button> pageButtons = pages.get(page);

        if (pageButtons == null) {
            player.sendMessage(ChatColor.RED + "无效的页码！");
            ChatInMC.debug.err("无效的页码！");
            return;
        }

        int slot = 0;
        for (Button button : pageButtons) {
            simpleGUI.addButton(button);
            slot++;
        }

        if (page > 1) {
            simpleGUI.addButton(new Button(45, prevPageItem) {
                @Override
                public void call(Player p) {
                    open(p, page - 1);
                }
            });
        }

        if (page < totalPages) {
            simpleGUI.addButton(new Button(45, nextPageItem) {
                @Override
                public void call(Player p) {
                    open(p, page + 1);
                }
            });
        }

        simpleGUI.open(player);
    }
}
