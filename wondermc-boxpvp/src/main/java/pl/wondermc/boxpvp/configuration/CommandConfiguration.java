package pl.wondermc.boxpvp.configuration;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import pl.wondermc.boxpvp.item.SellItem;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
@Header("# Konfiguracja komend.")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class CommandConfiguration extends OkaeriConfig {

    @Comment("# Wiadomosci po uzyciu komendy /pomoc.")
    private List<String> helpCommandMessage = Arrays.asList(
            "/vip",
            "/svip",
            "cokolwiek"
    );

    @Comment("# Wiadomosci po uzyciu komendy /vip.")
    private List<String> vipCommandMessage = Arrays.asList(
            "zakup vipa na www.adwd.pl",
            ""
    );

    @Comment("# Wiadomosci po uzyciu komendy /svip.")
    private List<String> svipCommandMessage = Arrays.asList(
            "zakup svipa na www.adwd.pl",
            ""
    );

    @Comment("# Wiadomosci po uzyciu komendy /elita.")
    private List<String> elitaCommandMessage = Arrays.asList(
            "zakup elite na www.adwd.pl",
            ""
    );

    @Comment("# Wiadomosci po uzyciu komendy /wonder.")
    private List<String> wonderCommandMessage = Arrays.asList(
            "zakup wonder na www.adwd.pl",
            ""
    );

    private List<SellItem> sellableItemsList = Arrays.asList(
            new SellItem(
                    Material.COBBLESTONE,
                    64,
                    1
            ),
            new SellItem(
                    Material.GOLD_BLOCK,
                    32,
                    24
            )
    );
    @Comment("# Material itemu ktory otrzyma gracz po sprzedazy.")
    private Material sellItemMaterial = Material.GOLD_NUGGET;
    @Comment("# Nazwa itemu ktory otrzyma gracz po sprzedazy.")
    private String sellItemName = "Moneta";
    @Comment("# Lore itemu ktory otrzyma gracz po sprzedazy.")
    private List<String> sellItemLore = Arrays.asList(
            "Monety"
    );
    @Comment("# Enchant itemu ktory otrzyma gracz po sprzedazy.")
    private Enchantment sellItemEnchant = Enchantment.DURABILITY;
    @Comment("# Level itemu ktory otrzyma gracz po sprzedazy.")
    private int sellItemEnchantLevel = 3;

    @Comment("# Wiadomosc gdy gracz sprzeda wszystkie surowce.")
    private String sucessfullySold = "&aPomyslnie sprzedano surowiec!";
    @Comment("# Wiadomosc gdy gracz nie ma nic do sprzedania.")
    private String nothingToSell = "Nie masz nic do sprzedania!";

    public String getNothingToSell() {
        return nothingToSell;
    }

    public String getSucessfullySold() {
        return sucessfullySold;
    }

    public Material getSellItemMaterial() {
        return sellItemMaterial;
    }

    public String getSellItemName() {
        return sellItemName;
    }

    public List<String> getSellItemLore() {
        return sellItemLore;
    }

    public Enchantment getSellItemEnchant() {
        return sellItemEnchant;
    }

    public int getSellItemEnchantLevel() {
        return sellItemEnchantLevel;
    }

    private String tpCommandUse = "&c/tp <player> <player>";
    private String playerIsOffline = "{PLAYER} jest offline lub nie ma takiego gracza!";

    public String getTpCommandUse() {
        return tpCommandUse;
    }

    public String getPlayerIsOffline() {
        return playerIsOffline;
    }

    private String enableStatus = "&aWlaczyles";
    private String disableStatus = "&cWylaczyles";
    private String flyInfo = "{STATUS} &7mozliwosc latania!";
    private String healInfo = "&aPomyslnie zostales uleczony!";
    private String feedInfo = "&aPomyslnie zostales nakarmiony!";
    private String godInfo = "{STATUS} &7god'a!";
    private String godActionbar = "&cJestes nieznisczalny!";
    private String vanishInfo = "{STATUS} vanisha!";
    private String vanishActionbar = "&aJestes niewidzialny!";

    private String privateMessageUsage = "&c/msg <nick> <wiadomość>";
    private String privateMessageReplyUsage = "&c/r <wiadomość>";
    private String privateMessageEmpty = "&4Błąd&8: &cAby wysłać wiadomośc musisz coś napisać";
    private String privateMessageTarget = "&b{PLAYER} &8> &3JA&8: &7{MESSAGE}";
    private String privateMessageSender = "&3JA&8 &8> &b{PLAYER}&8: &7{MESSAGE}";
    private String privateMessageSelfInteract = "&4Błąd&8: &cNie możesz wysłać wiadomości do siebie.";
    private String privateMessageReplyEmpty = "&4Błąd&8: &cNie masz komu odpisać.";
    private String privateMessageDeniedByIgnore = "&cTen gracz cie ignoruje!";

    private String ignoredPlayer = "&cOd teraz ignorujesz {PLAYER}";
    private String unIgnoredPlayer = "&aJuz nie ignorujesz {PLAYER}!";
    private String cannotIngoreUrself = "Nie mozesz sie sam ignorowac!";
    private String ignoringPlayer = "&cIgnorujesz {PLAYER}, musisz go odignorowac aby napisac wiadomosc!";

    public String getIgnoringPlayer() {
        return ignoringPlayer;
    }

    public String getCannotIngoreUrself() {
        return cannotIngoreUrself;
    }

    public String getPrivateMessageDeniedByIgnore() {
        return privateMessageDeniedByIgnore;
    }

    private String socialSpy = "&7Pomyślnie {STATUS} &7socialSpy.";
    private String socialSpyMessage = "&b{PLAYER_SENDER} &8> &3{PLAYER_TARGET}&8: &7{MESSAGE}";

    private String invseeUsage = "&c/invsee <nick>";
    private String enderchestUsage = "&c/ec <nick>";
    private String ignoreCommandUsage = "&c/ingore <nick>";
    private String teleportHereUsage = "/tphere <nick>";
    private String gamemodeUsage = "/gamemode 0,1,2,3 <player>";

    private String teleportRequestUsage = "&c/tpa <nick>";
    private String teleportRequestSender = "&7Pomyślnie wysłano prośbe o teleportacje do&8: &b{PLAYER}";
    private String teleportRequestTarget = "&7Otrzymano prośbe o teleportacje od&8: &b{PLAYER}\n&7aby zaakceptować wpisz&8: &b/tpaccept {PLAYER}";
    private String teleportRequestSelfInteract = "&4Błąd&8: &cNie możesz wysłać prośby o teleport do siebie.";
    private String teleportRequestAlreadyHas = "&4Błąd&8: &cPodany gracz posiada już prośbe o teleportacje od ciebie.";

    private String teleportAcceptUsage = "&c/tpaccept <nick/*>";
    private String teleportAcceptRequestsEmpty = "&4Błąd&8: &cNie posiadasz żadnej prośby o teleportacje.";
    private String teleportAcceptSenderPlayer = "&7Pomyślnie zaakceptowane prośbe o teleportacje od gracza&8: &b{PLAYER}";
    private String teleportAcceptSenderAll = "&7Pomyślnie zaakceptowano wszystkie prośby o teleportacje.";
    private String teleportAcceptTarget = "&7Gracz&8: &b{PLAYER} &7zaakceptował twoją prośbe o teleportacje.";
    private int teleportTime = 10;
    private String teleportTitle = "&fTeleportacja&8: &b{TIME}";
    private String teleportMove = "&4Podczas teleportacji nie można się poruszać.";
    private String teleportSuccess = "&aPomyslnie przeteleportowano!";
    private String teleportedHereByPlayer = "&aZostales przeteleportowany przez {PLAYER}";
    private String teleportedHerePlayer = "&aPrzeteleportowales gracza {PLAYER} do siebie";

    private String gamemodeChange = "&aZmieniles swoj tryb gry na {GAMEMODE}!";
    private String gamemodeChangeByPlayer = "Zmieniono twoj tryb gry na {GAMEMODE} przez {PLAYER}";
    private String gamemodeChangeForPlayer = "Zmieniles tryb gry graczowi {PLAYER} na {GAMEMODE}";

    public String getGamemodeChange() {
        return gamemodeChange;
    }

    public String getGamemodeChangeByPlayer() {
        return gamemodeChangeByPlayer;
    }

    public String getGamemodeChangeForPlayer() {
        return gamemodeChangeForPlayer;
    }

    public String getGamemodeUsage() {
        return gamemodeUsage;
    }

    public String getTeleportHereUsage() {
        return teleportHereUsage;
    }

    public String getTeleportedHereByPlayer() {
        return teleportedHereByPlayer;
    }

    public String getTeleportedHerePlayer() {
        return teleportedHerePlayer;
    }

    public String getTeleportSuccess() {
        return teleportSuccess;
    }

    public int getTeleportTime() {
        return teleportTime;
    }

    public String getTeleportTitle() {
        return teleportTitle;
    }

    public String getTeleportMove() {
        return teleportMove;
    }

    public String getTeleportAcceptUsage() {
        return teleportAcceptUsage;
    }

    public String getTeleportAcceptRequestsEmpty() {
        return teleportAcceptRequestsEmpty;
    }

    public String getTeleportAcceptSenderPlayer() {
        return teleportAcceptSenderPlayer;
    }

    public String getTeleportAcceptSenderAll() {
        return teleportAcceptSenderAll;
    }

    public String getTeleportAcceptTarget() {
        return teleportAcceptTarget;
    }

    public String getTeleportRequestAlreadyHas() {
        return teleportRequestAlreadyHas;
    }

    public String getTeleportRequestUsage() {
        return teleportRequestUsage;
    }

    public String getTeleportRequestSender() {
        return teleportRequestSender;
    }

    public String getTeleportRequestTarget() {
        return teleportRequestTarget;
    }

    public String getTeleportRequestSelfInteract() {
        return teleportRequestSelfInteract;
    }

    public String getSocialSpy() {
        return socialSpy;
    }

    public String getPrivateMessageReplyEmpty() {
        return privateMessageReplyEmpty;
    }

    public String getPrivateMessageSelfInteract() {
        return privateMessageSelfInteract;
    }

    public String getPrivateMessageSender() {
        return privateMessageSender;
    }

    public String getPrivateMessageTarget() {
        return privateMessageTarget;
    }

    public String getPrivateMessageEmpty() {
        return privateMessageEmpty;
    }

    public String getPrivateMessageUsage() {
        return privateMessageUsage;
    }

    public String getPrivateMessageReplyUsage() {
        return privateMessageReplyUsage;
    }

    public String getVanishInfo() {
        return vanishInfo;
    }

    public String getGodActionbar() {
        return godActionbar;
    }

    public String getVanishActionbar() {
        return vanishActionbar;
    }

    public String getGodInfo() {
        return godInfo;
    }

    public String getHealInfo() {
        return healInfo;
    }

    public String getFeedInfo() {
        return feedInfo;
    }

    public String getEnableStatus() {
        return enableStatus;
    }

    public String getDisableStatus() {
        return disableStatus;
    }

    public String getFlyInfo() {
        return flyInfo;
    }

    public List<SellItem> getSellableItemsList() {
        return Collections.unmodifiableList(sellableItemsList);
    }

    public List<String> getElitaCommandMessage() {
        return elitaCommandMessage;
    }

    public List<String> getVipCommandMessage() {
        return vipCommandMessage;
    }

    public List<String> getSvipCommandMessage() {
        return svipCommandMessage;
    }

    public List<String> getWonderCommandMessage() {
        return wonderCommandMessage;
    }

    public List<String> getHelpCommandMessage() {
        return helpCommandMessage;
    }

    public String getInvseeUsage() {
        return this.invseeUsage;
    }

    public String getEnderchestUsage() {
        return enderchestUsage;
    }

    public String getSocialSpyMessage() {
        return socialSpyMessage;
    }

    public String getIgnoreCommandUsage() {
        return ignoreCommandUsage;
    }

    public String getIgnoredPlayer() {
        return ignoredPlayer;
    }

    public String getUnIgnoredPlayer() {
        return unIgnoredPlayer;
    }
}
