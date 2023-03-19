package ltseed.chatinmc.PlayerInteraction.GUI;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static ltseed.chatinmc.ChatInMC.ts;

/**
 This class represents a clickable inventory item that can be used to trigger a specific action when clicked by a player.

 The class provides methods to create an ItemStack representing the item, set the display name, lore, material, and skull owner.

 It also provides a method to reset the item's slot position in the inventory and a method to trigger the item's action when clicked.

 The class uses the Bukkit API to create and modify ItemStacks and to handle player interactions.
 @author LtSeed
 @version 1.0
 */
public abstract class Button {

    private short x; // The x coordinate of the button in the GUI
    private short y; // The y coordinate of the button in the GUI
    private final Material material; // The material of the button
    private final String displayName; // The display name of the button
    private final List<String> lore; // The lore of the button
    private final UUID skullOwner; // The UUID of the skull owner for a skull button
    private final String texture; // The texture URL for a custom skull button
    boolean pressed = false; // Whether the button has been pressed or not

    /**

     Constructs a button with the given coordinates, material, display name, and lore.
     @param x the x coordinate of the button
     @param y the y coordinate of the button
     @param material the material of the button
     @param displayName the display name of the button
     @param lore the lore of the button
     */
    public Button(short x, short y, Material material, String displayName, List<String> lore) {
        this.x = x;
        this.y = y;
        this.material = material;
        this.displayName = displayName;
        this.lore = lore;
        this.skullOwner = null;
        this.texture = null;
    }

    /**

     Constructs a button with the given slot, material, display name, and lore.
     @param slot the slot in the inventory where the button should be placed
     @param material the material of the button
     @param displayName the display name of the button
     @param lore the lore of the button
     */
    public Button(int slot, Material material, String displayName, List<String> lore) {
        if(slot == -1){
            this.x = (short) 0;
            this.y = (short) 1;
        } else {
            this.x = (short) (slot % 9);
            this.y = (short) ((short) (slot - this.x) / 9);
            x++;y++;
        }
        this.material = material;
        this.displayName = displayName;
        this.lore = lore;
        this.skullOwner = null;
        this.texture = null;
    }

    /**

     Constructs a button with the given slot and item stack.
     @param slot the slot in the inventory where the button should be placed
     @param is the item stack of the button
     */
    public Button(int slot, ItemStack is) {
        if(slot == -1){
            this.x = (short) 0;
            this.y = (short) 1;
        } else {
            this.x = (short) (slot % 9);
            this.y = (short) ((short) (slot - this.x) / 9);
            x++;y++;
        }
        this.material = is.getType();
        this.displayName = Objects.requireNonNull(is.getItemMeta()).getDisplayName();
        this.lore = is.getItemMeta().getLore();
        this.skullOwner = null;
        this.texture = null;
    }

    /**

     Returns the slot of the button in the GUI.
     @return the slot of the button
     */
    public int getSlot(){
        return (y-1) * 9 + (x-1);
    }

    /**

     Sets up the button in the given inventory.
     @param inventory the inventory where the button should be placed
     */
    public void setupButton(Inventory inventory){
        if(getSlot() != -1)
            inventory.setItem(getSlot(), toItemStack());
        else inventory.addItem(toItemStack());
    }

    /**

     Converts the ClickableItem instance to an ItemStack.
     @return an ItemStack representing the ClickableItem
     */
    public ItemStack toItemStack() {
        ItemStack itemStack;
        if (skullOwner != null && texture != null) {
            itemStack = createSkullItemStack(skullOwner, displayName, lore, texture);
        } else {
            itemStack = new ItemStack(material);
            ItemMeta meta = itemStack.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(displayName);
                meta.setLore(lore);
            }
            itemStack.setItemMeta(meta);
        }
        return itemStack;
    }

    /**

     Reads the texture file and encodes it to Base64.
     @param imageFile The file containing the image to read and encode.
     @return A Base64 encoded string representation of the image.
     */
    @SuppressWarnings("unused")
    private String readTexture(File imageFile){
        String base64 = null;
        try {
            BufferedImage image = ImageIO.read(imageFile);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", outputStream);
            byte[] imageBytes = outputStream.toByteArray();
            base64 = Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64;
    }

    /**

     Creates a skull item stack with the given parameters.
     @param owner The UUID of the skull's owner.
     @param displayName The display name of the skull item stack.
     @param lore The lore of the skull item stack.
     @param texture The Base64 encoded texture of the skull item stack.
     @return An ItemStack representing the skull item stack.
     */
    private ItemStack createSkullItemStack(UUID owner, String displayName, List<String> lore, String texture) {
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        if (skullMeta != null) {
            skullMeta.setOwningPlayer(ts.getOfflinePlayer(owner));
        } else return null;
        skullMeta.setDisplayName(displayName);
        skullMeta.setLore(lore);

        if (texture != null && !texture.isEmpty()) {
            GameProfile profile = new GameProfile(owner, null);
            profile.getProperties().put("textures", new Property("textures", texture));
            try {
                Field profileField = skullMeta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(skullMeta, profile);
            } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        itemStack.setItemMeta(skullMeta);
        return itemStack;
    }

    /**

     What to do when button is pressed.
     @param player the called player
     */
    public abstract void call(Player player);

    /**

     Resets the slot position of the item in the inventory to the specified position.
     @param slot the slot position of the item in the inventory
     */
    public void resetSlot(int slot) {
        if(getSlot() == -1){
            if(slot == -1){
                this.x = (short) 0;
                this.y = (short) 1;
            } else {
                this.x = (short) ((short) (slot % 9));
                this.y = (short) ((short) (slot - this.x) / 9);
                x++;y++;
            }
        }
    }

    /**

     What to do when button is pressed.
     This method only call once.
     @param player the called player
     */
    public void callOnce(Player player) {
        if(pressed) return;
        pressed = true;
        call(player);
    }
}
