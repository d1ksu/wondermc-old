package pl.wondermc.discordbot.configuration.factory;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.yaml.snakeyaml.YamlSnakeYamlConfigurer;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class ConfigurationFactory {

    private final File dataFile;

    public ConfigurationFactory(File dataFile) {
        this.dataFile = dataFile;
    }

    public <T extends OkaeriConfig> T produceConfig(@NotNull Class<T> clazz, @NotNull String fileName, @NotNull ObjectSerializer<?>... serializers) {
        return this.produceConfig(clazz, new File(this.dataFile, fileName), serializers);
    }

    public <T extends OkaeriConfig> T produceConfig(@NotNull Class<T> clazz, @NotNull File file, @NotNull ObjectSerializer<?>... serializers) {
        return ConfigManager.create(clazz, initializer -> initializer
                .withConfigurer(new YamlSnakeYamlConfigurer(), registry -> {
                    for (@NotNull ObjectSerializer<?> serializer : serializers) {
                        registry.register(serializer);
                    }
                })
                .withBindFile(file)
                .saveDefaults()
                .load(true));
    }
}
