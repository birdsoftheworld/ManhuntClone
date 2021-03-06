package birds.manhuntclone.ManhuntClone.menu;

import birds.manhuntclone.ManhuntClone.ManhuntClone;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class InventoryPage implements InventoryHolder, Listener {
    Inventory page;
    protected ManhuntClone plugin;

    InventoryPage(ManhuntClone plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return; // make sure they're a player
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        Inventory inventory = event.getInventory();
        int slot = event.getRawSlot();

        if(inventory.getHolder() != this) return; // make sure they're clicking in this inventory
        if(slot == -999) return; // make sure they clicked in the inventory
        if(item == null || item.getType().equals(Material.AIR)) return; // make sure they clicked an item

        if (event.getClick().equals(ClickType.NUMBER_KEY)) {
            event.setCancelled(true); // cancel number
        }

        event.setCancelled(true); // cancel item pickup

        runSlotAction(slot, item, player, event.getClick());
    }

    public abstract void runSlotAction(int slot, ItemStack item, Player player, ClickType click);
    public abstract void setUpSlots();

    public void showInventory(Player player) {
        // open the inventory
        player.openInventory(page);
    }

    ItemStack createGuiItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);

        ArrayList<String> metaLore = new ArrayList<>(Arrays.asList(lore));

        meta.setLore(metaLore);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public Inventory getInventory() {
        return page;
    }
}
