package pl.wondermc.boxpvp.configuration;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import pl.wondermc.boxpvp.warp.Warp;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Header("# Konfiguracja warpow.")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class WarpConfiguration extends OkaeriConfig {

    @Comment("# Lista warpow.")
    private List<Warp> warpList = Arrays.asList(
            new Warp(
                  "dirt",
                    new Location(Bukkit.getWorld("world"), 1, 1, 1, 1, 1),
                    5,
                    "warp.dirt",
                    Material.DIRT,
                    "&cDirt",
                    Arrays.asList("", "Kliknij aby sie teleportowac"),
                    10
            )
    );


    public Optional<Warp> findWarp(String name){
        return this.warpList.stream().filter(warp -> warp.getName().equalsIgnoreCase(name)).findFirst();
    }

    public List<Warp> getWarpList() {
        return Collections.unmodifiableList(warpList);
    }

    @Comment("# Ilosc kolumn w inventory.")
    private int inventoryRows = 3;
    @Comment("# Nazwa inventory.")
    private String inventoryName = "&5Warpy:";


    @Comment("# Wiadomosc gdy gracz sie przeteleportuje.")
    private String teleportSucces = "&aZostales pomyslnie przeteleportowany!";
    @Comment("# Wiadomosc gdy teleport zostanie anulowany.")
    private String teleportDeny = "&cAnulowano teleport!";
    @Comment("# Wiadomosc gdy gracz sie teleportuje. {TIME} = czas.")
    private String duringTeleport = "&aZostaniesz przeteleportowany za &f{TIME}";

    @Comment("# Wiadomosc gdy gracza nie ma permisji warpu.")
    private String noAccess = "&cNie posiadasz permisji";

    @Comment("# Nazwa reionu spawna.")
    private String spawnRegionName = "spawn";

    @Comment("# Poprawne uzycie komendy /warp. ")
    private String warpCommandUsage = "&c/warp <nazwa>";
    @Comment("# Poprawne uzycie komendy /setwarp. ")
    private String setWarpCommandUsage = "&c/setwarp <nazwa>";

    @Comment("# Wiadomosc gdy podczas ustawiania lokalizacji warpa albo proby teleportacji warp nie bedzie istniec.")
    private String warpNotFound = "&cNie znaleziono warpa!";

    @Comment("# Permisja dla /setwarp")
    private String warpSetPermission = "wondermc.warps.set";

    @Comment("# Wiadomosc gdy gracz nie ma permisji do /setwarp")
    private String noPermissionToSet = "&cNie masz permisji do ustawiania warpow!";

    @Comment("# Wiadomosc gdy gracz pomyslnie ustawi warpa")
    private String warpSuccessfullySet = "&aPomyslnie ustawiles warpa!";



    public String getWarpSuccessfullySet() {
        return warpSuccessfullySet;
    }

    public String getNoPermissionToSet() {
        return noPermissionToSet;
    }

    public String getWarpSetPermission() {
        return warpSetPermission;
    }

    public String getWarpNotFound() {
        return warpNotFound;
    }

    public String getSetWarpCommandUsage() {
        return setWarpCommandUsage;
    }

    public String getWarpCommandUsage() {
        return warpCommandUsage;
    }

    public String getSpawnRegionName() {
        return spawnRegionName;
    }

    public String getNoAccess() {
        return noAccess;
    }

    public String getDuringTeleport() {
        return duringTeleport;
    }

    public String getTeleportDeny() {
        return teleportDeny;
    }

    public String getTeleportSucces() {
        return teleportSucces;
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public int getInventoryRows() {
        return inventoryRows;
    }
}
