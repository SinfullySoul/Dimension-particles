package me.___soul_.techp;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class createitem {

    private static Field profileField;

    public static ItemStack setSkull(String url, List<String> lore, String itemname) {

        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) item.getItemMeta();

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] data = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(data)));

        try {
            if (profileField == null) {
                profileField = meta.getClass().getDeclaredField("profile");
            }
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', itemname));
            meta.setLore(lore);
            meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
            profileField.setAccessible(true);
            profileField.set(meta, profile);

            item.setItemMeta(meta);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return item;
    }
    public static ShapedRecipe recipesetup(ItemStack item){
        startup plugin = startup.getPlugin(startup.class);

        ShapedRecipe recipe = new ShapedRecipe(item);

        String I = plugin.getConfig().getString("recipe.TL");
        String O = plugin.getConfig().getString("recipe.TM");
        String P = plugin.getConfig().getString("recipe.TR");
        String J = plugin.getConfig().getString("recipe.ML");
        String K = plugin.getConfig().getString("recipe.MM");
        String L = plugin.getConfig().getString("recipe.MR");
        String B = plugin.getConfig().getString("recipe.BL");
        String N = plugin.getConfig().getString("recipe.BM");
        String M = plugin.getConfig().getString("recipe.BR");

        recipe.shape("IOP", "JKL", "BNM");

        //top items\\
        recipe.setIngredient('I', Material.getMaterial(I));
        recipe.setIngredient('O', Material.getMaterial(O));
        recipe.setIngredient('P', Material.getMaterial(P));
        //=-----------------------=\\

        //middle items\\
        recipe.setIngredient('J', Material.getMaterial(J));
        recipe.setIngredient('K', Material.getMaterial(K));
        recipe.setIngredient('L', Material.getMaterial(L));
        //=-----------------------=\\

        //middle items\\
        recipe.setIngredient('B', Material.getMaterial(B));
        recipe.setIngredient('N', Material.getMaterial(N));
        recipe.setIngredient('M', Material.getMaterial(M));
        //=-----------------------=\\
        plugin.setrecipe(recipe);
        return recipe;
    }
}
