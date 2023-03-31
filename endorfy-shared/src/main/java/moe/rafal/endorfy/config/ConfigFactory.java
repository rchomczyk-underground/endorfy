package moe.rafal.endorfy.config;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.configurer.Configurer;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;

import java.io.File;
import java.util.function.Supplier;

public class ConfigFactory {

    private final File dataFile;
    private final Supplier<Configurer> configurerSupplier;

    public ConfigFactory(File dataFile, Supplier<Configurer> configurerSupplier) {
        this.dataFile = dataFile;
        this.configurerSupplier = configurerSupplier;
    }

    public <T extends OkaeriConfig> T produceConfig(Class<T> configClass, String configFileName, OkaeriSerdesPack... serdesPacks) {
        return produceConfig(configClass, new File(dataFile, configFileName), serdesPacks);
    }

    public <T extends OkaeriConfig> T produceConfig(Class<T> configClass, File configFile, OkaeriSerdesPack... serdesPacks) {
        return ConfigManager.create(configClass, initializer -> initializer
            .withConfigurer(configurerSupplier.get(), serdesPacks)
            .withBindFile(configFile)
            .saveDefaults()
            .load(true));
    }
}
