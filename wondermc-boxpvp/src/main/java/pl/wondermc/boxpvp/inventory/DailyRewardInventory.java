package pl.wondermc.boxpvp.inventory;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.wondermc.api.helper.TimeHelper;
import pl.wondermc.boxpvp.configuration.DailyRewardConfiguration;
import pl.wondermc.boxpvp.database.DatabaseProvider;
import pl.wondermc.boxpvp.helper.ChatHelper;
import pl.wondermc.boxpvp.reward.DailyReward;
import pl.wondermc.boxpvp.reward.DailyRewardService;
import pl.wondermc.boxpvp.user.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DailyRewardInventory {

    private final DailyRewardConfiguration dailyRewardConfiguration;
    private final DailyRewardService dailyRewardService;
    private final DatabaseProvider databaseProvider;

    public DailyRewardInventory(DailyRewardConfiguration dailyRewardConfiguration,
                                DailyRewardService dailyRewardService,
                                DatabaseProvider databaseProvider) {
        this.dailyRewardConfiguration = dailyRewardConfiguration;
        this.dailyRewardService = dailyRewardService;
        this.databaseProvider = databaseProvider;
    }

    public void open(Player player, User user){
        Gui gui = Gui
                .gui()
                .rows(dailyRewardConfiguration.getInventoryRows())
                .title(Component.text(ChatHelper.fixColor(dailyRewardConfiguration.getInventoryName())))
                .create();
        int[] pinkGlassSlots = {0,2,4,6,8,18,26, 36, 46, 48, 50, 52, 44};
        int[] whiteGlassSlots = {1,3,5,7,9,17, 35, 27, 45, 47, 49, 51, 53};
        GuiItem whiteGlass = ItemBuilder.from(Material.WHITE_STAINED_GLASS_PANE).asGuiItem();
        GuiItem pinkGlass = ItemBuilder.from(Material.MAGENTA_STAINED_GLASS_PANE).asGuiItem();
        for(int i : pinkGlassSlots){
            gui.setItem(i, pinkGlass);
        }
        for(int i : whiteGlassSlots){
            gui.setItem(i, whiteGlass);
        }
        List<Component> components = new ArrayList<>();
        dailyRewardConfiguration.getItemInfoLore().forEach(text -> {
            components.add(Component.text(ChatHelper.fixColor(text)));
        });

        GuiItem infoItem = ItemBuilder.from(dailyRewardConfiguration.getInfoItemMaterial())
                .name(Component.text(ChatHelper.fixColor(dailyRewardConfiguration.getInfoItemName())))
                .lore(components)
                .asGuiItem();
        for(DailyReward dailyReward : dailyRewardService.getDailyRewardList()){
            Date currentDate = new Date(System.currentTimeMillis());
            Calendar calendar = Calendar. getInstance();
            calendar.setTime(currentDate);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            GuiItem guiItem = ItemBuilder.from((user.getDailyRewardMissedMap().get(dailyReward.getDay()) != null) ?
                            dailyRewardConfiguration.getDailyRewardMissedMaterial()
                            : dailyRewardConfiguration.getDailyRewardMaterial())
                    .name(Component.text(ChatHelper.fixColor(dailyReward.getName())))
                    .lore(List.of(
                            Component.text(ChatHelper.fixColor("" )),
                            Component.text(ChatHelper.fixColor(
                                            (user.getDailyRewardMap().get(dailyReward.getDay())
                                                    ? this.dailyRewardConfiguration.getAlreadyCollectedReward() :
                                            user.getDailyRewardMissedMap().get(dailyReward.getDay()) != null ?
                                                    this.dailyRewardConfiguration.getMissedReward()
                                                            .replace("{TIME}", TimeHelper.timeToString(user.getDailyRewardMissedMap().get(dailyReward.getDay())))
                                                    : (dayOfMonth != dailyReward.getDay()) ?
                                                    this.dailyRewardConfiguration.getCannotCollectReward():
                                                    ((user.getSpentTimeMap().get(dailyReward.getDay()) + (System.currentTimeMillis()
                                                            - user.getJoinedAt()) >
                                                            TimeHelper.timeFromString(dailyRewardConfiguration.getRequiredSpentTime()))
                                                            ? this.dailyRewardConfiguration.getClickToCollectReward()
                                                            : this.dailyRewardConfiguration.getWaitToCollect()
                                                            .replace("{TIME}", this.dailyRewardConfiguration.getRequiredSpentTime()))))),
                            Component.text(ChatHelper.fixColor("" ))
                    ))
                    .asGuiItem(event -> {
                        event.setCancelled(true);
                        if(user.getDailyRewardMap().get(dailyReward.getDay())
                                && user.getDailyRewardMissedMap().get(dailyReward.getDay()) == null){
                            player.sendMessage(ChatHelper.fixColor(dailyRewardConfiguration.getAlreadyCollected()));
                            return;
                        }
                        if(dayOfMonth == dailyReward.getDay()
                                || user.getDailyRewardMissedMap().get(dailyReward.getDay()) != null){
                            if(user.getSpentTimeMap().get(dailyReward.getDay())
                                    + (System.currentTimeMillis() - user.getJoinedAt()) <
                                    TimeHelper.timeFromString(dailyRewardConfiguration.getRequiredSpentTime())){
                                player.sendMessage(ChatHelper.fixColor(dailyRewardConfiguration.getCollectionDeniedByTime()));
                                return;
                            }
                            for(String command : dailyReward.getCommands()){
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command
                                        .replace("{PLAYER}", player.getName()));
                            }
                            player.sendMessage(ChatHelper.fixColor(dailyRewardConfiguration.getSuccessfullyCollected()));
                            user.getDailyRewardMap().put(dailyReward.getDay(), true);
                            if(user.getDailyRewardMissedMap().get(dailyReward.getDay()) != null){
                                user.getDailyRewardMissedMap().remove(dailyReward.getDay());
                            }
                            this.databaseProvider.saveUser(user);
                            this.open(player, user);
                        } else {
                            player.sendMessage(ChatHelper.fixColor(dailyRewardConfiguration.getCollectionDenied()));
                        }
                    });

            gui.addItem(guiItem);
        }
        gui.setItem(dailyRewardConfiguration.getInfoItemSlot(), infoItem);
        gui.setDefaultClickAction(event -> {
            event.setCancelled(true);
        });
        gui.open(player);
    }
}
