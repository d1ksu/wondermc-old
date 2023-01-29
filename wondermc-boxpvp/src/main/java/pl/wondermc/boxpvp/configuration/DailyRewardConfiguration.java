package pl.wondermc.boxpvp.configuration;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import org.bukkit.Material;
import pl.wondermc.boxpvp.reward.DailyReward;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Header("# Konfiguracja dziennych nagrod.")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class DailyRewardConfiguration extends OkaeriConfig {

    @Comment("# Lista nagrod. {PLAYER} = nick gracza.")
    private List<DailyReward> dailyRewardList = List.of(
            new DailyReward(
                    "#1",
                    1,
                    Arrays.asList(
                            "give nick diamond",
                            "pex group add vip"
                    )),
            new DailyReward(
                    "#2",
                    2,
                    Arrays.asList(
                            "give nick diamond",
                            "pex group add vip"))
    );

    @Comment("# Ilosc kolumn w inventory.")
    private int inventoryRows = 6;

    @Comment("# Nazwa inventory")
    private String inventoryName = "&8Codzienne nagrody";

    @Comment("# Item codzienniej nagrody w inventory")
    private Material dailyRewardMaterial = Material.CHEST_MINECART;

    @Comment("# Item codzienniej nagrody w inventory gdy nagroda zostala nie odebrana.")
    private Material dailyRewardMissedMaterial = Material.BARRIER;

    @Comment("# Nazwa itemu informacji.")
    private String infoItemName = "&eInformacje";

    @Comment("# Slot itemu z informacja.")
    private int infoItemSlot = 4;

    @Comment("# Material itemu z informacja.")
    private Material infoItemMaterial = Material.PAINTING;

    @Comment("# Wymagany przegrany czas do odebrania nagrody.")
    private String requiredSpentTime = "30m";

    @Comment("# Wiadomosc gdy gracz pomyslnie odebral nagrode.")
    private String successfullyCollected = "&aPomyslnie odebrano nagrode!";

    @Comment("# Wiadomosc gdy gracz odebral juz nagrode.")
    private String alreadyCollected = "&cOdebrales juz nagrode za ten dzien!";

    @Comment("# Wiadomosc gdy gracz nie moze jeszcze odebrac nagrody.")
    private String collectionDenied = "&cNie mozesz jeszcze odebrac nagrody za ten dzien!";

    @Comment("# Wiadomosc gdy gracz nie ma przegranego odpowiedniego czasu.")
    private String collectionDeniedByTime = "&cMusisz spedzic 1min zeby odebrac nagrode! ";

    @Comment("# Lore itemu z informacja.")
    private List<String> itemInfoLore = Arrays.asList(
            "adawd",
            "awdawd"
    );



    @Comment(" # Lore nagrody gdy gracz juz odebral nagrode.")
    private String alreadyCollectedReward = "&aOdebrales juz nagrode!";
    @Comment(" # Lore nagrody gdy gracz ominal nagrode. {TIME} = spedzony czas w danym dniu")
    private String missedReward = "&cOminales ten dzien! Tego dnia spedziles {TIME}, Kliknij aby odebrac!";
    @Comment(" # Lore nagrody gdy gracz nie moze odebrac nagrody.")
    private String cannotCollectReward = "&cNie mozesz odebrac tej nagrody!";
    @Comment(" # Lore nagrody gdy gracz moze odebrac nagrode.")
    private String clickToCollectReward = "&aKliknij aby odebrac nagrode!";
    @Comment(" # Lore nagrody gdy gracz musi spedzic czas aby odebrac nagrode. {TIME} = Wymagany czas.")
    private String waitToCollect = "Musisz spedzic przynajmiej {TIME} aby odebrac ta nagrode!";


    public String getAlreadyCollectedReward() {
        return alreadyCollectedReward;
    }

    public String getMissedReward() {
        return missedReward;
    }

    public String getCannotCollectReward() {
        return cannotCollectReward;
    }

    public String getClickToCollectReward() {
        return clickToCollectReward;
    }

    public String getWaitToCollect() {
        return waitToCollect;
    }





    public List<String> getItemInfoLore() {
        return itemInfoLore;
    }

    public Material getInfoItemMaterial() {
        return infoItemMaterial;
    }

    public String getCollectionDeniedByTime() {
        return collectionDeniedByTime;
    }

    public String getAlreadyCollected() {
        return alreadyCollected;
    }

    public String getCollectionDenied() {
        return collectionDenied;
    }

    public String getSuccessfullyCollected() {
        return successfullyCollected;
    }

    public String getRequiredSpentTime() {
        return requiredSpentTime;
    }

    public String getInfoItemName() {
        return infoItemName;
    }

    public int getInfoItemSlot() {
        return infoItemSlot;
    }

    public Material getDailyRewardMaterial() {
        return dailyRewardMaterial;
    }

    public Material getDailyRewardMissedMaterial() {
        return dailyRewardMissedMaterial;
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public int getInventoryRows() {
        return inventoryRows;
    }

    public List<DailyReward> getDailyRewardList() {
        return Collections.unmodifiableList(dailyRewardList);
    }


}
