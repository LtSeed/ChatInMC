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
import java.util.function.Consumer;

import static ltseed.chatinmc.ChatInMC.ts;

public abstract class Button {

    private final short x,y;
    private final Material material;
    private final String displayName;
    private final List<String> lore;
    private final UUID skullOwner;
    private final String texture;

    public Button(short x, short y, Material material, String displayName, List<String> lore) {
        this.x = x;
        this.y = y;
        this.material = material;
        this.displayName = displayName;
        this.lore = lore;
        this.skullOwner = null;
        this.texture = null;
    }

    public Button(int slot, Material material, String displayName, List<String> lore) {
        this.x = (short) (slot % 9);
        this.y = (short) ((short) (slot - this.x) / 9);
        this.material = material;
        this.displayName = displayName;
        this.lore = lore;
        this.skullOwner = null;
        this.texture = null;
    }

    public Button(short x, short y, String displayName, List<String> lore, File texture) {
        this.x = x;
        this.y = y;
        this.material = Material.PLAYER_HEAD;
        this.displayName = displayName;
        this.lore = lore;
        this.skullOwner = UUID.randomUUID();
        this.texture = readTexture(texture);
    }

    public Button(int slot, ItemStack is) {
        this.x = (short) (slot % 9);
        this.y = (short) ((short) (slot - this.x) / 9);
        this.material = is.getType();
        this.displayName = Objects.requireNonNull(is.getItemMeta()).getDisplayName();
        this.lore = is.getItemMeta().getLore();
        this.skullOwner = null;
        this.texture = null;
    }

    public int getSlot(){
        return (y-1) * 9 + (x-1);
    }

    public void setupButton(Inventory inventory){
        inventory.setItem(getSlot(), toItemStack());
    }

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

    public abstract void call(Player player);
}
