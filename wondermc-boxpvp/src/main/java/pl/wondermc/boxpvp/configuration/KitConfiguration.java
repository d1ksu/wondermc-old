package pl.wondermc.boxpvp.configuration;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import org.bukkit.Material;
import pl.wondermc.boxpvp.kit.Kit;
import pl.wondermc.boxpvp.kit.KitItem;
import pl.wondermc.boxpvp.kit.KitItemEnchant;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class KitConfiguration extends OkaeriConfig {

    @Comment("# Lista kitow.")
    private List<Kit> kits = Arrays.asList(
            new Kit(
                    "Gracz",
                    "5m",
                    Collections.singletonList(new KitItem(
                            Material.DIAMOND_PICKAXE,
                            1,
                            "WONDERMC.PL",
                            Arrays.asList(
                                    "Wondermc.pl",
                                    "dc.wondermc.pl"
                            ),
                            Arrays.asList(
                                    new KitItemEnchant(
                                            "DURABILITY",
                                            3
                                    ),
                                    new KitItemEnchant(
                                            "DIG_SPEED",
                                            5
                                    )
                            )
                    )),
                    "&7Kit gracz",
                    Material.IRON_PICKAXE,
                    Arrays.asList("Kit gracz", "Kliknij aby podejrzec itemy"),
                    10
            )
    );

    @Comment("# Nazwa inventory.")
    private String inventoryName = "&5Kity:";
    @Comment("# Ilosc kolumn w inventory.")
    private int inventoryRows = 3;
    @Comment("# Ilosc kolumn w kit inventory.")
    private int kitInventoryRows = 5;
    @Comment("# Nazwa kit inventory, {KIT} = nazwa kita.")
    private String kitInventoryName = "&5 Kit {KIT}";



    @Comment("# Nazwa itemu do wrocenia do glownego inventory.")
    private String goBackItemInventoryName = "&cWroc";
    @Comment("# Material itemu do wrocenia do glownego inventory.")
    private Material goBackItemMaterial = Material.ARROW;
    @Comment("# Slot itemu do wrocenia do glownego inventory.")
    private int goBackItemSlot = 36;

    @Comment("# Material itemu odbierania gdy gracz nie moze odebrac kitu.")
    private Material cannotCollectMaterial = Material.BARRIER;
    @Comment("# Material itemu odbierania gdy gracz moze odebrac kit.")
    private Material canCollectMaterial = Material.GREEN_DYE;

    @Comment("# Nazwa itemu odbierania gdy gracz nie moze odebrac kitu.")
    private String cannotCollectItemName = "&cNie mozesz jeszcze go odebrac!";
    @Comment("# Nazwa itemu odbierania gdy gracz moze odebrac kitu.")
    private String canCollectItemName = "&aKliknij aby odebrac kit!";
    @Comment("# Lore itemu odbierania gdy gracz nie moze odebrac kitu. {TIME} = pozostaly czas, {KIT} = nazwa kitu.")
    private List<String> cannotCollectLore = Arrays.asList(
            "",
            "Nie mozesz jeszcze odebrac tego kitu, pozostalo {TIME}"
    );
    @Comment("# Lore itemu odbierania gdy gracz moze odebrac kitu.")
    private List<String> canCollectLore = Arrays.asList(
            "",
            "&aKliknij aby oderbac kit!"
    );
    @Comment("# Slot itemu do odbierania.")
    private int collectItemSlot = 44;

    @Comment("# Wiadomosc gdy gracz moze jeszcze odebrac kita.")
    private String collectTitle = "&aOdebrales kita!";
    @Comment("# Wiadomosc gdy gracz nie moze jeszcze odebrac kita.")
    private String cannotCollectTitle = "&cNie mozesz jeszcze go odebrac, pozostaly czas {TIME}";

    @Comment("# Kit ktory otrzymuje nowy gracz.")
    private String newUserKit = "Gracz";

    public String getNewUserKit() {
        return newUserKit;
    }

    public String getCannotCollectTitle() {
        return cannotCollectTitle;
    }

    public String getCollectTitle() {
        return collectTitle;
    }

    public int getCollectItemSlot() {
        return collectItemSlot;
    }

    public Material getCannotCollectMaterial() {
        return cannotCollectMaterial;
    }

    public Material getCanCollectMaterial() {
        return canCollectMaterial;
    }

    public String getCannotCollectItemName() {
        return cannotCollectItemName;
    }

    public String getCanCollectItemName() {
        return canCollectItemName;
    }

    public List<String> getCannotCollectLore() {
        return cannotCollectLore;
    }

    public List<String> getCanCollectLore() {
        return canCollectLore;
    }

    public int getGoBackItemSlot() {
        return goBackItemSlot;
    }

    public Material getGoBackItemMaterial() {
        return goBackItemMaterial;
    }

    public String getGoBackItemInventoryName() {
        return goBackItemInventoryName;
    }




    public int getKitInventoryRows() {
        return kitInventoryRows;
    }

    public String getKitInventoryName() {
        return kitInventoryName;
    }

    public int getInventoryRows() {
        return inventoryRows;
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public List<Kit> getKits() {
        return Collections.unmodifiableList(kits);
    }
}
