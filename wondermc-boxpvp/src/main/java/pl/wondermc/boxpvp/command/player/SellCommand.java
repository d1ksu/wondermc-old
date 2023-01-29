package pl.wondermc.boxpvp.command.player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.wondermc.boxpvp.command.framework.Command;
import pl.wondermc.boxpvp.command.framework.CommandArgs;
import pl.wondermc.boxpvp.configuration.CommandConfiguration;
import pl.wondermc.boxpvp.helper.ChatHelper;
import pl.wondermc.boxpvp.helper.ItemHelper;
import pl.wondermc.boxpvp.item.SellItem;

import java.util.ArrayList;
import java.util.List;

public class SellCommand {

    private final CommandConfiguration commandConfiguration;

    public SellCommand(CommandConfiguration commandConfiguration) {
        this.commandConfiguration = commandConfiguration;
    }

    @Command(name = "sellall", permission = "wondermc.player", inGameOnly = true)
    public void execute(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();
        final List<SellItem> sellableItems = this.commandConfiguration.getSellableItemsList();
        int itemAmount = 0;
        for (SellItem sellItem : sellableItems) {
            ItemStack item = new ItemStack(sellItem.getMaterial());
            int requiredAmount = sellItem.getBuyAmount();
            int playerAmount = ItemHelper.getItemAmountInInventory(player, item);
            if (playerAmount < requiredAmount) {
                continue;
            }
            itemAmount = playerAmount / requiredAmount;

            ItemHelper.removeItem(player, item, requiredAmount * itemAmount);
            ItemHelper.addItem(player,  this.reward(sellItem.getSellAmount() * itemAmount));
            player.showTitle(Title.title(Component.text(StringUtils.EMPTY),
                    Component.text(ChatHelper.fixColor(this.commandConfiguration.getSucessfullySold()))));

        }
        if(itemAmount <= 0){
            player.showTitle(Title.title(Component.text(StringUtils.EMPTY),
                    Component.text(ChatHelper.fixColor(this.commandConfiguration.getNothingToSell()))));
        }

    }

    private ItemStack reward(int amount) {
        ItemStack reward = new ItemStack(this.commandConfiguration.getSellItemMaterial(), amount);
        ItemMeta rewardMeta = reward.getItemMeta();
        rewardMeta.displayName(Component.text(ChatHelper.fixColor(this.commandConfiguration.getSellItemName())));
        List<Component> components = new ArrayList<>();
        for (String text : this.commandConfiguration.getSellItemLore()) {
            components.add(Component.text(ChatHelper.fixColor(text)));
        }
        rewardMeta.lore(components);
        rewardMeta.addEnchant(this.commandConfiguration.getSellItemEnchant(),
                this.commandConfiguration.getSellItemEnchantLevel(), true);
        reward.setItemMeta(rewardMeta);
        return reward;
    }
}
