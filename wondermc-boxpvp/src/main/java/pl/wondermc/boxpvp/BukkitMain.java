package pl.wondermc.boxpvp;


import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.pieszku.messaging.nats.NatsMessenger;
import pl.wondermc.api.database.Nats;
import pl.wondermc.boxpvp.command.admin.*;
import pl.wondermc.boxpvp.command.framework.CommandFramework;
import pl.wondermc.boxpvp.command.player.*;
import pl.wondermc.boxpvp.configuration.*;
import pl.wondermc.boxpvp.configuration.factory.ConfigurationFactory;
import pl.wondermc.boxpvp.configuration.serdes.*;
import pl.wondermc.boxpvp.database.DatabaseProvider;
import pl.wondermc.boxpvp.listener.block.BlockBreakListener;
import pl.wondermc.boxpvp.listener.entity.EntityDamageByEntityListener;
import pl.wondermc.boxpvp.listener.entity.EntityDamageListener;
import pl.wondermc.boxpvp.listener.inventory.InventoryOpenListener;
import pl.wondermc.boxpvp.listener.player.*;
import pl.wondermc.boxpvp.reward.DailyRewardService;
import pl.wondermc.boxpvp.task.InformationTask;
import pl.wondermc.boxpvp.task.UserBarTask;
import pl.wondermc.boxpvp.task.UserFightTask;
import pl.wondermc.boxpvp.user.User;
import pl.wondermc.boxpvp.user.UserService;
import pl.wondermc.boxpvp.user.sub.UserFight;

import java.util.Arrays;

public class BukkitMain extends JavaPlugin {

    private static BukkitMain instance;

    private ConfigurationFactory configurationFactory;
    private UserService userService;
    private NatsMessenger natsMessenger;
    private DatabaseProvider databaseProvider;
    private PluginConfiguration pluginConfiguration;

    @Override
    public void onEnable() {
        instance = this;
        this.configurationFactory = new ConfigurationFactory(this.getDataFolder());
        this.pluginConfiguration =
                this.configurationFactory.produceConfig(PluginConfiguration.class, "config.yml",
                        new DatabaseSerializer(), new NatsSerializer());
        DailyRewardConfiguration dailyRewardConfiguration =
                this.configurationFactory.produceConfig(DailyRewardConfiguration.class, "reward.yml",
                        new DailyRewardSerializer());
        WarpConfiguration warpConfiguration =
                this.configurationFactory.produceConfig(WarpConfiguration.class, "warps.yml",
                new WarpSerializer());
        CommandConfiguration commandConfiguration =
                this.configurationFactory.produceConfig(CommandConfiguration.class, "commands.yml",
                        new SellItemSerializer());
        KitConfiguration kitConfiguration =
                this.configurationFactory.produceConfig(KitConfiguration.class, "kits.yml",
                        new KitSerializer(),  new KitItemSerializer(), new KitItemEnchantSerializer());
        this.userService = new UserService(dailyRewardConfiguration, kitConfiguration);
        this.databaseProvider = new DatabaseProvider(pluginConfiguration, userService);
        DailyRewardService dailyRewardService = new DailyRewardService(dailyRewardConfiguration);
        this.initializeNats( "messenger_response",
                "wondermc_discord", "wondermc_spigot");
        CommandFramework commandFramework = new CommandFramework(this);
        commandFramework.registerCommands(
                new HelpopCommand(userService, pluginConfiguration, natsMessenger),
                new DailyRewardCommand(userService, dailyRewardConfiguration, dailyRewardService, databaseProvider),
                new SetWarpCommand(warpConfiguration),
                new WarpCommand(warpConfiguration),
                new SpawnCommand(pluginConfiguration),
                new HelpCommand(commandConfiguration),
                new VipCommand(commandConfiguration),
                new SvipCommand(commandConfiguration),
                new ElitaCommand(commandConfiguration),
                new WonderCommand(commandConfiguration),
                new RewardCommand(natsMessenger, pluginConfiguration, userService),
                new KitCommand(kitConfiguration, userService, databaseProvider),
                new FlyCommand(commandConfiguration),
                new HealCommand(commandConfiguration),
                new FeedCommand(commandConfiguration),
                new GodCommand(commandConfiguration, userService),
                new VanishCommand(commandConfiguration, userService),
                new SellCommand(commandConfiguration),
                new ReplyCommand(userService, commandConfiguration),
                new MsgCommand(userService, commandConfiguration),
                new SocialSpyCommand(commandConfiguration, userService),
                new EnderchestCommand(commandConfiguration),
                new InvseeCommand(commandConfiguration),
                new TeleportRequestCommand(commandConfiguration, userService),
                new TeleportAcceptCommand(commandConfiguration, userService),
                new WorkBenchCommand(),
                new TpCommand(commandConfiguration),
                new IgnoreCommand(userService, commandConfiguration),
                new TpHereCommand(commandConfiguration),
                new GamemodeCommand(commandConfiguration));
        this.registerListeners(
                new AsyncPlayerPreLoginListener(userService),
                new PlayerJoinListener(pluginConfiguration, kitConfiguration, userService, dailyRewardConfiguration),
                new InventoryOpenListener(pluginConfiguration, userService),
                new PlayerQuitListener(userService, dailyRewardConfiguration, databaseProvider, pluginConfiguration),
                new PlayerRespawnListener(kitConfiguration, userService),
                new EntityDamageByEntityListener(userService, pluginConfiguration),
                new BlockBreakListener(pluginConfiguration),
                new EntityDamageListener(userService),
                new PlayerDeathListener(userService, pluginConfiguration),
                new PlayerCommandPreProcessListener(userService, pluginConfiguration),
                new PlayerMoveListener(userService, pluginConfiguration)
        );
        Bukkit.getScheduler().runTaskTimer(this,
                new UserBarTask(userService), 1L, 20L);
        Bukkit.getScheduler().runTaskTimer(this,
                new UserFightTask(userService, pluginConfiguration), 0L, 20L);
        Bukkit.getScheduler().runTaskTimer(this,
                new InformationTask(userService, commandConfiguration), 1L, 20L);

    }

    @Override
    public void onDisable() {
        for(User user : this.userService.getUserMap().values()){
            UserFight userFight = user.getUserFight();
            userFight.setLastFight(0L);
            userFight.setLastAttacker(null);
            this.databaseProvider.saveUser(user);
        }
    }


    public static BukkitMain getInstance() {
        return instance;
    }

    private void registerListeners(final Listener... listeners){
        PluginManager pluginManager = Bukkit.getPluginManager();
        Arrays.stream(listeners).forEach(listener -> pluginManager.registerEvents(listener, this));
    }

    public void initializeNats(String... listenChannels){
        Nats nats = this.pluginConfiguration.getNatsConnector();
        this.natsMessenger = new NatsMessenger(nats.getHostname(), nats.getPort(),nats.getPassword(),
                "pl.wondermc.boxpvp.nats", listenChannels);
    }

    public NatsMessenger getNatsMessenger() {
        return natsMessenger;
    }

    public UserService getUserService() {
        return userService;
    }


}
