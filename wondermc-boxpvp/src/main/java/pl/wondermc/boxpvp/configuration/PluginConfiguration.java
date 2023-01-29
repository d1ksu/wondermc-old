package pl.wondermc.boxpvp.configuration;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import pl.wondermc.api.database.Database;
import pl.wondermc.api.database.Nats;

import java.util.Arrays;
import java.util.List;
@Header("# Glowna konfiguracja pluginu wondermc-boxpvp.")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfiguration extends OkaeriConfig {

    @Comment("# Dane do polaczenia z baza danych MySQL.")
    private Database database = new Database(
            "hostname",
            "database",
            3306,
            "user",
            "password"
    );

    @Comment("# Dane do polaczenia z natsem.")
    private Nats natsConnector = new Nats(
            "127.0.0.1",
            4222,
            ""
    );

    @Comment("# Nagrody za discorda. {PLAYER} = nick.")
    private List<String> discordRewards = Arrays.asList(
            "case give 10",
            "group set vip cokolwiek"

    );
    @Comment("# Wiadomosc gdy gracz juz odebral nagrode.")
    private String alreadyCollected = "&aOdebrales juz nagrode!";
    @Comment("# Wiadomosc gdy gracz pomyslnie odebral nagrode.")
    private String successfullyCollected = "&aPomyslnie odebrano nagrode!";
    @Comment("# Wiadomosc gdy gracz nie odebral nagrody na discodzie.")
    private String discordJoinRequired = "&aNajpierw musisz odebrac nagrode na discordzie!";

    @Comment("# Komenda warpu dla /spawn!")
    private String warpSpawnCommand = "warp spawn";


    @Comment("# Wiadomosci o wejsciu gracza na serwer do wszystkich graczy online. {PLAYER} = nick gracza")
    private List<String> playerGlobalJoinMessage = Arrays.asList(
            "&7Gracz {PLAYER} dolaczyl na serwer",
            ""
    );

    @Comment("# Wiadomosci ktore gracz otrzyma po wejsciu na serwer. {PLAYER} = nick gracza")
    private List<String> playerJoinMessage = Arrays.asList(
            "&7Witaj &a{PLAYER} na serwerze wondermc.pl!",
            "&7 dc.wondermc.pl",
            "&7 www.wondermc.pl"
    );
    @Comment("# Wiadomosci ktore gracze otrzymaja po wyjsciu gracza z serwera. {PLAYER} = nick gracza")
    private List<String> playerGlobalQuitMessage = Arrays.asList(
            "",
            "&fGracz {PLAYER} opuscil serwer"
    );

    @Comment("# Itemy potrzebne do naprawienia itemu w kowadle")
    private List<ItemStack> anvilRepairCostItem = Arrays.asList(
            new ItemStack(Material.DIAMOND, 10)
    );

    @Comment("# Itemy ktore mozna naprawic w kowadle")
    private List<Material> anvilAcceptableItems = Arrays.asList(
            Material.DIAMOND_PICKAXE
    );

    @Comment("# Itemy ktore maja zostac pominiete w dropie do eq.")
    private List<Material> avoidItems = Arrays.asList(
            Material.GOLD_ORE
    );


    public List<Material> getAvoidItems() {
        return avoidItems;
    }

    @Comment("# Wiadomosc gdy gracz nie ma wystarczajacych przedmiotow do naprawy w kowadle.")
    private String anvilRepairDeny = "&cNie posiadasz wystarczajacych przedmiotow do naprawy!";

    @Comment("# Wiadomosc gdy gracz naprawi przedmiot w kowadle")
    private String anvilRepairSuccess = "&aPomyslnie naprawiles przedmiot!";

    @Comment("# Wiadomosci gdy nie mozna naprawic danego przedmiotu w kowadle")
    private String anvilWrongItemDeny = "&cTego przedmiotu nie mozesz naprawic!";

    @Comment("# Wiadomosc gdy item podczas naprawiana w kowadle jest juz naprawiony.")
    private String anvilRepairedItemDeny = "&cTen przedmiot jest juz naprawiony!";

    @Comment("# Permisja dla graczy widzacych wiadomosci z komendy /helpop.")
    private String helpopPermission = "wondermc.helpop";

    @Comment("# Cooldown dla uzycia komendy /helpop.")
    private String helpopCooldown = "30s";

    @Comment("# Poprawne uzycie komendy /helpop.")
    private String helpopUsage = "&c/helpop <wiadomosc>";

    @Comment("# Wiadomosc o cooldown'ie przy uzyciu komendy /helpop. {TIME} = pozostaly czas")
    private String helpopCooldownMessage = "&cNastepna wiadomosc mozesz zglosic za: {TIME}";

    @Comment("# Wiadomosc o wyslanym zgloszeniu na /helpop.")
    private String helpopMessageSent = "&cWyslales zgloszenie!";

    @Comment("# Wiadomosc do administracji o zgloszeniu na /helpop. {PLAYER} = nick gracza, {MESSAGE} = wiadomosc")
    private String helpopAdminMessage = "&c[HELPOP] &7{PLAYER}: &f{MESSAGE}";

    @Comment("# Czas antylogoutu.")
    private String fightTime = "30s";

    @Comment("# Actionbar z informacja o antylogocie.")
    private String fightInformation = "&5Antylogout: &f{TIME}";
    @Comment("# Dlugosc antylogoutu po zabojstwie.")
    private String fightDecrease = "5s";
    @Comment("# Wiadomosc gdy gracz ma zablokowana interakcje podczas pvp.")
    private String denyByFight = "&cNie mozesz tego zrobic podczas pvp!";

    @Comment("# Lista dozwolonych inventory podczas antylogoutu.")
    private List<InventoryType> allowedInventoriesFight = Arrays.asList(
            InventoryType.DISPENSER
    );
    @Comment("# Lista dozwolonych komend podczas antylogoutu.")
    private List<String> allowedCommandsFight = Arrays.asList(
            "/msg"
    );

    @Comment("# Wiadomosc gdy gracz sie wyloguje podczas walki. {PLAYER} = nick")
    private String fightQuitInformation = "&cGracz {PLAYER} wylogowal sie podczas walki!";

    @Comment("# Nazwa regionu safezone.")
    private String safeZoneRegion = "spawn";
    @Comment("# Wiadomosc gdy gracz probuje wejsc na safezone podczas pvp")
    private String safeZoneInfo = "&cNie mozesz wejsc na safezone podczas pvp!";

    @Comment("# Lista zablokowanych komend.")
    private List<String> notAllowedCommands = Arrays.asList(
            "pl",
            "help"
    );

    @Comment("# Wiadomosc gdy gracz wykona zablokowana komende.")
    private String notAllowedCommandPerform = "Ta komenda nie jest dostepna dla graczy!";

    public List<String> getNotAllowedCommands() {
        return notAllowedCommands;
    }

    public String getNotAllowedCommandPerform() {
        return notAllowedCommandPerform;
    }

    public List<String> getPlayerGlobalQuitMessage() {
        return playerGlobalQuitMessage;
    }

    public String getSafeZoneInfo() {
        return safeZoneInfo;
    }

    public String getSafeZoneRegion() {
        return safeZoneRegion;
    }

    public String getFightQuitInformation() {
        return fightQuitInformation;
    }

    public List<String> getAllowedCommandsFight() {
        return allowedCommandsFight;
    }

    public List<InventoryType> getAllowedInventoriesFight() {
        return allowedInventoriesFight;
    }

    public String getDenyByFight() {
        return denyByFight;
    }

    public String getFightDecrease() {
        return fightDecrease;
    }

    public String getFightTime() {
        return fightTime;
    }

    public String getFightInformation() {
        return fightInformation;
    }

    public String getAlreadyCollected() {
        return alreadyCollected;
    }

    public String getSuccessfullyCollected() {
        return successfullyCollected;
    }

    public String getDiscordJoinRequired() {
        return discordJoinRequired;
    }

    public String getWarpSpawnCommand() {
        return warpSpawnCommand;
    }

    public Nats getNatsConnector() {
        return natsConnector;
    }



    public List<String> getDiscordRewards() {
        return discordRewards;
    }

    public Database getDatabase() {
        return database;
    }

    public String getHelpopAdminMessage() {
        return helpopAdminMessage;
    }

    public String getHelpopMessageSent() {
        return helpopMessageSent;
    }

    public String getHelpopCooldownMessage() {
        return helpopCooldownMessage;
    }

    public String getHelpopUsage() {
        return helpopUsage;
    }

    public String getHelpopCooldown() {
        return helpopCooldown;
    }

    public String getHelpopPermission() {
        return helpopPermission;
    }

    public String getAnvilRepairedItemDeny() {
        return anvilRepairedItemDeny;
    }

    public List<Material> getAnvilAcceptableItems() {
        return anvilAcceptableItems;
    }

    public String getAnvilRepairDeny() {
        return anvilRepairDeny;
    }

    public String getAnvilRepairSuccess() {
        return anvilRepairSuccess;
    }

    public List<ItemStack> getAnvilRepairCostItem() {
        return anvilRepairCostItem;
    }


    public String getAnvilWrongItemDeny() {
        return anvilWrongItemDeny;
    }

    public List<String> getPlayerGlobalJoinMessage() {
        return playerGlobalJoinMessage;
    }

    public List<String> getPlayerJoinMessage() {
        return playerJoinMessage;
    }


}
